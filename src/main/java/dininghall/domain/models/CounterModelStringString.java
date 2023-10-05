package dininghall.domain.models;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import lombok.Data;

import java.io.Serializable;

//Not insert and update
//just reader
@ASqlTable(TableName = "Counter")
@Data
public class CounterModelStringString implements Serializable,ISqlTable
{
    @AColumn(name = "name")
    private String names;
    @AColumn(name = "value")
    private String value;


}
