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
package dininghall.view.landing;

import dininghall.service.common.ContactMessageService;
import dininghall.domain.common.ContactMessage;
import dininghall.service.common.MailService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ManagedBean(name = "contactView")
@ViewScoped
public class ContactView implements Serializable {
    private ContactMessage contactMessage;

    @ManagedProperty("#{contactMessageService}")
    private ContactMessageService contactMessageService;

    @ManagedProperty("#{mailService}")
    private MailService mailService;


    @PostConstruct
    public void init() {
        contactMessage = new ContactMessage();
    }

    public void sendMessage() {

        contactMessage.setCreatedDate(new Date());
        contactMessage.setExpiryDate(new Date());

        if (contactMessageService.insertContactMessage(contactMessage)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mesajınız başarıyla iletilmiştir, teşekkürler!", contactMessage.getEmail());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            List<String> recipientList = new ArrayList<>();
            recipientList.add("info@keas.com.tr");

            mailService.sendContactMail(recipientList, contactMessage);

            contactMessage = new ContactMessage();

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Üzgünüz mesajınız iletilemedi, lütfen tekrar deneyiniz!",
                    contactMessage.getEmail());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public ContactMessage getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(ContactMessage contactMessage) {
        this.contactMessage = contactMessage;
    }

    public ContactMessageService getContactMessageService() {
        return contactMessageService;
    }

    public void setContactMessageService(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public MailService getMailService() {
        return mailService;
    }
}
