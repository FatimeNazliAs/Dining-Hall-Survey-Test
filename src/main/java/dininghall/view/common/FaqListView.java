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


import dininghall.lazydomain.common.LazyFaqDataModel;
import dininghall.service.common.FaqService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import dininghall.domain.common.Faq;
import dininghall.domain.common.FaqVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;


@ManagedBean(name = "faqListView")
@ViewScoped
public class FaqListView implements Serializable {
    private LazyDataModel<FaqVW> lazyFaqDataModel;

    private FaqVW selectedFaqVW;

    @ManagedProperty("#{faqService}")
    private FaqService faqService;

    private int pageSize = 10;


    @PostConstruct
    public void init() {
        lazyFaqDataModel = new LazyFaqDataModel(faqService.getFaqVWList(0, pageSize, null, null));
    }


    public void onRowEdit(RowEditEvent event) {
        FaqVW faqVW = (FaqVW) event.getObject();
        FacesMessage msg;

        faqVW.setUpdatedAt(new Date());

        if (faqService.updateFaq(faqVW)) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "SSS başarıyla güncellendi", faqVW.getQuestion());

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SSS düzenlenemedi.", faqVW.getQuestion());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FaqVW faqVW = (FaqVW) event.getObject();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Düzenleme iptal edildi.", faqVW.getQuestion());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteFaq(int faqId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Faq faq = faqService.getFaqByFaqId(faqId);
        if (faq == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Düzenlenecek SSS bulunamadı!"));
            return;
        }

        if (faqService.deleteFaq(faq)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "SSS başarıyla silindi."));

            lazyFaqDataModel = new LazyFaqDataModel(faqService.getFaqVWList(0, pageSize, null, null));

            selectedFaqVW = new FaqVW();

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, SSS silinemedi."));
        }

    }


    public LazyDataModel<FaqVW> getLazyFaqDataModel() {
        return lazyFaqDataModel;
    }

    public void setLazyFaqDataModel(LazyDataModel<FaqVW> lazyFaqDataModel) {
        this.lazyFaqDataModel = lazyFaqDataModel;
    }

    public FaqVW getSelectedFaqVW() {
        return selectedFaqVW;
    }

    public void setSelectedFaqVW(FaqVW selectedFaqVW) {
        this.selectedFaqVW = selectedFaqVW;
    }

    public FaqService getFaqService() {
        return faqService;
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
