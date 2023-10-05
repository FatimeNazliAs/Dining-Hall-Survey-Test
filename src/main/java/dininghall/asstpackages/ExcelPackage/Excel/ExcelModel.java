package dininghall.asstpackages.ExcelPackage.Excel;

import dininghall.asstpackages.ExcelPackage.Annatations.AExcelDbDistinct;
import dininghall.asstpackages.ExcelPackage.Models.DbDistinct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelModel {
    int rowNumber;
    int colNumber;



    private   String displayName;
  private  Object value;
  private  String fieldName;
  private  String listTitle;
  private  String columnName;
  private Integer displayOrder;
  private String dbSyntax;
  private String dbFieldName;
  private String indirectValue;
  private Map<String ,List<DbDistinct> > indirectList;
  private Map<String ,Object > keyValues;

    public int getColNumber()
    {
        return colNumber;
    }

    public void setColNumber(int colNumber)
    {
        this.colNumber = colNumber;
    }

    public String getIndirectValue()
    {
        return indirectValue;
    }

    public void setIndirectValue(String indirectValue)
    {
        this.indirectValue = indirectValue;
    }

    public Map<String, List<DbDistinct>> getIndirectList()
    {
        return indirectList;
    }

    public void setIndirectList(Map<String, List<DbDistinct>> indirectList)
    {
        this.indirectList = indirectList;
    }

    public Map<String, Object> getKeyValues()
    {
        return keyValues;
    }

    public void setKeyValues(Map<String, Object> keyValues)
    {
        this.keyValues = keyValues;
    }
    public void addKeyValues(String key,Object value)
    {
        if (this.keyValues==null)
            keyValues=new HashMap<>();
        this.keyValues .put(key,value);
    }

    private AExcelDbDistinct aExcelDbDistinct;


    public AExcelDbDistinct getaExcelDbDistinct() {
        return aExcelDbDistinct;
    }

    public void setaExcelDbDistinct(AExcelDbDistinct aExcelDbDistinct) {
        this.aExcelDbDistinct = aExcelDbDistinct;
    }

    public String getDbFieldName() {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public String getDbSyntax() {
        return dbSyntax;
    }

    public void setDbSyntax(String dbSyntax) {
        this.dbSyntax = dbSyntax;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    private dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn AExcelColumn;

    public dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn getAExcelColumn() {
        return AExcelColumn;
    }

    public void setAExcelColumn( dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn AExcelColumn) {
        this.AExcelColumn = AExcelColumn;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
