/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import org.primefaces.model.SortOrder;
import dininghall.common.ConnectionFactory;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.common.NewsletterUnRegister;
import dininghall.domain.common.NewsletterUnRegisterVW;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class NewsletterUnRegisterDAOImpl implements NewsletterUnRegisterDAO, Serializable {

    @Override
    public List<NewsletterUnRegister> getNewsletterUnRegisterList() {
        List<NewsletterUnRegister> newsletterUnRegisterList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM NewsletterUnRegister";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                NewsletterUnRegister newsletterUnRegister = new NewsletterUnRegister();
                newsletterUnRegister.setUnRegisterId(resultSet.getInt("unRegisterId"));
                newsletterUnRegister.setEmail(resultSet.getString("email"));
                newsletterUnRegister.setUnRegisterDate(resultSet.getDate("unRegisterDate"));
                newsletterUnRegister.setComment(resultSet.getString("comment"));

                newsletterUnRegisterList.add(newsletterUnRegister);
            }

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsletterUnRegisterList;

    }

    @Override
    public List<NewsletterUnRegisterVW> getNewsletterUnRegisterVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<NewsletterUnRegisterVW> newsletterUnRegisterVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY unRegisterDate DESC, email ASC ) as row " +
                    "FROM NewsletterUnRegisterView WHERE 1=1 ";


            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if (!filterProperty.equals("globalFilter")) {
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
            while (resultSet.next()) {
                NewsletterUnRegisterVW newsletterUnRegisterVW = new NewsletterUnRegisterVW();
                newsletterUnRegisterVW.setUnRegisterId(resultSet.getInt("unRegisterId"));
                newsletterUnRegisterVW.setEmail(resultSet.getString("email"));
                newsletterUnRegisterVW.setUnRegisterDate(resultSet.getDate("unRegisterDate"));
                newsletterUnRegisterVW.setUnRegisterDateStr(resultSet.getString("unRegisterDateStr"));

                newsletterUnRegisterVWList.add(newsletterUnRegisterVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsletterUnRegisterVWList;
    }

    @Override
    public NewsletterUnRegisterVW getNewsletterUnRegisterVWUnRegisterId(int unRegisterId) {
        NewsletterUnRegisterVW newsletterUnRegisterVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM NewsletterUnRegisterView WHERE newsletterId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, unRegisterId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                newsletterUnRegisterVW = new NewsletterUnRegisterVW();
                newsletterUnRegisterVW.setUnRegisterId(resultSet.getInt("unRegisterId"));
                newsletterUnRegisterVW.setEmail(resultSet.getString("email"));
                newsletterUnRegisterVW.setUnRegisterDate(resultSet.getDate("unRegisterDate"));
                newsletterUnRegisterVW.setUnRegisterDateStr(resultSet.getString("unRegisterDateStr"));

                return newsletterUnRegisterVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsletterUnRegisterVW;
    }


    @Override
    public boolean addNewsletterUnRegister(NewsletterUnRegister newsletterUnRegister) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO NewsletterUnRegister(email, unRegisterDate, comment) VALUES(?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, newsletterUnRegister.getEmail());
            pstmt.setTimestamp(2, new java.sql.Timestamp((new Date()).getTime()));
            pstmt.setString(3, newsletterUnRegister.getComment());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsletterUnRegister Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }


    @Override
    public boolean deleteNewsletterUnRegister(int unRegisterId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM NewsletterUnRegister WHERE unRegisterId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, unRegisterId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("NewsletterUnRegister deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public int getNewsletterUnRegisterCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int newsletterCount = 0;

        try {
            String queryString = "SELECT COUNT(unRegisterId) as newsletterUnRegisterCount FROM NewsletterUnRegisterView WHERE 1=1 ";
            connection = ConnectionFactory.getInstance().getConnection();


            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if (!filterProperty.equals("globalFilter")) {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue + "%'";
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                newsletterCount = resultSet.getInt("newsletterUnRegisterCount");
            }

        } catch (SQLException e) {
            Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterUnRegisterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsletterCount;
    }

}
