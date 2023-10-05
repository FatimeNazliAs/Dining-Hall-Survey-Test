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

            this.excelFilePath = desktopPath + "sample.xlsx";
            this.file = new File(excelFilePath);
            assertTrue(this.file.exists());


//            FileInputStream stream = new FileInputStream(this.file);
//            Workbook workbook = WorkbookFactory.create(stream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            int startRow =5;
//
//            boolean isEmpty = true;
//
//            for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                if (row != null) {
//                    for (Cell cell : row) {
//                        if (cell.getCellType() != CellType.BLANK) {
//                            System.out.println(String.valueOf(cell.getRowIndex()) +" "+
//                                    cell.getStringCellValue());
//                            isEmpty = false;
//                            break;
//                        }
//                    }
//                }
//                if (!isEmpty) {
//                    break;
//                }
//            }
//
//            if (isEmpty) {
//                System.out.println("3. satırdan itibaren tüm satırlar boş.");
//            } else {
//                System.out.println("3. satırdan itibaren en az bir satır dolu.");
//            }
//
//            workbook.close();
//            stream.close();


        }


        @Test
        @Tag("handleFileUpload")
        @DisplayName("excel is correct, " +
                "yemekler_template will be imported")
        void whenPushIceriAktarButtonTemplateWillBeImported() throws FileNotFoundException {

            this.excelFilePath = desktopPath + "correctSample.xlsx";
            this.file = new File(excelFilePath);
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
        @Tag("handleFileUpload")
        @DisplayName("excel is empty," +
                " yemekler_template wont be imported")
        void whenPushIceriAktarButtonTemplateWontBeImported() throws FileNotFoundException {

            this.excelFilePath = desktopPath + "emptySample.xlsx";
            this.file = new File(excelFilePath);

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


        Map<String, SortMeta> sortMap;
        Map<String, FilterMeta> filterMetaMap;

        @BeforeAll
        void setUp() {

            this.desktopPath = System.getProperty("user.home") + "\\Desktop\\";
        }

        @Test
        void whenPushDisariAktarButtonYemeklerTemplateWillBeExported() {

            List<FoodVW>  list = new LazyService().
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
            assertTrue(file.length()>=0);


        }


    }


}







































