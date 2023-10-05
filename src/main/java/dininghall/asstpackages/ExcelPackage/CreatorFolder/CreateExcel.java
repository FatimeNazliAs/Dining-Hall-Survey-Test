package dininghall.asstpackages.ExcelPackage.CreatorFolder;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AnnotationFind;
import dininghall.asstpackages.ExcelPackage.Excel.ExcelModel;
import dininghall.asstpackages.ExcelPackage.Excel.SeparatorExcel;
import dininghall.asstpackages.ExcelPackage.Models.DbDistinct;
import dininghall.asstpackages.ExcelPackage.Services.DataService;
import dininghall.generic.Manager.PostgreSqlManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static dininghall.asstpackages.ExcelPackage.CreatorFolder.CreateMethods.setCellValidation;

public class CreateExcel implements Serializable {
    Map<String, Map<String, List<DbDistinct>>> itemsIndirect = new HashMap<>();
    Map<String, Map<String, Object>> propKeyValue = new HashMap<>();
    Map<String, List<String>> categoryItems = new HashMap<>();
    Map<String, List<DbDistinct>> categoryItemsGlobal = new HashMap<>();

    Map<String, List<DbDistinct>> datas = new HashMap<>();

    public Map<String, List<DbDistinct>> getCategoryItemsGlobal() {
        return categoryItemsGlobal;
    }

    public void setItemsIndirect(Map<String, Map<String, List<DbDistinct>>> itemsIndirect) {
        this.itemsIndirect = itemsIndirect;
    }

    public void setPropKeyValue(Map<String, Map<String, Object>> propKeyValue) {
        this.propKeyValue = propKeyValue;
    }

    private static final String desktopPath = "C:\\Users\\Askin\\Desktop\\";

    public void setColumnName(Row row, Sheet sheet, int colNum, String columnName, String cellType, Workbook workbook, CellStyle cellStyle) {
        Cell cell = row.createCell(colNum);
        cell.setCellValue(columnName);


        //     cellStyle.setDataFormat(fmt.getFormat("@"));
        //   cell.setCellValue((Date) value_);
        CreationHelper createHelper = workbook.getCreationHelper();
        if (cellType.equalsIgnoreCase("Long"))
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0"));
        if (cellType.equalsIgnoreCase("Integer"))
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0"));
        else if (cellType.equalsIgnoreCase("Date"))
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy;@"));
        else if (cellType.equalsIgnoreCase("String") || cellType.equalsIgnoreCase("Boolean")) {

            //    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("@"));

        }

        sheet.setDefaultColumnStyle(colNum++, cellStyle);
        //   cell.setCellStyle(cellStyle);
    }

    public <T> int Create(List<T> list, boolean removeFirst) {
        return Create(list, removeFirst, false);
    }

    public <T> int Create(List<T> list) {
        return Create(list, false, false);
    }

    public <T> int Create(List<T> list, boolean removeFirst, boolean multi) {
        if (list == null || list.isEmpty())
            return -1;
        var workbook = new XSSFWorkbook();

        AExcel obj = AnnotationFind.findAExcel(list.get(0));
        if (obj == null)
            return -1;
        ArrayList<List<ExcelModel>> map_row = new ArrayList<>();

        for (var item : list) {
            List<ExcelModel> data_temp = DataService.getData(item);
            if (data_temp != null) {
                map_row.add(data_temp);
            }
        }

        getListData(workbook, map_row.get(0), list.get(0), obj.sqlTableName());

        List<String> columnNames = DataService.getColumnNames(list.get(0));

        int rowNum = 0;
        System.out.println("Creating excel");
        var sheet = workbook.createSheet(list.get(0).getClass().getSimpleName());
        int row_ = 0;
        int colNum = 0;
        int maxCol = columnNames.size();
        int maxRow = map_row.size();
        int t_ = 0;

        var row = sheet.createRow(rowNum++);
        boolean ind = false;
        for (int i = 0; i < map_row.get(0).size(); i++) {
            var datatype = map_row.get(0).get(i);
            String cellType = datatype.getAExcelColumn().cellType();
            CellStyle cellStyle = workbook.createCellStyle();
            String columnName = datatype.getColumnName();
            boolean indirect = datatype.getaExcelDbDistinct() != null && datatype.getaExcelDbDistinct().indirect();
            if (indirect) {
                if (!ind) {
                    columnName = datatype.getaExcelDbDistinct().indirectTableName();
                    if (datatype.getaExcelDbDistinct().distincColumnHeader().length()>0)
                        columnName=datatype.getaExcelDbDistinct().distincColumnHeader();
                    ind = true;
                    i--;
                } else
                    ind = false;


            }
            setColumnName(row, sheet, colNum++, columnName, cellType, workbook, cellStyle);

        }
//FOR START
        Map<String, Integer> sameDataMap = new HashMap<>();
Map<String ,List<Integer>> cellIndirects=new HashMap<>();
        for (int i = 0; i < maxRow; i++) {

            row = sheet.createRow(rowNum++);
            boolean indirect = false;
            int size_ = 0;
            for (int j = 0; j < columnNames.size(); j++) {
                var s = map_row.get(i).get(j);

                var value_ = map_row.get(i).get(j).getValue();
                var cell = row.createCell(j + size_);
                boolean next = false;

                if (map_row.get(0).get(j).getaExcelDbDistinct() != null && map_row.get(0).get(j).getaExcelDbDistinct().indirect()) {
                    indirect = true;

                    var items_ = itemsIndirect.get(s.getAExcelColumn().Indirect());
                    for (var entry : items_.entrySet()) {
                        for (var item : entry.getValue()) {
                            if (item.getValue().equals(s.getValue().toString())) {
                                if (map_row.get(0).get(j).getaExcelDbDistinct().sameData()) {
                                    if (sameDataMap.get(item.getIndirectKey()) == null) {
                                        sameDataMap.put(item.getIndirectKey(), j + size_);
                                    }
                                }
                                cell.getCellStyle().setLocked(true);
                                sheet.getColumnStyle(j + size_).setLocked(true);
                                if (map_row.get(i).get(j).getAExcelColumn().hide())
                                 sheet.setColumnWidth(j + size_, 0);
                                else
                                    sheet.setColumnWidth(j + size_ , 6900);
                                CreateMethods.setValue(item.getIndirectKey(), "String", cell, s, workbook);
                                value_ = item.getValue();
                                cell = row.createCell(j + size_ + 1);
                               if (cellIndirects.get(item.getIndirectKey())==null)
                                   cellIndirects.put(item.getIndirectKey(),new ArrayList<>());
                                cellIndirects.get(item.getIndirectKey()).add(j + size_ );
                                CreateMethods.setValue(value_, "String", cell, s, workbook);
                                sheet.getColumnStyle(j + size_ + 1).setLocked(false);
                                next = true;
                                sheet.setColumnWidth(j + size_ + 1, 6900);
                                setCellValidation(sheet, map_row.get(i).get(j), i + 1, j + size_);
                                indirect = true;

                                break;
                            }

                            if (next)
                                break;
                        }
                        if (next)
                            break;
                    }
                    size_++;
                    continue;


                }


                // cell = row.createCell(j+size_);

                setCellValidation(sheet, map_row.get(i).get(j), i + 1, j + size_);
                sheet.getColumnStyle(j + size_).setLocked(false);

                String cellType = map_row.get(i).get(j).getAExcelColumn().cellType();

                //  CellStyle cellStyle = workbook.createCellStyle();

                //  DataFormat fmt = workbook.createDataFormat();
                //  cellStyle.setDataFormat(fmt.getFormat("@"));
                sheet.setColumnWidth(j + size_, 6900);


                if (value_ == null) {
                    continue;
                }

                if (propKeyValue.get(s.getFieldName()) != null) {
                    if (s.getAExcelColumn().database()) {
                        value_ = propKeyValue.get(s.getFieldName()).get(value_ + "");
                        if (value_ == null) {
                            value_ = s.getValue();
                        }
                    } else if (s.getAExcelColumn().getlist().length > 0) {
                        var str__ = value_;
                        value_ = propKeyValue.get(s.getFieldName()).get(str__.toString() + "");

                        if (value_ == null)
                            for (var val : propKeyValue.get(s.getFieldName()).values()) {
                                if (val.toString().equalsIgnoreCase(str__.toString())) {
                                    value_ = str__;
                                    break;
                                }
                            }
                    }
                }
                CreateMethods.setValue(value_, cellType, cell, s, workbook);

                //dateCellStyle   ad(map_row,workbook,sheet,1, cell.getCellStyle());
                // cell.setCellStyle(cell.getCellStyle());


            }

        }

        for (int i = 1; i < 500; i++) {
            var rw = sheet.getRow(i);
            if (rw == null)
                rw = sheet.createRow(i);
            for (var item : cellIndirects.entrySet()) {
                for (var i_item:item.getValue()
                     ) {
                    var cl = rw.createCell(i_item);
                    cl.setCellValue(item.getKey());

                }

            }
        }
        if (removeFirst) {
            // İlk satırı al
            var row_shift = sheet.getRow(1);
            // Eğer satır null değilse sil
            if (row_shift != null) {
                sheet.removeRow(row_shift);
                // Diğer satırları bir sıra yukarı kaydır
                sheet.shiftRows(2, sheet.getLastRowNum(), -1);
            }
        }

        workbook.setSheetHidden(0, true);
        //set Sheet1 active
        workbook.setActiveSheet(1);
        String desktopPath = System.getProperty("user.home") + "\\Desktop\\";
        String excelFilePath = desktopPath + "sample.xlsx";
        try {
            OutputStream fileOut = new FileOutputStream(excelFilePath);

            workbook.write(fileOut);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String filePath = null;

        try {
            Path temp = Files.createTempFile("", ".xlsx");
            FileOutputStream outputStream = new FileOutputStream(temp.toFile().getAbsolutePath());
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return CreateMethods.download(temp.toFile().getAbsolutePath(),
                    obj.sqlTableName());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");

        return -1;
    }


    public <T> Map<String, List<DbDistinct>> getListDb(List<ExcelModel> models, T clazz) {
        this.categoryItemsGlobal = new HashMap<>();


        String syntax = "";

        boolean first = true;
        for (var model : models) {


            if (model.getAExcelColumn().database()) {
                String str = CreateMethods.getListDbStringColumnKey(model.getAExcelColumn().columnKey(), clazz, model);
                if (str != null) {
                    if (!first)
                        syntax += " UNION ";
                    syntax += str;
                    first = false;
                }
            }


        }
        List<DbDistinct> list2 = new ArrayList();
        if (syntax.length() > 0) {

            list2 = PostgreSqlManager.Read(new DbDistinct(), syntax);
            list2.sort(Comparator.comparing(DbDistinct::getProperty));
        }

        /*
         if (list != null && list.length > 0)
                categoryItems.put(model.getListTitle(), list);
        * */


        while (0 < list2.size()) {
            if (!list2.get(0).getIndirectName().equalsIgnoreCase("indirect_value")) {
                if (categoryItemsGlobal.get(list2.get(0).getIndirectKey()) != null) {


                    categoryItemsGlobal.get(list2.get(0).getIndirectKey()).add(list2.get(0));
                    list2.remove(0);
                } else {
                    var lists = new ArrayList<DbDistinct>();
                    categoryItemsGlobal.put(list2.get(0).getIndirectKey(), lists);
                }
            } else if (categoryItemsGlobal.get(list2.get(0).getProperty()) != null) {


                categoryItemsGlobal.get(list2.get(0).getProperty()).add(list2.get(0));
                list2.remove(0);
            } else {
                var lists = new ArrayList<DbDistinct>();
                categoryItemsGlobal.put(list2.get(0).getProperty(), lists);
            }

        }

        return this.categoryItemsGlobal;
    }

    public <T> Map<String, Map<String, List<DbDistinct>>> dbListToExcelList(List<ExcelModel> models, T clazz, String tableName) {
        datas = getListDb(models, clazz);

        for (var model : models) {

            List<String> list = new ArrayList<>();
            if (model.getAExcelColumn().database() && model.getaExcelDbDistinct() != null) {
                String property = "";
                List<String> list_temp = new ArrayList<>();
                var listt = datas.get(model.getaExcelDbDistinct().databaseReferanceColumnName());
                if (listt != null) {
                    for (var ob : listt) {
                        list_temp.add(ob.getValue());
                        model.addKeyValues(ob.getKey().toString(), ob.getValue());

                    }
                }
                if (model.getaExcelDbDistinct().indirect()) {

                    Map<String, List<DbDistinct>> inMap = new HashMap<>();
                    for (var dt : datas.entrySet()) {
                        var indirectlist = new ArrayList<DbDistinct>();
                        for (var st : dt.getValue()) {
                            if (st.getProperty().equalsIgnoreCase(model.getaExcelDbDistinct().databaseReferanceColumnName())) {

                                indirectlist.add(st);

                            }
                        }
                        if (indirectlist.size() > 0)
                            inMap.put(dt.getKey(), indirectlist);

                    }
                    itemsIndirect.put(model.getAExcelColumn().Indirect(), inMap);
                }
                if (list_temp.size() > 0) {
                    if (model.getaExcelDbDistinct().indirect()) {
                        Map<String, List<DbDistinct>> dis = new HashMap<>();
                        dis.put(model.getaExcelDbDistinct().indirectKey(), datas.get(model.getaExcelDbDistinct().indirectKey()));
                        model.setIndirectList(dis);
                        categoryItems.put(model.getListTitle(), list_temp);
                    } else
                        categoryItems.put(model.getListTitle(), list_temp);
                }
            } else {
                list = Arrays.asList(model.getAExcelColumn().getlist());
                for (var str : list) {
                    model.addKeyValues(str.split(SeparatorExcel.TreeSeperator)[1], str.split(SeparatorExcel.TreeSeperator)[0]);

                }
            }
            if (list != null && list.size() > 0) {
                var a = AnnotationFind.getAnnotationExcelDist(clazz, model.getFieldName());
                if (a != null && model.getaExcelDbDistinct().indirect()) {
                    categoryItems.put(model.getListTitle(), list);

                } else
                    categoryItems.put(model.getListTitle(), list);

            }
            if (model.getKeyValues() != null && model.getKeyValues().size() > 0)
                propKeyValue.put(model.getFieldName(), model.getKeyValues());
        }

        return itemsIndirect;
    }


    private <T> Sheet getListData(Workbook workbook, List<ExcelModel> models, T clazz, String tableName) {
        Sheet sheet = workbook.createSheet("ListSheet");

        this.dbListToExcelList(models, clazz, tableName);

        Row row;
        Name namedRange;
        String colLetter = null;
        String reference;

        int c = 0;
        //put the data in
        for (String key : categoryItems.keySet()) {
            int r = 0;
            row = sheet.getRow(r);
            if (row == null)
                row = sheet.createRow(r);
            r++;
            row.createCell(c).setCellValue(key);
            List<String> items = categoryItems.get(key);
            for (String item : items) {
                row = sheet.getRow(r);
                if (row == null) row = sheet.createRow(r);
                r++;

                row.createCell(c).setCellValue(item.contains(SeparatorExcel.TreeSeperator) ? item.split(SeparatorExcel.TreeSeperator)[0] : item);
            }

            //create names for the item list constraints, each named from the current key***
            colLetter = CellReference.convertNumToColString(c);
            namedRange = workbook.createName();
            namedRange.setNameName(key);
            reference = "ListSheet!$" + colLetter + "$2:$" + colLetter + "$" + r;
            namedRange.setRefersToFormula(reference);
            c++;
        }
        int i = 0;
        for (var indirect : itemsIndirect.entrySet()) {
            int c_ = c;

            for (var entry : indirect.getValue().entrySet()) {
                int r = 0;
                row = sheet.getRow(r);
                if (row == null) row = sheet.createRow(r);
                r++;
                row.createCell(c).setCellValue(entry.getKey());

                for (var item : entry.getValue()) {
                    row = sheet.getRow(r);
                    if (row == null) row = sheet.createRow(r);
                    r++;
                    row.createCell(c).setCellValue(item.getValue());
                }
                //create names for the item list constraints, each named from the current key
                colLetter = CellReference.convertNumToColString(c);
                namedRange = workbook.createName();
                namedRange.setNameName(entry.getKey().replace(" ", ""));

                reference = "ListSheet!$" + colLetter + "$2:$" + colLetter + "$" + r;
                namedRange.setRefersToFormula(reference);
                c++;
            }
            i++;
            colLetter = CellReference.convertNumToColString((c - 1));
            String temp = CellReference.convertNumToColString((c_));
            namedRange = workbook.createName();
            namedRange.setNameName(indirect.getKey());
            reference = "ListSheet!$" + temp + "$1:$" + colLetter + "$1";//ListSheet!$A$1:$R$1
            namedRange.setRefersToFormula(reference);
        }

        sheet.setSelected(false);
        return sheet;
    }


}
