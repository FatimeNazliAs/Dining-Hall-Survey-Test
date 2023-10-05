package dininghall.domain.survey.models;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;
import java.util.Date;

@ASqlTable(TableName = "day_of_menu_with_detail_view")
@Data
public class DayOfMenuWithDetailViewModel implements Serializable, ISqlTable
{
    @AColumn(name = "day_of_menu_id",primary = true)
    private long dayOfMenuId;
    @AColumn(name = "day_of_menu_parent_id")
    private long dayOfMenuParentId;
   @AColumn(name = "food_id")
    private long foodId;
    @AColumn(name = "menu_date")
    private Date menuDate;
    @AColumn(name = "created_date")
    private Date createdDate;
    @AColumn(name = "updated_date")
    private Date updatedDate;
    @AColumn(name = "local_user_id")
    private long localUserId;
    @AColumn(name = "category_id")
    private int categoryId;
    @AColumn(name = "title")
    private String title;
    @AColumn(name = "image_url")

    private String imageUrl;
    @AColumn(name = "food_category_id")
    private long foodCategoryId;
    @AColumn(name = "food_category_name")
    private String foodCategoryName;
    @AColumn(name = "category_name")
    private String categoryName;
    @AColumn(name = "calori")
    private int calori;
    @AColumn(name = "column_key")
    private String  columnKey;
}
