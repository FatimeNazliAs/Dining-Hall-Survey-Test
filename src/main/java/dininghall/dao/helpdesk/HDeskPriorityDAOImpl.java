/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskPriority;

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
public class HDeskPriorityDAOImpl implements HDeskPriorityDAO, Serializable {

    @Override
    public List<HDeskPriority> getHDeskPriorityList() {
        List<HDeskPriority> hDeskPriorityList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskPriority";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                HDeskPriority hDeskPriority = new HDeskPriority();
                hDeskPriority.setPriorityId(resultSet.getInt("priorityId"));
                hDeskPriority.setPriority(resultSet.getString("priority"));
                hDeskPriority.setDescription(resultSet.getString("description"));

                hDeskPriorityList.add(hDeskPriority);
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

        return hDeskPriorityList;

    }

    @Override
    public HDeskPriority getHDeskPriorityByHDeskPriorityId(int priorityId) {
        HDeskPriority hDeskPriority = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskPriority WHERE priorityId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, priorityId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskPriority = new HDeskPriority();
                hDeskPriority.setPriorityId(resultSet.getInt("priorityId"));
                hDeskPriority.setPriority(resultSet.getString("priority"));
                hDeskPriority.setDescription(resultSet.getString("description"));


                return hDeskPriority;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskPriority;
    }

    @Override
    public HDeskPriority getHDeskPriorityByName(String name) {
        HDeskPriority hDeskPriority = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskPriority WHERE priority=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskPriority = new HDeskPriority();
                hDeskPriority.setPriorityId(resultSet.getInt("priorityId"));
                hDeskPriority.setPriority(resultSet.getString("priority"));
                hDeskPriority.setDescription(resultSet.getString("description"));


                return hDeskPriority;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskPriority;
    }

    @Override
    public boolean addHDeskPriority(HDeskPriority hDeskPriority) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO HDeskPriority(priority, description) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskPriority.getPriority());
            pstmt.setString(2, hDeskPriority.getDescription());


            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskPriority Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateHDeskPriority(HDeskPriority hDeskPriority) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskPriority SET priority=?, description=? WHERE priorityId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskPriority.getPriority());
            pstmt.setString(2, hDeskPriority.getDescription());
            pstmt.setInt(3, hDeskPriority.getPriorityId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskPriority Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean deleteHDeskPriority(int priorityId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskPriority WHERE priorityId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, priorityId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskPriority deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskPriorityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
