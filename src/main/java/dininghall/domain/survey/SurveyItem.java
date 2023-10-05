package dininghall.domain.survey;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@ASqlTable(TableName = "survey_item")
@Data
public class SurveyItem implements Serializable, ISqlTable {

    @AColumn(name = "survey_item_id",primary = true)
    private long surveyItemId;
    @AColumn(name = "food_id")
    private long foodId;
    @AColumn(name = "rating")
    private int rating;
    @AColumn(name = "answer")
    private String  answer;
    @AColumn(name = "survey_id")
    private long surveyId;
    @AColumn(name = "day_of_menu_id")
    private long dayOfMenuId;
}
