package dininghall.view.survey;

import dininghall.asstpackages.ExcelPackage.ExcelFood;
import dininghall.asstpackages.ExcelPackage.Models.ExcelReadOut;
import dininghall.asstpackages.ExcelPackage.Models.OutPutExcel;
import dininghall.domain.survey.Food;
import dininghall.domain.survey.FoodCategory;
import dininghall.domain.survey.FoodVW;
import dininghall.domain.survey.exceltemplate.FoodTemplate;
import dininghall.generic.LazyPack.LazyService;
import dininghall.generic.Manager.DbManager;
import lombok.Data;
import lombok.SneakyThrows;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@ManagedBean(name = "foodListView")
@ViewScoped
@Data
public class FoodListView implements Serializable {
    OutPutExcel outputs = new OutPutExcel();
    LazyDataModel<FoodVW> foodLazyDataModel;
    FoodVW selectedFood;
    List<FoodCategory> foodCategoryList;
    Map<String, SortMeta> sortMap;
    Map<String, FilterMeta> filterMetaMap;
    private int selectedExport = 1;
    private int selectedImport;
    private UploadedFile file;

    @PostConstruct
    public void init() {
        System.out.println("WEB SAYFASINA GİRİŞ YAPILDI");
        selectedFood = new FoodVW();
        //categories=new DbManager(Category.class).GetAll();
        foodCategoryList = new DbManager(FoodCategory.class).GetAll();

        foodLazyDataModel = new LazyDataModel<FoodVW>() {

            @Override
            public int count(Map<String, FilterMeta> map) {
                return 0;
            }

            @Override
            public List<FoodVW> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                filterMetaMap = map1;
                sortMap = map;
                return new LazyService().FilterOperation(foodLazyDataModel, new FoodVW(), i, i1, map, map1, "");
            }
        };
    }

    public void addMessage(String message, String description, FacesMessage.Severity msgType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(msgType,
                        message,
                        description));
        PrimeFaces.current().ajax().update("form:msgs");
    }

    public void sablonExport() {

       FacesMessage message = new FacesMessage("Aktarım Başladı",
                "Boş Şablon Dışarı Aktarılıyor.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().update("form:messages");


        List<FoodTemplate> exportList = new ArrayList<>();


        if (exportList.size() == 0) {
            exportList.add(new FoodTemplate());
        }

        int resultTest=new ExcelFood().export(exportList);

   if (resultTest == 1) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Dosya başarıyla oluşturuldu.",
                            "personel listesi şablonu"));

        }

        System.out.println(exportList.size());





    }



    public void newFood() {
        selectedFood = new FoodVW();

    }

    public String categoryNameGetById(int categoryId) {
        if (foodCategoryList == null)
            foodCategoryList = new ArrayList<>();
        for (var item : foodCategoryList) {
            if (item.getFoodCategoryId() == categoryId)
                return item.getFoodCategoryName();
        }
        return categoryId + "";
    }

    //yeni food ekleme veya düzenleme kodu
    public String uploadImage(String detail) {

        if (file == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Dosya yüklenirken bir hata oluştu."));

            return "";
        }

        // Dosyayı kaydetme işlemleri burada yapılabilir.
        // Örneğin, bir dizine kaydedebilirsiniz.
        String fileName = detail; // Rastgele bir dosya adı
        String directory = "C:\\food"; // Rastgele bir dosya adı
        try {
            Files.copy(this.inputStream, Paths.get(directory, fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Başarılı bir şekilde kaydedildiğine dair kullanıcıya mesaj gösterebilirsiniz.
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dosya başarıyla yüklendi."));
        return directory + "\\" + fileName;


    }

    public void saveFood() {
        Locale turkishLocale = new Locale("tr", "TR");
        Locale.setDefault(turkishLocale);

        Food food = new Food();
        food.setFoodId(selectedFood.getFoodId());
        food.setFoodCategoryId(selectedFood.getFoodCategoryId());
        food.setTitle(selectedFood.getTitle());
        food.setCalori(selectedFood.getCalori());
        food.setDesription(selectedFood.getDesription());
        if (this.inputStream != null) {
            String imageUrl = uploadImage(food.getTitle().toLowerCase(turkishLocale) + ".png");
            if (imageUrl.length() > 0)
                food.setImageUrl(imageUrl);
        }
        food.setTitle(food.getTitle().toUpperCase(turkishLocale));

        if (food.getCalori() < 1) {
            addMessage("Kalori Geçersiz Girildi!", food.getCalori() + " Girdiniz(Geçersiz)" + "", FacesMessage.SEVERITY_ERROR);

            return;
        }
        if (food.getFoodId() > 0) {
            if (new DbManager(food).Update()) {
                addMessage("Güncelleme Başarılı", food.getFoodId() + "", FacesMessage.SEVERITY_INFO);
            } else {
                addMessage("Güncelleme Başarısız!", food.getTitle(), FacesMessage.SEVERITY_ERROR
                );

            }

        } else {
            int id = new DbManager(food).Add();
            if (id > 0) {
                addMessage("Ekleme Başarılı", "", FacesMessage.SEVERITY_INFO);

            } else {

                addMessage("Ekleme Başarısız!", "", FacesMessage.SEVERITY_ERROR);

            }
        }
    }

    public void deleteFood() {
        new DbManager(selectedFood).Delete();
    }

    public void clearFilters() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:foodListDT");
        if (dataTable != null) {
            dataTable.reset();

            PrimeFaces.current().ajax().update("form:msgs", "form:foodListDT");
        }

    }

    public <T> void export() {

        List<FoodVW> list = new ArrayList<>();

        list = new LazyService().
                FilterOperation
                        (null, new FoodVW(), 0,
                                1000, sortMap, filterMetaMap, "");
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



        FacesContext facesContext = FacesContext.getCurrentInstance();

        facesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Dışa Aktarma Başladı",
                        "dosya oluşturuluyor"));


        if (new ExcelFood().export(exportList) == 1) {
            facesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Dosya başarıyla oluşturuldu.",
                            "personel listesi şablonu"));


        } else {
            facesContext.responseComplete();
            facesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Dışa Aktarma Hatası",
                            "Sistem hatası"));
        }
        PrimeFaces.current().ajax().update("form:msgs");

    }



    public void handleFileUpload(FileUploadEvent event) {
        try {
            var ex = new ExcelFood().
                    importExcel(event.getFile().getInputStream(),FoodTemplate.class.getSimpleName());

            outputs = new OutPutExcel();
            for (ExcelReadOut out : ex.getErrors()) {
                outputs.outputError += out.getRowNumber() + ". Satırda Hata :" + out.getOutText();
            }

            for (ExcelReadOut out : ex.getUpdate()) {
                outputs.outputUpdate += out.getRowNumber() + ". Güncellendi :" + out.getOutText() + "\n";
            }
            for (ExcelReadOut out : ex.getAdd()) {
                outputs.outputAdd += out.getRowNumber() + ". Eklendi :" + out.getOutText() + "\n";
            }


            FacesMessage message = new FacesMessage("Başarılı", event.getFile().getFileName() + " eklendi.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        catch (IOException e) {
            // this.originalImageFile = null;
            FacesMessage message = new FacesMessage("Başarısız", event.getFile().getFileName() + " eklenemedi.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            throw new RuntimeException(e);
        }


    }




    String base64;

    public void convertToBase64(InputStream stream) {
        try {

            byte[] imageBytes = stream.readAllBytes();

            base64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private CroppedImage croppedImage;

    private UploadedFile originalImageFile;
    private InputStream inputStream = null;

    @SneakyThrows
    public void handleFileUpload2(FileUploadEvent event) {
        this.originalImageFile = null;
        this.croppedImage = null;

        UploadedFile file = event.getFile();
        if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            this.originalImageFile = file;
            this.file = this.originalImageFile;
            inputStream = this.file.getInputStream();
            convertToBase64(this.file.getInputStream());
            FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    @SneakyThrows
    public void updateImage() {
        base64 = "";
        this.file = null;
        this.inputStream = null;
        if (selectedFood != null && selectedFood.getImageUrl() != null && selectedFood.getImageUrl().length() > 0) {
            File file = new File(selectedFood.getImageUrl());
            try{
                FileInputStream imageInputStream = new FileInputStream(file);
                convertToBase64(imageInputStream);
                PrimeFaces.current().ajax().update("dialogs:showImage");
            }
            catch (Exception e){}

        }

    }
}
