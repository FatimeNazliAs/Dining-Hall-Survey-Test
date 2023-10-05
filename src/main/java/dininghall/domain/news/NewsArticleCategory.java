/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.news;

import java.io.Serializable;

/**
 * @author Tolga
 */
public class NewsArticleCategory implements Serializable
{
    private int newsArticleCategoryId;
    private int newsCategoryId;
    private int newsArticleId;


    public int getNewsArticleCategoryId()
    {
        return newsArticleCategoryId;
    }

    public void setNewsArticleCategoryId(int newsArticleCategoryId)
    {
        this.newsArticleCategoryId = newsArticleCategoryId;
    }

    public int getNewsCategoryId()
    {
        return newsCategoryId;
    }

    public void setNewsCategoryId(int newsCategoryId)
    {
        this.newsCategoryId = newsCategoryId;
    }

    public int getNewsArticleId()
    {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId)
    {
        this.newsArticleId = newsArticleId;
    }
}
