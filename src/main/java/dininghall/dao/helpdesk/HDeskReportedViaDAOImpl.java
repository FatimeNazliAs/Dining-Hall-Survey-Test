/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskReportedVia;

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
public class HDeskReportedViaDAOImpl implements HDeskReportedViaDAO, Serializable {

    @Override
    public List<HDeskReportedVia> getHDeskReportedViaList() {
        List<HDeskReportedVia> hDeskReportedViaList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskReportedVia";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                HDeskReportedVia hDeskReportedVia = new HDeskReportedVia();
                hDeskReportedVia.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskReportedVia.setReportedVia(resultSet.getString("reportedVia"));
                hDeskReportedVia.setDescription(resultSet.getString("description"));

                hDeskReportedViaList.add(hDeskReportedVia);
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

        return hDeskReportedViaList;

    }

    @Override
    public HDeskReportedVia getHDeskReportedViaByHDeskReportedViaId(int reportedViaId) {
        HDeskReportedVia hDeskReportedVia = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskReportedVia WHERE reportedViaId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, reportedViaId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskReportedVia = new HDeskReportedVia();
                hDeskReportedVia.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskReportedVia.setReportedVia(resultSet.getString("reportedVia"));
                hDeskReportedVia.setDescription(resultSet.getString("description"));

                return hDeskReportedVia;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskReportedVia;
    }

    @Override
    public HDeskReportedVia getHDeskReportedViaByName(String name) {
        HDeskReportedVia hDeskReportedVia = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskReportedVia WHERE reportedVia=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskReportedVia = new HDeskReportedVia();
                hDeskReportedVia.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskReportedVia.setReportedVia(resultSet.getString("reportedVia"));
                hDeskReportedVia.setDescription(resultSet.getString("description"));

                return hDeskReportedVia;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskReportedVia;
    }

    @Override
    public boolean addHDeskReportedVia(HDeskReportedVia hDeskReportedVia) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO HDeskReportedVia(reportedVia, description) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskReportedVia.getReportedVia());
            pstmt.setString(2, hDeskReportedVia.getDescription());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskReportedVia Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateHDeskReportedVia(HDeskReportedVia hDeskReportedVia) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskReportedVia SET reportedVia=?, description=? WHERE reportedViaId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskReportedVia.getReportedVia());
            pstmt.setString(2, hDeskReportedVia.getDescription());
            pstmt.setInt(3, hDeskReportedVia.getReportedViaId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskReportedVia Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean deleteHDeskReportedVia(int reportedViaId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskReportedVia WHERE reportedViaId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, reportedViaId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskReportedVia deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskReportedViaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
