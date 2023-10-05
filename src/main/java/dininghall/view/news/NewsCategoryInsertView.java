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
import dininghall.domain.news.NewsCategory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class NewsCategoryInsertView implements Serializable {
    private NewsCategory newNewsCategory;

    @ManagedProperty("#{newsCategoryService}")
    private NewsCategoryService newsCategoryService;
    private int newsCategoryId;
    private String newsCategory;


    @PostConstruct
    public void init() {
        newNewsCategory = new NewsCategory();
    }

    public void insertNewsCategory() {
        if (newsCategoryId == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Geçersiz Blog Kategori Id girişi yaptınız.", newNewsCategory.getNewsCategory());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        newNewsCategory.setNewsCategoryId(newsCategoryId);
        newNewsCategory.setNewsCategory(newsCategory);

        if (newsCategoryService.insertNewsCategory(newNewsCategory)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Kategorisi başarıyla eklendi", newNewsCategory.getNewsCategory());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Blog Kategorisi eklenemedi.", newNewsCategory.getNewsCategory());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public NewsCategory getNewNewsCategory() {
        return newNewsCategory;
    }

    public void setNewNewsCategory(NewsCategory newNewsCategory) {
        this.newNewsCategory = newNewsCategory;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }


    public NewsCategoryService getNewsCategoryService() {
        return newsCategoryService;
    }

    public void setNewsCategoryService(NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }


    public int getNewsCategoryId() {
        return newsCategoryId;
    }

    public void setNewsCategoryId(int newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
    }
}
