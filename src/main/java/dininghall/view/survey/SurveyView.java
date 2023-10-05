package dininghall.view.survey;

import TestModels.SurveyModel;
import dininghall.domain.survey.Survey;
import dininghall.domain.survey.SurveyItem;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import dininghall.generic.Manager.DbManager;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "surveyView")
@ViewScoped
@Data
public class SurveyView implements Serializable
{
    private List<SurveyModel> modelList;
    private List<DayOfMenuWithDetailViewModel> nextDayList;
    private long iddomp = -1;
//    iddomp->day of menu parent id
    private int userType = 1;
    private Date menuDate;
    public String menuText="Şu an aktif menü yok";
    public String betweenMenuRate="Bir Sonraki Menü Saatini Bekleyiniz";
    public static String locationName="Gebze";
    public int menuType=1;

    @PostConstruct
    public void init()
    {
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int munite = time.getMinute();
        int result;
        List<DayOfMenuWithDetailViewModel> list = new ArrayList<>();
        if ( (hour >= 5 && hour < 7))
        {menuText="Sabah";
            betweenMenuRate="5:00 - 7:00";
            result = 1;
            list = new DbManager(DayOfMenuWithDetailViewModel.class).Get("menu_date=current_date and category_id=" + result);

        }
        else if (((hour >= 8 && munite>30 ) || hour>=8) && hour < 14)
        {menuText="Öğle";betweenMenuRate="8:30 - 14:00";
            result = 2;
            list = new DbManager(DayOfMenuWithDetailViewModel.class).Get("menu_date=current_date and category_id=" + result);

        } else if (hour >= 18 && hour < 20)
        {betweenMenuRate="18:00 - 20:00";
            menuText="Akşam";
            result = 3;
            list = new DbManager(DayOfMenuWithDetailViewModel.class).Get("menu_date=current_date and category_id=" + result);

        }

        modelList = new ArrayList<>();

        for (var item : list)
        {
            SurveyModel surveyModel = new SurveyModel();
            surveyModel.setModel(item);
            surveyModel.setRate(3);
            iddomp = item.getDayOfMenuParentId();
            menuDate = item.getMenuDate();
            modelList.add(surveyModel);
        }



    }



    public static String convertToBase64(String path) {
        if (path.isEmpty()){
            return "icon.png";

        }
        else{
        try {
            File file = new File(path);
            FileInputStream imageInputStream = new FileInputStream(file);
            byte[] imageBytes = new byte[(int) file.length()];
            imageInputStream.read(imageBytes);
            imageInputStream.close();

            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }}
    }

    public void save()
    {
        Survey survey = new Survey();
        survey.setCreatedDate(new Date());
        survey.setSurveyDate(menuDate);
        survey.setDompId(iddomp);
        survey.setUserType(userType);

        var id = new DbManager(survey).Add();

        List<SurveyItem> items=new ArrayList<>();
        for (var item : modelList)
        {
            SurveyItem surveyItem = new SurveyItem();
            surveyItem.setRating(item.getRate());
            surveyItem.setSurveyId(id);
            surveyItem.setAnswer(item.getAnswer());
            surveyItem.setDayOfMenuId(item.getModel().getDayOfMenuId());
          items.add(surveyItem);
        }
        new DbManager(SurveyItem.class).AddAll(items);
    }



public void canlitut(){
        int a=0;
        a++;
}
    public List<DayOfMenuWithDetailViewModel> nextDayList()
    {
        return new DbManager(DayOfMenuWithDetailViewModel.class).
                Get("menu_date=current_date+1");
    }
}
