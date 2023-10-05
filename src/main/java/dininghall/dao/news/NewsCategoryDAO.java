/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.domain.news.NewsCategory;
import dininghall.domain.news.NewsCategoryVW;

import java.util.List;

/**
 *
 * @author Tolga
 */
public interface NewsCategoryDAO
{
    public List<NewsCategory> getNewsCategoryList();

    public NewsCategory getNewsCategoryByNewsCategoryId(int statusId);

    public NewsCategory getNewsCategoryByName(String name);

    public boolean addNewsCategory(NewsCategory newsCategory);

    public boolean updateNewsCategory(NewsCategory newsCategory);

    public boolean updateNewsCategory(NewsCategoryVW newsCategoryVW);

    public boolean deleteNewsCategory(int statusId);

    public List<NewsCategoryVW> getNewsCategoryVWList();

    public NewsCategoryVW getNewsCategoryVWByNewsCategoryId(int newsCategoryId);
}
