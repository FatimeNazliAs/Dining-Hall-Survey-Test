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
import dininghall.lazydomain.helpdesk.LazyHDeskCaseDataModel;
import dininghall.service.helpdesk.*;
import dininghall.service.user.LocalUserService;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import dininghall.common.MenuUtil;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserSession;
//import uk.co.digiturk.service.helpdesk.*;

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


@ManagedBean(name = "hDeskCaseListView")
@ViewScoped
public class HDeskCaseListView implements Serializable {
    @ManagedProperty("#{localUserService}")
    private LocalUserService localUserService;
    private List<String> creatorUserItemListStr;
    private List<SelectItem> assignedUserItemList;
    private List<String> assignedUserItemListStr;
    private List<LocalUser> assignedUserList;
    private long assignedUserId;
    private String assignedUser;

    @ManagedProperty("#{hDeskCategoryService}")
    private HDeskCategoryService categoryService;
    private List<SelectItem> categoryItemList;
    private List<HDeskCategory> categoryList;
    private int catId;
    private String category;

    private List<SelectItem> subCategoryItemList;
    private List<HDeskCategory> subCategoryList;
    private int subCatId;
    private String subCategory;

    @ManagedProperty("#{hDeskPriorityService}")
    private HDeskPriorityService priorityService;
    private List<SelectItem> priorityItemList;
    private List<String> priorityItemListStr;
    private List<HDeskPriority> priorityList;
    private int priorityId;
    private String priority;

    @ManagedProperty("#{hDeskImpactService}")
    private HDeskImpactService impactService;
    private List<SelectItem> impactItemList;
    private List<String> impactItemListStr;
    private List<HDeskImpact> impactList;
    private int impactId;
    private String impactVia;

    @ManagedProperty("#{hDeskReportedViaService}")
    private HDeskReportedViaService reportedViaService;
    private List<SelectItem> reportedViaItemList;
    private List<String> reportedViaItemListStr;
    private List<HDeskReportedVia> reportedViaList;
    private int reportedViaId;
    private String reportedVia;

    @ManagedProperty("#{hDeskStatusService}")
    private HDeskStatusService statusService;
    private List<SelectItem> statusItemList;
    private List<String> statusItemListStr;
    private List<HDeskStatus> statusList;
    private int statusId;
    private String status;

    @ManagedProperty("#{hDeskScoreService}")
    private HDeskScoreService scoreService;
    private List<SelectItem> scoreItemList;
    private List<String> scoreItemListStr;
    private List<HDeskScore> scoreList;
    private int scoreId;
    private String score;

    private MenuUtil menuUtil = new MenuUtil();

    private LazyDataModel<HDeskCaseVW> lazyHDeskCaseVWDataModel;

    private int pageSize = 20;

    private HDeskCaseVW selectedHDeskCaseVW;

    private List<HDeskCaseVW> filteredHDeskCaseVWList;

    @ManagedProperty("#{hDeskCaseService}")
    private HDeskCaseService hDeskCaseService;

    @PostConstruct
    public void init() {
        lazyHDeskCaseVWDataModel = new LazyHDeskCaseDataModel(hDeskCaseService.getHDeskCaseVWList(0, pageSize, null, null));

        creatorUserItemListStr = new ArrayList<>();
        creatorUserItemListStr.add("");

        assignedUserItemList = new ArrayList<>();
        assignedUserItemListStr = new ArrayList<>();
        assignedUserItemListStr.add("");
        assignedUserItemList.add(new SelectItem(0, "Select One..."));
        assignedUserList = localUserService.getLocalUsers(100);
        for (LocalUser localUser : assignedUserList) {
            creatorUserItemListStr.add(localUser.getFirstName() + " " + localUser.getLastName());
            assignedUserItemListStr.add(localUser.getFirstName() + " " + localUser.getLastName());
            assignedUserItemList.add(new SelectItem(localUser.getLocalUserId(), localUser.getFirstName() + " " + localUser.getLastName()));
        }

        categoryItemList = new ArrayList<>();
        categoryItemList.add(new SelectItem(0, "Select One..."));
        categoryList = categoryService.getHDeskCategorySubListByParentCatId(0);
        for (HDeskCategory hDeskCategory : categoryList) {
            categoryItemList.add(new SelectItem(hDeskCategory.getCatId(), hDeskCategory.getName()));
        }

        priorityItemList = new ArrayList<>();
        priorityItemListStr = new ArrayList<>();
        priorityItemListStr.add("");
        priorityList = priorityService.getHDeskPriorityList(100);
        for (HDeskPriority hDeskPriority : priorityList) {
            priorityItemListStr.add(hDeskPriority.getPriority());
            priorityItemList.add(new SelectItem(hDeskPriority.getPriorityId(), hDeskPriority.getPriority()));
        }

        impactItemList = new ArrayList<>();
        impactItemListStr = new ArrayList<>();
        impactItemListStr.add("");
        impactList = impactService.getHDeskImpactList(100);
        for (HDeskImpact hDeskImpact : impactList) {
            impactItemListStr.add(hDeskImpact.getImpact());
            impactItemList.add(new SelectItem(hDeskImpact.getImpactId(), hDeskImpact.getImpact()));
        }

        reportedViaItemList = new ArrayList<>();
        reportedViaItemListStr = new ArrayList<>();
        reportedViaItemListStr.add("");
        reportedViaList = reportedViaService.getHDeskReportedViaList(100);
        for (HDeskReportedVia hDeskReportedVia : reportedViaList) {
            reportedViaItemListStr.add(hDeskReportedVia.getReportedVia());
            reportedViaItemList.add(new SelectItem(hDeskReportedVia.getReportedViaId(), hDeskReportedVia.getReportedVia()));
        }

        statusItemList = new ArrayList<>();
        statusItemListStr = new ArrayList<>();
        statusItemListStr.add("");
        statusList = statusService.getHDeskStatusList(100);
        for (HDeskStatus hDeskStatus : statusList) {
            statusItemListStr.add(hDeskStatus.getStatus());
            statusItemList.add(new SelectItem(hDeskStatus.getStatusId(), hDeskStatus.getStatus()));
        }

        scoreItemList = new ArrayList<>();
        scoreItemListStr = new ArrayList<>();
        scoreItemListStr.add("");
        scoreItemList.add(new SelectItem(0, "Select One..."));
        scoreList = scoreService.getHDeskScoreList(100);
        for (HDeskScore hDeskScore : scoreList) {
            scoreItemListStr.add(hDeskScore.getScore());
            scoreItemList.add(new SelectItem(hDeskScore.getScoreId(), hDeskScore.getScore()));
        }
    }

    public List<SelectItem> initSubCategoryItemList(int catId) {
        List<SelectItem> subCategoryItemList = new ArrayList<>();
        List<HDeskCategory> subCategoryList = new ArrayList<>();
        subCategoryItemList.add(new SelectItem(0, "Select One..."));
        subCategoryList = categoryService.getHDeskCategorySubListByParentCatId(catId);
        for (HDeskCategory hDeskCategory : subCategoryList) {
            subCategoryItemList
                    .add(new SelectItem(hDeskCategory.getCatId(), hDeskCategory.getName()));
        }

        return subCategoryItemList;
    }

    public void updateInfo(long caseId) {
        selectedHDeskCaseVW = hDeskCaseService.getHDeskCaseVWByHDeskCaseId(caseId);
    }

    public void rowEditInit(RowEditEvent event) {
        selectedHDeskCaseVW = (HDeskCaseVW) event.getObject();
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskCaseVW hDeskCaseVW = (HDeskCaseVW) event.getObject();

        LocalUserSession localUserSession = menuUtil.getLocalUserSession();

        hDeskCaseVW.setLastModifiedByUserId(localUserSession.getUserId());

        if (hDeskCaseService.updateHDeskCase(hDeskCaseVW)) {
            FacesMessage msg = new FacesMessage("HDeskCase Edited", hDeskCaseVW.getSubject());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:caseTable");
            dataTable.loadLazyData();

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "HDeskCase Not Updated", hDeskCaseVW.getDescription());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        HDeskCaseVW hDeskCaseVW = (HDeskCaseVW) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Edit Cancelled", hDeskCaseVW.getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void deleteCase(long hDeskCaseId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskCase hDeskCase = hDeskCaseService.getHDeskCaseByHDeskCaseId(hDeskCaseId);
        if (hDeskCase == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskCase not found!"));
            return;
        }

        if (hDeskCaseService.deleteHDeskCase(hDeskCase)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskCase deleted successfully."));

            lazyHDeskCaseVWDataModel = new LazyHDeskCaseDataModel(hDeskCaseService.getHDeskCaseVWList(0, pageSize, null, null));


        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskCase not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        HDeskCaseVW hDeskCaseVW = (HDeskCaseVW) event.getObject();

        DataTable uiDataTable = (DataTable) event.getComponent();

        long rowIndex = 0;//lazyHDeskCaseVWDataModel.indexOf(hDeskCaseVW);

        subCategoryItemList = new ArrayList<>();
        subCategoryList = categoryService.getHDeskCategorySubListByParentCatId(hDeskCaseVW.getCatId());
        subCatId = hDeskCaseVW.getSubCatId();
        for (HDeskCategory sub : subCategoryList) {
            subCategoryItemList.add(new SelectItem(sub.getCatId(), sub.getName()));
        }

        PrimeFaces.current().ajax().update(":form:caseTable:subCategory");

        FacesMessage msg = new FacesMessage("HDeskCase Selected", hDeskCaseVW.getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskCase Unselected", ((HDeskCaseVW) event.getObject()).getDescription());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void categorySelectionChanged() {
        subCategoryItemList = new ArrayList<>();
        subCategoryItemList.add(new SelectItem(0, "Select One..."));
        subCategoryList = categoryService.getHDeskCategorySubListByParentCatId(selectedHDeskCaseVW.getCatId());
        for (HDeskCategory sub : subCategoryList) {
            subCategoryItemList.add(new SelectItem(sub.getCatId(), sub.getName()));
        }
    }


    public HDeskCaseVW getSelectedHDeskCaseVW() {
        return selectedHDeskCaseVW;
    }

    public void setSelectedHDeskCaseVW(HDeskCaseVW selectedHDeskCaseVW) {
        this.selectedHDeskCaseVW = selectedHDeskCaseVW;
    }


    public LocalUserService getLocalUserService() {
        return localUserService;
    }

    public void setLocalUserService(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }

    public List<SelectItem> getAssignedUserItemList() {
        return assignedUserItemList;
    }

    public void setAssignedUserItemList(List<SelectItem> assignedUserItemList) {
        this.assignedUserItemList = assignedUserItemList;
    }

    public List<LocalUser> getAssignedUserList() {
        return assignedUserList;
    }

    public void setAssignedUserList(List<LocalUser> assignedUserList) {
        this.assignedUserList = assignedUserList;
    }

    public long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public MenuUtil getMenuUtil() {
        return menuUtil;
    }

    public void setMenuUtil(MenuUtil menuUtil) {
        this.menuUtil = menuUtil;
    }

    public HDeskStatusService getStatusService() {
        return statusService;
    }

    public void setStatusService(HDeskStatusService statusService) {
        this.statusService = statusService;
    }

    public List<SelectItem> getStatusItemList() {
        return statusItemList;
    }

    public void setStatusItemList(List<SelectItem> statusItemList) {
        this.statusItemList = statusItemList;
    }

    public List<HDeskStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<HDeskStatus> statusList) {
        this.statusList = statusList;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HDeskCategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(HDeskCategoryService categoryService) {
        this.categoryService = categoryService;
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

    public HDeskPriorityService getPriorityService() {
        return priorityService;
    }

    public void setPriorityService(HDeskPriorityService priorityService) {
        this.priorityService = priorityService;
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

    public HDeskImpactService getImpactService() {
        return impactService;
    }

    public void setImpactService(HDeskImpactService impactService) {
        this.impactService = impactService;
    }

    public List<SelectItem> getImpactItemList() {
        return impactItemList;
    }

    public void setImpactItemList(List<SelectItem> impactItemList) {
        this.impactItemList = impactItemList;
    }

    public List<HDeskImpact> getImpactList() {
        return impactList;
    }

    public void setImpactList(List<HDeskImpact> impactList) {
        this.impactList = impactList;
    }

    public int getImpactId() {
        return impactId;
    }

    public void setImpactId(int impactId) {
        this.impactId = impactId;
    }

    public String getImpactVia() {
        return impactVia;
    }

    public void setImpactVia(String impactVia) {
        this.impactVia = impactVia;
    }

    public HDeskReportedViaService getReportedViaService() {
        return reportedViaService;
    }

    public void setReportedViaService(HDeskReportedViaService reportedViaService) {
        this.reportedViaService = reportedViaService;
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

    public HDeskScoreService getScoreService() {
        return scoreService;
    }

    public void setScoreService(HDeskScoreService scoreService) {
        this.scoreService = scoreService;
    }

    public List<SelectItem> getScoreItemList() {
        return scoreItemList;
    }

    public void setScoreItemList(List<SelectItem> scoreItemList) {
        this.scoreItemList = scoreItemList;
    }

    public List<HDeskScore> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<HDeskScore> scoreList) {
        this.scoreList = scoreList;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public HDeskCaseService gethDeskCaseService() {
        return hDeskCaseService;
    }

    public void sethDeskCaseService(HDeskCaseService hDeskCaseService) {
        this.hDeskCaseService = hDeskCaseService;
    }

    public LazyDataModel<HDeskCaseVW> getLazyHDeskCaseVWDataModel() {
        return lazyHDeskCaseVWDataModel;
    }

    public void setLazyHDeskCaseVWDataModel(LazyDataModel<HDeskCaseVW> lazyHDeskCaseVWDataModel) {
        this.lazyHDeskCaseVWDataModel = lazyHDeskCaseVWDataModel;
    }

    public List<String> getStatusItemListStr() {
        return statusItemListStr;
    }

    public void setStatusItemListStr(List<String> statusItemListStr) {
        this.statusItemListStr = statusItemListStr;
    }

    public List<String> getReportedViaItemListStr() {
        return reportedViaItemListStr;
    }

    public void setReportedViaItemListStr(List<String> reportedViaItemListStr) {
        this.reportedViaItemListStr = reportedViaItemListStr;
    }

    public List<String> getAssignedUserItemListStr() {
        return assignedUserItemListStr;
    }

    public void setAssignedUserItemListStr(List<String> assignedUserItemListStr) {
        this.assignedUserItemListStr = assignedUserItemListStr;
    }

    public List<String> getPriorityItemListStr() {
        return priorityItemListStr;
    }

    public void setPriorityItemListStr(List<String> priorityItemListStr) {
        this.priorityItemListStr = priorityItemListStr;
    }

    public List<String> getImpactItemListStr() {
        return impactItemListStr;
    }

    public void setImpactItemListStr(List<String> impactItemListStr) {
        this.impactItemListStr = impactItemListStr;
    }

    public List<String> getScoreItemListStr() {
        return scoreItemListStr;
    }

    public void setScoreItemListStr(List<String> scoreItemListStr) {
        this.scoreItemListStr = scoreItemListStr;
    }

    public List<String> getCreatorUserItemListStr() {
        return creatorUserItemListStr;
    }

    public void setCreatorUserItemListStr(List<String> creatorUserItemListStr) {
        this.creatorUserItemListStr = creatorUserItemListStr;
    }
}
