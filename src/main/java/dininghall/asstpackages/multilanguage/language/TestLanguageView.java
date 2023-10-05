package dininghall.asstpackages.multilanguage.language;

import dininghall.asstpackages.multilanguage.language.Models.Language;
import dininghall.asstpackages.multilanguage.language.Models.LanguageString;
import org.apache.commons.collections4.map.HashedMap;
import dininghall.generic.Manager.DbManager;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Map;

@SessionScoped
@ManagedBean(name = "d1")
@ApplicationScoped
public class TestLanguageView
{
    boolean enabled = true;
    static ArrayList<Language> list;
    static int next = 0;
    private Language selectedLanguage;
    private Map<String, String> allLanguages;

    public Map<String, String> getAllLanguages()
    {
        return allLanguages;
    }

    public Map<String, String> getLanguageList()
    {
        allLanguages = new HashedMap();

        ArrayList<Language> arrayList = new DbManager(new Language()).Get("published=true");
        for (Language map : arrayList)
        {
            allLanguages.put(map.getName(), map.getUniqueSeoCode());
        }

        return allLanguages;
    }

    public TestLanguageView()
    {
        if (enabled)
            return;
        //new ExcelLanguageReader();
        if (this.selectedLanguage == null)
            this.selectedLanguage = new Language();
        getLanguageList();

        DbManager manager = new DbManager(new Language());

        list = manager.GetAll();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        // default language
        String userLanguage;

        selectedLanguage = (Language) sessionMap.get("userLanguage");

        if (selectedLanguage == null)
        {
            // default will be en
            userLanguage = "tr";
        } else
        {
            userLanguage = selectedLanguage.getUniqueSeoCode();
        }

        selectedLanguage = new DbManager(new Language()).GetFirst(" unique_seo_code='" + userLanguage + "'");

        sessionMap.put("userLanguage", selectedLanguage);
    }

    Map<String, Map<String, String>> allResource;

    public String getTextDb(String data)
    {
        if (enabled)
            return data;
        DbManager manager = new DbManager(new LanguageString());
        String[] page = data.split("\\.");
        if (allResource == null || allResource.get(page[0]) == null)
        {
            if (allResource == null)
                allResource = new HashedMap();
            ArrayList<LanguageString> strings = manager.Get(" resource_name like  '" + page[0] + ".%' and language_id='" + selectedLanguage.getId() + "'");
            Map<String, String> stringMap = new HashedMap();
            for (LanguageString item : strings)
            {
                stringMap.put(item.getResourceName(), item.getResourceValue());
            }
            allResource.put(page[0], stringMap);
        }

        if (allResource.get(page[0]) != null)
        {
            String datav = allResource.get(page[0]).get(data);
            if (datav != null)
                return datav;
            return data;
        }
        return data;

    }

    public void changeSystemLanguage(String uniqueCode)
    {
        System.setProperty("user.language", uniqueCode);

        selectedLanguage = new DbManager(new Language()).GetFirst(" unique_seo_code='" + uniqueCode + "'");

        update();
    }

    public void changeSystemLanguage()
    {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String uniqueCode = params.get("uniqueCode");

        if (uniqueCode == null || uniqueCode.isEmpty())
        {
            uniqueCode = "en";
        }

        System.setProperty("user.language", uniqueCode);

        selectedLanguage = new DbManager(new Language()).GetFirst(" unique_seo_code='" + uniqueCode + "'");

        sessionMap.put("userLanguage", selectedLanguage);

        update();
    }


    public void languageSelect()
    {
        if (enabled)
            return;
        String uniqueCode = "en";
        String temp_u = selectedLanguage.getUniqueSeoCode();
        if (temp_u != null && !temp_u.isEmpty() && temp_u.length() > 0 && temp_u.length() < 3)
            uniqueCode = temp_u;
        changeSystemLanguage(uniqueCode);
    }

    private void update()
    {
        allResource = null;
        getLanguageList();
    }

    public Language getSelectedLanguage()
    {
        return selectedLanguage;
    }

    public void setSelectedLanguage(Language selectedLanguage)
    {
        this.selectedLanguage = selectedLanguage;
    }
}

