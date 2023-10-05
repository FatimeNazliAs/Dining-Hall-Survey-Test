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

import dininghall.domain.helpdesk.*;
import dininghall.service.helpdesk.*;
import dininghall.common.MenuUtil;
import dininghall.domain.user.LocalUserSession;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ManagedBean(name = "hDeskCaseInsertView")
@ViewScoped
public class HDeskCaseInsertView implements Serializable {
    @ManagedProperty("#{hDeskCaseService}")
    private HDeskCaseService service;

    @ManagedProperty("#{hDeskCategoryService}")
    private HDeskCategoryService hDeskCategoryService;

    @ManagedProperty("#{hDeskImpactService}")
    private HDeskImpactService hDeskImpactService;

    @ManagedProperty("#{hDeskPriorityService}")
    private HDeskPriorityService hDeskPriorityService;

    @ManagedProperty("#{hDeskReportedViaService}")
    private HDeskReportedViaService hDeskReportedViaService;

    private MenuUtil menuUtil = new MenuUtil();


    private HDeskCase newHDeskCase;

    private long caseId;
    private String subject;
    private String description;

    private long creatorUserId;
    private String creatorUser;

    private List<SelectItem> categoryItemList;
    private List<HDeskCategory> categoryList;
    private int catId;
    private String category;

    private List<SelectItem> subCategoryItemList;
    private List<HDeskCategory> subCategoryList;
    private int subCatId;
    private String subCategory;

    private List<SelectItem> reportedViaItemList;
    private List<HDeskReportedVia> reportedViaList;
    private int reportedViaId;
    private String reportedVia;

    private List<SelectItem> priorityItemList;
    private List<HDeskPriority> priorityList;
    private int priorityId;
    private String priority;

    private List<SelectItem> impactItemList;
    private List<HDeskImpact> impactList;
    private int impactId;
    private String impact;


    @PostConstruct
    public void init() {
        newHDeskCase = new HDeskCase();

        categoryItemList = new ArrayList<>();
        categoryItemList.add(new SelectItem(0, "Select One..."));
        categoryList = gethDeskCategoryService().getHDeskCategorySubListByParentCatId(0);
        for (HDeskCategory category : categoryList) {
            categoryItemList.add(new SelectItem(category.getCatId(), category.getName()));
        }

        subCategoryItemList = new ArrayList<>();
        subCategoryItemList.add(new SelectItem(0, "Select One..."));

        reportedViaItemList = new ArrayList<>();
        reportedViaItemList.add(new SelectItem(0, "Select One..."));
        reportedViaList = gethDeskReportedViaService().getHDeskReportedViaList(10);
        for (HDeskReportedVia reportedVia : reportedViaList) {
            reportedViaItemList.add(new SelectItem(reportedVia.getReportedViaId(), reportedVia.getReportedVia()));
        }

        priorityItemList = new ArrayList<>();
        priorityItemList.add(new SelectItem(0, "Select One..."));
        priorityList = gethDeskPriorityService().getHDeskPriorityList(10);
        for (HDeskPriority priority : priorityList) {
            priorityItemList.add(new SelectItem(priority.getPriorityId(), priority.getPriority()));
        }

        impactItemList = new ArrayList<>();
        impactItemList.add(new SelectItem(0, "Select One..."));
        setImpactList(gethDeskImpactService().getHDeskImpactList(10));
        for (HDeskImpact impact : getImpactList()) {
            impactItemList.add(new SelectItem(impact.getImpactId(), impact.getImpact()));
        }

    }

    public void insertCase() {
        LocalUserSession localUserSession = menuUtil.getLocalUserSession();

        newHDeskCase.setCreatorUserId(localUserSession.getUserId());
        newHDeskCase.setSubject(subject);
        newHDeskCase.setDescription(description);
        newHDeskCase.setCatId(catId);
        newHDeskCase.setSubCatId(subCatId);
        newHDeskCase.setReportedViaId(reportedViaId);
        newHDeskCase.setPriorityId(priorityId);
        newHDeskCase.setImpactId(impactId);
        newHDeskCase.setCreatedTime(new Date());

        if (service.insertHDeskCase(newHDeskCase)) {
            FacesMessage msg = new FacesMessage("HDeskCase added successfully", newHDeskCase.getDescription());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, hDeskCase not added.", newHDeskCase.getDescription());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCategoryChanged() {
        subCategoryItemList = new ArrayList<>();
        subCategoryList = gethDeskCategoryService().getHDeskCategorySubListByParentCatId(catId);
        for (HDeskCategory category : subCategoryList) {
            subCategoryItemList.add(new SelectItem(category.getCatId(), category.getName()));
        }
    }

    public void setService(HDeskCaseService service) {
        this.service = service;
    }

    public HDeskCase getNewHDeskCase() {
        return newHDeskCase;
    }

    public void setNewHDeskCase(HDeskCase newHDeskCase) {
        this.newHDeskCase = newHDeskCase;
    }


    public long getCaseId() {
        return caseId;
    }

    public void setCaseId(long caseId) {
        this.caseId = caseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(int subCatId) {
        this.subCatId = subCatId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(String creatorUser) {
        this.creatorUser = creatorUser;
    }


    public int getReportedViaId() {
        return reportedViaId;
    }

    public void setReportedViaId(int reportedViaId) {
        this.reportedViaId = reportedViaId;
    }

    public String getReportedVia() {
        return reportedVia;
    }

    public void setReportedVia(String reportedVia) {
        this.reportedVia = reportedVia;
    }


    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getImpactId() {
        return impactId;
    }

    public void setImpactId(int impactId) {
        this.impactId = impactId;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public HDeskCategoryService gethDeskCategoryService() {
        return hDeskCategoryService;
    }

    public void sethDeskCategoryService(HDeskCategoryService hDeskCategoryService) {
        this.hDeskCategoryService = hDeskCategoryService;
    }

    public List<SelectItem> getSubCategoryItemList() {
        return subCategoryItemList;
    }

    public void setSubCategoryItemList(List<SelectItem> subCategoryItemList) {
        this.subCategoryItemList = subCategoryItemList;
    }

    public List<HDeskCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<HDeskCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public HDeskReportedViaService gethDeskReportedViaService() {
        return hDeskReportedViaService;
    }

    public void sethDeskReportedViaService(HDeskReportedViaService hDeskReportedViaService) {
        this.hDeskReportedViaService = hDeskReportedViaService;
    }

    public HDeskImpactService gethDeskImpactService() {
        return hDeskImpactService;
    }

    public void sethDeskImpactService(HDeskImpactService hDeskImpactService) {
        this.hDeskImpactService = hDeskImpactService;
    }

    public HDeskPriorityService gethDeskPriorityService() {
        return hDeskPriorityService;
    }

    public void sethDeskPriorityService(HDeskPriorityService hDeskPriorityService) {
        this.hDeskPriorityService = hDeskPriorityService;
    }

    public List<HDeskImpact> getImpactList() {
        return impactList;
    }

    public void setImpactList(List<HDeskImpact> impactList) {
        this.impactList = impactList;
    }

    public List<SelectItem> getReportedViaItemList() {
        return reportedViaItemList;
    }

    public void setReportedViaItemList(List<SelectItem> reportedViaItemList) {
        this.reportedViaItemList = reportedViaItemList;
    }

    public List<HDeskReportedVia> getReportedViaList() {
        return reportedViaList;
    }

    public void setReportedViaList(List<HDeskReportedVia> reportedViaList) {
        this.reportedViaList = reportedViaList;
    }

    public List<SelectItem> getPriorityItemList() {
        return priorityItemList;
    }

    public void setPriorityItemList(List<SelectItem> priorityItemList) {
        this.priorityItemList = priorityItemList;
    }

    public List<HDeskPriority> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(List<HDeskPriority> priorityList) {
        this.priorityList = priorityList;
    }

    public List<SelectItem> getImpactItemList() {
        return impactItemList;
    }

    public void setImpactItemList(List<SelectItem> impactItemList) {
        this.impactItemList = impactItemList;
    }
}
