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
import dininghall.service.info.CityService;
import dininghall.service.info.ContinentService;
import dininghall.service.info.CountryService;
import dininghall.service.info.SubDivisionService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
//import uk.co.digiturk.domain.info.*;

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
import java.util.Map;


@ManagedBean
@ViewScoped
public class CityListView implements Serializable {

    private List<CityVW> cityVWList;

    private CityVW selectedCityVW;

    private List<CityVW> filteredCityVWList;

    @ManagedProperty("#{cityService}")
    private CityService service;

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


    @PostConstruct
    public void init() {
        cityVWList = service.getCityVWList(10);
    }

    public List<CityVW> getCityVWList() {
        return cityVWList;
    }

    public void onRowEdit(RowEditEvent event) {
        CityVW cityVW = (CityVW) event.getObject();
        FacesMessage msg = new FacesMessage("City Edited", cityVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateCity(cityVW);
    }

    public void onRowCancel(RowEditEvent event) {
        CityVW cityVW = (CityVW) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", cityVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showInfo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();

        selectedCityVW = service.getCityVWByCityId(Integer.parseInt(map.get("cityId")));

        continentItemList = new ArrayList<>();
        continentItemList.add(new SelectItem(0, "Select One..."));
        continentList = continentService.getContinentList(10);
        for (Continent continent : continentList) {
            continentItemList.add(new SelectItem(continent.getContinentId(), continent.getEnglishName()));
        }

        countryItemList = new ArrayList<>();
        countryItemList.add(new SelectItem(0, "Select One..."));
        countryList = countryService.getCountryListByContinentId(selectedCityVW.getContinentId());
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }

        subDivisionItemList = new ArrayList<>();
        subDivisionItemList.add(new SelectItem(0, "Select One..."));
        subDivisionList = subDivisionService.getSubDivisionListByCountryId(selectedCityVW.getCountryId());
        for (SubDivision subDivision : subDivisionList) {
            subDivisionItemList.add(new SelectItem(subDivision.getSubDivisionId(), subDivision.getEnglishName()));
        }

    }

    public void updateInfo() {
        if (service.updateCity(selectedCityVW)) {
            FacesMessage msg = new FacesMessage("Edit Successful", selectedCityVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            cityVWList = service.getCityVWList(10);

        } else {
            FacesMessage msg = new FacesMessage("Edit Not Successful", selectedCityVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void deleteCity(int cityId) {
        FacesContext context = FacesContext.getCurrentInstance();

        City city = service.getCityByCityId(cityId);
        if (city == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "City not found!"));
            return;
        }

        if (service.deleteCity(city)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "City deleted successfully."));
            cityVWList = service.getCityVWList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, city not deleted."));
        }

    }

    public void continentSelectionChanged() {
        countryItemList = new ArrayList<>();
        countryItemList.add(new SelectItem(0, "Select One..."));
        countryList = countryService.getCountryListByContinentId(selectedCityVW.getContinentId());
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }

        selectedCityVW.setCountryId(0);
        selectedCityVW.setSubDivisionId(0);

        countrySelectionChanged();
        subDivisionSelectionChanged();

    }

    public void countrySelectionChanged() {
        subDivisionItemList = new ArrayList<>();
        subDivisionItemList.add(new SelectItem(0, "Select One..."));
        subDivisionList = subDivisionService.getSubDivisionListByCountryId(selectedCityVW.getCountryId());
        for (SubDivision subDivision : subDivisionList) {
            subDivisionItemList.add(new SelectItem(subDivision.getSubDivisionId(), subDivision.getEnglishName()));
        }

        selectedCityVW.setSubDivisionId(0);

    }

    public void subDivisionSelectionChanged() {
        System.out.println("New value: " + selectedCityVW.getSubDivisionId());
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("City Selected", ((City) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("City Unselected", ((City) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<CityVW> getFilteredCityVWList() {
        return filteredCityVWList;
    }

    public void setFilteredCityVWList(List<CityVW> filteredCityVWList) {
        this.filteredCityVWList = filteredCityVWList;
    }

    public void setService(CityService service) {
        this.service = service;
    }

    public CityVW getSelectedCityVW() {
        return selectedCityVW;
    }

    public void setSelectedCityVW(CityVW selectedCityVW) {
        this.selectedCityVW = selectedCityVW;
    }

    public void setCityVWList(List<CityVW> cityVWList) {
        this.cityVWList = cityVWList;
    }

    public CityService getService() {
        return service;
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

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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

    public int getSubDivisionId() {
        return subDivisionId;
    }

    public void setSubDivisionId(int subDivisionId) {
        this.subDivisionId = subDivisionId;
    }


}
