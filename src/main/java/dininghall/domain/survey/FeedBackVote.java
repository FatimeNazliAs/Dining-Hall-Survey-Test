package dininghall.domain.survey;

import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@ASqlTable(TableName = "feed_back_vote_view")
public class FeedBackVote implements ISqlTable, Serializable
{
    @AColumn(name = "counts")
    private ArrayList<Integer>   counts;

    @AColumn(name = "ratings")
    private ArrayList<Integer> ratings;
    @AColumn(name = "title")
    private String title;
    @AColumn(name = "domp_id")
    private long dompId;
/*    @AColumn(name = "survey_id")
    private long surveyId;*/
/*    @AColumn(name = "menu_date")
    private Date menuDate = new Date();*/
/*    @AColumn(name = "category_id")
    private int categoryId;*/
/*
    @AColumn(name = "dom_ids")
    private ArrayList<Integer> domIds;
    @AColumn(name = "food_category_ids")
    private ArrayList<Integer> foodCategoryIds;

    @AColumn(name = "food_category_names")
    private  ArrayList<String> foodCategoryNames;*/

}
