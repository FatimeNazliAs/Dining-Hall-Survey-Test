/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.common.ConnectionFactory;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.news.NewsCategory;
import dininghall.domain.news.NewsCategoryVW;

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
public class NewsCategoryDAOImpl implements NewsCategoryDAO, Serializable
{

    @Override
    public List<NewsCategory> getNewsCategoryList()
    {
        List<NewsCategory> newsCategoryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_category";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {

                NewsCategory newsCategory = new NewsCategory();
                newsCategory.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsCategory.setNewsCategory(resultSet.getString("news_category"));

                newsCategoryList.add(newsCategory);
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

        return newsCategoryList;

    }

    @Override
    public List<NewsCategoryVW> getNewsCategoryVWList()
    {
        List<NewsCategoryVW> newsCategoryVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_category";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsCategoryVW newsCategoryVW = new NewsCategoryVW();
                newsCategoryVW.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsCategoryVW.setNewsCategory(resultSet.getString("news_category"));
                newsCategoryVW.setNewNewsCategoryId(resultSet.getInt("news_category_id"));
                newsCategoryVWList.add(newsCategoryVW);
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

        return newsCategoryVWList;

    }

    @Override
    public NewsCategory getNewsCategoryByNewsCategoryId(int newsCategoryId)
    {
        NewsCategory newsCategory = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_category WHERE news_category_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsCategoryId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsCategory = new NewsCategory();
                newsCategory.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsCategory.setNewsCategory(resultSet.getString("news_category"));
                newsCategory.setNewNewsCategoryId(resultSet.getInt("news_category_id"));

                return newsCategory;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsCategory;
    }

    public NewsCategoryVW getNewsCategoryVWByNewsCategoryId(int newsCategoryId)
    {
        NewsCategoryVW newsCategoryVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_category WHERE news_category_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsCategoryId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsCategoryVW = new NewsCategoryVW();
                newsCategoryVW.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsCategoryVW.setNewsCategory(resultSet.getString("news_category"));
                newsCategoryVW.setNewNewsCategoryId(resultSet.getInt("news_category_id"));

                return newsCategoryVW;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsCategoryVW;
    }

    @Override
    public NewsCategory getNewsCategoryByName(String name)
    {
        NewsCategory newsCategory = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_category WHERE news_category=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsCategory = new NewsCategory();
                newsCategory.setNewsCategoryId(resultSet.getInt("news_category_id"));
                newsCategory.setNewsCategory(resultSet.getString("news_category"));
                newsCategory.setNewNewsCategoryId(resultSet.getInt("news_category_id"));

                return newsCategory;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsCategory;
    }

    @Override
    public boolean addNewsCategory(NewsCategory newsCategory)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "INSERT INTO news_category (news_category_id, news_category) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsCategory.getNewsCategoryId());
            pstmt.setString(2, newsCategory.getNewsCategory());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsCategory Added Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateNewsCategory(NewsCategory newsCategory)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "UPDATE news_category SET news_category_id =?, news_category=? WHERE news_category_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsCategory.getNewNewsCategoryId());
            pstmt.setString(2, newsCategory.getNewsCategory());
            pstmt.setInt(3, newsCategory.getNewsCategoryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsCategory Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateNewsCategory(NewsCategoryVW newsCategoryVW)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "UPDATE news_category SET news_category_id=?, news_category=?  WHERE news_category_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, newsCategoryVW.getNewNewsCategoryId());
            pstmt.setString(2, newsCategoryVW.getNewsCategory());
            pstmt.setInt(3, newsCategoryVW.getNewsCategoryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsCategory Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteNewsCategory(int newsCategoryId)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "DELETE FROM news_category WHERE news_category_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsCategoryId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsCategory deleted Successfully");

            return true;

        } catch (Exception e)
        {
            Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
