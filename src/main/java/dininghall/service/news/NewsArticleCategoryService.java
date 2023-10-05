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


import dininghall.dao.news.NewsArticleCategoryDAO;
import dininghall.dao.news.NewsArticleCategoryDAOImpl;
import dininghall.domain.news.NewsArticleCategory;
import dininghall.domain.news.NewsArticleCategoryVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "newsArticleCategoryService")
@ApplicationScoped
public class NewsArticleCategoryService
{

    private final static NewsArticleCategoryDAO newsArticleCategoryDAO;

    static
    {
        newsArticleCategoryDAO = new NewsArticleCategoryDAOImpl();
    }

    public List<NewsArticleCategory> getNewsArticleCategoryList(int size)
    {
        return newsArticleCategoryDAO.getNewsArticleCategoryList();
    }

    public boolean updateNewsArticleCategory(NewsArticleCategory newsArticleCategory)
    {
        return newsArticleCategoryDAO.updateNewsArticleCategory(newsArticleCategory);
    }

    public boolean updateNewsArticleCategory(NewsArticleCategoryVW newsArticleCategoryVW)
    {
        return newsArticleCategoryDAO.updateNewsArticleCategory(newsArticleCategoryVW);
    }

    public boolean deleteNewsArticleCategory(NewsArticleCategory newsArticleCategory)
    {
        return newsArticleCategoryDAO.deleteNewsArticleCategory(newsArticleCategory.getNewsArticleCategoryId());
    }

    public NewsArticleCategory getNewsArticleCategoryByNewsArticleCategoryId(int newsArticleCategoryId)
    {
        return newsArticleCategoryDAO.getNewsArticleCategoryByNewsArticleCategoryId(newsArticleCategoryId);
    }

    public boolean insertNewsArticleCategory(NewsArticleCategory newsArticleCategory)
    {
        return newsArticleCategoryDAO.addNewsArticleCategory(newsArticleCategory);
    }

    public List<NewsArticleCategoryVW> getNewsArticleCategoryVWList(int i)
    {
        return newsArticleCategoryDAO.getNewsArticleCategoryVWList();
    }

    public NewsArticleCategoryVW getNewsArticleCategoryVWByNewsArticleCategoryId(int newsArticleCategoryId)
    {
        return newsArticleCategoryDAO.getNewsArticleCategoryVWByNewsArticleCategoryId(newsArticleCategoryId);
    }
}
