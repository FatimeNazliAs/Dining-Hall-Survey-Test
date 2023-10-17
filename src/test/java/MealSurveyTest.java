import TestModels.SurveyModel;
import dininghall.domain.survey.Survey;
import dininghall.domain.survey.SurveyItem;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import dininghall.generic.Manager.DbManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MealSurveyTest {

    private Survey survey;
    private List<SurveyModel> modelList;

    //    iddomp->day of menu parent id
    private int userType = 1;

    private Date menuDate;

    private List<SurveyItem> items;

    public List<DayOfMenuWithDetailViewModel> list;

    private long iddomp = -1;


    @BeforeEach
    void setUp() {
        survey = new Survey();
        survey.setCreatedDate(new Date());
        survey.setSurveyDate(new Date());
        survey.setDompId(iddomp);
        survey.setUserType(userType);


        items = new ArrayList<>();


        modelList = new ArrayList<>();
        list = new ArrayList<>();
        list = new DbManager(DayOfMenuWithDetailViewModel.class).
                Get("menu_date=current_date and category_id=" + 2);
        for (var item : list) {
            SurveyModel surveyModel = new SurveyModel();
            surveyModel.setModel(item);
            surveyModel.setRate(3);
            iddomp = item.getDayOfMenuParentId();
            menuDate = item.getMenuDate();
            modelList.add(surveyModel);
        }

    }


    @Test
    @DisplayName("Survey should be saved into db if data is correct")
    @Tag("correctData")
    void whenDataIsCorrectItWillBeSavedIntoDb() {

        var id = new DbManager(survey).Add();

       assertTrue(id >= 0);

        showModelList(modelList);


        for (var item : modelList) {
            SurveyItem surveyItem = new SurveyItem();
            surveyItem.setRating(5);
            surveyItem.setSurveyId(id);
            surveyItem.setAnswer("harika");
            surveyItem.setDayOfMenuId(item.getModel().getDayOfMenuId());
            items.add(surveyItem);
        }
        var result = new DbManager(SurveyItem.class).AddAll(items);
        assertTrue(!result.isEmpty());

        for (var res : result) {
            System.out.println(res);

        }



    }

    private void showModelList(List<SurveyModel> modelList){
        for (var model : modelList) {
            System.out.println(model);
        }

    }




    }





