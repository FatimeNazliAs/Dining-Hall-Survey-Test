/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.domain.news.NewsArticleFile;
import dininghall.domain.news.NewsArticleFileVW;

import java.util.List;

/**
 *
 * @author Tolga
 */
public interface NewsArticleFileDAO
{
    List<NewsArticleFile> getNewsArticleFileList();

    NewsArticleFile getNewsArticleFileByNewsArticleFileId(int statusId);

    NewsArticleFile getNewsArticleFileByName(String name);

    boolean addNewsArticleFile(NewsArticleFile newsArticleFile);

    boolean updateNewsArticleFile(NewsArticleFile newsArticleFile);

    boolean updateNewsArticleFile(NewsArticleFileVW newsArticleFileVW);

    boolean deleteNewsArticleFile(int statusId);

    List<NewsArticleFileVW> getNewsArticleFileVWList();

    NewsArticleFileVW getNewsArticleFileVWByNewsArticleFileId(int newsArticleFileId);

    List<NewsArticleFile> getNewsArticleFileListByNewsArticleId(int newsArticleId);
}
