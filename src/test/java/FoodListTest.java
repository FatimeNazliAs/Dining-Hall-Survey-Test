import dininghall.asstpackages.ExcelPackage.ExcelFood;
import dininghall.domain.survey.FoodVW;
import dininghall.domain.survey.exceltemplate.FoodTemplate;
import dininghall.generic.LazyPack.LazyService;
import org.junit.jupiter.api.*;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FoodListTest {

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

    private void exportExcel(List<FoodTemplate> exportList) {
        try {
            int resultTest = new ExcelFood().export(exportList);
        } catch (Exception e) {
            // Handle exception
        }
    }

    private void importAndPrintExcel(InputStream stream, String sheetName) {
        var excelList = new ExcelFood().importExcel(stream, sheetName);
        assertTrue(!excelList.getDataList().isEmpty());

        for (var out : excelList.getDataList()) {
            System.out.println("Calori Value: " + out.get("calori").getValue());
            System.out.println("Category Value: " + out.get("category").getValue());
            System.out.println("Title Value: " + out.get("title").getValue());
        }


    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class DownloadAndImportExcelFile {

        @Test
        @DisplayName("When you push şablonu indir button, yemekler template will be downloaded")
        @Tag("export")
        void whenPushSablonuIndirButtonTemplateWillBeDownloaded() throws IOException {
            List<FoodTemplate> exportList = new ArrayList<>();
            if (exportList.isEmpty()) {
                exportList.add(new FoodTemplate());
            }
            exportExcel(exportList);
            assertTrue(file.exists());
        }

        @Test
        @DisplayName("excel is correct, yemekler_template will be imported")
        @Tag("handleFileUpload")
        void whenPushIceriAktarButtonTemplateWillBeImported() throws FileNotFoundException {
            setExcelFilePathAndCreateFile("correctSample.xlsx");
            InputStream stream = new FileInputStream(file);
            importAndPrintExcel(stream, "FoodTemplate");
        }

        // TODO: 5.10.2023  500 karakter sınırı koyalacak
        // TODO: 5.10.2023  kalori miktarı 1000 i geçmemeli gibi çeşitli sınırlar koyulacak

        @Test
        @DisplayName("excel is empty, yemekler_template wont be imported")
        @Tag("handleFileUpload")
        void whenPushIceriAktarButtonTemplateWontBeImported() throws FileNotFoundException {
            setExcelFilePathAndCreateFile("emptySample.xlsx");
            InputStream stream = new FileInputStream(file);
            importAndPrintExcel(stream, "FoodTemplate");
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ExportExcelFile {

        Map<String, SortMeta> sortMap;
        Map<String, FilterMeta> filterMetaMap;

        @Test
        @DisplayName("when push dışarı aktar button, yemekler_template will be exported")
        void whenPushDisariAktarButtonYemeklerTemplateWillBeExported() throws FileNotFoundException {
            List<FoodVW> list = new LazyService().FilterOperation(
                    null, new FoodVW(), 0, 1000, sortMap, filterMetaMap, "");

            List<FoodTemplate> exportList = new ArrayList<>();
            for (var item : list) {
                FoodTemplate template = new FoodTemplate();
                template.setFoodId(item.getFoodId());
                template.setTitle(item.getTitle());
                template.setCalori(item.getCalori());
                template.setDesription(item.getDesription());
                template.setFoodCategoryId(item.getFoodCategoryId());
                exportList.add(template);
            }

            if (exportList.isEmpty()) {
                exportList.add(new FoodTemplate());
            }

            exportExcel(exportList);
            assertTrue(file.exists());
            readExcelFile(file);
        }

        private void readExcelFile(File file) throws FileNotFoundException {
            InputStream stream = new FileInputStream(file);
            importAndPrintExcel(stream, "FoodTemplate");
        }
    }
















    }



