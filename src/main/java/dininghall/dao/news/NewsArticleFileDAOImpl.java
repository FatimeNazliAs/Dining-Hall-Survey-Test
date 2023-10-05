/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.common.ConnectionFactory;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.news.NewsArticleFile;
import dininghall.domain.news.NewsArticleFileVW;

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
public class NewsArticleFileDAOImpl implements NewsArticleFileDAO, Serializable
{

    @Override
    public List<NewsArticleFile> getNewsArticleFileList()
    {
        List<NewsArticleFile> newsArticleFileList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_file";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {

                NewsArticleFile newsArticleFile = new NewsArticleFile();
                newsArticleFile.setNewsArticleFileId(resultSet.getInt("news_article_file_id"));
                newsArticleFile.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleFile.setFilePath(resultSet.getString("file_path"));

                newsArticleFileList.add(newsArticleFile);
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

        return newsArticleFileList;

    }

    @Override
    public List<NewsArticleFile> getNewsArticleFileListByNewsArticleId(int newsArticleId)
    {
        List<NewsArticleFile> newsArticleFileList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_file WHERE news_article_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {

                NewsArticleFile newsArticleFile = new NewsArticleFile();
                newsArticleFile.setNewsArticleFileId(resultSet.getInt("news_article_file_id"));
                newsArticleFile.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleFile.setFilePath(resultSet.getString("file_path"));

                newsArticleFileList.add(newsArticleFile);
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

        return newsArticleFileList;

    }

    @Override
    public List<NewsArticleFileVW> getNewsArticleFileVWList()
    {
        List<NewsArticleFileVW> newsArticleFileVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_file";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsArticleFileVW newsArticleFileVW = new NewsArticleFileVW();
                newsArticleFileVW.setNewsArticleFileId(resultSet.getInt("news_article_file_id"));
                newsArticleFileVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleFileVW.setFilePath(resultSet.getString("file_path"));
                newsArticleFileVWList.add(newsArticleFileVW);
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

        return newsArticleFileVWList;

    }

    @Override
    public NewsArticleFile getNewsArticleFileByNewsArticleFileId(int newsArticleFileId)
    {
        NewsArticleFile newsArticleFile = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_file WHERE news_article_file_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleFileId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleFile = new NewsArticleFile();
                newsArticleFile.setNewsArticleFileId(resultSet.getInt("news_article_file_id"));
                newsArticleFile.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleFile.setFilePath(resultSet.getString("file_path"));

                return newsArticleFile;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleFile;
    }

    public NewsArticleFileVW getNewsArticleFileVWByNewsArticleFileId(int newsArticleFileId)
    {
        NewsArticleFileVW newsArticleFileVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_file WHERE news_article_file_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleFileId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleFileVW = new NewsArticleFileVW();
                newsArticleFileVW.setNewsArticleFileId(resultSet.getInt("news_article_file_id"));
                newsArticleFileVW.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleFileVW.setFilePath(resultSet.getString("file_path"));

                return newsArticleFileVW;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleFileVW;
    }

    @Override
    public NewsArticleFile getNewsArticleFileByName(String name)
    {
        NewsArticleFile newsArticleFile = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_article_file WHERE news_article_file=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsArticleFile = new NewsArticleFile();
                newsArticleFile.setNewsArticleFileId(resultSet.getInt("news_article_file_id"));
                newsArticleFile.setNewsArticleId(resultSet.getInt("news_article_id"));
                newsArticleFile.setFilePath(resultSet.getString("file_path"));

                return newsArticleFile;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsArticleFile;
    }

    @Override
    public boolean addNewsArticleFile(NewsArticleFile newsArticleFile)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "INSERT INTO news_article_file (news_article_id, file_path) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleFile.getNewsArticleId());
            pstmt.setString(2, newsArticleFile.getFilePath());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleFile Added Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateNewsArticleFile(NewsArticleFile newsArticleFile)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "UPDATE news_article_file SET news_article_id=?, file_path=? WHERE news_article_file_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleFile.getNewsArticleId());
            pstmt.setString(2, newsArticleFile.getFilePath());
            pstmt.setInt(3, newsArticleFile.getNewsArticleFileId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleFile Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateNewsArticleFile(NewsArticleFileVW newsArticleFileVW)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "UPDATE news_article_file SET news_article_id=?, file_path=?  WHERE news_article_file_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, newsArticleFileVW.getNewsArticleId());
            pstmt.setString(2, newsArticleFileVW.getFilePath());
            pstmt.setInt(3, newsArticleFileVW.getNewsArticleFileId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleFile Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteNewsArticleFile(int newsArticleFileId)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "DELETE FROM news_article_file WHERE news_article_file_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsArticleFileId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsArticleFile deleted Successfully");

            return true;

        } catch (Exception e)
        {
            Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsArticleFileDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
