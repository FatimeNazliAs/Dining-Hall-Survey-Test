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
import dininghall.service.info.SubDivisionService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.info.Continent;
import dininghall.domain.info.Country;
import dininghall.domain.info.SubDivision;
import dininghall.domain.info.SubDivisionVW;

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
public class SubDivisionListView implements Serializable {

    private List<SubDivisionVW> subDivisionVWList;

    private SubDivisionVW selectedSubDivisionVW;

    private List<SubDivisionVW> filteredSubDivisionVWList;

    @ManagedProperty("#{subDivisionService}")
    private SubDivisionService service;

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


    @PostConstruct
    public void init() {
        subDivisionVWList = service.getSubDivisionVWList(10);
    }

    public List<SubDivisionVW> getSubDivisionVWList() {
        return subDivisionVWList;
    }

    public void onRowEdit(RowEditEvent event) {
        SubDivisionVW subDivisionVW = (SubDivisionVW) event.getObject();
        FacesMessage msg = new FacesMessage("SubDivision Edited", subDivisionVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateSubDivision(subDivisionVW);
    }

    public void onRowCancel(RowEditEvent event) {
        SubDivisionVW subDivisionVW = (SubDivisionVW) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", subDivisionVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showInfo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();

        selectedSubDivisionVW = service.getSubDivisionVWBySubDivisionId(Integer.parseInt(map.get("subDivisionId")));

        continentItemList = new ArrayList<>();
        continentItemList.add(new SelectItem(0, "Select One..."));
        continentList = continentService.getContinentList(10);
        for (Continent continent : continentList) {
            continentItemList.add(new SelectItem(continent.getContinentId(), continent.getEnglishName()));
        }

        countryItemList = new ArrayList<>();
        countryItemList.add(new SelectItem(0, "Select One..."));
        countryList = countryService.getCountryListByContinentId(selectedSubDivisionVW.getContinentId());
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }


    }

    public void updateInfo() {
        if (service.updateSubDivision(selectedSubDivisionVW)) {
            FacesMessage msg = new FacesMessage("Edit Successful", selectedSubDivisionVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            subDivisionVWList = service.getSubDivisionVWList(10);

        } else {
            FacesMessage msg = new FacesMessage("Edit Not Successful", selectedSubDivisionVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void deleteSubDivision(int subDivisionId) {
        FacesContext context = FacesContext.getCurrentInstance();

        SubDivision subDivision = service.getSubDivisionBySubDivisionId(subDivisionId);
        if (subDivision == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "SubDivision not found!"));
            return;
        }

        if (service.deleteSubDivision(subDivision)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "SubDivision deleted successfully."));
            subDivisionVWList = service.getSubDivisionVWList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, subDivision not deleted."));
        }

    }

    public void continentSelectionChanged() {
        countryItemList = new ArrayList<>();
        countryItemList.add(new SelectItem(0, "Select One..."));
        countryList = countryService.getCountryListByContinentId(selectedSubDivisionVW.getContinentId());
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }

        selectedSubDivisionVW.setCountryId(0);

    }

    public void countrySelectionChanged() {
        System.out.println("New value: " + selectedSubDivisionVW.getSubDivisionId());
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("SubDivision Selected", ((SubDivision) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("SubDivision Unselected", ((SubDivision) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<SubDivisionVW> getFilteredSubDivisionVWList() {
        return filteredSubDivisionVWList;
    }

    public void setFilteredSubDivisionVWList(List<SubDivisionVW> filteredSubDivisionVWList) {
        this.filteredSubDivisionVWList = filteredSubDivisionVWList;
    }

    public void setService(SubDivisionService service) {
        this.service = service;
    }

    public SubDivisionVW getSelectedSubDivisionVW() {
        return selectedSubDivisionVW;
    }

    public void setSelectedSubDivisionVW(SubDivisionVW selectedSubDivisionVW) {
        this.selectedSubDivisionVW = selectedSubDivisionVW;
    }

    public void setSubDivisionVWList(List<SubDivisionVW> subDivisionVWList) {
        this.subDivisionVWList = subDivisionVWList;
    }

    public SubDivisionService getService() {
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


}
