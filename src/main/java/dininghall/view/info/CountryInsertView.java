/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dininghall.view.info;


import dininghall.service.info.ContinentService;
import dininghall.service.info.CountryService;
import dininghall.domain.info.Continent;
import dininghall.domain.info.Country;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class CountryInsertView implements Serializable {
    private Country newCountry;

    @ManagedProperty("#{continentService}")
    private ContinentService continentService;
    private List<SelectItem> continentItemList;
    private List<Continent> continentList;
    private String continent;
    private int continentId;

    @ManagedProperty("#{countryService}")
    private CountryService countryService;
    private String countryAlpha2Code;
    private String countryAlpha3Code;
    private String countryNumericCode;
    private String countryCode;
    private String englishName;
    private String nativeName;


    @PostConstruct
    public void init() {
        newCountry = new Country();

        continentItemList = new ArrayList<>();
        continentItemList.add(new SelectItem(0, "Select One..."));
        continentList = continentService.getContinentList(10);
        for (Continent continent : continentList) {
            continentItemList.add(new SelectItem(continent.getContinentId(), continent.getEnglishName()));
        }

    }


    public void insertCountry() {
        newCountry.setCountryAlpha2Code(countryCode);
        newCountry.setCountryAlpha3Code(countryCode);
        newCountry.setEnglishName(englishName);
        newCountry.setNativeName(nativeName);
        newCountry.setContinentId(continentId);

        if (countryService.insertCountry(newCountry)) {
            FacesMessage msg = new FacesMessage("Country added successfully", newCountry.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, Country not added.", newCountry.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Country getNewCountry() {
        return newCountry;
    }

    public void setNewCountry(Country newCountry) {
        this.newCountry = newCountry;
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }


    public ContinentService getContinentService() {
        return continentService;
    }

    public void setContinentService(ContinentService continentService) {
        this.continentService = continentService;
    }

    public List<SelectItem> getContinentItemList() {
        return continentItemList;
    }

    public void setContinentItemList(List<SelectItem> continentItemList) {
        this.continentItemList = continentItemList;
    }

    public List<Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }


    public CountryService getCountryService() {
        return countryService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
    }


    public String getCountryAlpha2Code() {
        return countryAlpha2Code;
    }

    public void setCountryAlpha2Code(String countryAlpha2Code) {
        this.countryAlpha2Code = countryAlpha2Code;
    }

    public String getCountryAlpha3Code() {
        return countryAlpha3Code;
    }

    public void setCountryAlpha3Code(String countryAlpha3Code) {
        this.countryAlpha3Code = countryAlpha3Code;
    }

    public String getCountryNumericCode() {
        return countryNumericCode;
    }

    public void setCountryNumericCode(String countryNumericCode) {
        this.countryNumericCode = countryNumericCode;
    }
}
