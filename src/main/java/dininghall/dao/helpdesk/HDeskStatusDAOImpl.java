/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskStatus;

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
public class HDeskStatusDAOImpl implements HDeskStatusDAO, Serializable {

    @Override
    public List<HDeskStatus> getHDeskStatusList() {
        List<HDeskStatus> hDeskStatusList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskStatus";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                HDeskStatus hDeskStatus = new HDeskStatus();
                hDeskStatus.setStatusId(resultSet.getInt("statusId"));
                hDeskStatus.setStatus(resultSet.getString("status"));
                hDeskStatus.setDescription(resultSet.getString("description"));

                hDeskStatusList.add(hDeskStatus);
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

        return hDeskStatusList;

    }

    @Override
    public HDeskStatus getHDeskStatusByHDeskStatusId(int statusId) {
        HDeskStatus hDeskStatus = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskStatus WHERE statusId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, statusId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskStatus = new HDeskStatus();
                hDeskStatus.setStatusId(resultSet.getInt("statusId"));
                hDeskStatus.setStatus(resultSet.getString("status"));
                hDeskStatus.setDescription(resultSet.getString("description"));

                return hDeskStatus;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskStatus;
    }

    @Override
    public HDeskStatus getHDeskStatusByName(String name) {
        HDeskStatus hDeskStatus = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskStatus WHERE name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskStatus = new HDeskStatus();
                hDeskStatus.setStatusId(resultSet.getInt("statusId"));
                hDeskStatus.setStatus(resultSet.getString("status"));
                hDeskStatus.setDescription(resultSet.getString("description"));

                return hDeskStatus;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskStatus;
    }

    @Override
    public boolean addHDeskStatus(HDeskStatus hDeskStatus) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO HDeskStatus(status, description) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskStatus.getStatus());
            pstmt.setString(2, hDeskStatus.getDescription());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskStatus Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateHDeskStatus(HDeskStatus hDeskStatus) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskStatus SET status=?, description=? WHERE statusId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskStatus.getStatus());
            pstmt.setString(2, hDeskStatus.getDescription());
            pstmt.setInt(3, hDeskStatus.getStatusId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskStatus Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean deleteHDeskStatus(int statusId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskStatus WHERE statusId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, statusId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskStatus deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskStatusDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
