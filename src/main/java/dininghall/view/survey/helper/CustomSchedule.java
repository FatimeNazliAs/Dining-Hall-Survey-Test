package dininghall.view.survey.helper;

import org.primefaces.model.DefaultScheduleEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomSchedule {

    public static int breakfast = 0;
    public static int lunch = 1;
    public static int dinner = 2;
    /**param=123*/
    /**param:title*/
    /**description*/
    /**
     * borderColor
     */
    private String[] breakfastAttributes = new String[]{"Kahvaltı", "Açıklama", "#FFA500","breakFastStyle", "6", "8"};
    private String[] lunchAttributes = new String[]{"Öğlen", "Açıklama", "#008000","lunchStyle", "11", "14"};
    private String[] dinnerAttributes = new String[]{"Akşam", "Açıklama", "#FF4500","dinnerStyle", "19", "21"};
    private static List<String[]> foodList = new ArrayList<>();

    public void Holdiday() {

    }

    public static DefaultScheduleEvent FoodTime(LocalDateTime referenceDate, int whichTime, long id, int menuCount) {
        foodList = new ArrayList<>();
        if (foodList.isEmpty()) {
            var customSch = new CustomSchedule();
            foodList.add(customSch.breakfastAttributes);
            foodList.add(customSch.lunchAttributes);
            foodList.add(customSch.dinnerAttributes);
        }
        referenceDate = referenceDate.withHour(Integer.parseInt(foodList.get(whichTime)[4]));
        var endRefernceDate = referenceDate.withHour(Integer.parseInt(foodList.get(whichTime)[5]));
        var a = new DefaultScheduleEvent();

        return DefaultScheduleEvent.builder()
                .title(foodList.get(whichTime)[0] + "-" + menuCount)
                .startDate(referenceDate)
                .endDate(endRefernceDate)
                .description(foodList.get(whichTime)[1])
                .draggable(false).id(id + "")
                .backgroundColor(foodList.get(whichTime)[2])
                .borderColor(foodList.get(whichTime)[2]).
                styleClass(foodList.get(whichTime)[3])

                .build();
    }


}
