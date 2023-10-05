package dininghall.generic;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@ASqlTable(TableName = "information_schema")
public class information_schema implements ISqlTable, Serializable {
    @AColumn(name = "table_catalog", primary = true)
    String table_catalog;
    @AColumn(name = "table_schema")
    String table_schema;
    @AColumn(name = "table_name")
    String table_name;
    @AColumn(name = "column_name", primary = true)
    String column_name;
    @AColumn(name = "data_type")
    String data_type;

    public String getTable_catalog() {
        return table_catalog;
    }

    public void setTable_catalog(String table_catalog) {
        this.table_catalog = table_catalog;
    }

    public String getTable_schema() {
        return table_schema;
    }

    public void setTable_schema(String table_schema) {
        this.table_schema = table_schema;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }
}