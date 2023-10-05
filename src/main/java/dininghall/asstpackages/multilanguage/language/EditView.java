package dininghall.asstpackages.multilanguage.language;

import dininghall.asstpackages.multilanguage.language.Models.LanguageString;
import lombok.Data;
import org.apache.commons.collections4.map.HashedMap;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.asstpackages.multilanguage.language.Models.Language;
import dininghall.generic.LazyPack.LazyService;
import dininghall.generic.Manager.DbManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "d")
@SessionScoped
@Data
public class EditView implements Serializable {
    //selected
    private LazyDataModel<LanguageString> Items;
    private LanguageString languageString;

    private LanguageString newLanguage;
    //selected
    private Language language;
    private Map<String, String> selectedLanguages;
    private Map<String, Integer> allLanguages;
    private Map<String, String> countries;

    @PostConstruct
    public void init() {
        countries = new HashMap<>();
        countries.put("USA", "USA");
        countries.put("Germany", "Germany");
        countries.put("Brazil", "Brazil");

        var list = Locale.getAvailableLocales();

        for (var item : list) {
            countries.put(item.getLanguage(), item.getDisplayName());
        }

        Items = new LazyDataModel<LanguageString>() {

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return 0;
            }


            @Override
            public List<LanguageString> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                List<LanguageString> searchItems = new LazyService().FilterOperation(Items, new LanguageString(), first, pageSize, sortBy, filterBy, "globalFilter");
                return searchItems;
            }

            public LanguageString getRowData(String rowKey) {
                for (LanguageString ob : getItems()) {
                    if (rowKey.equals(ob.getId() + "")) {
                        return ob;
                    }
                }
                return null;
            }

            public String getRowKey(LanguageString object) {
                return object.getId() + "";
            }
        };
        UpdateResource();
    }

    public void openNew() {
        allLanguages = new HashedMap();
        ArrayList<Language> arrayList = new DbManager(new Language()).GetAll();
        for (Language map : arrayList) {
            allLanguages.put(map.getName(), map.getId());
        }
        newLanguage = new LanguageString();

    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LanguageString getNewLanguage() {
        return newLanguage;
    }

    public void setNewLanguage(LanguageString newLanguage) {
        this.newLanguage = newLanguage;
    }

    public LanguageString getLanguageString() {
        return languageString;
    }

    public void setLanguageString(LanguageString languageString) {
        this.languageString = languageString;
    }


    public Map<String, Integer> getAllLanguages() {
        return allLanguages;
    }

    public void delete() {
        DbManager manager = new DbManager(languageString);

        manager.Delete();
        UpdateResource();
        addMessage("Confirmed", "Record deleted id:" + languageString.getId());
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public LazyDataModel<LanguageString> getItems() {
        return Items;
    }


    public void onRowEdit(RowEditEvent<LanguageString> event) {
        FacesMessage msg = new FacesMessage("LanguageString Edited", String.valueOf(event.getObject().getId()) + " " + String.valueOf(event.getObject().getLanguageId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        DbManager manager = new DbManager(event.getObject());
        manager.Update();
        UpdateResource();
    }

    private void UpdateResource() {
        PrimeFaces.current().executeScript("PF('manageLanguageDialog').hide()");
        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:msgs", "form:items");
        languageMap = null;
        getLanguages();
        UpdateSupportedLanguages();
    }

    public void onRowCancel(RowEditEvent<LanguageString> event) {
        FacesMessage msg = new FacesMessage("LanguageString Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveLanguageString() {

        String data = new DbManager(newLanguage).Add() + "";
        addMessage("Eklendi", data);
        UpdateResource();
    }

    static Map<String, Language> languageMap;

    public void getLanguages() {
        if (languageMap == null) {
            languageMap = new HashedMap();
            DbManager manager = new DbManager(new Language());
            List<Language> languageList = manager.GetAll();
            for (Language language : languageList) {
                languageMap.put(language.getId() + "", language);
            }

        }
    }

    private void UpdateSupportedLanguages() {
        selectedLanguages = new HashedMap();
        ArrayList<String> values = new ArrayList<>();
        for (Map.Entry<String, Language> a : languageMap.entrySet()) {
            values.add(a.getValue().getName());
            selectedLanguages.put(a.getValue().getName() + "", a.getKey() + "");
        }
    }

    public String getLanguageName(int Language_Id) {
        getLanguages();
        Language val = languageMap.get(Language_Id + "");

        return val != null ? val.getName() : "Unknown";

    }


    public void openNewLanguage() {
        language = new Language();
        language.setFlagimagefilename("");
        language.setPublished(true);
    }

    public void saveLanguage() {
        language.setPublished(true);
        language.setDisplayorder(1);
        language.setFlagimagefilename("");
        language.setLanguageCulture(language.getUniqueSeoCode());
        String data = new DbManager(language).Add() + "";
        addMessage("Eklendi", data);
        UpdateResource();
    }


    public Map<String, String> getSelectedLanguages() {
        return selectedLanguages;
    }

    public void setSelectedLanguages(Map<String, String> selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
    }


    public void onCountryChange() {
    }
}