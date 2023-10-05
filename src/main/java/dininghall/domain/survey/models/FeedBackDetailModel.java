package dininghall.domain.survey.models;

import dininghall.domain.survey.FeedBackDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackDetailModel
{
    private long dompId;
    private long surveyId;
    /*private Date menuDate;*/
    //   private int categoryId;
    //   private long categoryId;
    private String title1;
    private int rate1;
    private String answer1;
    private String title2;
    private int rate2;
    private String answer2;
    private String title3;
    private int rate3;
    private String answer3;
    private String title4;
    private int rate4;
    private String answer4;

    private String title5;

    private int rate5;
    private String answer5;
    private String title6;

    private int rate6;
    private String answer6;
    private String template;

    public FeedBackDetailModel(FeedBackDetail feedBackDetail)
    {
        template = "";

        for (int i = 0; i < feedBackDetail.getFoodCategoryNames().size(); i++)
        {


            if (feedBackDetail.getFoodCategoryIds().get(i).intValue() == 1)
            {
                this.title1 = feedBackDetail.getTitles().get(i);
                this.answer1 = feedBackDetail.getAnswers().get(i);
                this.rate1 = feedBackDetail.getRatings().get(i).intValue();
                template += "answer1" + "," + feedBackDetail.getTitles().get(i) + ",answer,1," +5  + "-";
                template += "rate1" + "," + "Oy" + ",rate,0," + 1 + "-";

            }

            if (feedBackDetail.getFoodCategoryIds().get(i).intValue() == 2)
            {

                this.title2 = feedBackDetail.getTitles().get(i);
                this.answer2 = feedBackDetail.getAnswers().get(i);
                this.rate2 = feedBackDetail.getRatings().get(i).intValue();
                template += "answer2" + "," + feedBackDetail.getTitles().get(i) + ",answer,1," + 5 + "-";
                template += "rate2" + "," + "Oy" + ",rate,0," + 1 + "-";
            }

            if (feedBackDetail.getFoodCategoryIds().get(i).intValue() == 3)
            {

                this.title3 = feedBackDetail.getTitles().get(i);
                this.answer3 = feedBackDetail.getAnswers().get(i);
                this.rate3 = feedBackDetail.getRatings().get(i).intValue();
                template += "answer3" + "," + feedBackDetail.getTitles().get(i) + ",answer,1," + 5 + "-";
                template += "rate3" + "," + "Oy" + ",rate,0," + 1 + "-";
            }

            if (feedBackDetail.getFoodCategoryIds().get(i).intValue() == 4)
            {

                this.title4 = feedBackDetail.getTitles().get(i);
                this.answer4 = feedBackDetail.getAnswers().get(i);
                this.rate4 = feedBackDetail.getRatings().get(i).intValue();
                template += "answer4" + "," + feedBackDetail.getTitles().get(i) + ",answer,1," + 5 + "-";
                template += "rate4" + "," + "Oy" + ",rate,0," + 1 + "-";
            }

            if (feedBackDetail.getFoodCategoryIds().get(i).intValue() == 5)
            {

                this.title5 = feedBackDetail.getTitles().get(i);
                this.answer5 = feedBackDetail.getAnswers().get(i);
                this.rate5 = feedBackDetail.getRatings().get(i).intValue();
                template += "answer5" + "," + feedBackDetail.getTitles().get(i) + ",answer,1," + 5 + "-";
                template += "rate5" + "," + "Oy" + ",rate,0," + 1 + "-";
            }
            if (feedBackDetail.getFoodCategoryIds().get(i).intValue() == 6)
            {

                this.title6 = feedBackDetail.getTitles().get(i);
                this.answer6 = feedBackDetail.getAnswers().get(i);
                this.rate6 = feedBackDetail.getRatings().get(i).intValue();

                template += "answer6" + "," + feedBackDetail.getTitles().get(i) + ",answer,1," + 5 + "-";
                template += "rate6" + "," + "Oy" + ",rate,0," + 1 + "-";
            }
        }
        //type=feedBackAvg.getCategoryId()==1?"Sabah":feedBackAvg.getCategoryId()==2?"Öğle":feedBackAvg.getCategoryId()==3?"Akşam":"yok";
/*        this.categoryId=feedBackDetail.getCategoryId();
        menuDate=feedBackDetail.getMenuDate();*/
        dompId = feedBackDetail.getDompId();
        //answer = feedBackDetail.getAnswer();
        surveyId = feedBackDetail.getSurveyId();

    }


}
