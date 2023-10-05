/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.common.ConnectionFactory;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.news.NewsArticle;
import dininghall.domain.news.NewsArticleCategory;
import dininghall.domain.news.NewsArticleCategoryVW;
import dininghall.domain.news.NewsArticleVW;
import dininghall.view.common.NewsStateEnum;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class NewsArticleDAOImpl implements NewsArticleDAO, Serializable
{

    private NewsArticleCategoryDAOImpl newsArticleCategoryDAO;

    public NewsArticleDAOImpl()
    {
        newsArticleCategoryDAO = new NewsArticleCategoryDAOImpl();
    }

    @Override
    public List<NewsArticle> getNewsArticleList()
    {
        List<NewsArticle> newsArticleList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticle newsArticle = new NewsArticle();
                newsArticle.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticle.setHeadline(resultSet.getString("headline"));
                newsArticle.setExtract(resultSet.getString("extract"));
                newsArticle.setText(resultSet.getString("text"));
                newsArticle.setPublishDate(resultSet.getDate("publish_date"));
                newsArticle.setByAuthor(resultSet.getString("by_author"));
                newsArticle.setTweetText(resultSet.getString("tweet_text"));
                newsArticle.setSource(resultSet.getString("source"));
                newsArticle.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticle.setClientQuote(resultSet.getString("client_quote"));
                newsArticle.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticle.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticle.setHtmlTitle(resultSet.getString("html_title"));
                newsArticle.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticle.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticle.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticle.setTags(resultSet.getString("tags"));
                newsArticle.setPriority(resultSet.getInt("priority"));
                newsArticle.setWordCount(resultSet.getInt("word_count"));
                newsArticle.setViewCount(resultSet.getInt("view_count"));
                newsArticle.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticle.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticle.setSlug(resultSet.getString("slug"));

                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticle.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticle.setCategoryIds(categoryIdList);
                newsArticle.setCategoryNames(categoryNameList);

                newsArticleList.add(newsArticle);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleList;

    }



    @Override
    public List<NewsArticleVW> getNewsArticleVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters)
    {
        List<NewsArticleVW> newsArticleVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY publish_date DESC, priority ASC) as row " +
                    "FROM news_article_view WHERE 1=1 ";


            if (filters != null && filters.size() > 0)
            {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); )
                {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if(!filterProperty.equals("globalFilter"))
                    {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue + "%'";
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }


            queryString += ") a WHERE row>? AND row<=?";

            System.out.println(queryString);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, first);
            pstmt.setInt(2, pageSize);



            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleVW newsArticleVW = new NewsArticleVW();
                newsArticleVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleVW.setHeadline(resultSet.getString("headline"));
                newsArticleVW.setExtract(resultSet.getString("extract"));
                newsArticleVW.setText(resultSet.getString("text"));
                newsArticleVW.setPublishDate(resultSet.getDate("publish_date"));
                newsArticleVW.setPublishDateStr(resultSet.getString("publish_datestr"));
                newsArticleVW.setByAuthor(resultSet.getString("by_author"));
                newsArticleVW.setTweetText(resultSet.getString("tweet_text"));
                newsArticleVW.setSource(resultSet.getString("source"));
                newsArticleVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticleVW.setClientQuote(resultSet.getString("client_quote"));
                newsArticleVW.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticleVW.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticleVW.setHtmlTitle(resultSet.getString("html_title"));
                newsArticleVW.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticleVW.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticleVW.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticleVW.setTags(resultSet.getString("tags"));
                newsArticleVW.setPriority(resultSet.getInt("priority"));
                newsArticleVW.setWordCount(resultSet.getInt("word_count"));
                newsArticleVW.setViewCount(resultSet.getInt("view_count"));
                newsArticleVW.setNewsState(resultSet.getString("news_state"));
                newsArticleVW.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticleVW.setLocalUser(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                newsArticleVW.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticleVW.setFilePath(resultSet.getString("file_path"));
                newsArticleVW.setSlug(resultSet.getString("slug"));

                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticleVW.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticleVW.setCategoryIds(categoryIdList);
                newsArticleVW.setCategoryNames(categoryNameList);

                newsArticleVWList.add(newsArticleVW);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleVWList;
    }
    
    @Override
    public List<NewsArticleVW> getNewsArticleVWListByCount(NewsStateEnum newsStateEnum, int size)
    {
        List<NewsArticleVW> newsArticleVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM news_article_view WHERE 1=1  ";

            String newsStateQuery = "";
            switch (newsStateEnum)
            {
                case ALL:
                    newsStateQuery = "";
                    break;
                case LIVE:
                    newsStateQuery = " AND news_state_id=1 ";
                    break;
                case APPROVAL:
                    newsStateQuery = " AND news_state_id=2 ";
                    break;
                case DRAFT:
                    newsStateQuery = " AND news_state_id=3 ";
                    break;
                case DELETED:
                    newsStateQuery = " AND news_state_id=4 ";
                    break;

            }


            queryString += newsStateQuery + " ORDER BY publish_date DESC, priority ASC LIMIT ?";

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, size);

            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleVW newsArticleVW = new NewsArticleVW();
                newsArticleVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleVW.setHeadline(resultSet.getString("headline"));
                newsArticleVW.setExtract(resultSet.getString("extract"));
                newsArticleVW.setText(resultSet.getString("text"));
                newsArticleVW.setPublishDate(resultSet.getDate("publish_date"));
                newsArticleVW.setPublishDateStr(resultSet.getString("publish_datestr"));
                newsArticleVW.setByAuthor(resultSet.getString("by_author"));
                newsArticleVW.setTweetText(resultSet.getString("tweet_text"));
                newsArticleVW.setSource(resultSet.getString("source"));
                newsArticleVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticleVW.setClientQuote(resultSet.getString("client_quote"));
                newsArticleVW.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticleVW.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticleVW.setHtmlTitle(resultSet.getString("html_title"));
                newsArticleVW.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticleVW.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticleVW.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticleVW.setTags(resultSet.getString("tags"));
                newsArticleVW.setPriority(resultSet.getInt("priority"));
                newsArticleVW.setWordCount(resultSet.getInt("word_count"));
                newsArticleVW.setViewCount(resultSet.getInt("view_count"));
                newsArticleVW.setNewsState(resultSet.getString("news_state"));
                newsArticleVW.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticleVW.setLocalUser(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                newsArticleVW.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticleVW.setFilePath(resultSet.getString("file_path"));
                newsArticleVW.setSlug(resultSet.getString("slug"));

                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticleVW.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticleVW.setCategoryIds(categoryIdList);
                newsArticleVW.setCategoryNames(categoryNameList);

                newsArticleVWList.add(newsArticleVW);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleVWList;

    }

    @Override
    public List<NewsArticleVW> getTopNewsArticleVWList(int size)
    {
        List<NewsArticleVW> newsArticleVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_view ORDER BY view_count DESC, priority ASC LIMIT ?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, size);

            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleVW newsArticleVW = new NewsArticleVW();
                newsArticleVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleVW.setHeadline(resultSet.getString("headline"));
                newsArticleVW.setExtract(resultSet.getString("extract"));
                newsArticleVW.setText(resultSet.getString("text"));
                newsArticleVW.setPublishDate(resultSet.getDate("publish_date"));
                newsArticleVW.setPublishDateStr(resultSet.getString("publish_datestr"));
                newsArticleVW.setByAuthor(resultSet.getString("by_author"));
                newsArticleVW.setTweetText(resultSet.getString("tweet_text"));
                newsArticleVW.setSource(resultSet.getString("source"));
                newsArticleVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticleVW.setClientQuote(resultSet.getString("client_quote"));
                newsArticleVW.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticleVW.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticleVW.setHtmlTitle(resultSet.getString("html_title"));
                newsArticleVW.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticleVW.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticleVW.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticleVW.setTags(resultSet.getString("tags"));
                newsArticleVW.setPriority(resultSet.getInt("priority"));
                newsArticleVW.setWordCount(resultSet.getInt("word_count"));
                newsArticleVW.setViewCount(resultSet.getInt("view_count"));
                newsArticleVW.setNewsState(resultSet.getString("news_state"));
                newsArticleVW.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticleVW.setLocalUser(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                newsArticleVW.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticleVW.setFilePath(resultSet.getString("file_path"));
                newsArticleVW.setSlug(resultSet.getString("slug"));

                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticleVW.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticleVW.setCategoryIds(categoryIdList);
                newsArticleVW.setCategoryNames(categoryNameList);

                newsArticleVWList.add(newsArticleVW);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleVWList;

    }

    @Override
    public List<NewsArticleVW> getNewsArticleVWList
            (NewsStateEnum newsStateEnum, int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters)
    {
        List<NewsArticleVW> newsArticleVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            connection = ConnectionFactory.getInstance().getConnection();

            String newsStateQuery = "";
            switch (newsStateEnum)
            {
                case ALL:
                    newsStateQuery = "";
                    break;
                case LIVE:
                    newsStateQuery = " AND news_state_id=1 ";
                    break;
                case APPROVAL:
                    newsStateQuery = " AND news_state_id=2 ";
                    break;
                case DRAFT:
                    newsStateQuery = " AND news_state_id=3 ";
                    break;
                case DELETED:
                    newsStateQuery = " AND news_state_id=4 ";
                    break;

            }

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY publish_date DESC, priority ASC) as row " +
                    "FROM news_article_view WHERE 1=1 " + newsStateQuery;


            if (filters != null && filters.size() > 0)
            {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); )
                {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if(!filterProperty.equals("globalFilter"))
                    {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue + "%'";
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }


            queryString += ") a WHERE row>? AND row<=?";

            System.out.println(queryString);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, first);
            pstmt.setInt(2, pageSize);



            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleVW newsArticleVW = new NewsArticleVW();
                newsArticleVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleVW.setHeadline(resultSet.getString("headline"));
                newsArticleVW.setExtract(resultSet.getString("extract"));
                newsArticleVW.setText(resultSet.getString("text"));
                newsArticleVW.setPublishDate(resultSet.getDate("publish_date"));
                newsArticleVW.setPublishDateStr(resultSet.getString("publish_datestr"));
                newsArticleVW.setByAuthor(resultSet.getString("by_author"));
                newsArticleVW.setTweetText(resultSet.getString("tweet_text"));
                newsArticleVW.setSource(resultSet.getString("source"));
                newsArticleVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticleVW.setClientQuote(resultSet.getString("client_quote"));
                newsArticleVW.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticleVW.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticleVW.setHtmlTitle(resultSet.getString("html_title"));
                newsArticleVW.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticleVW.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticleVW.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticleVW.setTags(resultSet.getString("tags"));
                newsArticleVW.setPriority(resultSet.getInt("priority"));
                newsArticleVW.setWordCount(resultSet.getInt("word_count"));
                newsArticleVW.setViewCount(resultSet.getInt("view_count"));
                newsArticleVW.setNewsState(resultSet.getString("news_state"));
                newsArticleVW.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticleVW.setLocalUser(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                newsArticleVW.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticleVW.setFilePath(resultSet.getString("file_path"));
                newsArticleVW.setSlug(resultSet.getString("slug"));

                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticleVW.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticleVW.setCategoryIds(categoryIdList);
                newsArticleVW.setCategoryNames(categoryNameList);

                newsArticleVWList.add(newsArticleVW);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleVWList;
    }



    @Override
    public NewsArticle getNewsArticleByNewsArticleId(int news_article_id)
    {
        NewsArticle newsArticle = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article WHERE news_article_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticle = new NewsArticle();
                newsArticle.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticle.setHeadline(resultSet.getString("headline"));
                newsArticle.setExtract(resultSet.getString("extract"));
                newsArticle.setText(resultSet.getString("text"));
                newsArticle.setPublishDate(resultSet.getDate("publish_date"));
                newsArticle.setByAuthor(resultSet.getString("by_author"));
                newsArticle.setTweetText(resultSet.getString("tweet_text"));
                newsArticle.setSource(resultSet.getString("source"));
                newsArticle.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticle.setClientQuote(resultSet.getString("client_quote"));
                newsArticle.setCreatedDate(resultSet.getDate("created_date"));
                newsArticle.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticle.setHtmlTitle(resultSet.getString("html_title"));
                newsArticle.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticle.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticle.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticle.setTags(resultSet.getString("tags"));
                newsArticle.setPriority(resultSet.getInt("priority"));
                newsArticle.setWordCount(resultSet.getInt("word_count"));
                newsArticle.setViewCount(resultSet.getInt("view_count"));
                newsArticle.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticle.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticle.setSlug(resultSet.getString("slug"));


                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticle.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticle.setCategoryIds(categoryIdList);
                newsArticle.setCategoryNames(categoryNameList);


                return newsArticle;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticle;
    }

    @Override
    public NewsArticleVW getNewsArticleVWByNewsArticleId(int news_article_id)
    {
        NewsArticleVW newsArticleVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_view WHERE news_article_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleVW = new NewsArticleVW();
                newsArticleVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleVW.setHeadline(resultSet.getString("headline"));
                newsArticleVW.setExtract(resultSet.getString("extract"));
                newsArticleVW.setText(resultSet.getString("text"));
                newsArticleVW.setPublishDate(resultSet.getDate("publish_date"));
                newsArticleVW.setPublishDateStr(resultSet.getString("publish_datestr"));
                newsArticleVW.setByAuthor(resultSet.getString("by_author"));
                newsArticleVW.setTweetText(resultSet.getString("tweet_text"));
                newsArticleVW.setSource(resultSet.getString("source"));
                newsArticleVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticleVW.setClientQuote(resultSet.getString("client_quote"));
                newsArticleVW.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticleVW.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticleVW.setHtmlTitle(resultSet.getString("html_title"));
                newsArticleVW.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticleVW.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticleVW.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticleVW.setTags(resultSet.getString("tags"));
                newsArticleVW.setPriority(resultSet.getInt("priority"));
                newsArticleVW.setWordCount(resultSet.getInt("word_count"));
                newsArticleVW.setViewCount(resultSet.getInt("view_count"));
                newsArticleVW.setNewsState(resultSet.getString("news_state"));
                newsArticleVW.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticleVW.setLocalUser(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                newsArticleVW.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticleVW.setFilePath(resultSet.getString("file_path"));
                newsArticleVW.setSlug(resultSet.getString("slug"));

                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticleVW.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticleVW.setCategoryIds(categoryIdList);
                newsArticleVW.setCategoryNames(categoryNameList);

                return newsArticleVW;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleVW;
    }

    @Override
    public NewsArticle getNewsArticleByHeadLine(String headline)
    {
        NewsArticle newsArticle = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article WHERE headline=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, headline);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticle = new NewsArticle();
                newsArticle.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticle.setHeadline(resultSet.getString("headline"));
                newsArticle.setExtract(resultSet.getString("extract"));
                newsArticle.setText(resultSet.getString("text"));
                newsArticle.setPublishDate(resultSet.getDate("publish_date"));
                newsArticle.setByAuthor(resultSet.getString("by_author"));
                newsArticle.setTweetText(resultSet.getString("tweet_text"));
                newsArticle.setSource(resultSet.getString("source"));
                newsArticle.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticle.setClientQuote(resultSet.getString("client_quote"));
                newsArticle.setCreatedDate(resultSet.getDate("created_date"));
                newsArticle.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticle.setHtmlTitle(resultSet.getString("html_title"));
                newsArticle.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticle.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticle.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticle.setTags(resultSet.getString("tags"));
                newsArticle.setPriority(resultSet.getInt("priority"));
                newsArticle.setWordCount(resultSet.getInt("word_count"));
                newsArticle.setViewCount(resultSet.getInt("view_count"));
                newsArticle.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticle.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticle.setSlug(resultSet.getString("slug"));


                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticle.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticle.setCategoryIds(categoryIdList);
                newsArticle.setCategoryNames(categoryNameList);

                return newsArticle;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticle;
    }

    @Override
    public NewsArticleVW getNewsArticleVWByHeadLine(String headline)
    {
        NewsArticleVW newsArticleVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_view WHERE headline=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, headline);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleVW = new NewsArticleVW();
                newsArticleVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleVW.setHeadline(resultSet.getString("headline"));
                newsArticleVW.setExtract(resultSet.getString("extract"));
                newsArticleVW.setText(resultSet.getString("text"));
                newsArticleVW.setPublishDate(resultSet.getDate("publish_date"));
                newsArticleVW.setPublishDateStr(resultSet.getString("publish_datestr"));
                newsArticleVW.setByAuthor(resultSet.getString("by_author"));
                newsArticleVW.setTweetText(resultSet.getString("tweet_text"));
                newsArticleVW.setSource(resultSet.getString("source"));
                newsArticleVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticleVW.setClientQuote(resultSet.getString("client_quote"));
                newsArticleVW.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticleVW.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticleVW.setHtmlTitle(resultSet.getString("html_title"));
                newsArticleVW.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticleVW.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticleVW.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticleVW.setTags(resultSet.getString("tags"));
                newsArticleVW.setPriority(resultSet.getInt("priority"));
                newsArticleVW.setWordCount(resultSet.getInt("word_count"));
                newsArticleVW.setViewCount(resultSet.getInt("view_count"));
                newsArticleVW.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticleVW.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticleVW.setFilePath(resultSet.getString("file_path"));
                newsArticleVW.setSlug(resultSet.getString("slug"));


                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticleVW.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticleVW.setCategoryIds(categoryIdList);
                newsArticleVW.setCategoryNames(categoryNameList);

                return newsArticleVW;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleVW;
    }

    @Override
    public NewsArticleVW getNewsArticleVWBySlug(String slug)
    {
        NewsArticleVW newsArticleVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_view WHERE slug=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, slug);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleVW = new NewsArticleVW();
                newsArticleVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleVW.setHeadline(resultSet.getString("headline"));
                newsArticleVW.setExtract(resultSet.getString("extract"));
                newsArticleVW.setText(resultSet.getString("text"));
                newsArticleVW.setPublishDate(resultSet.getDate("publish_date"));
                newsArticleVW.setPublishDateStr(resultSet.getString("publish_datestr"));
                newsArticleVW.setByAuthor(resultSet.getString("by_author"));
                newsArticleVW.setTweetText(resultSet.getString("tweet_text"));
                newsArticleVW.setSource(resultSet.getString("source"));
                newsArticleVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsArticleVW.setClientQuote(resultSet.getString("client_quote"));
                newsArticleVW.setCreatedDate(resultSet.getDate("created_datestr"));
                newsArticleVW.setLastModifiedDate(resultSet.getDate("last_modified_date"));
                newsArticleVW.setHtmlTitle(resultSet.getString("html_title"));
                newsArticleVW.setHtmlMetaDescription(resultSet.getString("html_meta_description"));
                newsArticleVW.setHtmlMetaKeywords(resultSet.getString("html_meta_keywords"));
                newsArticleVW.setHtmlMetaLanguage(resultSet.getString("html_meta_language"));
                newsArticleVW.setTags(resultSet.getString("tags"));
                newsArticleVW.setPriority(resultSet.getInt("priority"));
                newsArticleVW.setWordCount(resultSet.getInt("word_count"));
                newsArticleVW.setViewCount(resultSet.getInt("view_count"));
                newsArticleVW.setLocalUserId(resultSet.getLong("local_user_id"));
                newsArticleVW.setMainFileId(resultSet.getInt("main_file_id"));
                newsArticleVW.setFilePath(resultSet.getString("file_path"));
                newsArticleVW.setSlug(resultSet.getString("slug"));


                List<NewsArticleCategoryVW> nacList = newsArticleCategoryDAO.getNewsArticleCategoryVWListByNewsArticleId(newsArticleVW.getNewsArticleId());
                List<Integer> categoryIdList = new ArrayList<>();
                List<String> categoryNameList = new ArrayList<>();
                for(NewsArticleCategoryVW nacVW: nacList)
                {
                    categoryIdList.add(nacVW.getNewsCategoryId());
                    categoryNameList.add(nacVW.getNewsCategory());
                }
                newsArticleVW.setCategoryIds(categoryIdList);
                newsArticleVW.setCategoryNames(categoryNameList);

                return newsArticleVW;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleVW;
    }

    @Override
    public boolean addNewsArticle(NewsArticle newsArticle)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            Timestamp publishTime = new Timestamp(newsArticle.getPublishDate().getTime());
            Timestamp createdTime = new Timestamp(newsArticle.getCreatedDate().getTime());
            Timestamp lastModifiedTime = new Timestamp(newsArticle.getLastModifiedDate().getTime());

            String queryString = "INSERT INTO news_article (headline,extract,text,publish_date,by_author,tweet_text,source,news_state_id," +
                    "client_quote,created_date,last_modified_date,html_title,html_meta_description,html_meta_keywords,html_meta_language," +
                    "tags,priority, word_count,view_count,local_user_id, main_file_id, slug) " +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, newsArticle.getHeadline());
            pstmt.setString(2, newsArticle.getExtract());
            pstmt.setString(3, newsArticle.getText());
            pstmt.setTimestamp(4, publishTime);
            pstmt.setString(5, newsArticle.getByAuthor());
            pstmt.setString(6, newsArticle.getTweetText());
            pstmt.setString(7, newsArticle.getSource());
            pstmt.setInt(8, newsArticle.getNewsStateId());
            pstmt.setString(9, newsArticle.getClientQuote());
            pstmt.setTimestamp(10, createdTime);
            pstmt.setTimestamp(11, lastModifiedTime);
            pstmt.setString(12, newsArticle.getHtmlTitle());
            pstmt.setString(13, newsArticle.getHtmlMetaDescription());
            pstmt.setString(14, newsArticle.getHtmlMetaKeywords());
            pstmt.setString(15, newsArticle.getHtmlMetaLanguage());
            pstmt.setString(16, newsArticle.getTags());
            pstmt.setInt(17, newsArticle.getPriority());
            pstmt.setInt(18, newsArticle.getWordCount());
            pstmt.setInt(19, newsArticle.getViewCount());
            pstmt.setLong(20, newsArticle.getLocalUserId());
            if(newsArticle.getMainFileId() != 0)
            {
                pstmt.setInt(21, newsArticle.getMainFileId());
            }else
            {
                pstmt.setNull(21, Types.NULL);
            }
            pstmt.setString(22, newsArticle.getSlug());

            pstmt.executeUpdate();

            connection.commit();

            NewsArticle added = getNewsArticleByHeadLine(newsArticle.getHeadline());
            for(int i = 0; i < newsArticle.getCategoryIds().size(); i++)
            {
                NewsArticleCategory nac = new NewsArticleCategory();
                nac.setNewsArticleId(added.getNewsArticleId());

                String s = newsArticle.getCategoryIds().get(i) + "";
                nac.setNewsCategoryId(Integer.parseInt(s));

                newsArticleCategoryDAO.addNewsArticleCategory(nac);
            }

            System.out.println("NewsArticle Added Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally
        {
            try
            {
                if (pstmt != null)
                {
                    pstmt.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateNewsArticle(NewsArticle newsArticle)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            Timestamp publishTime = new Timestamp(newsArticle.getPublishDate().getTime());
            Timestamp createdTime = new Timestamp(newsArticle.getCreatedDate().getTime());
            Timestamp lastModifiedTime = new Timestamp(newsArticle.getLastModifiedDate().getTime());

            String queryString = "UPDATE news_article SET headline=?,extract=?,text=?,publish_date=?,by_author=?,tweet_text=?,source=?,news_state_id=?," +
                    "client_quote=?,created_date=?,last_modified_date=?,html_title=?,html_meta_description=?,html_meta_keywords=?,html_meta_language=?," +
                    "tags=?,priority=?,word_count=?,view_count=?, local_user_id=?, main_file_id=?, slug=?" +
                    " WHERE news_article_id=? ";

            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, newsArticle.getHeadline());
            pstmt.setString(2, newsArticle.getExtract());
            pstmt.setString(3, newsArticle.getText());
            pstmt.setTimestamp(4, publishTime);
            pstmt.setString(5, newsArticle.getByAuthor());
            pstmt.setString(6, newsArticle.getTweetText());
            pstmt.setString(7, newsArticle.getSource());
            pstmt.setInt(8, newsArticle.getNewsStateId());
            pstmt.setString(9, newsArticle.getClientQuote());
            pstmt.setTimestamp(10, createdTime);
            pstmt.setTimestamp(11, lastModifiedTime);
            pstmt.setString(12, newsArticle.getHtmlTitle());
            pstmt.setString(13, newsArticle.getHtmlMetaDescription());
            pstmt.setString(14, newsArticle.getHtmlMetaKeywords());
            pstmt.setString(15, newsArticle.getHtmlMetaLanguage());
            pstmt.setString(16, newsArticle.getTags());
            pstmt.setInt(17, newsArticle.getPriority());
            pstmt.setInt(18, newsArticle.getWordCount());
            pstmt.setInt(19, newsArticle.getViewCount());
            pstmt.setLong(20, newsArticle.getLocalUserId());
            if(newsArticle.getMainFileId() == 0)
            {
                pstmt.setNull(21, Types.INTEGER);
            }else
            {
                pstmt.setInt(21, newsArticle.getMainFileId());
            }
            pstmt.setString(22, newsArticle.getSlug());
            pstmt.setInt(23, newsArticle.getNewsArticleId());



            pstmt.executeUpdate();

            connection.commit();

            newsArticleCategoryDAO.deleteNewsArticleCategoryByNewsArticleId(newsArticle.getNewsArticleId());

            for(int i = 0; i < newsArticle.getCategoryIds().size(); i++)
            {
                NewsArticleCategory nac = new NewsArticleCategory();
                nac.setNewsArticleId(newsArticle.getNewsArticleId());

                String s = newsArticle.getCategoryIds().get(i) + "";
                nac.setNewsCategoryId(Integer.parseInt(s));

                newsArticleCategoryDAO.addNewsArticleCategory(nac);
            }

            System.out.println("NewsArticle Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally
        {
            try
            {
                if (pstmt != null)
                {
                    pstmt.close();
                }
                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
        return false;
    }

    @Override
    public boolean updateNewsArticle(NewsArticleVW newsArticleVW)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            Timestamp publishTime = new Timestamp(newsArticleVW.getPublishDate().getTime());
            Timestamp createdTime = new Timestamp(newsArticleVW.getCreatedDate().getTime());
            Timestamp lastModifiedTime = new Timestamp(newsArticleVW.getLastModifiedDate().getTime());

            String queryString = "UPDATE news_article SET headline=?,extract=?,text=?,publish_date=?,by_author=?,tweet_text=?,source=?,news_state_id=?," +
                    "client_quote=?,created_date=?,last_modified_date=?,html_title=?,html_meta_description=?,html_meta_keywords=?,html_meta_language=?," +
                    "tags=?,priority=?,word_count=?,view_count=?, local_user_id=?, main_file_id=?, slug=?" +
                    " WHERE news_article_id=? ";

            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, newsArticleVW.getHeadline());
            pstmt.setString(2, newsArticleVW.getExtract());
            pstmt.setString(3, newsArticleVW.getText());
            pstmt.setTimestamp(4, publishTime);
            pstmt.setString(5, newsArticleVW.getByAuthor());
            pstmt.setString(6, newsArticleVW.getTweetText());
            pstmt.setString(7, newsArticleVW.getSource());
            pstmt.setInt(8, newsArticleVW.getNewsStateId());
            pstmt.setString(9, newsArticleVW.getClientQuote());
            pstmt.setTimestamp(10, createdTime);
            pstmt.setTimestamp(11, lastModifiedTime);
            pstmt.setString(12, newsArticleVW.getHtmlTitle());
            pstmt.setString(13, newsArticleVW.getHtmlMetaDescription());
            pstmt.setString(14, newsArticleVW.getHtmlMetaKeywords());
            pstmt.setString(15, newsArticleVW.getHtmlMetaLanguage());
            pstmt.setString(16, newsArticleVW.getTags());
            pstmt.setInt(17, newsArticleVW.getPriority());
            pstmt.setInt(18, newsArticleVW.getWordCount());
            pstmt.setInt(19, newsArticleVW.getViewCount());
            pstmt.setLong(20, newsArticleVW.getLocalUserId());
            if(newsArticleVW.getMainFileId() == 0)
            {
                pstmt.setNull(21, Types.INTEGER);
            }else
            {
                pstmt.setInt(21, newsArticleVW.getMainFileId());
            }
            pstmt.setString(22, newsArticleVW.getSlug());
            pstmt.setInt(23, newsArticleVW.getNewsArticleId());

            pstmt.executeUpdate();

            connection.commit();

            newsArticleCategoryDAO.deleteNewsArticleCategoryByNewsArticleId(newsArticleVW.getNewsArticleId());

            for(int i = 0; i < newsArticleVW.getCategoryIds().size(); i++)
            {
                NewsArticleCategory nac = new NewsArticleCategory();
                nac.setNewsArticleId(newsArticleVW.getNewsArticleId());

                String s = newsArticleVW.getCategoryIds().get(i) + "";
                nac.setNewsCategoryId(Integer.parseInt(s));

                newsArticleCategoryDAO.addNewsArticleCategory(nac);
            }

            System.out.println("NewsArticle Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally
        {
            try
            {
                if (pstmt != null)
                {
                    pstmt.close();
                }
                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
        return false;
    }

    @Override
    public boolean updateNewsArticleViewCount(NewsArticleVW newsArticleVW)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {

            String queryString = "UPDATE news_article SET view_count=? WHERE news_article_id=? ";

            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleVW.getViewCount());
            pstmt.setInt(2, newsArticleVW.getNewsArticleId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticle Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally
        {
            try
            {
                if (pstmt != null)
                {
                    pstmt.close();
                }
                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
        return false;
    }

    @Override
    public boolean deleteNewsArticle(int news_article_id)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "DELETE FROM news_article WHERE news_article_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticle deleted Successfully");

            return true;

        } catch (Exception e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally
        {
            try
            {
                if (pstmt != null)
                {
                    pstmt.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public int getNewsArticleCount(Map<String, Object> filters)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int newsArticleCount = 0;

        try
        {
            String queryString = "SELECT COUNT(news_article_id) as newsArticleCount FROM news_article_view WHERE 1=1 ";
            connection = ConnectionFactory.getInstance().getConnection();


            if (filters != null && filters.size() > 0)
            {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); )
                {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if(!filterProperty.equals("globalFilter"))
                    {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue + "%'";
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleCount = resultSet.getInt("newsArticleCount");
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCount;
    }

    @Override
    public int getNewsArticleCount(NewsStateEnum newsStateEnum, Map<String, Object> filters)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int newsArticleCount = 0;

        try
        {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT COUNT(news_article_id) as newsArticleCount FROM news_article_view WHERE 1=1 ";

            String newsStateQuery = "";
            switch (newsStateEnum)
            {
                case ALL:
                    newsStateQuery = "";
                    break;
                case LIVE:
                    newsStateQuery = " AND news_state_id=1 ";
                    break;
                case APPROVAL:
                    newsStateQuery = " AND news_state_id=2 ";
                    break;
                case DRAFT:
                    newsStateQuery = " AND news_state_id=3 ";
                    break;
                case DELETED:
                    newsStateQuery = " AND news_state_id=4 ";
                    break;

            }

            queryString += newsStateQuery;

            if (filters != null && filters.size() > 0)
            {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); )
                {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if(!filterProperty.equals("globalFilter"))
                    {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue + "%'";
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleCount = resultSet.getInt("newsArticleCount");
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (pstmt != null)
                {
                    pstmt.close();
                }

                if (connection != null)
                {
                    connection.close();
                }

            } catch (SQLException e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCount;
    }

}
