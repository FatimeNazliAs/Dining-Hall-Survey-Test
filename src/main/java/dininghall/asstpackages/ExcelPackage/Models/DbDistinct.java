package dininghall.asstpackages.ExcelPackage.Models;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
@Data

public class DbDistinct
{
    @AColumn(name = "property")
    private String property;
    @AColumn(name = "tableName")
    private String tableName;
 @AColumn(name = "key")
    private String key;
    @AColumn(name = "value")
    private String value;
    @AColumn(name = "indirect_value")
    private String indirectName;

    @AColumn(name = "indirect_name")
    private String indirectKey;


    public DbDistinct()
    {
    }

    public DbDistinct(String property, String tableName, String key, String value)
    {
        this.property = property;
        this.tableName = tableName;
        this.key = key;
        this.value = value;
    }


}
