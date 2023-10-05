package dininghall.domain.survey;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;


@ASqlTable(TableName = "food")
@Data
public class Food implements Serializable, ISqlTable {
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


}
