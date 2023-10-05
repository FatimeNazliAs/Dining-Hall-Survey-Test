package dininghall.domain.survey.exceltemplate;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;
import java.util.Date;

@AExcel(updateId = "", sheetName = "Sheet1", sqlTableName = "",max = 10)
@ASqlTable(TableName = "")
@Data
public class ExcelMonthFoodListModelTemplate implements Serializable, ISqlTable
{

    @AExcelColumn(cellType = "Date", required = true, displayName = "Tarih", database = true, displayOrder = 1)
    private Date tarih;
    @AExcelColumn(cellType = "String", displayOrder = 2, displayName = "ÇORBALAR ")
    private String corba;
    @AExcelColumn(cellType = "Integer", displayOrder = 3, displayName = "Kalori1")

    private int kalori1;
    @AExcelColumn(cellType = "String", displayOrder = 4, displayName = "ANA YEMEK & Z.YAĞLI")

    private String ana;
    @AExcelColumn(cellType = "Integer", displayOrder = 5, displayName = "Kalori2")

    private int kalori2;
    @AExcelColumn(cellType = "String", displayOrder = 6, displayName = "ARA SICAK ")

    private String ara;
    @AExcelColumn(cellType = "Integer", displayOrder = 7, displayName = "Kalori3")

    private int kalori3;
    @AExcelColumn(cellType = "String", displayOrder = 8, displayName = "TATLI&MEYVE")

    private String tatli;
    @AExcelColumn(cellType = "Integer", displayOrder = 9, displayName = "Kalori4")

    private int kalori4;
    @AExcelColumn(cellType = "String", displayOrder = 10, displayName = "SALATA")

    private String salata;

    @AExcelColumn(cellType = "Integer", displayOrder = 11, displayName = "Kalori5")

    private int kalori5;
    @AExcelColumn(cellType = "String", displayOrder = 12, displayName = "İçecek")

    private String icecek;

    @AExcelColumn(cellType = "Integer", displayOrder = 13, displayName = "Kalori6")

    private int Kalori6;
 /*   @AExcelDbDistinct( databaseTableName = "category",databaseDisplayColumnName = "category_name",databaseReferanceColumnName = "category_id")
    @AExcelColumn(cellType = "String", displayOrder = 12, displayName = "Öğün", required = false,database = true,listTitle = "ct")
    private int category;*/
@AColumn(primary = true,name = "")
    private int aa;
}
