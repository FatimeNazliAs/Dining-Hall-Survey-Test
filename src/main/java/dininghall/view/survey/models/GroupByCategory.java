package dininghall.view.survey.models;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@ASqlTable(TableName = "")
@Data
public class GroupByCategory implements Serializable, ISqlTable
{

    @AColumn(name = "category_name")
    private String categoryName;
    @AColumn(name = "category_id",primary = true)
    private int categoryId;

}
