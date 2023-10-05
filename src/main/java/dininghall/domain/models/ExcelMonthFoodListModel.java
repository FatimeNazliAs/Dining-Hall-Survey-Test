package dininghall.domain.models;

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
public class ExcelMonthFoodListModel implements Serializable, ISqlTable
{

    @AExcelColumn(cellType = "Date", required = true, displayName = "Tarih", database = true, displayOrder = 1)
    private Date tarih;
    @AExcelColumn(cellType = "String", displayOrder = 2, displayName = "ÇORBALAR ", required = true)
    private String corba;
    @AExcelColumn(cellType = "Integer", displayOrder = 3, displayName = "Kalori1", required = true)

    private int kalori1;
    @AExcelColumn(cellType = "String", displayOrder = 4, displayName = "ANA YEMEK & Z.YAĞLI", required = true)

    private String anayemek;
    @AExcelColumn(cellType = "Integer", displayOrder = 5, displayName = "Kalori2", required = true)

    private int kalori2;
    @AExcelColumn(cellType = "String", displayOrder = 6, displayName = "ARA SICAK ", required = true)

    private String ara;
    @AExcelColumn(cellType = "Integer", displayOrder = 7, displayName = "Kalori3", required = true)

    private int kalori3;
    @AExcelColumn(cellType = "String", displayOrder = 8, displayName = "TATLI&MEYVE", required = true)

    private String tatli;
    @AExcelColumn(cellType = "Integer", displayOrder = 9, displayName = "Kalori4", required = true)

    private int kalori4;
    @AExcelColumn(cellType = "String", displayOrder = 10, displayName = "SALATA", required = true)

    private String salata;
    @AExcelColumn(cellType = "Integer", displayOrder = 9, displayName = "Kalori5", required = true)

    private int kalori5;
@AColumn(primary = true,name = "")
    private int aa;
}
