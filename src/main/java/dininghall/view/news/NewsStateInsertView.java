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
import dininghall.domain.news.NewsState;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class NewsStateInsertView implements Serializable {
    private NewsState newNewsState;

    @ManagedProperty("#{newsStateService}")
    private NewsStateService newsStateService;
    private int newsStateId;
    private String newsState;


    @PostConstruct
    public void init() {
        newNewsState = new NewsState();
    }

    public void insertNewsState() {
        if (newsStateId == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Geçersiz Blog Durum Id.", newNewsState.getNewsState());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        newNewsState.setNewsStateId(newsStateId);
        newNewsState.setNewsState(newsState);

        if (newsStateService.insertNewsState(newNewsState)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Durumu başarıyla eklendi.", newNewsState.getNewsState());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Blog Durumu Eklenemedi.", newNewsState.getNewsState());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public NewsState getNewNewsState() {
        return newNewsState;
    }

    public void setNewNewsState(NewsState newNewsState) {
        this.newNewsState = newNewsState;
    }

    public String getNewsState() {
        return newsState;
    }

    public void setNewsState(String newsState) {
        this.newsState = newsState;
    }


    public NewsStateService getNewsStateService() {
        return newsStateService;
    }

    public void setNewsStateService(NewsStateService newsStateService) {
        this.newsStateService = newsStateService;
    }


    public int getNewsStateId() {
        return newsStateId;
    }

    public void setNewsStateId(int newsStateId) {
        this.newsStateId = newsStateId;
    }
}
