/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskScore;

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
public class HDeskScoreDAOImpl implements HDeskScoreDAO, Serializable {

    @Override
    public List<HDeskScore> getHDeskScoreList() {
        List<HDeskScore> hDeskScoreList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskScore";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                HDeskScore hDeskScore = new HDeskScore();
                hDeskScore.setScoreId(resultSet.getInt("scoreId"));
                hDeskScore.setScore(resultSet.getString("score"));
                hDeskScore.setDescription(resultSet.getString("description"));

                hDeskScoreList.add(hDeskScore);
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

        return hDeskScoreList;

    }

    @Override
    public HDeskScore getHDeskScoreByHDeskScoreId(int scoreId) {
        HDeskScore hDeskScore = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskScore WHERE scoreId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, scoreId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskScore = new HDeskScore();
                hDeskScore.setScoreId(resultSet.getInt("scoreId"));
                hDeskScore.setScore(resultSet.getString("score"));
                hDeskScore.setDescription(resultSet.getString("description"));

                return hDeskScore;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskScore;
    }

    @Override
    public HDeskScore getHDeskScoreByName(String name) {
        HDeskScore hDeskScore = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskScore WHERE name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskScore = new HDeskScore();
                hDeskScore.setScoreId(resultSet.getInt("scoreId"));
                hDeskScore.setScore(resultSet.getString("score"));
                hDeskScore.setDescription(resultSet.getString("description"));

                return hDeskScore;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskScore;
    }

    @Override
    public boolean addHDeskScore(HDeskScore hDeskScore) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO HDeskScore(score, description) VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskScore.getScore());
            pstmt.setString(2, hDeskScore.getDescription());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskScore Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateHDeskScore(HDeskScore hDeskScore) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskScore SET score=?, description=? WHERE scoreId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskScore.getScore());
            pstmt.setString(2, hDeskScore.getDescription());
            pstmt.setInt(3, hDeskScore.getScoreId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskScore Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean deleteHDeskScore(int scoreId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskScore WHERE scoreId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, scoreId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskScore deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskScoreDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
