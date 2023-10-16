import dininghall.asstpackages.ExcelPackage.CreatorFolder.CreateExcel;
import dininghall.asstpackages.ExcelPackage.Models.ExcelOutData;
import dininghall.domain.survey.SurveyExcel.SurveyExcelManager;
import dininghall.domain.survey.exceltemplate.Lunch;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

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

        file = new File(DESKTOP_PATH + fileName);
    }

    private void exportExcel(List<Lunch> exportList) {
        try {
            new CreateExcel().Create(exportList);
        } catch (Exception e) {
            // Handle exception
        }
    }


    private void importAndPrintExcel(InputStream stream) throws ParseException {
        Map<Integer, ExcelOutData> excelOutDataMap = new HashMap<>();
        excelOutDataMap = new SurveyExcelManager().Import(stream, "Lunch", 2);

        System.out.println(excelOutDataMap.get(2).getDataList());
        for (var out : excelOutDataMap.get(2).getDataList()) {
            System.out.println("Corba: " + out.get("corba").getValue());
            System.out.println("ANA Kategori: " + out.get("ana").getValue());
            System.out.println("ARA Kategori: " + out.get("ara").getValue());
           System.out.println("TATLI Kategori: " + out.get("tatli").getValue());
            System.out.println("ICECEK Kategori: " + out.get("icecek").getValue());
            System.out.println("-------------------------------------");
//            System.out.println("MEZE Kategori: " + out.get("salata").getValue());
//            System.out.println("EK-1 Kategori: " + out.get("plus").getValue());
//            System.out.println("EK-2 Kategori: " + out.get("plustwo").getValue());
        }


    }


    @Nested
    class DownloadAndImportExcelFile {
        @Test
        @DisplayName("When you push şablonu indir button, yemekler template will be downloaded")
        @Tag("sabloneExport")
        void whenPushSablonuIndirButtonTemplateWillBeDownloaded() {

            List<Lunch> lunchTemplate = new ArrayList<>();

            if (lunchTemplate.isEmpty()) {
                lunchTemplate.add(new Lunch(new Date(),
                        "YAYLA ÇORBA", "KABAK SANDAL SEFASI",
                        "PEYNİRLİ TEPSİ BÖREĞİ", "AŞURE",
                        "YOĞURTLU SEMİZOTU", "YOĞURT",
                        "YAYLA ÇORBA", "YOĞURTLU SEMİZOTU",
                        2));
            }

            exportExcel(lunchTemplate);
            assertTrue(file.exists());
        }


        @Test
        @DisplayName("excel is correct, yemekler_template will be imported")
        @Tag("handleFileUpload")
        void whenPushIceriAktarButtonTemplateWillBeImported() throws
                FileNotFoundException, ParseException {
            setExcelFilePathAndCreateFile("sample.xlsx");
            InputStream stream = new FileInputStream(file);
            importAndPrintExcel(stream);
        }

    }


    @Nested
    class ExportExcelFile {

        @Test
        @DisplayName("when push dışarı aktar button, menu_template will be exported")
        @Tag("export")
        void whenPushDisariAktarButtonMenuTemplateWillBeExported() throws
                FileNotFoundException, ParseException {

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.YEAR, 2023);
            calendar.set(Calendar.MONTH, Calendar.OCTOBER);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();

            calendar.set(Calendar.DAY_OF_MONTH,3);
            Date endDate = calendar.getTime();
            List<Lunch> templates = new ArrayList<>();


            try{
                new SurveyExcelManager().
                        Export(startDate, endDate, 2);
            }
            catch (Exception e){

            }
            assertTrue(file.exists());
            InputStream stream = new FileInputStream(file);
            importAndPrintExcel(stream);





        }


    }


}



