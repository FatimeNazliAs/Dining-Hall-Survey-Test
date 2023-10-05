/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.common.ConnectionFactory;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.news.NewsState;
import dininghall.domain.news.NewsStateVW;

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
public class NewsStateDAOImpl implements NewsStateDAO, Serializable
{

    @Override
    public List<NewsState> getNewsStateList()
    {
        List<NewsState> newsStateList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_state";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {

                NewsState newsState = new NewsState();
                newsState.setNewsStateId(resultSet.getInt("news_state_id"));
                newsState.setNewsState(resultSet.getString("news_state"));

                newsStateList.add(newsState);
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

        return newsStateList;

    }

    @Override
    public List<NewsStateVW> getNewsStateVWList()
    {
        List<NewsStateVW> newsStateVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_state";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
            {
                NewsStateVW newsStateVW = new NewsStateVW();
                newsStateVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsStateVW.setNewsState(resultSet.getString("news_state"));
                newsStateVW.setNewNewsStateId(resultSet.getInt("news_state_id"));
                newsStateVWList.add(newsStateVW);
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

        return newsStateVWList;

    }

    @Override
    public NewsState getNewsStateByNewsStateId(int newsStateId)
    {
        NewsState newsState = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_state WHERE news_state_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsStateId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsState = new NewsState();
                newsState.setNewsStateId(resultSet.getInt("news_state_id"));
                newsState.setNewsState(resultSet.getString("news_state"));
                newsState.setNewNewsStateId(resultSet.getInt("news_state_id"));

                return newsState;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsState;
    }

    public NewsStateVW getNewsStateVWByNewsStateId(int newsStateId)
    {
        NewsStateVW newsStateVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_state WHERE news_state_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsStateId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsStateVW = new NewsStateVW();
                newsStateVW.setNewsStateId(resultSet.getInt("news_state_id"));
                newsStateVW.setNewsState(resultSet.getString("news_state"));
                newsStateVW.setNewNewsStateId(resultSet.getInt("news_state_id"));

                return newsStateVW;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsStateVW;
    }

    @Override
    public NewsState getNewsStateByName(String name)
    {
        NewsState newsState = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try
        {
            String queryString = "SELECT * FROM news_state WHERE news_state=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next())
            {
                newsState = new NewsState();
                newsState.setNewsStateId(resultSet.getInt("news_state_id"));
                newsState.setNewsState(resultSet.getString("news_state"));
                newsState.setNewNewsStateId(resultSet.getInt("news_state_id"));

                return newsState;
            }

        } catch (SQLException e)
        {
            Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsState;
    }

    @Override
    public boolean addNewsState(NewsState newsState)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "INSERT INTO news_state (news_state_id, news_state) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsState.getNewsStateId());
            pstmt.setString(2, newsState.getNewsState());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsState Added Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateNewsState(NewsState newsState)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "UPDATE news_state SET news_state_id=?, news_state=? WHERE news_state_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsState.getNewNewsStateId());
            pstmt.setString(2, newsState.getNewsState());
            pstmt.setInt(3, newsState.getNewsStateId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsState Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateNewsState(NewsStateVW newsStateVW)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "UPDATE news_state SET news_state_id=?, news_state=?  WHERE news_state_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, newsStateVW.getNewNewsStateId());
            pstmt.setString(2, newsStateVW.getNewsState());
            pstmt.setInt(3, newsStateVW.getNewsStateId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsState Updated Successfully");

            return true;

        } catch (SQLException e)
        {
            Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteNewsState(int newsStateId)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try
        {
            String queryString = "DELETE FROM news_state WHERE news_state_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsStateId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsState deleted Successfully");

            return true;

        } catch (Exception e)
        {
            Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try
            {
                if (connection != null)
                {
                    connection.rollback();
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e)
            {
                Logger.getLogger(NewsStateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
