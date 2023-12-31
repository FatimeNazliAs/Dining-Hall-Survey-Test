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


public class FoodListViewTest {


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class DownloadAndImportExcelFile {

        private File file;
        private String desktopPath;
        private String excelFilePath;

        private String fileName;

        @BeforeAll
        void setUp() {

            this.desktopPath = System.getProperty("user.home") + "\\Desktop\\";
            this.excelFilePath = excelFilePath;
        }

        public void setExcelFilePathAndCreateFile(String fileName) {
            this.excelFilePath = desktopPath + fileName;
            this.file = new File(excelFilePath);
        }

        @Test
        @DisplayName("When you push şablonu indir button, yemekler template will be downloaded")
        @Tag("export")
        void whenPushSablonuIndirButtonTemplateWillBeDownloaded()
                throws IOException {

            List<FoodTemplate> exportList = new ArrayList<>();

            if (exportList.size() == 0) {
                exportList.add(new FoodTemplate());
            }

            try {
                int resultTest = new ExcelFood().export(exportList);


            } catch (Exception e) {

            }

            setExcelFilePathAndCreateFile("sample.xlsx");
            assertTrue(this.file.exists());

        }


        @Test
        @DisplayName("excel is correct, " +
                "yemekler_template will be imported")
        @Tag("handleFileUpload")
        void whenPushIceriAktarButtonTemplateWillBeImported() throws FileNotFoundException {

            setExcelFilePathAndCreateFile("correctSample.xlsx");
            InputStream stream = new FileInputStream(this.file);


            var excelList = new ExcelFood().importExcel(stream, "FoodTemplate");
            assertTrue(!excelList.getDataList().isEmpty());

            for (var out : excelList.getDataList()) {
                System.out.println("Calori Value: " + out.get("calori").getValue());
                System.out.println("Category Value: " + out.get("category").getValue());
                System.out.println("Title Value: " + out.get("title").getValue());
            }

            // TODO: 5.10.2023  500 karakter sınırı koyalacak
            // TODO: 5.10.2023  kalori miktarı 1000 i geçmemeli gibi çeşitli sınırlar koyulacak


        }

        @Test
        @DisplayName("excel is empty," +
                " yemekler_template wont be imported")
        @Tag("handleFileUpload")
        void whenPushIceriAktarButtonTemplateWontBeImported() throws FileNotFoundException {

            setExcelFilePathAndCreateFile("emptySample.xlsx");

            InputStream stream = new FileInputStream(this.file);
            System.out.println(this.file.getName());


            var excelList = new ExcelFood().importExcel(stream, "FoodTemplate");
            assertTrue(excelList.getDataList().isEmpty());


        }


    }


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ExportExcelFile {

        private String desktopPath;
        private File file;

        private String excelFilePath;

        private String fileName;


        Map<String, SortMeta> sortMap;
        Map<String, FilterMeta> filterMetaMap;


        @BeforeAll
        void setUp() {

            this.desktopPath = System.getProperty("user.home") + "\\Desktop\\";
            this.excelFilePath = excelFilePath;
        }

        @Test
        void whenPushDisariAktarButtonYemeklerTemplateWillBeExported() throws FileNotFoundException {

            List<FoodVW> list = new LazyService().
                    FilterOperation
                            (null, new FoodVW(), 0,
                                    1000, sortMap, filterMetaMap,
                                    "");

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

            if (exportList.size() == 0) {
                exportList.add(new FoodTemplate());
            }

            try {
                int resultTest = new ExcelFood().export(exportList);

            } catch (Exception e) {

            }
            String excelFilePath = this.desktopPath + "sample.xlsx";
            File file = new File(excelFilePath);
            System.out.println(file);
            assertTrue(file.exists());
            readExcelFile(file);


        }

        private void readExcelFile(File file) throws FileNotFoundException {
           InputStream stream = new FileInputStream(file);


            var excelList = new ExcelFood().importExcel(stream, "FoodTemplate");
            assertTrue(!excelList.getDataList().isEmpty());

            for (var out : excelList.getDataList()) {
                System.out.println("Calori Value: " + out.get("calori").getValue());
                System.out.println("Category Value: " + out.get("category").getValue());
                System.out.println("Title Value: " + out.get("title").getValue());
            }



        }


    }


}







































