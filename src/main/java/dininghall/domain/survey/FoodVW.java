package dininghall.domain.survey;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import lombok.Data;

import java.io.Serializable;

@ASqlTable(TableName = "food_view")
@Data
public class FoodVW  implements Serializable, ISqlTable {
    @AColumn(name = "calori")
    private int calori;
    @AColumn(name = "description")
    private String desription;

    @AColumn(name = "title")
    private String title;
    @AColumn(name = "image_url")
    private String imageUrl;

    @AColumn(name = "food_id",primary = true)
    private long foodId;
    @AColumn(name = "food_category_id")
    private int foodCategoryId;
    @AColumn(name = "food_category_name")
    private String  foodCategoryName;
    @AColumn(name = "food_category_name")
    private String uploadFile;
}
