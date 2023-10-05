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
package dininghall.view.common;


import dininghall.lazydomain.common.LazyContactMessageDataModel;
import dininghall.service.common.ContactMessageService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import dininghall.domain.common.ContactMessage;
import dininghall.domain.common.ContactMessageVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


@ManagedBean(name = "contactMessageListView")
@ViewScoped
public class ContactMessageListView implements Serializable {

    private LazyDataModel<ContactMessageVW> lazyContactMessageDataModel;

    private ContactMessageVW selectedContactMessageVW;

    private List<ContactMessageVW> filteredContactMessageVWList;

    @ManagedProperty("#{contactMessageService}")
    private ContactMessageService contactMessageService;

    private int pageSize = 10;

    @PostConstruct
    public void init() {
        lazyContactMessageDataModel = new LazyContactMessageDataModel(contactMessageService.getContactMessageVWList(0, pageSize, null, null));
    }


    public void onRowEdit(RowEditEvent event) {
        ContactMessageVW contactMessageVW = (ContactMessageVW) event.getObject();
        FacesMessage msg;

        if (contactMessageService.updateContactMessage(contactMessageVW)) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mesaj başarıyla düzenlendi.", contactMessageVW.getEmail());

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mesaj düzenlenemedi.", contactMessageVW.getEmail());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        ContactMessage contactMessage = (ContactMessage) event.getObject();

        FacesMessage msg = new FacesMessage("Düzenleme İptal edildi.", contactMessage.getEmail());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteContactMessage(int contactMessageId) {
        FacesContext context = FacesContext.getCurrentInstance();

        ContactMessage contactMessage = contactMessageService.getContactMessageByContactMessageId(contactMessageId);
        if (contactMessage == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Silinecem mesaj bulunamadı!"));
            return;
        }

        if (contactMessageService.deleteContactMessage(contactMessage)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Mesaj Başarıyla silindi.."));

            lazyContactMessageDataModel = new LazyContactMessageDataModel(contactMessageService.getContactMessageVWList(0, pageSize, null, null));

            selectedContactMessageVW = new ContactMessageVW();

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Mesaj Silinemedi."));
        }

    }


    public LazyDataModel<ContactMessageVW> getLazyContactMessageDataModel() {
        return lazyContactMessageDataModel;
    }

    public void setLazyContactMessageDataModel(LazyDataModel<ContactMessageVW> lazyContactMessageDataModel) {
        this.lazyContactMessageDataModel = lazyContactMessageDataModel;
    }

    public ContactMessageVW getSelectedContactMessageVW() {
        return selectedContactMessageVW;
    }

    public void setSelectedContactMessageVW(ContactMessageVW selectedContactMessageVW) {
        this.selectedContactMessageVW = selectedContactMessageVW;
    }

    public List<ContactMessageVW> getFilteredContactMessageVWList() {
        return filteredContactMessageVWList;
    }

    public void setFilteredContactMessageVWList(List<ContactMessageVW> filteredContactMessageVWList) {
        this.filteredContactMessageVWList = filteredContactMessageVWList;
    }

    public ContactMessageService getContactMessageService() {
        return contactMessageService;
    }

    public void setContactMessageService(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
