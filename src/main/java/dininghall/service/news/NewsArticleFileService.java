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


import dininghall.dao.news.NewsArticleFileDAO;
import dininghall.dao.news.NewsArticleFileDAOImpl;
import dininghall.domain.news.NewsArticleFile;
import dininghall.domain.news.NewsArticleFileVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "newsArticleFileService")
@ApplicationScoped
public class NewsArticleFileService
{

    private final static NewsArticleFileDAO newsArticleFileDAO;

    static
    {
        newsArticleFileDAO = new NewsArticleFileDAOImpl();
    }

    public List<NewsArticleFile> getNewsArticleFileList(int size)
    {
        return newsArticleFileDAO.getNewsArticleFileList();
    }

    public boolean updateNewsArticleFile(NewsArticleFile newsArticleFile)
    {
        return newsArticleFileDAO.updateNewsArticleFile(newsArticleFile);
    }

    public boolean updateNewsArticleFile(NewsArticleFileVW newsArticleFileVW)
    {
        return newsArticleFileDAO.updateNewsArticleFile(newsArticleFileVW);
    }

    public boolean deleteNewsArticleFile(NewsArticleFile newsArticleFile)
    {
        return newsArticleFileDAO.deleteNewsArticleFile(newsArticleFile.getNewsArticleFileId());
    }

    public NewsArticleFile getNewsArticleFileByNewsArticleFileId(int newsArticleFileId)
    {
        return newsArticleFileDAO.getNewsArticleFileByNewsArticleFileId(newsArticleFileId);
    }

    public boolean insertNewsArticleFile(NewsArticleFile newsArticleFile)
    {
        return newsArticleFileDAO.addNewsArticleFile(newsArticleFile);
    }

    public List<NewsArticleFileVW> getNewsArticleFileVWList(int i)
    {
        return newsArticleFileDAO.getNewsArticleFileVWList();
    }

    public List<NewsArticleFile> getNewsArticleFileListByNewsArticleId(int newsArticleId)
    {
        return newsArticleFileDAO.getNewsArticleFileListByNewsArticleId(newsArticleId);
    }
}
