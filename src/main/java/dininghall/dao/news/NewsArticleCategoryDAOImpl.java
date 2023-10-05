/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.common.ConnectionFactory;
import dininghall.domain.news.NewsArticleCategory;
import dininghall.domain.news.NewsArticleCategoryVW;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class NewsArticleCategoryDAOImpl implements NewsArticleCategoryDAO, Serializable
{

    @Override
    public List<NewsArticleCategory> getNewsArticleCategoryList()
    {
        List<NewsArticleCategory> newsArticleCategoryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_category";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleCategory newsArticleCategory = new NewsArticleCategory();
                newsArticleCategory.setNewsArticleCategoryId(resultSet.getInt("news_article_category_id"));
                newsArticleCategory.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsArticleCategory.setNewsArticleId(resultSet.getInt("news_article_id"));

                newsArticleCategoryList.add(newsArticleCategory);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCategoryList;

    }

    @Override
    public List<NewsArticleCategoryVW> getNewsArticleCategoryVWList()
    {
        List<NewsArticleCategoryVW> newsArticleCategoryVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_category_view";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleCategoryVW newsArticleCategoryVW = new NewsArticleCategoryVW();
                newsArticleCategoryVW.setNewsArticleCategoryId(resultSet.getInt("news_article_category_id"));
                newsArticleCategoryVW.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsArticleCategoryVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleCategoryVW.setNewsCategory(resultSet.getString("news_category"));

                newsArticleCategoryVWList.add(newsArticleCategoryVW);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCategoryVWList;

    }

    @Override
    public NewsArticleCategory getNewsArticleCategoryByNewsArticleCategoryId(int news_article_category_id)
    {
        NewsArticleCategory newsArticleCategory = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_category WHERE news_article_category_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_category_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleCategory = new NewsArticleCategory();
                newsArticleCategory.setNewsArticleCategoryId(resultSet.getInt("news_article_category_id"));
                newsArticleCategory.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsArticleCategory.setNewsArticleId(resultSet.getInt("news_article_id"));

                return newsArticleCategory;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCategory;
    }

    @Override
    public NewsArticleCategoryVW getNewsArticleCategoryVWByNewsArticleCategoryId(int news_article_category_id)
    {
        NewsArticleCategoryVW newsArticleCategoryVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_category_view WHERE news_article_category_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_category_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleCategoryVW = new NewsArticleCategoryVW();
                newsArticleCategoryVW.setNewsArticleCategoryId(resultSet.getInt("news_article_category_id"));
                newsArticleCategoryVW.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsArticleCategoryVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleCategoryVW.setNewsCategory(resultSet.getString("news_category"));

                return newsArticleCategoryVW;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCategoryVW;
    }

    @Override
    public List<NewsArticleCategory> getNewsArticleCategoryListByNewsArticleId(int news_article_id)
    {
        List<NewsArticleCategory> newsArticleCategoryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_category WHERE news_article_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_id);

            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleCategory newsArticleCategory = new NewsArticleCategory();
                newsArticleCategory.setNewsArticleCategoryId(resultSet.getInt("news_article_category_id"));
                newsArticleCategory.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsArticleCategory.setNewsArticleId(resultSet.getInt("news_article_id"));

                newsArticleCategoryList.add(newsArticleCategory);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCategoryList;
    }

    @Override
    public List<NewsArticleCategoryVW> getNewsArticleCategoryVWListByNewsArticleId(int news_article_id)
    {
        List<NewsArticleCategoryVW> newsArticleCategoryVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_category_view WHERE news_article_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_id);

            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleCategoryVW newsArticleCategoryVW = new NewsArticleCategoryVW();
                newsArticleCategoryVW.setNewsArticleCategoryId(resultSet.getInt("news_article_category_id"));
                newsArticleCategoryVW.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsArticleCategoryVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleCategoryVW.setNewsCategory(resultSet.getString("news_category"));

                newsArticleCategoryVWList.add(newsArticleCategoryVW);
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCategoryVWList;
    }

    @Override
    public NewsArticleCategory getNewsArticleCategoryByName(String name)
    {
        NewsArticleCategory newsArticleCategory = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_category WHERE news_article_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleCategory = new NewsArticleCategory();
                newsArticleCategory.setNewsArticleCategoryId(resultSet.getInt("news_article_category_id"));
                newsArticleCategory.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsArticleCategory.setNewsArticleId(resultSet.getInt("news_article_id"));

                return newsArticleCategory;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleCategory;
    }

    @Override
    public boolean addNewsArticleCategory(NewsArticleCategory newsArticleCategory)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "INSERT INTO news_article_category(news_article_id, news_category_id) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleCategory.getNewsArticleId());
            pstmt.setInt(2, newsArticleCategory.getNewsCategoryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleCategory Added Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateNewsArticleCategory(NewsArticleCategory newsArticleCategory)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = " UPDATE news_article_category SET news_article_id=?, news_category_id=? WHERE news_article_category_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleCategory.getNewsArticleId());
            pstmt.setInt(2, newsArticleCategory.getNewsCategoryId());
            pstmt.setInt(3, newsArticleCategory.getNewsArticleCategoryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleCategory Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateNewsArticleCategory(NewsArticleCategoryVW newsArticleCategoryVW)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = " UPDATE news_article_category SET news_article_id=?, news_category_id=? WHERE news_article_category_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleCategoryVW.getNewsArticleId());
            pstmt.setInt(2, newsArticleCategoryVW.getNewsCategoryId());
            pstmt.setInt(3, newsArticleCategoryVW.getNewsArticleCategoryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleCategory Updated Successfully");

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteNewsArticleCategory(int news_article_category_id)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "DELETE FROM news_article_category WHERE news_article_category_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_category_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleCategory deleted Successfully");

            return true;

        } catch (Exception e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean deleteNewsArticleCategoryByNewsArticleId(int news_article_id)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "DELETE FROM news_article_category WHERE news_article_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, news_article_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleCategory deleted Successfully");

            return true;

        } catch (Exception e)
        {
            Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }
    
}
