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


import dininghall.service.news.NewsStateService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.news.NewsState;
import dininghall.domain.news.NewsStateVW;

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
public class NewsStateListView implements Serializable {

    private List<NewsStateVW> newsStateVWList;

    private NewsStateVW selectedNewsStateVW;

    private List<NewsStateVW> filteredNewsStateVWList;

    @ManagedProperty("#{newsStateService}")
    private NewsStateService newsStateService;
    private String newsState;
    private int newsStateId;
    private int newNewsStateId;


    @PostConstruct
    public void init() {
        newsStateVWList = newsStateService.getNewsStateVWList(10);
    }


    public void onRowEdit(RowEditEvent event) {
        NewsStateVW newsStateVW = (NewsStateVW) event.getObject();
        FacesMessage msg;

        if (newNewsStateId == 0) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Geçersiz Blog Durum Id.", newsStateVW.getNewsState());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            newNewsStateId = newsStateVW.getNewNewsStateId();

            return;
        }

        if (newsStateService.updateNewsState(newsStateVW)) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Durumu başarıyla güncellendi.", newsStateVW.getNewsState());

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Blog Durumu güncellenemedi.", newsStateVW.getNewsState());
        }

        newsStateVWList = newsStateService.getNewsStateVWList(10);

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        NewsStateVW newsStateVW = (NewsStateVW) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Güncelleme İptal edildi.", newsStateVW.getNewsState());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteNewsState(int newsStateId) {
        FacesContext context = FacesContext.getCurrentInstance();

        NewsState newsState = newsStateService.getNewsStateByNewsStateId(newsStateId);
        if (newsState == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Blog Durumu bulunamadı!"));
            return;
        }

        if (newsStateService.deleteNewsState(newsState)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Blog Durumu başarıyla silindi."));
            newsStateVWList = newsStateService.getNewsStateVWList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Blog Durum silinemedi."));
        }

    }


    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Durumu seçildi.", ((NewsState) event.getObject()).getNewsState());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Durumu seçimi iptal edildi.", ((NewsState) event.getObject()).getNewsState());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<NewsStateVW> getFilteredNewsStateVWList() {
        return filteredNewsStateVWList;
    }

    public void setFilteredNewsStateVWList(List<NewsStateVW> filteredNewsStateVWList) {
        this.filteredNewsStateVWList = filteredNewsStateVWList;
    }


    public NewsStateVW getSelectedNewsStateVW() {
        return selectedNewsStateVW;
    }

    public void setSelectedNewsStateVW(NewsStateVW selectedNewsStateVW) {
        this.selectedNewsStateVW = selectedNewsStateVW;
    }

    public void setNewsStateVWList(List<NewsStateVW> newsStateVWList) {
        this.newsStateVWList = newsStateVWList;
    }


    public NewsStateService getNewsStateService() {
        return newsStateService;
    }

    public void setNewsStateService(NewsStateService newsStateService) {
        this.newsStateService = newsStateService;
    }

    public String getNewsState() {
        return newsState;
    }

    public void setNewsState(String newsState) {
        this.newsState = newsState;
    }

    public int getNewsStateId() {
        return newsStateId;
    }

    public void setNewsStateId(int newsStateId) {
        this.newsStateId = newsStateId;
    }


    public List<NewsStateVW> getNewsStateVWList() {
        return newsStateVWList;
    }


    public int getNewNewsStateId() {
        return newNewsStateId;
    }

    public void setNewNewsStateId(int newNewsStateId) {
        this.newNewsStateId = newNewsStateId;
    }
}
