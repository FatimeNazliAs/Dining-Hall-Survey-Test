/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.domain.news.NewsArticleCategory;
import dininghall.domain.news.NewsArticleCategoryVW;

import java.util.List;

/**
 *
 * @author Tolga
 */
public interface NewsArticleCategoryDAO
{
    public List<NewsArticleCategory> getNewsArticleCategoryList();

    public NewsArticleCategory getNewsArticleCategoryByNewsArticleCategoryId(int newsArticleCategoryId);

    public NewsArticleCategory getNewsArticleCategoryByName(String name);

    public List<NewsArticleCategory> getNewsArticleCategoryListByNewsArticleId(int newsArticleId);

    public List<NewsArticleCategoryVW> getNewsArticleCategoryVWListByNewsArticleId(int newsArticleId);

    public boolean addNewsArticleCategory(NewsArticleCategory newsArticleCategory);

    public boolean updateNewsArticleCategory(NewsArticleCategory newsArticleCategory);

    public boolean updateNewsArticleCategory(NewsArticleCategoryVW newsArticleCategoryVW);

    public boolean deleteNewsArticleCategory(int newsArticleCategoryId);

    public List<NewsArticleCategoryVW> getNewsArticleCategoryVWList();

    public NewsArticleCategoryVW getNewsArticleCategoryVWByNewsArticleCategoryId(int newsArticleCategoryId);

    public boolean deleteNewsArticleCategoryByNewsArticleId(int newsArticleId);


}
