package dininghall.view.siteadmin;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import dininghall.asstpackages.ExcelPackage.Excel.SeparatorExcel;
import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@AExcel(sqlTableName = "Order", sheetName = "Order", updateId = "employeeRecordId", max = 3)
@ASqlTable(TableName = "employee")
@Data
public class Sablon2 implements Serializable, ISqlTable {


    @AExcelColumn(required = false, cellType = "String", displayName = "EMAIL", displayOrder = 1)
    String mail;


    @AExcelColumn(required = false, listTitle = "packages", nonZero = false, getlist = {"SPOR VE AILE" + SeparatorExcel.TreeSeperator + "47", "CANAK" + SeparatorExcel.TreeSeperator + "49"}, cellType = "String", displayName = "NUMBER", displayOrder = 2)
    String paket;
    @AExcelColumn(required = false, cellType = "String", displayName = "Date", displayOrder = 3)
    String date;

    @AColumn(primary = true, name = "number")
    String tt;
}
