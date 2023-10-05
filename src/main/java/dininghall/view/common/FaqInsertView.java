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

import dininghall.service.common.FaqService;
import dininghall.domain.common.Faq;

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
public class FaqInsertView implements Serializable {
    private Faq newFaq;

    @ManagedProperty("#{faqService}")
    private FaqService faqService;

    @PostConstruct
    public void init() {
        newFaq = new Faq();
    }

    public void insertFaq() {
        newFaq.setCreatedAt(new Date());

        if (faqService.insertFaq(newFaq)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "SSS başarıyla eklendi.", newFaq.getQuestion());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, SSS eklenemedi.", newFaq.getQuestion());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public Faq getNewFaq() {
        return newFaq;
    }

    public void setNewFaq(Faq newFaq) {
        this.newFaq = newFaq;
    }

    public FaqService getFaqService() {
        return faqService;
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }


}
