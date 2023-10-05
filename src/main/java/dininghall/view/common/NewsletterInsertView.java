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

import dininghall.service.common.NewsletterService;
import dininghall.domain.common.Newsletter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class NewsletterInsertView implements Serializable {
    private Newsletter newNewsletter;

    @ManagedProperty("#{newsletterService}")
    private NewsletterService newsletterService;

    private String email;


    @PostConstruct
    public void init() {
        newNewsletter = new Newsletter();
    }

    public void insertNewsletter() {
        newNewsletter.setEmail(email);

        if (newsletterService.insertNewsletter(newNewsletter)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alıcı adresi başarıyla eklendi.", newNewsletter.getEmail());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Alıcı adresi eklenemedi.", newNewsletter.getEmail());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public Newsletter getNewNewsletter() {
        return newNewsletter;
    }

    public void setNewNewsletter(Newsletter newNewsletter) {
        this.newNewsletter = newNewsletter;
    }

    public NewsletterService getNewsletterService() {
        return newsletterService;
    }

    public void setNewsletterService(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
