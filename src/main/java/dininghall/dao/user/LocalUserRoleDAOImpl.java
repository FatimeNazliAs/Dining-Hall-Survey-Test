/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.user;

import dininghall.common.ConnectionFactory;
import dininghall.domain.user.LocalUserRole;
import dininghall.domain.user.RoleVW;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class LocalUserRoleDAOImpl implements LocalUserRoleDAO, Serializable {

    @Override
    public RoleVW getRoleViewByUserId(long local_user_id) {
        RoleVW roleVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM role_view WHERE local_user_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                roleVW = new RoleVW();
                roleVW.setRoleId(resultSet.getInt("local_role_id"));
                roleVW.setRole(resultSet.getString("local_role"));

                return roleVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return roleVW;
    }

    @Override
    public LocalUserRole getLocalUserRoleByUserId(long userId) {
        LocalUserRole localUserRole = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user_role WHERE local_user_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, userId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                localUserRole = new LocalUserRole();
                localUserRole.setLocalUserId(resultSet.getLong("local_user_id"));
                localUserRole.setLocalRoleId(resultSet.getLong("local_role_id"));

                return localUserRole;
            }

        } catch (SQLException e) {
            Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return localUserRole;
    }

    @Override
    public boolean addLocalUserRole(LocalUserRole localUserRole) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO local_user_role(local_user_id, local_role_id) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, localUserRole.getLocalUserId());
            pstmt.setLong(2, localUserRole.getLocalRoleId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("User Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateLocalUserRole(LocalUserRole localUserRole) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE local_user_role SET local_user_id=?, local_role_id=? WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setLong(1, localUserRole.getLocalUserId());
            pstmt.setLong(2, localUserRole.getLocalRoleId());
            pstmt.setLong(3, localUserRole.getLocalUserId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("UserRole Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean deleteLocalUserRole(long local_user_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM local_user_role WHERE local_user_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("UserRole deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserRoleDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
