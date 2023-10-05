package dininghall.domain.survey.exceltemplate;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelDbDistinct;
import lombok.AllArgsConstructor;
import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AExcel(updateId = "", sheetName = "Sheet1", sqlTableName = "MenuListesi",max = 19)
@ASqlTable(TableName = "")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lunch implements Serializable, ISqlTable,IExcel
{

    @AExcelColumn(columnKey = "menu_date",cellType = "Date", required = true, displayName = "Tarih", database = true, displayOrder = 1)
    private Date tarih;

    @AExcelDbDistinct(distincColumnHeader="CORBA Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(columnKey = "corba",cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "ÇORBALAR ",database = true,IndirectColumn = true,Indirect = "categry")
    private String corba;
  /*  @AExcelColumn(cellType = "Integer", displayOrder = 3, displayName = "Kalori1")

    private int kalori1;*/
  @AExcelDbDistinct(distincColumnHeader="ANA Kategori",sameData = true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

  @AExcelColumn(columnKey = "ana",cellType = "String", displayOrder = 3,listTitle = "yemekler", displayName = "ANA YEMEK & Z.YAĞLI",database = true,IndirectColumn = true,Indirect = "categry")

    private String ana;
/*    @AExcelColumn(cellType = "Integer", displayOrder = 5, displayName = "Kalori2")

    private int kalori2;*/

    @AExcelDbDistinct(distincColumnHeader="ARA Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(columnKey = "ara",cellType = "String", displayOrder = 4, displayName = "ARA SICAK ",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ara;
/*    @AExcelColumn(cellType = "Integer", displayOrder = 7, displayName = "Kalori3")

    private int kalori3;*/
    @AExcelColumn(columnKey = "tatli",cellType = "String", displayOrder =5, displayName = "TATLI&MEYVE",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")
    @AExcelDbDistinct(distincColumnHeader="TATLI Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    private String tatli;
/*    @AExcelColumn(cellType = "Integer", displayOrder = 9, displayName = "Kalori4")

    private int kalori4;*/
    @AExcelColumn(columnKey = "salata",cellType = "String", displayOrder = 6, displayName = "SALATA",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")
    @AExcelDbDistinct(distincColumnHeader="MEZE Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    private String salata;

 /*   @AExcelColumn(cellType = "Integer", displayOrder = 11, displayName = "Kalori5")

    private int kalori5;*/
 @AExcelDbDistinct(distincColumnHeader="ICECEK Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

 @AExcelColumn(columnKey = "icecek",cellType = "String", displayOrder = 7, displayName = "İçecek",listTitle = "yemekler",
         database = true,IndirectColumn = true,Indirect = "categry")

 private String icecek;
    @AExcelDbDistinct(distincColumnHeader="EK-1 Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(hide=false,columnKey = "plus",cellType = "String", displayOrder = 8, displayName = "EK-1",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ek1;
    @AExcelDbDistinct(distincColumnHeader="EK-2 Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(hide=false,columnKey = "plustwo",cellType = "String", displayOrder = 9, displayName = "EK-2",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ek2;

/*    @AExcelColumn(cellType = "Integer", displayOrder = 13, displayName = "Kalori6")

    private int Kalori6;*/
 /*   @AExcelDbDistinct( databaseTableName = "category",databaseDisplayColumnName = "category_name",databaseReferanceColumnName = "category_id")
    @AExcelColumn(cellType = "String", displayOrder = 12, displayName = "Öğün", required = false,database = true,listTitle = "ct")
    private int category;*/
@AColumn(primary = true,name = "")
    private int aa;
}
