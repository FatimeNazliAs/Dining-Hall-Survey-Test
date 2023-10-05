package dininghall.domain.survey.SurveyExcel;

import dininghall.asstpackages.ExcelPackage.Models.ExcelOutData;
import lombok.SneakyThrows;
import dininghall.asstpackages.ExcelPackage.ExcelLanguageReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SurveyExcelManager {
    public void Export(Date startDate, Date endDate, int categoryId) {
        if (categoryId > 1)
            new DayOfMenuManager().ExportDayOfMenu(startDate, endDate, categoryId);

        else
            new DayOfMenuManager().ExportDayOfMenuBreakFast(startDate, endDate, categoryId);
    }

    @SneakyThrows
    public Map<Integer, ExcelOutData> Import(InputStream stream, String templateName, int... categoryId) throws ParseException {
        if (categoryId == null)
            return null;
        Map<Integer, ExcelOutData> dataMap = new HashMap<>();

        FoodManager manager = new FoodManager();
        DayOfMenuManager day_manager = new DayOfMenuManager();
        //taşıma yöntemi ile kopyalama .
        //bu şekilde inputstream kapanmıyor;
        var by = stream.readAllBytes();
        for (var item : categoryId) {
            InputStream stream1 = new ByteArrayInputStream(by);
            var excelOutData = new ExcelLanguageReader().ExcelReader(stream1, templateName, false, templateName);
            //manager.FoodAdd(excelOutData.getDataList());
            if (excelOutData.getDataList().size() == 0) {
                Map<Integer, ExcelOutData> map = new HashMap<>();
                map.put(item, excelOutData);
                return map;
            }
            day_manager.DayOfMenuAdd(excelOutData, item);
            dataMap.put(item, excelOutData);
        }
        stream.close();
        return dataMap;
    }

    public void SablonExport(int selectedExport) {

        new DayOfMenuManager().SablonExportDayOfMenu(selectedExport);
    }
}
