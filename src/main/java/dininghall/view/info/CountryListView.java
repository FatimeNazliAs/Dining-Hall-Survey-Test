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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.info.Continent;
import dininghall.domain.info.Country;
import dininghall.domain.info.CountryVW;

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
public class CountryListView implements Serializable {

    private List<CountryVW> countryVWList;

    private CountryVW selectedCountryVW;

    private List<CountryVW> filteredCountryVWList;

    @ManagedProperty("#{countryService}")
    private CountryService service;

    @ManagedProperty("#{continentService}")
    private ContinentService continentService;
    private List<SelectItem> continentItemList;
    private List<Continent> continentList;
    private String continent;
    private int continentId;


    @PostConstruct
    public void init() {
        countryVWList = service.getCountryVWList(10);
    }

    public List<CountryVW> getCountryVWList() {
        return countryVWList;
    }

    public void onRowEdit(RowEditEvent event) {
        CountryVW countryVW = (CountryVW) event.getObject();
        FacesMessage msg = new FacesMessage("Country Edited", countryVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateCountry(countryVW);
    }

    public void onRowCancel(RowEditEvent event) {
        CountryVW countryVW = (CountryVW) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", countryVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showInfo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();

        selectedCountryVW = service.getCountryVWByCountryId(Integer.parseInt(map.get("countryId")));

        continentItemList = new ArrayList<>();
        continentItemList.add(new SelectItem(0, "Select One..."));
        continentList = continentService.getContinentList(10);
        for (Continent continent : continentList) {
            continentItemList.add(new SelectItem(continent.getContinentId(), continent.getEnglishName()));
        }

    }

    public void updateInfo() {
        if (service.updateCountry(selectedCountryVW)) {
            FacesMessage msg = new FacesMessage("Edit Successful", selectedCountryVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            countryVWList = service.getCountryVWList(10);

        } else {
            FacesMessage msg = new FacesMessage("Edit Not Successful", selectedCountryVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void deleteCountry(int countryId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Country country = service.getCountryByCountryId(countryId);
        if (country == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Country not found!"));
            return;
        }

        if (service.deleteCountry(country)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Country deleted successfully."));
            countryVWList = service.getCountryVWList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, country not deleted."));
        }

    }

    public void continentSelectionChanged() {
        System.out.println("New value: " + selectedCountryVW.getCountryId());

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Country Selected", ((Country) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Country Unselected", ((Country) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<CountryVW> getFilteredCountryVWList() {
        return filteredCountryVWList;
    }

    public void setFilteredCountryVWList(List<CountryVW> filteredCountryVWList) {
        this.filteredCountryVWList = filteredCountryVWList;
    }

    public void setService(CountryService service) {
        this.service = service;
    }

    public CountryVW getSelectedCountryVW() {
        return selectedCountryVW;
    }

    public void setSelectedCountryVW(CountryVW selectedCountryVW) {
        this.selectedCountryVW = selectedCountryVW;
    }

    public void setCountryVWList(List<CountryVW> countryVWList) {
        this.countryVWList = countryVWList;
    }

    public CountryService getService() {
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


}
