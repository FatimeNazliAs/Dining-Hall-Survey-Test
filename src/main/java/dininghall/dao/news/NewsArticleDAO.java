/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.domain.news.NewsArticle;
import dininghall.domain.news.NewsArticleVW;
import dininghall.view.common.NewsStateEnum;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface NewsArticleDAO
{
    List<NewsArticle> getNewsArticleList();

    NewsArticle getNewsArticleByNewsArticleId(int newsArticleId);

    NewsArticleVW getNewsArticleVWByNewsArticleId(int newsArticleId);

    boolean addNewsArticle(NewsArticle newsArticle);

    boolean updateNewsArticle(NewsArticle newsArticle);

    boolean updateNewsArticle(NewsArticleVW newsArticleVW);

    boolean updateNewsArticleViewCount(NewsArticleVW newsArticleVW);

    boolean deleteNewsArticle(int newsArticleId);

    NewsArticle getNewsArticleByHeadLine(String headline);

    NewsArticleVW getNewsArticleVWByHeadLine(String headline);

    NewsArticleVW getNewsArticleVWBySlug(String slug);

    List<NewsArticleVW> getNewsArticleVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);

    List<NewsArticleVW> getNewsArticleVWListByCount(NewsStateEnum newsStateEnum, int count);

    int getNewsArticleCount(Map<String, Object> filters);

    List<NewsArticleVW> getTopNewsArticleVWList(int size);

    List<NewsArticleVW> getNewsArticleVWList(NewsStateEnum newsStateEnum, int first, int pageSize,  Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);

    int getNewsArticleCount(NewsStateEnum newsStateEnum, Map<String, Object> filters);



}
