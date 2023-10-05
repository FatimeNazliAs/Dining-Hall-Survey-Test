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

import dininghall.service.news.NewsArticleService;
import dininghall.view.common.NewsStateEnum;
import dininghall.domain.news.NewsArticleVW;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@ManagedBean(name = "newsArticleDetailView")
@ViewScoped
public class NewsArticleDetailView implements Serializable {
    private NewsArticleVW selectedNewsArticleVW;

    @ManagedProperty("#{newsArticleService}")
    private NewsArticleService newsArticleService;

    private String headline;

    private String slug;

    private List<NewsArticleVW> newsArticleVWList;

    private List<NewsArticleVW> newsArticleVWSimilarList;

    @PostConstruct
    public void init() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String slug = params.get("slug");

        selectedNewsArticleVW = newsArticleService.getNewsArticleVWBySlug(slug);

        if (selectedNewsArticleVW != null) {
            selectedNewsArticleVW.setViewCount(selectedNewsArticleVW.getViewCount() + 1);
            newsArticleService.updateNewsArticleViewCount(selectedNewsArticleVW);
        }

        newsArticleVWList = newsArticleService.getNewsArticleVWListByCount(NewsStateEnum.LIVE, 3);

        newsArticleVWSimilarList = newsArticleService.getNewsArticleVWListByCount(NewsStateEnum.LIVE, 5);
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

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public List<NewsArticleVW> getNewsArticleVWList() {
        return newsArticleVWList;
    }

    public void setNewsArticleVWList(List<NewsArticleVW> newsArticleVWList) {
        this.newsArticleVWList = newsArticleVWList;
    }

    public List<NewsArticleVW> getNewsArticleVWSimilarList() {
        return newsArticleVWSimilarList;
    }

    public void setNewsArticleVWSimilarList(List<NewsArticleVW> newsArticleVWSimilarList) {
        this.newsArticleVWSimilarList = newsArticleVWSimilarList;
    }
}
