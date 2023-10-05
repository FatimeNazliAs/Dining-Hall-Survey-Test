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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.info.Continent;
import dininghall.domain.info.ContinentVW;

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
public class ContinentListView implements Serializable {

    private List<ContinentVW> continentVWList;

    private ContinentVW selectedContinentVW;

    private List<ContinentVW> filteredContinentVWList;

    @ManagedProperty("#{continentService}")
    private ContinentService service;

    @ManagedProperty("#{continentService}")
    private ContinentService continentService;
    private List<SelectItem> continentItemList;
    private List<Continent> continentList;
    private String continent;
    private int continentId;


    @PostConstruct
    public void init() {
        continentVWList = service.getContinentVWList(10);
    }

    public List<ContinentVW> getContinentVWList() {
        return continentVWList;
    }

    public void onRowEdit(RowEditEvent event) {
        ContinentVW continentVW = (ContinentVW) event.getObject();
        FacesMessage msg = new FacesMessage("Continent Edited", continentVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateContinent(continentVW);
    }

    public void onRowCancel(RowEditEvent event) {
        ContinentVW continentVW = (ContinentVW) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", continentVW.getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showInfo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();

        selectedContinentVW = service.getContinentVWByContinentId(Integer.parseInt(map.get("continentId")));

        continentItemList = new ArrayList<>();
        continentItemList.add(new SelectItem(0, "Select One..."));
        continentList = continentService.getContinentList(10);
        for (Continent continent : continentList) {
            continentItemList.add(new SelectItem(continent.getContinentId(), continent.getEnglishName()));
        }

    }

    public void updateInfo() {
        if (service.updateContinent(selectedContinentVW)) {
            FacesMessage msg = new FacesMessage("Edit Successful", selectedContinentVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            continentVWList = service.getContinentVWList(10);

        } else {
            FacesMessage msg = new FacesMessage("Edit Not Successful", selectedContinentVW.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void deleteContinent(int continentId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Continent continent = service.getContinentByContinentId(continentId);
        if (continent == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Continent not found!"));
            return;
        }

        if (service.deleteContinent(continent)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Continent deleted successfully."));
            continentVWList = service.getContinentVWList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, continent not deleted."));
        }

    }

    public void continentSelectionChanged() {
        System.out.println("New value: " + selectedContinentVW.getContinentId());

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Continent Selected", ((Continent) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Continent Unselected", ((Continent) event.getObject()).getEnglishName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<ContinentVW> getFilteredContinentVWList() {
        return filteredContinentVWList;
    }

    public void setFilteredContinentVWList(List<ContinentVW> filteredContinentVWList) {
        this.filteredContinentVWList = filteredContinentVWList;
    }

    public void setService(ContinentService service) {
        this.service = service;
    }

    public ContinentVW getSelectedContinentVW() {
        return selectedContinentVW;
    }

    public void setSelectedContinentVW(ContinentVW selectedContinentVW) {
        this.selectedContinentVW = selectedContinentVW;
    }

    public void setContinentVWList(List<ContinentVW> continentVWList) {
        this.continentVWList = continentVWList;
    }

    public ContinentService getService() {
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
