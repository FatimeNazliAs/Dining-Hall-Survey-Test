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
public class NewsletterView implements Serializable {
    private Newsletter newNewsletter;

    @ManagedProperty("#{newsletterService}")
    private NewsletterService newsletterService;

    private String email;

    private String message;

    @PostConstruct
    public void init() {
        newNewsletter = new Newsletter();
    }

    public void register() {
        newNewsletter.setEmail(email);

        if (newsletterService.insertNewsletter(newNewsletter)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kayıt işlemi başarıyla gerçekleştirildi, aramıza hoşgeldiniz!",
                    newNewsletter.getEmail());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            email = "";
            newNewsletter = new Newsletter();
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Girdiğiniz e-posta adresi daha önce kayıt edilmiştir, lütfen tekrar deneyiniz!",
                    newNewsletter.getEmail());
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
