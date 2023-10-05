package dininghall.asstpackages.ExcelPackage;


import com.ocpsoft.pretty.faces.util.StringUtils;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelDbDistinct;
import dininghall.asstpackages.ExcelPackage.Annatations.AnnotationFind;
import dininghall.asstpackages.ExcelPackage.CreatorFolder.CreateExcel;
import dininghall.asstpackages.ExcelPackage.Excel.ExcelModel;
import dininghall.asstpackages.ExcelPackage.Models.DbDistinct;
import dininghall.asstpackages.ExcelPackage.Models.ExcelOutData;
import dininghall.asstpackages.ExcelPackage.Models.ExcelReadOut;
import dininghall.domain.survey.models.testModel;
import dininghall.generic.Manager.PropertyManager;
import dininghall.generic.Service.DataService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelLanguageReader implements Serializable {
    // TODO: 3/1/2023 her veririn tüm bilgileri girilcek Excelmodel list halinde döndürülmeye  uygun olacak
    Map<String, Map<String, String>> langmap;
    ExcelOutData output_data = new ExcelOutData();
    private boolean addDatabase = false;

    ArrayList<ExcelReadOut> errorModels = new ArrayList<>();
    ArrayList<ExcelReadOut> updateModels = new ArrayList<>();
    ArrayList<ExcelReadOut> addedModels = new ArrayList<>();

    //class bilgisi alınacak , dosya yolu alınacak
    //addDatabase otomatik ekleme yapar

    /**
     * eğer verilerin döndürülmesi  istenirse  addDatabase=false olarak ayarlayınız bu işlem verilerin dönmesini engeller
     */
    public ExcelOutData ExcelReader(InputStream stream, String clazz_name) {
        return ExcelReader(stream, clazz_name, true, "");
    }

    public ExcelOutData ExcelReader(InputStream stream, String clazz_name, String sheetName) {
        return ExcelReader(stream, clazz_name, true, sheetName);
    }

    public ExcelOutData ExcelReader(InputStream stream, String clazz_name, boolean addDatabase) {
        return ExcelReader(stream, clazz_name, addDatabase, "");
    }

    //çoklu sheet devredışı
    public ExcelOutData ExcelReader(InputStream stream, String clazz_name, boolean addDatabase, String sheetName) {
        boolean succes = false;
        this.addDatabase = addDatabase;
        if (!this.addDatabase)
            output_data.setDataList(new ArrayList<>());
        String className = null;
        List<List<ExcelModel>> model_big = new ArrayList<>();
        langmap = new HashMap<>();
        List<String> tableNames = new ArrayList<>();
        tableNames.add("Sample".toLowerCase(Locale.ENGLISH));
        try {

            //   FileInputStream file = new FileInputStream(new File("C:\\Users\\Askin\\Desktop\\test.xlsx"));

            XSSFWorkbook workbook = null;

            try {
                workbook = new XSSFWorkbook(stream);

            } catch (Exception e) {
                errorModels.add(new ExcelReadOut("Hata", -1));
                ExcelOutData data = new ExcelOutData();
                data.setErrors(errorModels);
                data.setUpdate(new ArrayList<>());
                data.setAdd(new ArrayList<>());
                return data;
            }

            Map<String, String> stringMap = new HashMap<>();
            Object test = null;
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Sheet> sheets = workbook.sheetIterator();
            //Sheet sheets=workbook.getSheetAt(0);
            //sheet name ile ilgili arama yapılacak
            //veriler excelmodele aktarılacak
            //db'ye aktarılacak
            // tableNames.add(sheetName);
            while (sheets.hasNext()) {
                Sheet sh = sheets.next();
                String str = sh.getSheetName();
                //listeyi gizler
                if (str.equalsIgnoreCase("listsheet"))
                    continue;
                className = clazz_name;

                System.out.println("Sheet name is " + sheetName);
                System.out.println("---------");
                Class clazz = null;
                if (sheetName.length() == 0) {
                    clazz = getObj(sh.getSheetName());
                    if (clazz == null)
                        continue;
                    if (!className.equalsIgnoreCase(clazz_name)) {
                        errorModels.add(new ExcelReadOut("Farklı bir excel verisi girdiniz girdiğiniz veri " + clazz.getSimpleName() + "\'e ait", -1));
                        ExcelOutData data = new ExcelOutData();
                        data.setErrors(errorModels);
                        data.setUpdate(new ArrayList<>());
                        data.setAdd(new ArrayList<>());

                        return data;

                    }
                } else {
                    if (!sh.getSheetName().equalsIgnoreCase(sheetName))
                        continue;
                    clazz = getObj(clazz_name);

                    if (clazz == null) {
                        errorModels.add(new ExcelReadOut("Sayfa Adı Geçersiz " + sheetName + "\'e ait", -1));
                        ExcelOutData data = new ExcelOutData();
                        data.setErrors(errorModels);
                        data.setUpdate(new ArrayList<>());
                        data.setAdd(new ArrayList<>());
                        continue;
                    }


                }
                if (sheetName.length() > 0 && !sh.getSheetName().equals(sheetName))
                {
                    errorModels.add(new ExcelReadOut("Farklı Bir Yere Ekleme Yapıyorsunuz Ekleme yapmak istediğiniz "+sheetName+ " fakat siz  "+sh.getSheetName()+ "\'e ekleme yapıyorsunuz", -1));

                    continue;
                }
                Object obj = clazz.newInstance();

                AExcel aExcel = AnnotationFind.findAExcel(obj);
                if (aExcel == null) {
                    continue;


                }
                succes = true;
                Iterator<Row> iterator = sh.iterator();
                iterator.next();
                while (iterator.hasNext()) {


                    Row row = iterator.next();
                    if (isEmptyRow(row))
                        continue;
                    int i = row.getRowNum();
                    List<ExcelModel> models = new ArrayList<>();
                    Iterator<Cell> cellIterator = row.iterator();
                    int c = -1;
                    boolean error = false;
                    String temp_ = "";
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        c++;
                        if (c != cell.getColumnIndex())
                            c = cell.getColumnIndex();
                        if (cell == null)
                            continue;
                        ExcelModel model = new ExcelModel();
                        model.setDisplayName(sh.getRow(0).getCell(cell.getColumnIndex()) + "");
                        model.setColumnName(sh.getRow(0).getCell(cell.getColumnIndex()) + "");
                        model.setRowNumber(i + 1);
                        model.setColNumber(cell.getColumnIndex());
                        if (aExcel.max() < cell.getColumnIndex())
                            continue;
                        if (temp_ != "") {
                            model.setIndirectValue(temp_);
                            temp_ = "";
                        }
                        AExcelColumn column = AnnotationFind.findAExcelColumnFindColumnName(obj, model.getColumnName());
                        if (column == null) {
                            String nameindirect = sh.getRow(0).getCell(c + 1).getStringCellValue();
                            AExcelColumn indirect_Test = AnnotationFind.findAExcelColumnFindColumnName(obj, nameindirect);
                            if (indirect_Test != null) {
                                temp_ = dataFormatter.formatCellValue(cell);
                                continue;

                            } else {
                                errorModels.add(new ExcelReadOut("uyumsuz veri Satır:" + cell.getRowIndex() + " Sütun: " + cell.getColumnIndex(), model.getRowNumber()));
                                error = true;
                                continue;
                            }
                        }
                        var field = AnnotationFind.findFieldNameFindColumnName(obj, model.getColumnName());

                        if (column != null) {
                            model.setAExcelColumn(column);
                            model.setFieldName(field);
                        }
                        Object title = "";
                        switch (cell.getCellType()) {
                            case STRING:
                                title = dataFormatter.formatCellValue(cell);
                                break;
                            case BOOLEAN:
                                title = dataFormatter.formatCellValue(cell);
                                break;
                            case NUMERIC:

                                title = dataFormatter.formatCellValue(cell);
                                break;

                        }
                        if (model.getAExcelColumn().cellType().equals("Date")) {
                            try {

                                Date excelDate = cell.getDateCellValue();


                                title = excelDate;


                                ///  title = new SimpleDateFormat("MM/dd/yyyy").format(javaDate);
                                /// FIXME: 10/24/2022 bu bilgisayarda hataya neden olduğu için bu şekilde ayarlandı
                                title = new SimpleDateFormat("yyyy/MM/dd").format(title);


                            } catch (Exception e) {
                                try {

                                    Date javaDate = DateUtil.getJavaDate(Double.parseDouble(title + ""));

                                    title = new SimpleDateFormat("yyyy/MM/dd").format(javaDate);

                                    ///  title = new SimpleDateFormat("MM/dd/yyyy").format(javaDate);

                                } catch (Exception ex) {
                                    error = true;
                                    errorModels.add(new ExcelReadOut("Hücre tipi tarih olmalıdır!!(Sütünü Tarihe Çevirin) Hücre:Satır " + cell.getRowIndex() + ", Sutun" + cell.getColumnIndex() + " ", model.getRowNumber()));

                                }
                            }
                        }
                        if ((title == null || title.toString().length() < 1) && model.getAExcelColumn().required()) {
                            errorModels.add(new ExcelReadOut(model.getColumnName() + " Zorunlu alandır boş veya hatalı girdiniz ! " + cell.getRowIndex() + ", Sutun " + cell.getColumnIndex() + " Satır " + cell.getRowIndex(), model.getRowNumber()));
                            error = true;
                            break;
                        }
                        model.setValue(title);


                        models.add(model);
                    }
                    if (!error)
                        model_big.add(models);

                    // i++;
                }
            }

            if (model_big.size() > 0)
                addAll(className, model_big);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (!succes) {
            errorModels.add(new ExcelReadOut("Uyumsuz Şablon Lütfen Excel Şablon Adını Kontrol Ediniz ! Yada Tekrar Bir Şablon Dışarı Aktarıp İşlem Yapınız!" + clazz_name + "\'e ait", -1));
            ExcelOutData data = new ExcelOutData();
            data.setErrors(errorModels);
            data.setUpdate(new ArrayList<>());
            data.setAdd(new ArrayList<>());

        }

        output_data.setAdd(addedModels);
        output_data.setUpdate(updateModels);
        output_data.setErrors(errorModels);
        return output_data;
    }

    public Class getObj(String tableName) {
        String sqlname;
        Class t = null;
        try {
            t = Class.forName("dininghall.domain.survey.exceltemplate." + tableName);
            if (t == null)
                return null;
            return t;

        } catch (ClassNotFoundException e) {
            return null;

        }
    }

    public String addAll(String tableName, List<List<ExcelModel>> big_model) {
        String sqlname;
        Class t = null;
        try {
            t = Class.forName("dininghall.domain.survey.exceltemplate." + tableName);

            if (t == null)
                return "";
            AExcel excel = (AExcel) t.getAnnotation(AExcel.class);
            if (excel == null)
                return "";
            sqlname = excel.sqlTableName();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        startOperation(big_model, t);
        return "";
    }

    private void startOperation(List<List<ExcelModel>> big_model, Class clazz) {


        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        int maxSize = 0;
        int indexMaxSizeItem = 0;
        int index_counter = 0;
        for (var item : big_model) {
            if (item.size() > maxSize) {
                maxSize = item.size();
                indexMaxSizeItem = index_counter;
            }
            index_counter++;
        }


        Map<String, String> data = AnnotationFind.getAnnotationColumnNameAndFieldName(obj);
        String tableName = AnnotationFind.findAExcel(obj).sqlTableName();
        for (var mod : big_model) {
            for (var t : mod) {
                AExcelColumn column = AnnotationFind.findAExcelColumnFindColumnName(obj, t.getColumnName());
                if (column != null) {
                    t.setAExcelColumn(column);
                    if (column.columnKey().length() > 0)
                        t.setFieldName(column.columnKey());
                }
            }
        }

        CreateExcel createExcel = new CreateExcel();
        var excelDbMap = createExcel.dbListToExcelList(big_model.get(indexMaxSizeItem), obj, obj.getClass().getSimpleName());
        Map<String, List<DbDistinct>> listMap = createExcel.getCategoryItemsGlobal();
        Locale.setDefault(Locale.ENGLISH);
        String primaryValue = DataService.getAnnationToFieldPrimaryFieldName(obj.getClass());
        ExcelModel first = new ExcelModel();
        for (List<ExcelModel> t : big_model) {
            boolean error = false;
            String syntax1 = "";
            String syntax2 = "";
            String errorSyntax = "";

            int i = -1;
            Map<String, testModel> dbData = new HashMap<>();
            for (var model : t) {
                i++;
                model.setAExcelColumn(big_model.get(indexMaxSizeItem).get(i).getAExcelColumn());
                if (model.getAExcelColumn() != null) {
                    AExcelDbDistinct excelDbDistinct = AnnotationFind.getAnnotationExcelDistWithColumnKey(obj, model.getAExcelColumn().columnKey());
                    if (excelDbDistinct != null)
                        model.setaExcelDbDistinct(excelDbDistinct);
                }
                if (model.getValue() == "")
                    continue;
                if (primaryValue.equals(model.getFieldName())) {
                    continue;
                }
                String value = "";
                String key = "";
                if (model.getAExcelColumn().required() && (model.getValue() == null || model.getValue().toString().length() == 0)) {
                    error = true;
                    errorSyntax += model.getColumnName() + ",";
                    continue;
                }
                if (error == true)
                    continue;
                boolean brek = false;
                if (model.getaExcelDbDistinct() != null && model.getaExcelDbDistinct().indirect()) {
                    int a = 0;
                    a = a;
                    var prevList = excelDbMap.get(model.getAExcelColumn().Indirect());
                    if (prevList.get(model.getIndirectValue()) != null) {
                        for (var i_ : prevList.get(model.getIndirectValue())) {
                            if (model.getValue().equals(i_.getValue()) || model.getValue().equals(i_.getIndirectName())) {
                                int sda = 0;
                                sda = 0;
                                testModel mdl = new testModel();
                                mdl.setKey(i_.getKey());
                                mdl.setValue(i_.getValue());
                                mdl.setColumnKey(model.getFieldName());
                                mdl.setRowNum(model.getRowNumber());
                                mdl.setColName(model.getColumnName());
                                dbData.put(model.getFieldName(), mdl);

                                brek = true;
                                break;
                            }
                        }
                    }
                    if (brek)
                        continue;
                    error = true;
                    errorSyntax = "[" + model.getRowNumber() + "," + model.getColNumber() + "]" + "Girdiğiniz dolaylı veri eşleşmesi gerçekleştirelememiştir bilginiz olsun Şu satırdaki  : " + model.getRowNumber() + " Şu Sütunu inceleyin : " + model.getColumnName() + "  ,";
                    System.out.println(errorSyntax);
                    break;
                } else if (data.get(model.getColumnName()) != null) {

                    AExcelDbDistinct distinct = AnnotationFind.getAnnotationExcelDistWithColumnKey(obj, model.getFieldName());

                    if (distinct != null) {
                        boolean te_mp = false;
                        key = model.getFieldName();
                        String key_map = distinct.databaseReferanceColumnName();
                        var listt = listMap.get(key_map);
                        if (listt == null) {
                            error = true;
                            errorSyntax = "Veritabanında Liste bulunamadı " + distinct.databaseTableName();
                            continue;
                        }
                        for (var disc : listt) {
                            if (disc.getValue().equalsIgnoreCase(model.getValue().toString())) {
                                value = disc.getKey().toString();
                                te_mp = true;
                                break;
                            }
                        }
                        if (!te_mp) {
                            error = true;
                            errorSyntax = "Veritabanında Veri bulunamadı " + distinct.databaseTableName();
                            continue;
                        }

                    }

                }
                if (value.length() == 0) {
                    AExcelColumn aExcelColumn = model.getAExcelColumn();
                    String[] values_ = aExcelColumn.getlist();
                    for (String str_ : values_) {
                        String st = str_.split(":::")[0];
                        if (st.split(":::")[0].equals(model.getValue())) {
                            value = str_.split(":::")[1];
                            break;
                        }

                    }
                    if (value.length() == 0)
                        value = model.getValue() + "";


                    if (value.length() == 0)
                        value = "\'\'";
                    if (key.length() == 0)

                        key = model.getFieldName();

                }

                // TODO: 3/6/2023 her bir verinin satır ve sutün bilgisi ile çekimi sağlanacak
                testModel mdl = new testModel();
                mdl.setKey(key);
                mdl.setValue(value);
                mdl.setColumnKey(model.getFieldName());
                mdl.setRowNum(model.getRowNumber());
                mdl.setColName(model.getColumnName());
                dbData.put(model.getFieldName(), mdl);


               /* syntax1 += "\'" + key.replace("\'", "\'\'") + "\' ,";
                syntax2 += " \'" + value.replace("\'", "\'\'") + "\' ,";*/

            }
            if (!this.addDatabase) {
                /**verileri sisteme özel olarak eklemek için kullanılır*/
                output_data.getDataList().add(dbData);
                continue;
            }
            if (dbData.size() > 0 && !error) {
                try {
                    dbData.putAll(getValue(obj));
                    Object a = PropertyManager.InsertCommandWithASqlTableAndAExcel(dbData, tableName, obj.getClass());

                    if (a instanceof String) {
                        String data1 = "Veritabanına ekleme başarısız :";
                        if (a.toString().contains("Detail:")) {

                            a = a.toString().split("Detail:")[1].replace("Detail", "Detay");

                        }
                        if (a.toString().contains("already exists")) {

                            a = a.toString().replace("already exists", "veritabanında mevcut.");

                        }
                        if (a.toString().contains("duplicate key value violates unique constraint")) {
                            a = a.toString().replace("duplicate key value violates unique constraint", "Bu bilgi bulunmaktadır.(Güncelleme Aktif Değil)");

                        }
                        errorModels.add(new ExcelReadOut(data1 + "" + a, t.get(0).getRowNumber()));

                    } else {
                        addedModels.add(new ExcelReadOut("Eklendi", t.get(0).getRowNumber()));
                    }
                } catch (Exception e) {
                    errorModels.add(new ExcelReadOut("Satırda(****) istisna Var " + e.getMessage(), t.get(0).getRowNumber()));
                }/* syntax1 = "insert into  + tableName + ( " + syntax1.substring(0, syntax1.length() - 1) + ") values (" + syntax2.substring(0, syntax2.length() - 1) + ")";
                 */
            } else if (error == true)
                errorModels.add(new ExcelReadOut("Zorunlu alan girilmedi (" + errorSyntax.substring(0, errorSyntax.length() - 1) + ") ", t.get(0).getRowNumber()));
            else
                errorModels.add(new ExcelReadOut("Satırda istisna Var 1 ", t.get(0).getRowNumber()));

            syntax1 = syntax1;
            //ekle hata varsa erromodele
            //güncelleme istiyorsa updatemodele
// başarılı ise aa ya

        }

    }

    private Map<String, testModel> getValue(Object clazz) {
        Map<String, String> methods = AnnotationFind.getAExcelMethodMap(clazz);
        Map<String, testModel> keyAndValues = new HashMap<>();
        for (var entry : methods.entrySet()) {
            if (entry.getValue().equalsIgnoreCase("getNowTime")) {
                var mdl = new testModel();
                mdl.setValue(getDateTimeNow());
                mdl.setColumnKey(entry.getKey());
                mdl.setKey(entry.getKey());
                keyAndValues.put(entry.getKey(), mdl);
            }
        }
        return keyAndValues;
    }

    private String getDateTimeNow() {

        return new SimpleDateFormat("dd/MM/yyyy").format(LocaleUtil.getLocaleCalendar().getTime());
        //  return new SimpleDateFormat("MM/dd/yyyy").format(LocaleUtil.getLocaleCalendar().getTime());

    }


    boolean isEmptyRow(Row row) {
        boolean isEmptyRow = true;
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                isEmptyRow = false;
            }
        }
        return isEmptyRow;
    }


}

