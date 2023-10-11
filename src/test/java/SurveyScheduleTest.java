import dininghall.asstpackages.ExcelPackage.CreatorFolder.CreateExcel;
import dininghall.domain.survey.exceltemplate.Lunch;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SurveyScheduleTest {

    private static final String DESKTOP_PATH = System.getProperty("user.home") + "\\Desktop\\";
    private static final String EXCEL_FILE_PATH = DESKTOP_PATH + "sample.xlsx";

    private File file;

    @BeforeEach
    void setUp() {
        file = new File(EXCEL_FILE_PATH);
    }

    private void setExcelFilePathAndCreateFile(String fileName) {

        file = new File(DESKTOP_PATH + fileName);}

    private void exportExcel(List<Lunch> exportList) {
        try {
            new CreateExcel().Create(exportList);
        } catch (Exception e) {
            // Handle exception
        }
    }


@Nested
    class DownloadAndImportExcelFile{



    @Test
    @DisplayName("When you push şablonu indir button, yemekler template will be downloaded")
    @Tag("export")
    void whenPushSablonuIndirButtonTemplateWillBeDownloaded()  {

        List<Lunch> lunchTemplate = new ArrayList<>();

        if (lunchTemplate.isEmpty()){
            lunchTemplate.add(new Lunch(new Date(),
                    "EZOGELİN ÇORBA", "KARNIYARIK",
                    "PİLAV", "AŞURE",
                    "YOGURT", "AYRAN",
                    "EZOGELİN ÇORBA", "KARNIYARIK",
                    1));
        }
        exportExcel(lunchTemplate);
        assertTrue(file.exists());




    }


}







}
