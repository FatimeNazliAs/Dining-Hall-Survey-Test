/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.dao.user.LocalUserDAOImpl;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.common.ConnectionFactory;
import dininghall.domain.common.Newsletter;
import dininghall.domain.common.NewsletterVW;

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
public class NewsletterDAOImpl implements NewsletterDAO, Serializable {

    @Override
    public List<Newsletter> getNewsletterList() {
        List<Newsletter> newsletterList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM newsletter";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Newsletter newsletter = new Newsletter();
                newsletter.setNewsletterId(resultSet.getInt("newsletter_id"));
                newsletter.setEmail(resultSet.getString("email"));
                newsletter.setRegisteredDate(resultSet.getDate("registered_date"));

                newsletterList.add(newsletter);
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

        return newsletterList;

    }

    @Override
    public List<NewsletterVW> getNewsletterVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<NewsletterVW> newsletterVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY registered_date DESC, email ASC ) as row " +
                    "FROM newsletter_view WHERE 1=1 ";


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
                NewsletterVW newsletterVW = new NewsletterVW();
                newsletterVW.setNewsletterId(resultSet.getInt("newsletter_id"));
                newsletterVW.setEmail(resultSet.getString("email"));
                newsletterVW.setRegisteredDate(resultSet.getDate("registered_date"));
                newsletterVW.setRegisteredDateStr(resultSet.getString("registered_datestr"));

                newsletterVWList.add(newsletterVW);
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

        return newsletterVWList;
    }

    @Override
    public Newsletter getNewsletterByNewsletterId(int newsletter_id) {
        Newsletter newsletter = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM newsletter WHERE newsletter_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsletter_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                newsletter = new Newsletter();
                newsletter.setNewsletterId(resultSet.getInt("newsletter_id"));
                newsletter.setEmail(resultSet.getString("email"));
                newsletter.setRegisteredDate(resultSet.getDate("registered_date"));

                return newsletter;
            }

        } catch (SQLException e) {
            Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsletter;
    }

    @Override
    public Newsletter getNewsletterByEmail(String email) {
        Newsletter newsletter = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM newsletter WHERE email=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, email);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                newsletter = new Newsletter();
                newsletter.setNewsletterId(resultSet.getInt("newsletter_id"));
                newsletter.setEmail(resultSet.getString("email"));
                newsletter.setRegisteredDate(resultSet.getDate("registered_date"));

                return newsletter;
            }

        } catch (SQLException e) {
            Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsletter;
    }

    @Override
    public boolean addNewsletter(Newsletter newsletter) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO newsletter (email, registered_date) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, newsletter.getEmail());
            pstmt.setTimestamp(2, new java.sql.Timestamp((new Date()).getTime()));

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Newsletter Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateNewsletter(Newsletter newsletter) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE newsletter SET email=? WHERE newsletter_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, newsletter.getEmail());
            pstmt.setInt(2, newsletter.getNewsletterId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Newsletter Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateNewsletter(NewsletterVW newsletterVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE newsletter SET email=? WHERE newsletter_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, newsletterVW.getEmail());
            pstmt.setInt(2, newsletterVW.getNewsletterId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Newsletter Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteNewsletter(int newsletter_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM newsletter WHERE newsletter_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, newsletter_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Newsletter deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public int getNewsletterCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int newsletterCount = 0;

        try {
            String queryString = "SELECT COUNT(newsletter_id) as newsletterCount FROM newsletter_view WHERE 1=1 ";
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
                newsletterCount = resultSet.getInt("newsletterCount");
            }

        } catch (SQLException e) {
            Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(NewsletterDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return newsletterCount;
    }

}
