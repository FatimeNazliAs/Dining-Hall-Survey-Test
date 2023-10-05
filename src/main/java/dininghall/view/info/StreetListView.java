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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
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
import java.util.Map;


@ManagedBean
@ViewScoped
public class StreetListView implements Serializable {

    private List<StreetVW> streetVWList;

    private StreetVW selectedStreetVW;

    private List<StreetVW> filteredStreetVWList;

    @ManagedProperty("#{streetService}")
    private StreetService service;

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

    @ManagedProperty("#{stateService}")
    private StateService stateService;
    private List<SelectItem> stateItemList;
    private List<State> stateList;
    private String state;
    private int stateId;


    @PostConstruct
    public void init() {
        streetVWList = service.getStreetVWList(10);
    }

    public List<StreetVW> getStreetVWList() {
        return streetVWList;
    }

    public void onRowEdit(RowEditEvent event) {
        StreetVW streetVW = (StreetVW) event.getObject();
        FacesMessage msg = new FacesMessage("Street Edited", streetVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateStreet(streetVW);
    }

    public void onRowCancel(RowEditEvent event) {
        StreetVW streetVW = (StreetVW) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", streetVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showInfo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();

        selectedStreetVW = service.getStreetVWByStreetId(Integer.parseInt(map.get("streetId")));

        continentItemList = new ArrayList<>();
        continentItemList.add(new SelectItem(0, "Select One..."));
        continentList = continentService.getContinentList(10);
        for (Continent continent : continentList) {
            continentItemList.add(new SelectItem(continent.getContinentId(), continent.getEnglishName()));
        }

        countryItemList = new ArrayList<>();
        countryItemList.add(new SelectItem(0, "Select One..."));
        countryList = countryService.getCountryListByContinentId(selectedStreetVW.getContinentId());
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }

        subDivisionItemList = new ArrayList<>();
        subDivisionItemList.add(new SelectItem(0, "Select One..."));
        subDivisionList = subDivisionService.getSubDivisionListByCountryId(selectedStreetVW.getCountryId());
        for (SubDivision subDivision : subDivisionList) {
            subDivisionItemList.add(new SelectItem(subDivision.getSubDivisionId(), subDivision.getEnglishName()));
        }

        cityItemList = new ArrayList<>();
        cityItemList.add(new SelectItem(0, "Select One..."));
        cityList = cityService.getCityListBySubDivisionId(selectedStreetVW.getSubDivisionId());
        for (City city : cityList) {
            cityItemList.add(new SelectItem(city.getCityId(), city.getEnglishName()));
        }

        stateItemList = new ArrayList<>();
        stateItemList.add(new SelectItem(0, "Select One..."));
        stateList = stateService.getStateListByCityId(selectedStreetVW.getCityId());
        for (State state : stateList) {
            stateItemList.add(new SelectItem(state.getStateId(), state.getEnglishName()));
        }
    }

    public void updateInfo() {
        if (service.updateStreet(selectedStreetVW)) {
            FacesMessage msg = new FacesMessage("Edit Successful", selectedStreetVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            streetVWList = service.getStreetVWList(10);

        }

    }

    public void deleteStreet(int streetId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Street street = service.getStreetByStreetId(streetId);
        if (street == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Street not found!"));
            return;
        }

        if (service.deleteStreet(street)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Street deleted successfully."));
            streetVWList = service.getStreetVWList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, street not deleted."));
        }

    }

    public void continentSelectionChanged() {
        countryItemList = new ArrayList<>();
        countryItemList.add(new SelectItem(0, "Select One..."));
        countryList = countryService.getCountryListByContinentId(selectedStreetVW.getContinentId());
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }

        selectedStreetVW.setCountryId(0);
        selectedStreetVW.setSubDivisionId(0);
        selectedStreetVW.setCityId(0);
        selectedStreetVW.setStateId(0);

        countrySelectionChanged();
        subDivisionSelectionChanged();
        citySelectionChanged();
    }

    public void countrySelectionChanged() {
        subDivisionItemList = new ArrayList<>();
        subDivisionItemList.add(new SelectItem(0, "Select One..."));
        subDivisionList = subDivisionService.getSubDivisionListByCountryId(selectedStreetVW.getCountryId());
        for (SubDivision subDivision : subDivisionList) {
            subDivisionItemList.add(new SelectItem(subDivision.getSubDivisionId(), subDivision.getEnglishName()));
        }

        selectedStreetVW.setSubDivisionId(0);
        selectedStreetVW.setCityId(0);
        selectedStreetVW.setStateId(0);

        subDivisionSelectionChanged();
        citySelectionChanged();
    }

    public void subDivisionSelectionChanged() {
        cityItemList = new ArrayList<>();
        cityItemList.add(new SelectItem(0, "Select One..."));
        cityList = cityService.getCityListBySubDivisionId(selectedStreetVW.getSubDivisionId());
        for (City city : cityList) {
            cityItemList.add(new SelectItem(city.getCityId(), city.getEnglishName()));
        }

        selectedStreetVW.setCityId(0);
        selectedStreetVW.setStateId(0);

        citySelectionChanged();
    }

    public void citySelectionChanged() {
        stateItemList = new ArrayList<>();
        stateItemList.add(new SelectItem(0, "Select One..."));
        stateList = stateService.getStateListByCityId(selectedStreetVW.getCityId());
        for (State state : stateList) {
            stateItemList.add(new SelectItem(state.getStateId(), state.getEnglishName()));
        }

        selectedStreetVW.setStateId(0);
    }


    public void stateSelectionChanged() {
        System.out.println("New value: " + selectedStreetVW.getStateId());
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Street Selected", ((Street) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Street Unselected", ((Street) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<StreetVW> getFilteredStreetVWList() {
        return filteredStreetVWList;
    }

    public void setFilteredStreetVWList(List<StreetVW> filteredStreetVWList) {
        this.filteredStreetVWList = filteredStreetVWList;
    }

    public void setService(StreetService service) {
        this.service = service;
    }

    public StreetVW getSelectedStreetVW() {
        return selectedStreetVW;
    }

    public void setSelectedStreetVW(StreetVW selectedStreetVW) {
        this.selectedStreetVW = selectedStreetVW;
    }

    public void setStreetVWList(List<StreetVW> streetVWList) {
        this.streetVWList = streetVWList;
    }

    public StreetService getService() {
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public List<SelectItem> getStateItemList() {
        return stateItemList;
    }

    public void setStateItemList(List<SelectItem> stateItemList) {
        this.stateItemList = stateItemList;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
}
