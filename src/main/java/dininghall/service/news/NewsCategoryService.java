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


import dininghall.dao.news.NewsCategoryDAO;
import dininghall.dao.news.NewsCategoryDAOImpl;
import dininghall.domain.news.NewsCategory;
import dininghall.domain.news.NewsCategoryVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "newsCategoryService")
@ApplicationScoped
public class NewsCategoryService
{

    private final static NewsCategoryDAO newsCategoryDAO;

    static
    {
        newsCategoryDAO = new NewsCategoryDAOImpl();
    }

    public List<NewsCategory> getNewsCategoryList(int size)
    {
        return newsCategoryDAO.getNewsCategoryList();
    }

    public boolean updateNewsCategory(NewsCategory newsCategory)
    {
        return newsCategoryDAO.updateNewsCategory(newsCategory);
    }

    public boolean updateNewsCategory(NewsCategoryVW newsCategoryVW)
    {
        return newsCategoryDAO.updateNewsCategory(newsCategoryVW);
    }

    public boolean deleteNewsCategory(NewsCategory newsCategory)
    {
        return newsCategoryDAO.deleteNewsCategory(newsCategory.getNewsCategoryId());
    }

    public NewsCategory getNewsCategoryByNewsCategoryId(int newsCategoryId)
    {
        return newsCategoryDAO.getNewsCategoryByNewsCategoryId(newsCategoryId);
    }

    public boolean insertNewsCategory(NewsCategory newsCategory)
    {
        return newsCategoryDAO.addNewsCategory(newsCategory);
    }

    public List<NewsCategoryVW> getNewsCategoryVWList(int i)
    {
        return newsCategoryDAO.getNewsCategoryVWList();
    }

    public NewsCategoryVW getNewsCategoryVWByNewsCategoryId(int newsCategoryId)
    {
        return newsCategoryDAO.getNewsCategoryVWByNewsCategoryId(newsCategoryId);
    }
}
