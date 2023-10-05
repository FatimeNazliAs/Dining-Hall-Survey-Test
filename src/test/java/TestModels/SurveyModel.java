package TestModels;

import lombok.Data;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;


@Data
public class SurveyModel
{
    private DayOfMenuWithDetailViewModel model;
    private int rate;
    private String answer;


}
