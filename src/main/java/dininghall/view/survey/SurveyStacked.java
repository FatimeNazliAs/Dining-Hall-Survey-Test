package dininghall.view.survey;

import dininghall.domain.survey.FeedBackVote;
import dininghall.generic.LazyPack.LazyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SurveyStacked implements Serializable
{
    private BarChartModel stackedGroupBarModel;

    public SurveyStacked(long dompId)
    {
        init(dompId);
    }

    private long dompId;

    public void init(long dompId)
    {
        this.dompId = dompId;
        createStackedGroupBarModel();
    }

    public BarChartDataSet addBarCharDataSet(String datasetTitle, String stackName, String colorRgb)
    {
        BarChartDataSet barDataSet3 = new BarChartDataSet();
        barDataSet3.setLabel(datasetTitle);
        barDataSet3.setBackgroundColor(colorRgb);
        barDataSet3.setStack(stackName);
        List<Number> dataVal3 = new ArrayList<>();
        dataVal3.add(10);
        dataVal3.add(73);
        dataVal3.add(15);
        dataVal3.add(20);
        dataVal3.add(30);
        dataVal3.add(12);
        dataVal3.add(21);
        barDataSet3.setData(dataVal3);
        return barDataSet3;
    }

    @Data
    @AllArgsConstructor
    public class StackedGroupModel
    {
        BarChartDataSet barDataSet;
        List<Number> numberList;
    }

    public void createStackedGroupBarModel()
    {

        Map<Number, StackedGroupModel> modelMap = new HashMap<>();
        var a = new BarChartDataSet();
        a.setStack("Stack 1");
        a.setLabel("1 ⭐");
        a.setBackgroundColor("rgb(127,44,22)");
        a.setHoverBackgroundColor("rgb(0,0,0)");

        //  a.set("Stack 1");
        modelMap.put(1, new StackedGroupModel(a, new ArrayList<>()));
        a = new BarChartDataSet();
        a.setStack("Stack 2");
        a.setLabel("2 ⭐");
        a.setBackgroundColor("rgb(212,190,50)");
        a.setHoverBackgroundColor("rgb(0,0,0)");
        modelMap.put(2, new StackedGroupModel(a, new ArrayList<>()));
        a = new BarChartDataSet();
        a.setStack("Stack 3");
        a.setLabel("3 ⭐");
        a.setBackgroundColor("rgb(212,82,50)");
        a.setHoverBackgroundColor("rgb(0,0,0)");
        a.setBorderColor("rgb(0,0,0)");
        modelMap.put(3, new StackedGroupModel(a, new ArrayList<>()));
        a = new BarChartDataSet();
        a.setStack("Stack 4");
        a.setLabel("4 ⭐");
        a.setBackgroundColor("rgb(100,100,100)");
        a.setHoverBackgroundColor("rgb(0,0,0)");
        modelMap.put(4, new StackedGroupModel(a, new ArrayList<>()));
        a = new BarChartDataSet();
        a.setStack("Stack 5");
        a.setLabel("5 ⭐");
        a.setBackgroundColor("rgb(255,51,51)");
        a.setHoverBackgroundColor("rgb(0,0,0)");
        modelMap.put(5, new StackedGroupModel(a, new ArrayList<>()));
        List<FeedBackVote> items = new LazyService().FilterOperation(null, new FeedBackVote(), 0, 100, new HashMap<>(), new HashMap<>(), "", "domp_id=" + dompId);
        List<String> labels = new ArrayList<>();

        for (var item : items)
        {

            for (int i = 1; i < 6; i++)
            {
                boolean ret = item.getRatings().contains(i);
                if (!ret)
                {
                    item.getRatings().add(i);
                    item.getCounts().add(0);
                }
            }

        }
        if (!items.isEmpty())
        {
            int index = 0;
            for (var item : items)
            {

                labels.add(item.getTitle());

                for (int i = 0; i < item.getRatings().size(); i++)
                {
                    modelMap.get(item.getRatings().get(i)).numberList.add(item.getCounts().get(i));
                }

            }
        }

        stackedGroupBarModel = new BarChartModel();

        ChartData data = new ChartData();

        //  data.addChartDataSet(barDataSet);
        for (var item : modelMap.entrySet())
        {
            item.getValue().barDataSet.setData(item.getValue().numberList);
            data.addChartDataSet(item.getValue().barDataSet);

        }
        //  data.addChartDataSet(addBarCharDataSet("4 Yıldız", "Stack 3", "rgb(128, 128, 128)"));
        List<String> stackNames = new ArrayList<>();
        for (var item : modelMap.entrySet())
        {
            stackNames.add(item.getValue().barDataSet.getStack());
        }
        data.setLabels(labels);

        stackedGroupBarModel.setData(data);
        //Options
        BarChartOptions options = new BarChartOptions();

        CartesianScales cScales = new CartesianScales();


        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setStacked(true);
        linearAxes.setPosition("top");
        linearAxes.setOffset(true);
        cScales.addXAxesData(linearAxes);
        cScales.addYAxesData(linearAxes);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Grupla - Yıldız Grupları");
        options.setTitle(title);
        Tooltip tooltip = new Tooltip();

        tooltip.setMode("index");
        tooltip.setPosition("top");
        tooltip.setIntersect(true);
        tooltip.setDisplayColors(true);
        tooltip.setEnabled(true);
        options.setScales(cScales);

        options.setTooltip(tooltip);
        stackedGroupBarModel.setOptions(options);

    }


}
