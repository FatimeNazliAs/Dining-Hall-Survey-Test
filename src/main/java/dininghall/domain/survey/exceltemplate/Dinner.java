package dininghall.domain.survey.exceltemplate;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelDbDistinct;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AExcel(updateId = "", sheetName = "Sheet1", sqlTableName = "MenuListesi",max = 13)
@ASqlTable(TableName = "")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dinner implements Serializable, ISqlTable,IExcel
{

    @AExcelColumn(cellType = "Date", required = true, displayName = "Tarih", database = true, displayOrder = 1)
    private Date tarih;

    @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "ÇORBALAR ",database = true,IndirectColumn = true,Indirect = "categry")
    private String corba;
  /*  @AExcelColumn(cellType = "Integer", displayOrder = 3, displayName = "Kalori1")

    private int kalori1;*/
  @AExcelDbDistinct(sameData = true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

  @AExcelColumn(cellType = "String", displayOrder = 3,listTitle = "yemekler", displayName = "ANA YEMEK & Z.YAĞLI",database = true,IndirectColumn = true,Indirect = "categry")

    private String ana;
/*    @AExcelColumn(cellType = "Integer", displayOrder = 5, displayName = "Kalori2")

    private int kalori2;*/

    @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(cellType = "String", displayOrder = 4, displayName = "ARA SICAK ",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ara;
/*    @AExcelColumn(cellType = "Integer", displayOrder = 7, displayName = "Kalori3")

    private int kalori3;*/
    @AExcelColumn(cellType = "String", displayOrder =5, displayName = "TATLI&MEYVE",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")
    @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    private String tatli;
/*    @AExcelColumn(cellType = "Integer", displayOrder = 9, displayName = "Kalori4")

    private int kalori4;*/
    @AExcelColumn(cellType = "String", displayOrder = 6, displayName = "SALATA",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")
    @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    private String salata;

 /*   @AExcelColumn(cellType = "Integer", displayOrder = 11, displayName = "Kalori5")

    private int kalori5;*/
 @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

 @AExcelColumn(cellType = "String", displayOrder = 7, displayName = "İçecek",listTitle = "yemekler",
         database = true,IndirectColumn = true,Indirect = "categry")

    private String icecek;

/*    @AExcelColumn(cellType = "Integer", displayOrder = 13, displayName = "Kalori6")

    private int Kalori6;*/
 /*   @AExcelDbDistinct( databaseTableName = "category",databaseDisplayColumnName = "category_name",databaseReferanceColumnName = "category_id")
    @AExcelColumn(cellType = "String", displayOrder = 12, displayName = "Öğün", required = false,database = true,listTitle = "ct")
    private int category;*/
@AColumn(primary = true,name = "")
    private int aa;
}
