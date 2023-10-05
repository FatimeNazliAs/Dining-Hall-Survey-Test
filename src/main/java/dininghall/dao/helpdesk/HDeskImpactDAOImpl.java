/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskImpact;

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
public class HDeskImpactDAOImpl implements HDeskImpactDAO, Serializable {

    @Override
    public List<HDeskImpact> getHDeskImpactList() {
        List<HDeskImpact> hDeskImpactList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskImpact";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                HDeskImpact hDeskImpact = new HDeskImpact();
                hDeskImpact.setImpactId(resultSet.getInt("impactId"));
                hDeskImpact.setImpact(resultSet.getString("impact"));
                hDeskImpact.setDescription(resultSet.getString("description"));

                hDeskImpactList.add(hDeskImpact);
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

        return hDeskImpactList;

    }

    @Override
    public HDeskImpact getHDeskImpactByHDeskImpactId(int impactId) {
        HDeskImpact hDeskImpact = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskImpact WHERE impactId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, impactId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskImpact = new HDeskImpact();
                hDeskImpact.setImpactId(resultSet.getInt("impactId"));
                hDeskImpact.setImpact(resultSet.getString("impact"));
                hDeskImpact.setDescription(resultSet.getString("description"));

                return hDeskImpact;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskImpact;
    }

    @Override
    public HDeskImpact getHDeskImpactByName(String name) {
        HDeskImpact hDeskImpact = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskImpact WHERE name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskImpact = new HDeskImpact();
                hDeskImpact.setImpactId(resultSet.getInt("impactId"));
                hDeskImpact.setImpact(resultSet.getString("impact"));
                hDeskImpact.setDescription(resultSet.getString("description"));

                return hDeskImpact;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskImpact;
    }

    @Override
    public boolean addHDeskImpact(HDeskImpact hDeskImpact) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO HDeskImpact(impact, description) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskImpact.getImpact());
            pstmt.setString(2, hDeskImpact.getDescription());


            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskImpact Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateHDeskImpact(HDeskImpact hDeskImpact) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskImpact SET impact=?, description=? WHERE impactId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskImpact.getImpact());
            pstmt.setString(2, hDeskImpact.getDescription());
            pstmt.setInt(3, hDeskImpact.getImpactId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskImpact Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean deleteHDeskImpact(int impactId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskImpact WHERE impactId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, impactId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskImpact deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskImpactDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
