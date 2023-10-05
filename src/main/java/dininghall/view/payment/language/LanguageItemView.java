package dininghall.view.payment.language;

import dininghall.asstpackages.multilanguage.language.Models.Language;
import dininghall.asstpackages.multilanguage.language.Models.LanguageItem;
import lombok.Data;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.generic.LazyPack.LazyService;
import dininghall.generic.Manager.DbManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "languageItemView")
@ViewScoped
@Data
public class LanguageItemView {
    LazyDataModel<LanguageItem> items;
    List<Language> languages;


    LanguageItem languageItem;
    Map<String, Integer> languageList = new HashMap<>();
    Map<String, Integer> packageMap = new HashMap<>();
    Map<String, String> itemMap = new HashMap<>();

    @PostConstruct
    public void init() {
        items = new LazyDataModel<LanguageItem>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return 0;
            }

            @Override
            public List<LanguageItem> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return new LazyService().FilterOperation(items, new LanguageItem(), i, i1, map, map1, "globalFilter");
            }
        };

        languageItem = new LanguageItem();

        languages = new DbManager(Language.class).GetAll();

        if (languages != null) {
            for (var lng : languages) {
                languageList.put(lng.getName(), lng.getId());
            }
        }

      /*  List<ProductPackage> packageList = new DbManager(ProductPackage.class).GetAll();

        if (packageList != null) {
            for (var pck : packageList) {
                packageMap.put(pck.getPackageName(), pck.getPackageId());
            }
        }*/

        itemMap.put("Paket Başlığı", "name");
        itemMap.put("Paket Gösterim Başlığı", "header");
        itemMap.put("Teklif Başlığı", "offer_title");
        itemMap.put("Ödeme Seçenek Başlığı", "pay_title");
        itemMap.put("Kutu Yazısı", "box_title");
        itemMap.put("description", "description");

        //items= new DbManager().Get()
    }

    public void onLanguageChange() {
        int a = 0;
        a = a;
    }

    public String mapValueToKey(Map<String, Object> map, Object obj) {
        for (var map_item : map.entrySet()) {
            if ((map_item.getValue() + "").equals(obj + ""))
                return map_item.getKey();
        }
        return obj + "";
    }

    public void SavePackageLanguage() {

        if (languageItem.getId() > 0) {
            boolean sc = new DbManager(languageItem).Update();
            if (sc)
                addMessage(FacesMessage.SEVERITY_INFO, "Succes", "");
            else
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "");
        } else {
            languageItem.setClassName("ProductPackage");
            int rowCount = new DbManager(languageItem).GetCountRows("where item_id=" + languageItem.getItemId() + " and language_id=" + languageItem.getLanguageId() + "  and item_class_name='" + languageItem.getClassName() + "' and item_property_name='" + languageItem.getPropertyName() + "'");
            if (rowCount > 0) {
                System.out.println("Added Language Item Failed");
                addMessage(FacesMessage.SEVERITY_INFO, "Failed ", "");
                return;
            }
            int a = new DbManager(languageItem).Add();
            if (a > 0) {
                System.out.println("Added Language Item Succes");
                addMessage(FacesMessage.SEVERITY_INFO, "Succes", "");
            } else
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "");
        }
    }


    public void openNew() {
        languageItem = new LanguageItem();
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public static String getCurrentLanguage() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        // default language
        String userLanguage;

        Language language = (Language) sessionMap.get("userLanguage");

        if (language == null) {
            // default will be en
            userLanguage = "tr";
        } else {
            userLanguage = language.getUniqueSeoCode();
        }

        return userLanguage;

    }
}
