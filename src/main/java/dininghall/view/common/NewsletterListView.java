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


import dininghall.lazydomain.common.LazyNewsletterDataModel;
import dininghall.service.common.NewsletterService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import dininghall.domain.common.Newsletter;
import dininghall.domain.common.NewsletterVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


@ManagedBean(name = "newsletterListView")
@ViewScoped
public class NewsletterListView implements Serializable {
    private LazyDataModel<NewsletterVW> lazyNewsletterDataModel;

    private NewsletterVW selectedNewsletterVW;

    private List<NewsletterVW> filteredNewsletterVWList;

    @ManagedProperty("#{newsletterService}")
    private NewsletterService newsletterService;

    private int pageSize = 10;


    @PostConstruct
    public void init() {
        lazyNewsletterDataModel = new LazyNewsletterDataModel(newsletterService.getNewsletterVWList(0, pageSize, null, null));
    }


    public void onRowEdit(RowEditEvent event) {
        NewsletterVW newsletterVW = (NewsletterVW) event.getObject();
        FacesMessage msg;

        if (newsletterService.updateNewsletter(newsletterVW)) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Üye bilgisi başarıyla güncellendi", newsletterVW.getEmail());

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Üye bilgisi düzenlenemedi.", newsletterVW.getEmail());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        NewsletterVW newsletterVW = (NewsletterVW) event.getObject();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Düzenleme iptal edildi.", newsletterVW.getEmail());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteNewsletter(int newsletterId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Newsletter newsletter = newsletterService.getNewsletterByNewsletterId(newsletterId);
        if (newsletter == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Düzenlenecek üye kaydı bulunamadı!"));
            return;
        }

        if (newsletterService.deleteNewsletter(newsletter)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Üye kaydı başarıyla silindi."));

            lazyNewsletterDataModel = new LazyNewsletterDataModel(newsletterService.getNewsletterVWList(0, pageSize, null, null));

            selectedNewsletterVW = new NewsletterVW();

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Üye Kaydı silinemedi."));
        }

    }


    public LazyDataModel<NewsletterVW> getLazyNewsletterDataModel() {
        return lazyNewsletterDataModel;
    }

    public void setLazyNewsletterDataModel(LazyDataModel<NewsletterVW> lazyNewsletterDataModel) {
        this.lazyNewsletterDataModel = lazyNewsletterDataModel;
    }

    public NewsletterVW getSelectedNewsletterVW() {
        return selectedNewsletterVW;
    }

    public void setSelectedNewsletterVW(NewsletterVW selectedNewsletterVW) {
        this.selectedNewsletterVW = selectedNewsletterVW;
    }

    public List<NewsletterVW> getFilteredNewsletterVWList() {
        return filteredNewsletterVWList;
    }

    public void setFilteredNewsletterVWList(List<NewsletterVW> filteredNewsletterVWList) {
        this.filteredNewsletterVWList = filteredNewsletterVWList;
    }

    public NewsletterService getNewsletterService() {
        return newsletterService;
    }

    public void setNewsletterService(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
