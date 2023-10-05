package dininghall.view.siteadmin;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcel;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@AExcel(sqlTableName = "employee", sheetName = "Employee", updateId = "employeeRecordId", max = 3)
@ASqlTable(TableName = "employee")
@Data
public class Sablon implements Serializable, ISqlTable {

    @AExcelColumn(required = false, cellType = "String", displayName = "UYE ISMI", displayOrder = 2)
    String firstName;
    @AExcelColumn(required = false, cellType = "String", displayName = "SOYISIM", displayOrder = 3)
    String soyIsım;

    @AExcelColumn(required = false, cellType = "String", displayName = "NUMBER", displayOrder = 1)
    String number;
    @AExcelColumn(required = false, cellType = "String", displayName = "EMAIL", displayOrder = 4)
    String mail;

    @AColumn(primary = true, name = "number")
    String tt;
}
