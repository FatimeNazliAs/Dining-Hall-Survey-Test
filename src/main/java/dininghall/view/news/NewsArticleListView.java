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


import dininghall.lazydomain.news.LazyNewsArticleDataModel;
import dininghall.service.news.NewsArticleService;
import dininghall.service.news.NewsCategoryService;
import dininghall.service.news.NewsStateService;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import dininghall.domain.news.NewsArticle;
import dininghall.domain.news.NewsArticleVW;
import dininghall.domain.news.NewsCategory;
import dininghall.domain.news.NewsState;
import dininghall.view.common.NewsStateEnum;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ManagedBean
@ViewScoped
public class NewsArticleListView implements Serializable {

    private LazyDataModel<NewsArticleVW> lazyNewsArticleDataModel;

    private NewsArticleVW selectedNewsArticleVW;

    private List<NewsArticleVW> filteredNewsArticleVWList;

    @ManagedProperty("#{newsArticleService}")
    private NewsArticleService newsArticleService;
    private String newsArticle;
    private int newsArticleId;
    private int newNewsArticleId;
    private String text;

    @ManagedProperty("#{newsStateService}")
    private NewsStateService newsStateService;
    private List<SelectItem> newsStateItemList;
    private List<NewsState> newsStateList;
    private int newsStateId;
    private String newsState;

    @ManagedProperty("#{newsCategoryService}")
    private NewsCategoryService newsCategoryService;
    private List<SelectItem> newsCategoryItemList;
    private List<NewsCategory> newsCategoryList;

    private int pageSize = 10;


    @PostConstruct
    public void init() {
        lazyNewsArticleDataModel =
                new LazyNewsArticleDataModel(NewsStateEnum.ALL, newsArticleService.getNewsArticleVWList(0, pageSize, null, null));

        newsCategoryItemList = new ArrayList<>();
        newsCategoryList = newsCategoryService.getNewsCategoryList(100);
        for (NewsCategory nc : newsCategoryList) {
            newsCategoryItemList.add(new SelectItem(nc.getNewsCategoryId(), nc.getNewsCategory()));
        }
    }

    public void deleteNewsArticle(int newsArticleId) {
        FacesContext context = FacesContext.getCurrentInstance();

        NewsArticle newsArticle = newsArticleService.getNewsArticleByNewsArticleId(newsArticleId);
        if (newsArticle == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Blog bulunamadı!"));
            return;
        }

        if (newsArticleService.deleteNewsArticle(newsArticle)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Blog başarıyla silindi."));
            lazyNewsArticleDataModel =
                    new LazyNewsArticleDataModel(NewsStateEnum.ALL, newsArticleService.getNewsArticleVWList(0, pageSize, null, null));

            selectedNewsArticleVW = new NewsArticleVW();
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Blog silinemedi."));
        }

    }

    public void showInfo() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> map = context.getExternalContext().getRequestParameterMap();

        selectedNewsArticleVW = newsArticleService.getNewsArticleVWByNewsArticleId(Integer.parseInt(map.get("newsArticleId")));

        text = selectedNewsArticleVW.getText();

    }

    public void updateInfo() {
        selectedNewsArticleVW.setText(text);

        if (newsArticleService.updateNewsArticle(selectedNewsArticleVW)) {
            FacesMessage msg = new FacesMessage("Düzenleme başarılı.", selectedNewsArticleVW.getHeadline());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            lazyNewsArticleDataModel =
                    new LazyNewsArticleDataModel(NewsStateEnum.ALL, newsArticleService.getNewsArticleVWList(0, pageSize, null, null));

        } else {
            FacesMessage msg = new FacesMessage("Düzenleme yapılamadı.", selectedNewsArticleVW.getHeadline());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }


    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog seçildi", ((NewsArticle) event.getObject()).getHeadline());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog seçimi iptal edildi.", ((NewsArticle) event.getObject()).getHeadline());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<NewsArticleVW> getFilteredNewsArticleVWList() {
        return filteredNewsArticleVWList;
    }

    public void setFilteredNewsArticleVWList(List<NewsArticleVW> filteredNewsArticleVWList) {
        this.filteredNewsArticleVWList = filteredNewsArticleVWList;
    }


    public NewsArticleVW getSelectedNewsArticleVW() {
        return selectedNewsArticleVW;
    }

    public void setSelectedNewsArticleVW(NewsArticleVW selectedNewsArticleVW) {
        this.selectedNewsArticleVW = selectedNewsArticleVW;
    }


    public NewsArticleService getNewsArticleService() {
        return newsArticleService;
    }

    public void setNewsArticleService(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    public String getNewsArticle() {
        return newsArticle;
    }

    public void setNewsArticle(String newsArticle) {
        this.newsArticle = newsArticle;
    }

    public int getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId) {
        this.newsArticleId = newsArticleId;
    }

    public int getNewNewsArticleId() {
        return newNewsArticleId;
    }

    public void setNewNewsArticleId(int newNewsArticleId) {
        this.newNewsArticleId = newNewsArticleId;
    }

    public NewsStateService getNewsStateService() {
        return newsStateService;
    }

    public void setNewsStateService(NewsStateService newsStateService) {
        this.newsStateService = newsStateService;
    }

    public List<SelectItem> getNewsStateItemList() {
        return newsStateItemList;
    }

    public void setNewsStateItemList(List<SelectItem> newsStateItemList) {
        this.newsStateItemList = newsStateItemList;
    }

    public List<NewsState> getNewsStateList() {
        return newsStateList;
    }

    public void setNewsStateList(List<NewsState> newsStateList) {
        this.newsStateList = newsStateList;
    }

    public int getNewsStateId() {
        return newsStateId;
    }

    public void setNewsStateId(int newsStateId) {
        this.newsStateId = newsStateId;
    }

    public String getNewsState() {
        return newsState;
    }

    public void setNewsState(String newsState) {
        this.newsState = newsState;
    }

    public NewsCategoryService getNewsCategoryService() {
        return newsCategoryService;
    }

    public void setNewsCategoryService(NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }

    public List<SelectItem> getNewsCategoryItemList() {
        return newsCategoryItemList;
    }

    public void setNewsCategoryItemList(List<SelectItem> newsCategoryItemList) {
        this.newsCategoryItemList = newsCategoryItemList;
    }

    public List<NewsCategory> getNewsCategoryList() {
        return newsCategoryList;
    }

    public void setNewsCategoryList(List<NewsCategory> newsCategoryList) {
        this.newsCategoryList = newsCategoryList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LazyDataModel<NewsArticleVW> getLazyNewsArticleDataModel() {
        return lazyNewsArticleDataModel;
    }

    public void setLazyNewsArticleDataModel(LazyDataModel<NewsArticleVW> lazyNewsArticleDataModel) {
        this.lazyNewsArticleDataModel = lazyNewsArticleDataModel;
    }
}
