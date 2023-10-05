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
package dininghall.view.news;


import dininghall.service.news.NewsCategoryService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.news.NewsCategory;
import dininghall.domain.news.NewsCategoryVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


@ManagedBean
@ViewScoped
public class NewsCategoryListView implements Serializable {

    private List<NewsCategoryVW> newsCategoryVWList;

    private NewsCategoryVW selectedNewsCategoryVW;

    private List<NewsCategoryVW> filteredNewsCategoryVWList;

    @ManagedProperty("#{newsCategoryService}")
    private NewsCategoryService newsCategoryService;
    private String newsCategory;
    private int newsCategoryId;
    private int newNewsCategoryId;


    @PostConstruct
    public void init() {
        newsCategoryVWList = newsCategoryService.getNewsCategoryVWList(10);
    }


    public void onRowEdit(RowEditEvent event) {
        NewsCategoryVW newsCategoryVW = (NewsCategoryVW) event.getObject();
        FacesMessage msg;

        if (newNewsCategoryId == 0) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Geçersiz Blog Kategori Id girişi yaptınız.", newsCategoryVW.getNewsCategory());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            newNewsCategoryId = newsCategoryVW.getNewNewsCategoryId();

            return;
        }

        if (newsCategoryService.updateNewsCategory(newsCategoryVW)) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Kategorisi başarıyla düzenlendi.", newsCategoryVW.getNewsCategory());

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Blog Kategorisi düzenlenemedi.", newsCategoryVW.getNewsCategory());
        }

        newsCategoryVWList = newsCategoryService.getNewsCategoryVWList(10);

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        NewsCategoryVW newsCategoryVW = (NewsCategoryVW) event.getObject();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Düzenleme İptal edildi.", newsCategoryVW.getNewsCategory());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteNewsCategory(int newsCategoryId) {
        FacesContext context = FacesContext.getCurrentInstance();

        NewsCategory newsCategory = newsCategoryService.getNewsCategoryByNewsCategoryId(newsCategoryId);
        if (newsCategory == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Blog Kategorisi bulunamadı!"));

            return;
        }

        if (newsCategoryService.deleteNewsCategory(newsCategory)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Blog Kategorisi başarıyla silindi."));

            newsCategoryVWList = newsCategoryService.getNewsCategoryVWList(50);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Blog Kategorisi silinemedi."));
        }

    }


    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Kategorisi seçildi.", ((NewsCategory) event.getObject()).getNewsCategory());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Kategorisi seçimi iptal edildi.", ((NewsCategory) event.getObject()).getNewsCategory());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<NewsCategoryVW> getFilteredNewsCategoryVWList() {
        return filteredNewsCategoryVWList;
    }

    public void setFilteredNewsCategoryVWList(List<NewsCategoryVW> filteredNewsCategoryVWList) {
        this.filteredNewsCategoryVWList = filteredNewsCategoryVWList;
    }


    public NewsCategoryVW getSelectedNewsCategoryVW() {
        return selectedNewsCategoryVW;
    }

    public void setSelectedNewsCategoryVW(NewsCategoryVW selectedNewsCategoryVW) {
        this.selectedNewsCategoryVW = selectedNewsCategoryVW;
    }

    public void setNewsCategoryVWList(List<NewsCategoryVW> newsCategoryVWList) {
        this.newsCategoryVWList = newsCategoryVWList;
    }


    public NewsCategoryService getNewsCategoryService() {
        return newsCategoryService;
    }

    public void setNewsCategoryService(NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public int getNewsCategoryId() {
        return newsCategoryId;
    }

    public void setNewsCategoryId(int newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
    }


    public List<NewsCategoryVW> getNewsCategoryVWList() {
        return newsCategoryVWList;
    }


    public int getNewNewsCategoryId() {
        return newNewsCategoryId;
    }

    public void setNewNewsCategoryId(int newNewsCategoryId) {
        this.newNewsCategoryId = newNewsCategoryId;
    }
}
