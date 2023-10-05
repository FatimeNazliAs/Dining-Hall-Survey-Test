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


import dininghall.domain.info.*;
import dininghall.service.info.*;
//import uk.co.digiturk.domain.info.*;
//import uk.co.digiturk.service.info.*;

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
public class StateInsertView implements Serializable {
    private State newState;

    @ManagedProperty("#{stateService}")
    private StateService stateService;

    @ManagedProperty("#{continentService}")
    private ContinentService continentService;
    private List<SelectItem> continentItemList;
    private List<Continent> continentList;
    private String continent;
    private int continentId;

    @ManagedProperty("#{countryService}")
    private CountryService countryService;
    private List<SelectItem> countryItemList;
    private List<Country> countryList;
    private String country;
    private int countryId;

    @ManagedProperty("#{subDivisionService}")
    private SubDivisionService subDivisionService;
    private List<SelectItem> subDivisionItemList;
    private List<SubDivision> subDivisionList;
    private String subDivision;
    private int subDivisionId;

    @ManagedProperty("#{cityService}")
    private CityService cityService;
    private List<SelectItem> cityItemList;
    private List<City> cityList;
    private String city;
    private int cityId;

    private String stateCode;
    private String englishName;
    private String nativeName;


    @PostConstruct
    public void init() {
        newState = new State();

        continentItemList = new ArrayList<>();
        continentItemList.add(new SelectItem(0, "Select One..."));
        continentList = continentService.getContinentList(10);
        for (Continent continent : continentList) {
            continentItemList.add(new SelectItem(continent.getContinentId(), continent.getEnglishName()));
        }

    }

    public void continentSelectionChanged() {
        countryItemList = new ArrayList<>();
        countryItemList.add(new SelectItem(0, "Select One..."));
        countryList = countryService.getCountryListByContinentId(continentId);
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }
    }

    public void countrySelectionChanged() {
        subDivisionItemList = new ArrayList<>();
        subDivisionItemList.add(new SelectItem(0, "Select One..."));
        subDivisionList = subDivisionService.getSubDivisionListByCountryId(countryId);
        for (SubDivision subDivision : subDivisionList) {
            subDivisionItemList.add(new SelectItem(subDivision.getSubDivisionId(), subDivision.getEnglishName()));
        }
    }

    public void subDivisionSelectionChanged() {
        cityItemList = new ArrayList<>();
        cityItemList.add(new SelectItem(0, "Select One..."));
        cityList = cityService.getCityListBySubDivisionId(subDivisionId);
        for (City city : cityList) {
            cityItemList.add(new SelectItem(city.getCityId(), city.getEnglishName()));
        }
    }


    public void insertState() {
        newState.setStateCode(stateCode);
        newState.setEnglishName(englishName);
        newState.setNativeName(nativeName);
        newState.setCityId(cityId);

        if (stateService.insertState(newState)) {
            FacesMessage msg = new FacesMessage("State added successfully", newState.getStateCode());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, State not added.", newState.getStateCode());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }


    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
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

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
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

    public List<SelectItem> getCountryItemList() {
        return countryItemList;
    }

    public void setCountryItemList(List<SelectItem> countryItemList) {
        this.countryItemList = countryItemList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public SubDivisionService getSubDivisionService() {
        return subDivisionService;
    }

    public void setSubDivisionService(SubDivisionService subDivisionService) {
        this.subDivisionService = subDivisionService;
    }

    public List<SelectItem> getSubDivisionItemList() {
        return subDivisionItemList;
    }

    public void setSubDivisionItemList(List<SelectItem> subDivisionItemList) {
        this.subDivisionItemList = subDivisionItemList;
    }

    public List<SubDivision> getSubDivisionList() {
        return subDivisionList;
    }

    public void setSubDivisionList(List<SubDivision> subDivisionList) {
        this.subDivisionList = subDivisionList;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public CityService getCityService() {
        return cityService;
    }

    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    public List<SelectItem> getCityItemList() {
        return cityItemList;
    }

    public void setCityItemList(List<SelectItem> cityItemList) {
        this.cityItemList = cityItemList;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getSubDivisionId() {
        return subDivisionId;
    }

    public void setSubDivisionId(int subDivisionId) {
        this.subDivisionId = subDivisionId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
