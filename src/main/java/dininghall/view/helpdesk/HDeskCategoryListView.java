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
package dininghall.view.helpdesk;

import dininghall.service.helpdesk.HDeskCategoryService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.helpdesk.HDeskCategory;
import dininghall.domain.helpdesk.HDeskCategoryVW;

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
public class HDeskCategoryListView implements Serializable {

    private List<HDeskCategoryVW> hDeskCategoryVWList;

    private HDeskCategoryVW selectedHDeskCategoryVW;

    private List<HDeskCategoryVW> filteredHDeskCategoryVWList;

    @ManagedProperty("#{hDeskCategoryService}")
    private HDeskCategoryService service;

    private List<SelectItem> categoryItemList;
    private List<HDeskCategory> categoryList;
    private int catId;
    private String category;

    @PostConstruct
    public void init() {
        hDeskCategoryVWList = service.getHDeskCategoryVWList(10);

        categoryItemList = new ArrayList<>();
        categoryItemList.add(new SelectItem(0, "Select One..."));
        categoryList = service.getHDeskCategorySubListByParentCatId(0);
        for (HDeskCategory hDeskCategory : categoryList) {
            categoryItemList.add(new SelectItem(hDeskCategory.getCatId(), hDeskCategory.getName()));
        }
    }

    public List<HDeskCategoryVW> getHDeskCategoryVWList() {
        return hDeskCategoryVWList;
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskCategoryVW hDeskCategoryVW = (HDeskCategoryVW) event.getObject();
        FacesMessage msg = new FacesMessage("HDeskCategoryVW Edited", hDeskCategoryVW.getCatName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        hDeskCategoryVW.setParentCatId(catId);

        HDeskCategory hdc = service.getHDeskCategoryByHDeskCategoryId(catId);

        if (service.updateHDeskCategory(hDeskCategoryVW)) {
            hDeskCategoryVW.setParentCatName(hdc.getName());
        }

    }

    public void onRowCancel(RowEditEvent event) {
        HDeskCategoryVW hDeskCategoryVW = (HDeskCategoryVW) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", hDeskCategoryVW.getCatName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteHDeskCategory(int catId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskCategory hDeskCategory = service.getHDeskCategoryByHDeskCategoryId(catId);
        if (hDeskCategory == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskCategory not found!"));
            return;
        }

        if (service.deleteHDeskCategory(hDeskCategory)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskCategory deleted successfully."));
            hDeskCategoryVWList = service.getHDeskCategoryVWList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskCategory not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskCategoryVW Selected", ((HDeskCategoryVW) event.getObject()).getCatName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskCategory Unselected", ((HDeskCategory) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<HDeskCategoryVW> getFilteredHDeskCategoryVWList() {
        return filteredHDeskCategoryVWList;
    }

    public void setFilteredHDeskCategoryVWList(List<HDeskCategoryVW> filteredHDeskCategoryVWList) {
        this.filteredHDeskCategoryVWList = filteredHDeskCategoryVWList;
    }

    public void setService(HDeskCategoryService service) {
        this.service = service;
    }

    public HDeskCategoryVW getSelectedHDeskCategoryVW() {
        return selectedHDeskCategoryVW;
    }

    public void setSelectedHDeskCategoryVW(HDeskCategoryVW selectedHDeskCategoryVW) {
        this.selectedHDeskCategoryVW = selectedHDeskCategoryVW;
    }


    public void setHDeskCategoryVWList(List<HDeskCategoryVW> hDeskCategoryVWList) {
        this.hDeskCategoryVWList = hDeskCategoryVWList;
    }

    public List<SelectItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<SelectItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public List<HDeskCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<HDeskCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
