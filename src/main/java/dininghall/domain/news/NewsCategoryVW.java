/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.news;

import java.io.Serializable;

/**
 *
 * @author Tolga
 */
public class NewsCategoryVW implements Serializable
{
    private int newsCategoryId;
    private String newsCategory;
    private int newNewsCategoryId;


    public int getNewsCategoryId()
    {
        return newsCategoryId;
    }

    public void setNewsCategoryId(int newsCategoryId)
    {
        this.newsCategoryId = newsCategoryId;
    }

    public String getNewsCategory()
    {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory)
    {
        this.newsCategory = newsCategory;
    }

    public int getNewNewsCategoryId()
    {
        return newNewsCategoryId;
    }

    public void setNewNewsCategoryId(int newNewsCategoryId)
    {
        this.newNewsCategoryId = newNewsCategoryId;
    }
}
