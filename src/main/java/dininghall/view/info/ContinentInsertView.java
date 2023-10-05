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
import dininghall.domain.info.Continent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;


@ManagedBean
@ViewScoped
public class ContinentInsertView implements Serializable {
    private Continent newContinent;

    @ManagedProperty("#{continentService}")
    private ContinentService continentService;
    private List<SelectItem> continentItemList;
    private List<Continent> continentList;
    private String continent;

    private String continentCode;
    private String englishName;
    private String nativeName;


    @PostConstruct
    public void init() {
        newContinent = new Continent();
    }


    public void insertContinent() {
        newContinent.setContinentCode(continentCode);
        newContinent.setEnglishName(englishName);
        newContinent.setNativeName(nativeName);

        if (continentService.insertContinent(newContinent)) {
            FacesMessage msg = new FacesMessage("Continent added successfully", newContinent.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, Continent not added.", newContinent.getEnglishName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Continent getNewContinent() {
        return newContinent;
    }

    public void setNewContinent(Continent newContinent) {
        this.newContinent = newContinent;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
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


    public ContinentService getContinentService() {
        return continentService;
    }

    public void setContinentService(ContinentService continentService) {
        this.continentService = continentService;
    }


}
