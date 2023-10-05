package dininghall.domain.survey;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@ASqlTable(TableName = "day_of_menu")
@Data
public class DayOfMenu implements Serializable, ISqlTable {
    @AColumn(name = "day_of_menu_id",primary = true)
    private long dayOfMenuId;
    @AColumn(name = "food_id")
    private long foodId;
    @AColumn(name = "category_id")
    private long categoryId;
    @AColumn(name = "column_key")
    private String columnKey;
    @AColumn(name = "day_of_menu_parent_id")
    private long dayOfMenuParentId;

}
