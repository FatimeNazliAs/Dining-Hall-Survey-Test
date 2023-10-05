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
import dininghall.service.common.NewsletterUnRegisterService;
import dininghall.domain.common.Newsletter;
import dininghall.domain.common.NewsletterUnRegister;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;


@ManagedBean
@ViewScoped
public class NewsletterUnRegisterView implements Serializable {
    private NewsletterUnRegister newNewsletterUnRegister;

    @ManagedProperty("#{newsletterUnRegisterService}")
    private NewsletterUnRegisterService newsletterUnRegisterService;

    @ManagedProperty("#{newsletterService}")
    private NewsletterService newsletterService;

    private String email;
    private String comment;

    @PostConstruct
    public void init() {
        newNewsletterUnRegister = new NewsletterUnRegister();
    }

    public void unRegister() {
        newNewsletterUnRegister.setEmail(email);
        newNewsletterUnRegister.setComment(comment);
        newNewsletterUnRegister.setUnRegisterDate(new Date());

        Newsletter newsletter = newsletterService.getNewsletterByEmail(email);

        if (newsletter != null) {
            if (newsletterUnRegisterService.insertNewsletterUnRegister(newNewsletterUnRegister)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kaydınız başarıyla silindi, sizi tekrar aramıza bekliyor olacağız.!",
                        newNewsletterUnRegister.getEmail());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                newsletterService.deleteNewsletter(newsletter);

                email = "";
                comment = "";
                newNewsletterUnRegister = new NewsletterUnRegister();

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Girdiğiniz e-posta adresi kayıtlarımız arasında bulunamamıştır, " +
                        "lütfen tekrar deneyiniz!", newNewsletterUnRegister.getEmail());

                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Girdiğiniz e-posta adresi kayıtlarımız arasında bulunamamıştır, " +
                    "lütfen tekrar deneyiniz!", newNewsletterUnRegister.getEmail());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }


    public NewsletterUnRegister getNewNewsletterUnRegister() {
        return newNewsletterUnRegister;
    }

    public void setNewNewsletterUnRegister(NewsletterUnRegister newNewsletterUnRegister) {
        this.newNewsletterUnRegister = newNewsletterUnRegister;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewsletterUnRegisterService(NewsletterUnRegisterService newsletterUnRegisterService) {
        this.newsletterUnRegisterService = newsletterUnRegisterService;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public NewsletterService getNewsletterService() {
        return newsletterService;
    }

    public void setNewsletterService(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }
}
