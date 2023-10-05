package dininghall.domain.survey;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import java.io.Serializable;
import lombok.Data;

@Data
@ASqlTable(TableName = "food_category")

public class FoodCategory implements Serializable, ISqlTable
{     @AColumn(name = "food_category_id",primary = true)
    private int foodCategoryId;
    @AColumn(name = "food_category_name")
    private String foodCategoryName;
}
