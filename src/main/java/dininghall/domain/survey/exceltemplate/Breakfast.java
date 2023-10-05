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

@AExcel(updateId = "", sheetName = "Sheet1", sqlTableName = "MenuListesi",max = 23)
@ASqlTable(TableName = "")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Breakfast implements Serializable, ISqlTable,IExcel
{

    @AExcelColumn(columnKey="menu_date",cellType = "Date", required = true, displayName = "Tarih", database = true, displayOrder = 1)
    private Date tarih;

    @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(columnKey="kahvalti1",cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-1 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti11;

     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(columnKey="kahvalti2",cellType = "String", displayOrder = 3, listTitle = "yemekler",displayName = "KAHVALTI-2 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti12;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(columnKey="kahvalti3",cellType = "String", displayOrder = 4, listTitle = "yemekler",displayName = "KAHVALTI-3 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti13;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(columnKey="kahvalti4",cellType = "String", displayOrder = 5, listTitle = "yemekler",displayName = "KAHVALTI-4 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti14;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(columnKey="kahvalti5",cellType = "String", displayOrder = 6, listTitle = "yemekler",displayName = "KAHVALTI-5 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti15;
    @AExcelDbDistinct(distincColumnHeader="EK-1 Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(hide=false,columnKey = "kahvalti6",cellType = "String", displayOrder = 7, displayName = "EK-1",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ek1;
    @AExcelDbDistinct(distincColumnHeader="EK-2 Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(hide=false,columnKey = "kahvalti7",cellType = "String", displayOrder = 8, displayName = "EK-2",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ek2;
    @AExcelDbDistinct(distincColumnHeader="EK-3 Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(hide=false,columnKey = "kahvalti8",cellType = "String", displayOrder = 9, displayName = "EK-3",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ek3;
  @AExcelDbDistinct(distincColumnHeader="EK-4 Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(hide=false,columnKey = "kahvalti9",cellType = "String", displayOrder = 11, displayName = "EK-4",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ek4;
  @AExcelDbDistinct(distincColumnHeader="EK-5 Kategori",sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")

    @AExcelColumn(hide=false,columnKey = "kahvalti10",cellType = "String", displayOrder = 12, displayName = "EK-5",listTitle = "yemekler",
            database = true,IndirectColumn = true,Indirect = "categry")

    private String ek5;
    /*
    *
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-6 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti6;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-7 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti7;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-8 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti8;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-9 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti9;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-10 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti10;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-11 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti11;
     @AExcelDbDistinct(sameData=true,indirect = true,indirectName = "food_category_name",indirectKey = "food_category_id",indirectTableName ="food_category",databaseTableName = "food",databaseReferanceColumnName = "food_id",databaseDisplayColumnName = "title")
    @AExcelColumn(cellType = "String", displayOrder = 2, listTitle = "yemekler",displayName = "KAHVALTI-12 ",database = true,IndirectColumn = true,Indirect = "categry")
    private String kahvalti12;

    *
    * */


@AColumn(primary = true,name = "")
    private int aa;
}
