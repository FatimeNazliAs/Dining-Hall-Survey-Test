package dininghall.domain.survey.models;

import dininghall.domain.survey.FeedBackAvg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackAvgModel
{
    private long dompId;
    private Date menuDate;
    private int categoryId;
    //   private long categoryId;
    private String title1;
    private String  rate1;
    private String title2;
    private String  rate2;
    private String title3;
    private String  rate3;
    private String title4;
    private String  rate4;
    private String title5;
    private String  rate5;
    private String title6;
    private String  rate6;

    private String title7;
    private String  rate7;
    private String title8;
    private String  rate8;
    private String title9;
    private String  rate9;
    private String title10;
    private String  rate10;



    public FeedBackAvgModel(FeedBackAvg feedBackAvg)
    {
        for (int i = 0; i < feedBackAvg.getFoodCategoryIds().size(); i++)
        {


            if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==1)
            {
                this.title1 = feedBackAvg.getTitles().get(i);
                this.rate1=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            }

            if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==2)
            {
                this.title2 = feedBackAvg.getTitles().get(i);
                this.rate2=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            }

            if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==3)
            {
                this.title3 = feedBackAvg.getTitles().get(i);
                this.rate3=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            }

            if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==4)
            {
                this.title4 = feedBackAvg.getTitles().get(i);
                this.rate4=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            }

            if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==5)
            {
                this.title5 = feedBackAvg.getTitles().get(i);
                this.rate5=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            }  if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==6)
            {
                this.title7 = feedBackAvg.getTitles().get(i);
                this.rate7=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            }
            if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==6)
            {
                this.title6 = feedBackAvg.getTitles().get(i);
                this.rate6=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            } if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==6)
            {
                this.title6 = feedBackAvg.getTitles().get(i);
                this.rate6=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            } if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==6)
            {
                this.title6 = feedBackAvg.getTitles().get(i);
                this.rate6=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            } if ( feedBackAvg.getFoodCategoryIds().get(i) .intValue()==6)
            {
                this.title6 = feedBackAvg.getTitles().get(i);
                this.rate6=(feedBackAvg.getAvgRatings().get(i)+"").substring(0,3);
            }
        }
        //type=feedBackAvg.getCategoryId()==1?"Sabah":feedBackAvg.getCategoryId()==2?"Öğle":feedBackAvg.getCategoryId()==3?"Akşam":"yok";
        this.categoryId=feedBackAvg.getCategoryId();
        menuDate=feedBackAvg.getMenuDate();
        dompId = feedBackAvg.getDompId();

    }

}
