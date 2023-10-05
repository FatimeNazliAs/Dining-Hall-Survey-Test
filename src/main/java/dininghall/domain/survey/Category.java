package dininghall.domain.survey;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@ASqlTable(TableName = "category")
@Data
public class Category implements Serializable, ISqlTable {
    @AColumn(name = "category_id",primary = true)
    private int categoryId;
    @AColumn(name = "category_name")
    private String categoryName;

}
