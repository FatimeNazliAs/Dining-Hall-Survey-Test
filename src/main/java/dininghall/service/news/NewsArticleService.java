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
package dininghall.service.news;


import dininghall.dao.news.NewsArticleDAO;
import dininghall.dao.news.NewsArticleDAOImpl;
import dininghall.domain.news.NewsArticle;
import dininghall.domain.news.NewsArticleVW;
import dininghall.view.common.NewsStateEnum;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "newsArticleService")
@ApplicationScoped
public class NewsArticleService
{

    private final static NewsArticleDAO newsArticleDAO;

    static
    {
        newsArticleDAO = new NewsArticleDAOImpl();
    }

    public List<NewsArticle> getNewsArticleList(int size)
    {
        return newsArticleDAO.getNewsArticleList();
    }

    public boolean updateNewsArticle(NewsArticle newsArticle)
    {
        return newsArticleDAO.updateNewsArticle(newsArticle);
    }

    public boolean updateNewsArticle(NewsArticleVW newsArticleVW)
    {
        return newsArticleDAO.updateNewsArticle(newsArticleVW);
    }

    public boolean deleteNewsArticle(NewsArticle newsArticle)
    {
        return newsArticleDAO.deleteNewsArticle(newsArticle.getNewsArticleId());
    }

    public NewsArticle getNewsArticleByNewsArticleId(int newsArticleId)
    {
        return newsArticleDAO.getNewsArticleByNewsArticleId(newsArticleId);
    }

    public boolean insertNewsArticle(NewsArticle newsArticle)
    {
        return newsArticleDAO.addNewsArticle(newsArticle);
    }



    public NewsArticleVW getNewsArticleVWByNewsArticleId(int newsArticleId)
    {
        return newsArticleDAO.getNewsArticleVWByNewsArticleId(newsArticleId);
    }

    public NewsArticle getNewsArticleByHeadLine(String headline)
    {
        return newsArticleDAO.getNewsArticleByHeadLine(headline);
    }

    public NewsArticleVW getNewsArticleVWByHeadLine(String headline)
    {
        return newsArticleDAO.getNewsArticleVWByHeadLine(headline);
    }

    public List<NewsArticleVW> getNewsArticleVWListByCount(NewsStateEnum newsStateEnum, int count)
    {
        return newsArticleDAO.getNewsArticleVWListByCount(newsStateEnum, count);
    }

    public int getNewsArticleCount(Map<String, Object> filters)
    {
        return newsArticleDAO.getNewsArticleCount(filters);
    }

    public List<NewsArticleVW> getNewsArticleVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters)
    {
        return newsArticleDAO.getNewsArticleVWList(first, pageSize, sortFilters, filters);
    }

    public List<NewsArticleVW> getTopNewsArticleVWList(int size)
    {
        return newsArticleDAO.getTopNewsArticleVWList(size);
    }

    public List<NewsArticleVW> getNewsArticleVWList(NewsStateEnum newsStateEnum, int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters)
    {
        return newsArticleDAO.getNewsArticleVWList(newsStateEnum, first, first + pageSize, sortFilters, filters);
    }

    public int getNewsArticleCount(NewsStateEnum newsStateEnum, Map<String, Object> filters)
    {
        return newsArticleDAO.getNewsArticleCount(newsStateEnum, filters);
    }

    public NewsArticleVW getNewsArticleVWBySlug(String slug)
    {
        return newsArticleDAO.getNewsArticleVWBySlug(slug);
    }

    public boolean updateNewsArticleViewCount(NewsArticleVW newsArticleVW)
    {
        return newsArticleDAO.updateNewsArticleViewCount(newsArticleVW);
    }
}
