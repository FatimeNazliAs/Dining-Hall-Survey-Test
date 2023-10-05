package dininghall.domain.survey.exceltemplate;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelDbDistinct;
import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@AExcel(sqlTableName = "Yemekler",sheetName = "yemeklistesi",updateId = "food_id",max = 3)
@ASqlTable(TableName = "food")
@Data
public class FoodTemplate implements Serializable, ISqlTable {
    @AExcelColumn(columnKey = "calori",displayName = "Kalori",cellType = "Integer",displayOrder = 3,required = true,database = false)
    @AColumn(name = "calori")
    private int calori;
    @AColumn(name = "description")
    private String desription;
    @AExcelColumn(columnKey = "title",displayName = "Yemek Ad",cellType = "String",displayOrder = 2,required = true,database = false)

    @AColumn(name = "title")
    private String title;
   // @AExcelColumn(displayName = "Tür",cellType = "Long",displayOrder =1,required = false,database = false)

    @AColumn(name = "food_id",primary = true)
    private long foodId;

    @AExcelDbDistinct(databaseDisplayColumnName = "food_category_name",databaseReferanceColumnName = "food_category_id",databaseTableName = "food_category")
    @AExcelColumn( columnKey = "category",displayName = "Tür",cellType = "String",displayOrder =4,listTitle = "foodcategory",required = true,database = true)

    @AColumn(name = "food_category_id")
    private int foodCategoryId;


}
