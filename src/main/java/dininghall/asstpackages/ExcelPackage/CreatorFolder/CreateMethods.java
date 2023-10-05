package dininghall.asstpackages.ExcelPackage.CreatorFolder;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcelDbDistinct;
import dininghall.asstpackages.ExcelPackage.Annatations.AnnotationFind;
import dininghall.asstpackages.ExcelPackage.Excel.ExcelModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.primefaces.shaded.commons.io.output.ByteArrayOutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class CreateMethods
{
    public static String discint(AExcelDbDistinct distinct)
    {
        if (!distinct.indirect())
            return "(SELECT distinct(" + distinct.databaseDisplayColumnName() + ") as value,('" + distinct.databaseReferanceColumnName() + "') as property,('indirect_key') as indirect_key,('indirect_value') as indirect_value,Cast( (" + distinct.databaseReferanceColumnName() + ") as text ) as key FROM " + distinct.databaseTableName() + " )  ";

//indirectkey=indirecy key olarak yapılıyor daha sonra geliştirilebilir
        return "select ('" + distinct.databaseReferanceColumnName() + "')\n" +
                " as property, " + distinct.indirectTableName() + "." + distinct.indirectName() +
                " as indirect_name, Cast(" + distinct.indirectTableName() + "." + distinct.indirectKey() + " as text )" +
                " as indirect_value," + distinct.databaseTableName() + "." + distinct.databaseDisplayColumnName() + " as value," +
                "Cast( " + distinct.databaseTableName() + "." + distinct.databaseReferanceColumnName() + " as text )as key from " + distinct.indirectTableName() + "," + distinct.databaseTableName() +
                " where " + distinct.indirectTableName() + "." + distinct.indirectKey() + "=" + distinct.databaseTableName() + "." + distinct.indirectKey() + " ";
    }

    public static <T> String getListDbStringColumnKey(String columnKey, T clazz, ExcelModel model)
    {
        AExcelDbDistinct distinct = AnnotationFind.getAnnotationExcelDistWithColumnKey(clazz, columnKey);
        if (distinct == null)
        {
            return null;
        }
        model.setaExcelDbDistinct(distinct);
        return discint(distinct);
    }
    public static <T> String getListDbStringFieldName(String fieldName, T clazz, ExcelModel model)
    {
        AExcelDbDistinct distinct = AnnotationFind.getAnnotationExcelDist(clazz, fieldName);
        if (distinct == null)
        {
            return null;
        }
        model.setaExcelDbDistinct(distinct);
        return discint(distinct);
    }

    protected static Cell setValue(Object value_, String cellType, Cell cell, ExcelModel s, Workbook workbook)
    {
        if (value_ == null)
        {
            value_ = "HATALI";
            //   cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
            // cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
        }

        if ((value_ + "").equalsIgnoreCase("HATALI") || cellType.equalsIgnoreCase("String") || cellType.equalsIgnoreCase("Boolean"))
        {

            // textStyle.setDataFormat();
            if ((s.getAExcelColumn().nonZero() && value_.toString().equals("0")) || (value_ + "").equalsIgnoreCase("HATALI"))
                cell.setCellValue("");

            else
                //  cell.setCellValue((String) value_);
                cell.setCellValue(new XSSFRichTextString(value_ + ""));

        } else if (cellType.equalsIgnoreCase("Long"))
        {

            if (value_ instanceof Long && Long.parseLong(value_ + "") > -1)
                cell.setCellValue(Long.valueOf(value_ + "").intValue());
        } else if (cellType.equalsIgnoreCase("Integer"))
        {
                  /*  CreationHelper createHelper = workbook.getCreationHelper();
                    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0"));*/
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();

            dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("0"));

            if (value_ instanceof Integer && Integer.parseInt(value_ + "") > -1)
                cell.setCellValue(Integer.parseInt(value_ + ""));


        } else if (cellType.equalsIgnoreCase("Date"))
        {
            if (value_ instanceof Date || value_ instanceof java.sql.Date)
            {
                cell.setCellValue((Date) value_);
                //   CreationHelper createHelper = workbook.getCreationHelper();
                // cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy;@"));

                CreationHelper creationHelper = workbook.getCreationHelper();
                CellStyle dateCellStyle = workbook.createCellStyle();

                dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));
                cell.setCellValue((Date) value_);
                cell.setCellStyle(dateCellStyle);

            } else
            {
                System.err.println("Tarih türü uyuşmazlığı boş veya geçeriz ");


            }

        }

        return cell;

    }
    protected static <T> boolean setCellValidation(Sheet sheet, ExcelModel model, int row, int col)
    {
        String ref = "";

        if (model.getaExcelDbDistinct() != null && model.getaExcelDbDistinct().indirect())
        {

            ref = model.getAExcelColumn().Indirect();

        } else if ((model.getAExcelColumn().getlist().length > 0 && model.getListTitle().length() > 0) || model.getAExcelColumn().database())
        {
            ref = model.getAExcelColumn().listTitle();
        }
        if (ref.equals(""))
            return false;
        //data validations
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        //data validation for categories in A2:
        //arama
        DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint(ref);
        sheet.autoSizeColumn(15);
        CellRangeAddressList addressList = new CellRangeAddressList(row, 1000, col, col);
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(validation);


        if (model.getaExcelDbDistinct() != null && model.getaExcelDbDistinct().indirect())
        {

            dvHelper = sheet.getDataValidationHelper();
            //data validation for categories in A2:
            //arama
            dvConstraint = dvHelper.createFormulaListConstraint(model.getAExcelColumn().Indirect());
            addressList = new CellRangeAddressList(row, 1000, col, col);
            col++;

            validation = dvHelper.createValidation(dvConstraint, addressList);
            sheet.addValidationData(validation);
            for (int i = 0; i < 1000; i++)
            {


                String colLetter = CellReference.convertNumToColString(col - 1);
                //data validation for items of the selected category in B2: bağımlı sorgu
                dvConstraint = dvHelper.createFormulaListConstraint("INDIRECT(SUBSTITUTE($" + colLetter + "$" + (row + i + 1) + ",\" \",\"\"))");

                addressList = new CellRangeAddressList(row + i, row + i, col, col);
                validation = dvHelper.createValidation(dvConstraint, addressList);
                sheet.addValidationData(validation);
            }
        }
        return true;
    }


    public static int download(String filePath, String nameTemplate)
    {
        try
        {
            boolean offline=false;
            if (offline)
            {
                OutputStream responseOutputStream = new ByteArrayOutputStream();
                InputStream fileInputStream = new FileInputStream(filePath);
                responseOutputStream.write(fileInputStream.readAllBytes());
                responseOutputStream.flush();
                responseOutputStream.close();
            } else
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-disposition", "attachment; filename=" + nameTemplate + "_template.xlsx");
                OutputStream responseOutputStream = response.getOutputStream();
                InputStream fileInputStream = new FileInputStream(filePath);
                responseOutputStream.write(fileInputStream.readAllBytes());
                responseOutputStream.flush();
                responseOutputStream.close();
                facesContext.responseComplete();
            }

        } catch (IOException e)
        {
            System.err.println("xlsx dışarı çıkarma hatası ***987987");
            return -1;
        }

        return 1;
    }

}
