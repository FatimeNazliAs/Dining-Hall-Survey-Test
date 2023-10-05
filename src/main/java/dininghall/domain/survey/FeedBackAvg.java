package dininghall.domain.survey;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ASqlTable(TableName = "feed_back_avg")
public class FeedBackAvg implements ISqlTable, Serializable
{
    @AColumn(name = "menu_date")
    private Date menuDate = new Date();
    @AColumn(name = "category_id")
    private int categoryId;
    @AColumn(name = "domp_id",primary = true)
    private long dompId;    @AColumn(name = "category_name" )
    private String  categoryName;
    @AColumn(name = "food_ids")
    private ArrayList<Integer> foodIds;
    @AColumn(name = "food_category_ids")
    private ArrayList<Integer> foodCategoryIds;
    @AColumn(name = "avg_ratings")
    private ArrayList<Double> avgRatings;
    @AColumn(name = "food_category_names")
    private  ArrayList<String> foodCategoryNames;
    @AColumn(name = "titles")
    private ArrayList<String> titles;
    @AColumn(name = "column_keys")
    private ArrayList<String> columnKeys;

}
