import dininghall.asstpackages.ExcelPackage.ExcelFood;
import dininghall.domain.survey.exceltemplate.FoodTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SurveyScheduleTest {

    private void exportExcel(List<FoodTemplate> exportList) {
        try {
            int resultTest = new ExcelFood().export(exportList);
        } catch (Exception e) {
            // Handle exception
        }
    }

@Nested
    class DownloadAndImportExcelFile{
    private int selectedImport = 1;

    @Test
    @DisplayName("When you push şablonu indir button, yemekler template will be downloaded")
    @Tag("export")
    void whenPushSablonuIndirButtonTemplateWillBeDownloaded()
    {








    }
}










}
