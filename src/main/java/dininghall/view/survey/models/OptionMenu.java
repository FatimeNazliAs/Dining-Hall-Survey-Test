package dininghall.view.survey.models;

import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import lombok.Data;
import dininghall.domain.survey.Food;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class OptionMenu implements Serializable
{
    private DayOfMenuWithDetailViewModel dofmVW;
    private Food selectedFood;
    private String UIkey;
    private int dofmType;
    private List<DayOfMenuWithDetailViewModel> modelList;
    private Map<String ,Integer>  typeList;
    private List<Food>  foodList;
    private String  query;
}
