/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.SubDivision;
import dininghall.domain.info.SubDivisionVW;

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
public class SubDivisionDAOImpl implements SubDivisionDAO, Serializable {

    @Override
    public List<SubDivision> getSubDivisionList() {
        List<SubDivision> subDivisionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivision  ORDER BY englishName ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                SubDivision subDivision = new SubDivision();
                subDivision.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivision.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivision.setEnglishName(resultSet.getString("englishName"));
                subDivision.setNativeName(resultSet.getString("nativeName"));

                subDivisionList.add(subDivision);
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivisionList;

    }

    @Override
    public List<SubDivision> getSubDivisionListByCountryId(int countryId) {
        List<SubDivision> subDivisionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivision WHERE countryId=?  ORDER BY englishName ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, countryId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                SubDivision subDivision = new SubDivision();
                subDivision.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivision.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivision.setEnglishName(resultSet.getString("englishName"));
                subDivision.setNativeName(resultSet.getString("nativeName"));


                subDivisionList.add(subDivision);
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivisionList;

    }

    @Override
    public List<SubDivisionVW> getSubDivisionVWList() {
        List<SubDivisionVW> subDivisionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivisionView  ORDER BY englishName ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                SubDivisionVW subDivisionVW = new SubDivisionVW();
                subDivisionVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivisionVW.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivisionVW.setEnglishName(resultSet.getString("englishName"));
                subDivisionVW.setNativeName(resultSet.getString("nativeName"));
                subDivisionVW.setCountryId(resultSet.getInt("countryId"));
                subDivisionVW.setCountry(resultSet.getString("countryEnglishName"));
                subDivisionVW.setContinentId(resultSet.getInt("continentId"));
                subDivisionVW.setContinent(resultSet.getString("continentEnglishName"));

                subDivisionList.add(subDivisionVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivisionList;

    }

    @Override
    public SubDivision getSubDivisionBySubDivisionId(int subDivisionId) {
        SubDivision subDivision = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivision WHERE subDivisionId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, subDivisionId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivision = new SubDivision();
                subDivision.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivision.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivision.setEnglishName(resultSet.getString("englishName"));
                subDivision.setNativeName(resultSet.getString("nativeName"));

                return subDivision;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivision;
    }

    @Override
    public SubDivisionVW getSubDivisionVWBySubDivisionId(int subDivisionId) {
        SubDivisionVW subDivisionVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivisionView WHERE subDivisionId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, subDivisionId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivisionVW = new SubDivisionVW();
                subDivisionVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivisionVW.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivisionVW.setEnglishName(resultSet.getString("englishName"));
                subDivisionVW.setNativeName(resultSet.getString("nativeName"));
                subDivisionVW.setCountryId(resultSet.getInt("countryId"));
                subDivisionVW.setCountry(resultSet.getString("countryEnglishName"));
                subDivisionVW.setContinentId(resultSet.getInt("continentId"));
                subDivisionVW.setContinent(resultSet.getString("continentEnglishName"));

                return subDivisionVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivisionVW;
    }

    @Override
    public SubDivision getSubDivisionBySubDivisionCode(String subDivisionCode) {
        SubDivision subDivision = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivision WHERE subDivisionCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, subDivisionCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivision = new SubDivision();
                subDivision.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivision.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivision.setEnglishName(resultSet.getString("englishName"));
                subDivision.setNativeName(resultSet.getString("nativeName"));

                return subDivision;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivision;
    }

    @Override
    public SubDivisionVW getSubDivisionVWBySubDivisionCode(String subDivisionCode) {
        SubDivisionVW subDivisionVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivisionView WHERE subDivisionCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, subDivisionCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivisionVW = new SubDivisionVW();
                subDivisionVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivisionVW.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivisionVW.setEnglishName(resultSet.getString("englishName"));
                subDivisionVW.setNativeName(resultSet.getString("nativeName"));
                subDivisionVW.setCountryId(resultSet.getInt("countryId"));
                subDivisionVW.setCountry(resultSet.getString("countryEnglishName"));
                subDivisionVW.setContinentId(resultSet.getInt("continentId"));
                subDivisionVW.setContinent(resultSet.getString("continentEnglishName"));

                return subDivisionVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivisionVW;
    }

    @Override
    public SubDivision getSubDivisionByEnglishName(String englishName) {
        SubDivision subDivision = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivision WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivision = new SubDivision();
                subDivision.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivision.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivision.setEnglishName(resultSet.getString("englishName"));
                subDivision.setNativeName(resultSet.getString("nativeName"));

                return subDivision;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivision;
    }

    @Override
    public SubDivisionVW getSubDivisionVWByEnglishName(String englishName) {
        SubDivisionVW subDivisionVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivisionView WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivisionVW = new SubDivisionVW();
                subDivisionVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivisionVW.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivisionVW.setEnglishName(resultSet.getString("englishName"));
                subDivisionVW.setNativeName(resultSet.getString("nativeName"));
                subDivisionVW.setCountryId(resultSet.getInt("countryId"));
                subDivisionVW.setCountry(resultSet.getString("countryEnglishName"));
                subDivisionVW.setContinentId(resultSet.getInt("continentId"));
                subDivisionVW.setContinent(resultSet.getString("continentEnglishName"));


                return subDivisionVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivisionVW;
    }

    @Override
    public SubDivision getSubDivisionByNativeName(String nativeName) {
        SubDivision subDivision = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivision WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivision = new SubDivision();
                subDivision.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivision.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivision.setEnglishName(resultSet.getString("englishName"));
                subDivision.setNativeName(resultSet.getString("nativeName"));

                return subDivision;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivision;
    }

    @Override
    public SubDivisionVW getSubDivisionVWByNativeName(String nativeName) {
        SubDivisionVW subDivisionVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM SubDivisionView WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                subDivisionVW = new SubDivisionVW();
                subDivisionVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                subDivisionVW.setSubDivisionCode(resultSet.getString("subDivisionCode"));
                subDivisionVW.setEnglishName(resultSet.getString("englishName"));
                subDivisionVW.setNativeName(resultSet.getString("nativeName"));
                subDivisionVW.setCountryId(resultSet.getInt("countryId"));
                subDivisionVW.setCountry(resultSet.getString("countryEnglishName"));
                subDivisionVW.setContinentId(resultSet.getInt("continentId"));
                subDivisionVW.setContinent(resultSet.getString("continentEnglishName"));

                return subDivisionVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return subDivisionVW;
    }

    @Override
    public boolean addSubDivision(SubDivision subDivision) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO SubDivision(subDivisionCode, englishName, nativeName, countryId) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, subDivision.getSubDivisionCode());
            pstmt.setString(2, subDivision.getEnglishName());
            pstmt.setString(3, subDivision.getNativeName());
            pstmt.setInt(4, subDivision.getCountryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("SubDivision Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateSubDivision(SubDivision subDivision) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE SubDivision SET subDivisionCode=?, englishName=?, nativeName=?, countryId=? WHERE subDivisionId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, subDivision.getSubDivisionCode());
            pstmt.setString(2, subDivision.getEnglishName());
            pstmt.setString(3, subDivision.getNativeName());
            pstmt.setInt(4, subDivision.getCountryId());
            pstmt.setInt(5, subDivision.getSubDivisionId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("SubDivision Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateSubDivision(SubDivisionVW subDivisionVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE SubDivision SET subDivisionCode=?, englishName=?, nativeName=?, countryId=? WHERE subDivisionId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, subDivisionVW.getSubDivisionCode());
            pstmt.setString(2, subDivisionVW.getEnglishName());
            pstmt.setString(3, subDivisionVW.getNativeName());
            pstmt.setInt(4, subDivisionVW.getCountryId());
            pstmt.setInt(5, subDivisionVW.getSubDivisionId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("SubDivision Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteSubDivision(int subDivisionId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM SubDivision WHERE subDivisionId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, subDivisionId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("SubDivision deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(SubDivisionDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
