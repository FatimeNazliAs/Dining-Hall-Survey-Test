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
package dininghall.view.user;

import dininghall.generic.Manager.DbManager;
import dininghall.lazydomain.user.LazyLocalUserDataModel;
import dininghall.service.user.LocalUserService;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@ManagedBean(name = "localUserListView")
@ViewScoped
public class LocalUserListView implements Serializable {
    private static final long serialVersionUID = -3192521384166408966L;

    @ManagedProperty("#{localUserService}")
    private LocalUserService localUserService;
    private List<SelectItem> localUserItemList;
    private List<LocalUser> localUserList;
    private String localUser;
    private int localUserId;
    private LocalUser selectedLocalUser;

    private LazyDataModel<LocalUserVW> lazyLocalUserDataModel;

    private int pageSize = 15;

    private LocalUserVW selectedLocalUserVW;

    private List<LocalUserVW> selectedLocalUsers;

    @PostConstruct
    public void init() {
        lazyLocalUserDataModel =
                new LazyLocalUserDataModel(localUserService.getLocalUserVWList(0, pageSize, null, null), localUserService);

    }


    public void onRowEdit(RowEditEvent event) {
        LocalUserVW localUserVW = (LocalUserVW) event.getObject();

        FacesContext context = FacesContext.getCurrentInstance();
        if (localUserService.updateLocalUser(localUserVW)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Kullanıcı Hesabı başarıyla güncellendi."
                    + localUserVW.getUsername()));

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Kullanıcı Hesabı güncellenemedi."));
        }

    }

    public void onRowCancel(RowEditEvent event) {
        LocalUserVW localUserVW = (LocalUserVW) event.getObject();

        FacesMessage msg = new FacesMessage("Kullanıcı Hesabı Düzenleme iptal edildi.", localUserVW.getUsername());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void openNew() {
        this.selectedLocalUserVW = new LocalUserVW();
        this.selectedLocalUserVW.setLocalUserId(0);
    }

    public void insertLocalUser() {
        LocalUser es = new DbManager(LocalUser.class).GetFirst("email='" + this.selectedLocalUserVW.getEmail() + "'");
        if (es.getLocalUserId() > 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kullanıcı Hesabı Veritabanında Mevcut.",
                    selectedLocalUserVW.getEmail());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else if (this.selectedLocalUserVW.getLocalUserId() == 0) {
            this.selectedLocalUserVW.setRegisteredDate(new Date());

            if (localUserService.insertLocalUser(selectedLocalUserVW)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı Hesabı başarıyla eklendi.",
                        selectedLocalUserVW.getUsername());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                PrimeFaces.current().executeScript("PF('manageLocalUserDialog').hide()");

                PrimeFaces.current().ajax().update("form:messages", "form:localUserList");

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Kullanıcı Hesabı eklenemedi.",
                        selectedLocalUserVW.getUsername());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                PrimeFaces.current().executeScript("PF('manageLocalUserDialog').hide()");

                PrimeFaces.current().ajax().update("form:messages");
            }
        } else {

            if (localUserService.updateLocalUser(selectedLocalUserVW)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı Hesabı başarıyla güncellendi.",
                        selectedLocalUserVW.getUsername());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                PrimeFaces.current().executeScript("PF('manageLocalUserDialog').hide()");

                PrimeFaces.current().ajax().update("form:messages", "form:localUserList");

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Kullanıcı Hesabı güncellenemedi.",
                        selectedLocalUserVW.getUsername());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                PrimeFaces.current().executeScript("PF('manageLocalUserDialog').hide()");

                PrimeFaces.current().ajax().update("form:messages");
            }
        }


    }

    public void updateLocalUserPassword() {
        LocalUser localUser = localUserService.getLocalUserByLocalUserId(selectedLocalUserVW.getLocalUserId());

        if (localUser != null) {
            if (localUserService.updateLocalUserPassword(selectedLocalUserVW.getLocalUserId(), selectedLocalUserVW.getNewPassword())) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı Şifresi başarıyla güncellendi.",
                        selectedLocalUserVW.getUsername());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                PrimeFaces.current().executeScript("PF('managePasswordDialog').hide()");

                PrimeFaces.current().ajax().update("form:messages", "form:localUserList");
            }
        }
    }

    public void deleteLocalUser() {
        FacesContext context = FacesContext.getCurrentInstance();

        LocalUser localUser = localUserService.getLocalUserByLocalUserId(selectedLocalUserVW.getLocalUserId());
        if (localUser == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Kullanıcı Hesabı bulunamadı!"));

            return;
        }

        if (localUserService.deleteLocalUser(localUser)) {
            this.selectedLocalUsers = null;

            this.selectedLocalUserVW = null;

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Kullanıcı Hesabı başarıyla silindi."));

            PrimeFaces.current().ajax().update("form:messages", "form:localUserList", "form:delete-localUsers-button");

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Kullanıcı Hesabı silinemedi."));
        }

    }

    public void deleteSelectedLocalUsers() {
        for (LocalUserVW localUserVW : selectedLocalUsers) {
            LocalUser localUser = new LocalUser();
            localUser.setLocalUserId(localUserVW.getLocalUserId());

            localUserService.deleteLocalUser(localUser);
        }

        this.selectedLocalUsers = null;

        this.selectedLocalUserVW = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kullanıcı Hesabı silindi."));

        PrimeFaces.current().ajax().update("form:messages", "form:localUserList", "form:delete-localUsers-button");
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Kullanıcı Hesabı seçildi.", ((LocalUser) event.getObject()).getLocalUserId() + "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Kullanıcı Hesabı seçimi iptal edildi.", ((LocalUser) event.getObject()).getLocalUserId() + "");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public String getDeleteButtonMessage() {
        if (hasSelectedLocalUsers()) {
            int size = this.selectedLocalUsers.size();
            return size > 1 ? size + " Kullanıcı Hesabı seçili" : "1 Kullanıcı Hesabı seçili";
        }

        return "Sil";
    }

    public boolean hasSelectedLocalUsers() {
        return this.selectedLocalUsers != null && !this.selectedLocalUsers.isEmpty();
    }

    public void onRowToggle(ToggleEvent event) {
        if (event.getVisibility() == Visibility.VISIBLE) {
            LocalUserVW localUserVW = (LocalUserVW) event.getData();


        }
    }


    public LocalUserVW getSelectedLocalUserVW() {
        return selectedLocalUserVW;
    }

    public void setSelectedLocalUserVW(LocalUserVW selectedLocalUserVW) {
        this.selectedLocalUserVW = selectedLocalUserVW;
    }

    public LazyDataModel<LocalUserVW> getLazyLocalUserDataModel() {
        return lazyLocalUserDataModel;
    }

    public void setLazyLocalUserDataModel(LazyDataModel<LocalUserVW> lazyLocalUserDataModel) {
        this.lazyLocalUserDataModel = lazyLocalUserDataModel;
    }

    public LocalUserService getLocalUserService() {
        return localUserService;
    }

    public void setLocalUserService(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }

    public List<LocalUserVW> getSelectedLocalUsers() {
        return selectedLocalUsers;
    }

    public void setSelectedLocalUsers(List<LocalUserVW> selectedLocalUsers) {
        this.selectedLocalUsers = selectedLocalUsers;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<SelectItem> getLocalUserItemList() {
        return localUserItemList;
    }

    public void setLocalUserItemList(List<SelectItem> localUserItemList) {
        this.localUserItemList = localUserItemList;
    }

    public List<LocalUser> getLocalUserList() {
        return localUserList;
    }

    public void setLocalUserList(List<LocalUser> localUserList) {
        this.localUserList = localUserList;
    }

    public String getLocalUser() {
        return localUser;
    }

    public void setLocalUser(String localUser) {
        this.localUser = localUser;
    }

    public int getLocalUserId() {
        return localUserId;
    }

    public void setLocalUserId(int localUserId) {
        this.localUserId = localUserId;
    }

    public LocalUser getSelectedLocalUser() {
        return selectedLocalUser;
    }

    public void setSelectedLocalUser(LocalUser selectedLocalUser) {
        this.selectedLocalUser = selectedLocalUser;
    }


}
