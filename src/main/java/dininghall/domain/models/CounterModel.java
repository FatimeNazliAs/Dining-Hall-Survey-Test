package dininghall.domain.models;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

//Not insert and update
//just reader
@ASqlTable(TableName = "Counter")
@Data
public class CounterModel implements Serializable,ISqlTable
{
    @AColumn(name = "name")
    private String names;
    @AColumn(name = "value")
    private int value;


}
