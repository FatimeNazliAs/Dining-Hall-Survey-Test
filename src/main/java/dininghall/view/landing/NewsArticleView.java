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

import dininghall.lazydomain.news.LazyNewsArticleDataModel;
import dininghall.service.news.NewsArticleService;
import org.primefaces.model.LazyDataModel;
import dininghall.domain.news.NewsArticleVW;
import dininghall.view.common.NewsStateEnum;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;


@ManagedBean(name = "newsArticleView")
@SessionScoped
public class NewsArticleView implements Serializable {
    private LazyDataModel<NewsArticleVW> lazyNewsArticleDataModel;

    @ManagedProperty("#{newsArticleService}")
    private NewsArticleService newsArticleService;

    private int pageSize = 10;

    private String slug;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String slug = params.get("slug");

        lazyNewsArticleDataModel =
                new LazyNewsArticleDataModel(NewsStateEnum.LIVE, newsArticleService.getNewsArticleVWList(0, pageSize, null, null));
    }


    public NewsArticleService getNewsArticleService() {
        return newsArticleService;
    }

    public void setNewsArticleService(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<NewsArticleVW> getLazyNewsArticleDataModel() {
        return lazyNewsArticleDataModel;
    }

    public void setLazyNewsArticleDataModel(LazyDataModel<NewsArticleVW> lazyNewsArticleDataModel) {
        this.lazyNewsArticleDataModel = lazyNewsArticleDataModel;
    }
}
