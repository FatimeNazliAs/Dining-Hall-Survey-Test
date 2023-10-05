import dininghall.domain.survey.SurveyExcel.DayOfMenuManager;
import dininghall.domain.survey.SurveyExcel.FoodManager;
import dininghall.domain.survey.SurveyExcel.SurveyExcelManager;
import dininghall.domain.survey.exceltemplate.Lunch;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class ExcelTest
{
    public static void main(String[] args) throws FileNotFoundException, ParseException
    {

importE();

    }
public static void export(){
    Date date = new Date();
    Calendar calendarStartDate = Calendar.getInstance();
    Calendar calendarEndDate = Calendar.getInstance();
    calendarStartDate.set(2023, 6, 1);
    calendarEndDate.set(2023, 9, 1);

    new SurveyExcelManager().Export(calendarStartDate.getTime(), calendarEndDate.getTime(),3);

}
@SneakyThrows
public static void importE(){
    //File file = new File("C:\\Users\\Askin\\Desktop\\menuagust.xlsx");
    File file = new File("C:\\Users\\saskin\\Desktop\\sample.xlsx");
    InputStream stream = new FileInputStream(file);
    ExcelTest test = new ExcelTest();
    //  new CreateExcel().Create(sablon2s);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());

    // Bir gün çıkar
    calendar.add(Calendar.DAY_OF_MONTH, -1);

    // Sonuç tarih objesi olarak al
    Date newDate = calendar.getTime();
    var excelOutData  =new SurveyExcelManager().Import(stream, Lunch.class.getSimpleName(),2);
    //   var excelOutData = new ExcelLanguageReader().ExcelReader(stream, ExcelBreakfastTemplatePage.class.getSimpleName(), false,"Sabah");
    excelOutData = excelOutData;
    FoodManager manager = new FoodManager();
    // import
    DayOfMenuManager day_manager = new DayOfMenuManager();
    //   manager.FoodAdd(excelOutData.getDataList());
    //  day_manager.DayOfMenuAdd(excelOutData.getDataList(),1);
    //export
    //     day_manager.ExportDayOfMenu(newDate, new Date(),1);


}

}
