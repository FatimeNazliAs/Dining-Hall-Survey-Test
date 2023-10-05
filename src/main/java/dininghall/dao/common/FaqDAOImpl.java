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
import dininghall.domain.common.Faq;
import dininghall.domain.common.FaqVW;

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
public class FaqDAOImpl implements FaqDAO, Serializable {

    @Override
    public List<Faq> getFaqList() {
        List<Faq> faqList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM faq";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Faq faq = new Faq();
                faq.setFaqId(resultSet.getInt("faq_id"));
                faq.setQuestion(resultSet.getString("question"));
                faq.setAnswer(resultSet.getString("answer"));
                faq.setCreatedAt(resultSet.getDate("created_at"));
                faq.setUpdatedAt(resultSet.getDate("updated_at"));

                faqList.add(faq);
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

        return faqList;

    }

    @Override
    public List<FaqVW> getFaqVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<FaqVW> faqVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY question ASC ) as row " +
                    "FROM faq_view WHERE 1=1 ";


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
                FaqVW faqVW = new FaqVW();
                faqVW.setFaqId(resultSet.getInt("faq_id"));
                faqVW.setQuestion(resultSet.getString("question"));
                faqVW.setAnswer(resultSet.getString("answer"));
                faqVW.setCreatedAt(resultSet.getDate("created_at"));
                faqVW.setCreatedAtStr(resultSet.getString("created_atstr"));
                faqVW.setUpdatedAt(resultSet.getDate("updated_at"));
                faqVW.setUpdatedAtStr(resultSet.getString("updated_atstr"));

                faqVWList.add(faqVW);
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

        return faqVWList;
    }

    @Override
    public Faq getFaqByFaqId(int faq_id) {
        Faq faq = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM faq WHERE faq_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, faq_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                faq = new Faq();
                faq.setFaqId(resultSet.getInt("faq_id"));
                faq.setQuestion(resultSet.getString("question"));
                faq.setAnswer(resultSet.getString("answer"));
                faq.setCreatedAt(resultSet.getDate("created_at"));
                faq.setUpdatedAt(resultSet.getDate("updated_at"));

                return faq;
            }

        } catch (SQLException e) {
            Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return faq;
    }

    @Override
    public Faq getFaqByQuestion(String question) {
        Faq faq = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM faq WHERE question=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, question);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                faq = new Faq();
                faq.setFaqId(resultSet.getInt("faq_id"));
                faq.setQuestion(resultSet.getString("question"));
                faq.setAnswer(resultSet.getString("answer"));
                faq.setCreatedAt(resultSet.getDate("created_at"));
                faq.setUpdatedAt(resultSet.getDate("updated_at"));

                return faq;
            }

        } catch (SQLException e) {
            Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return faq;
    }

    @Override
    public boolean addFaq(Faq faq) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO faq (question, answer, created_at, updated_at) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, faq.getQuestion());
            pstmt.setString(2, faq.getAnswer());
            pstmt.setTimestamp(3, new java.sql.Timestamp((new Date()).getTime()));
            if (faq.getUpdatedAt() != null) {
                pstmt.setTimestamp(4, new java.sql.Timestamp((new Date()).getTime()));
            } else {
                pstmt.setNull(4, java.sql.Types.NULL);
            }

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Faq Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateFaq(Faq faq) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE faq SET question=?, answer=?, updated_at=? WHERE faq_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, faq.getQuestion());
            pstmt.setString(2, faq.getAnswer());
            pstmt.setTimestamp(3, new java.sql.Timestamp(faq.getUpdatedAt().getTime()));
            pstmt.setInt(4, faq.getFaqId());


            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Faq Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateFaq(FaqVW faqVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE faq SET question=?, answer=?, updated_at=? WHERE faq_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, faqVW.getQuestion());
            pstmt.setString(2, faqVW.getAnswer());
            pstmt.setTimestamp(3, new java.sql.Timestamp(faqVW.getUpdatedAt().getTime()));
            pstmt.setInt(4, faqVW.getFaqId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Faq Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteFaq(int faq_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM faq WHERE faq_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, faq_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Faq deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public int getFaqCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int faqCount = 0;

        try {
            String queryString = "SELECT COUNT(faq_id) as faqCount FROM faq_view WHERE 1=1 ";
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
                faqCount = resultSet.getInt("faqCount");
            }

        } catch (SQLException e) {
            Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(FaqDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return faqCount;
    }

}
