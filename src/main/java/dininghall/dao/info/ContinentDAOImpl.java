/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.Continent;
import dininghall.domain.info.ContinentVW;

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
public class ContinentDAOImpl implements ContinentDAO, Serializable {

    @Override
    public List<Continent> getContinentList() {
        List<Continent> continentList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent ORDER BY englishName ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Continent continent = new Continent();
                continent.setContinentId(resultSet.getInt("continentId"));
                continent.setContinentCode(resultSet.getString("continentCode"));
                continent.setEnglishName(resultSet.getString("englishName"));
                continent.setNativeName(resultSet.getString("nativeName"));

                continentList.add(continent);
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continentList;

    }

    @Override
    public List<ContinentVW> getContinentVWList() {
        List<ContinentVW> continentList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent ORDER BY englishName ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ContinentVW continentVW = new ContinentVW();
                continentVW.setContinentId(resultSet.getInt("continentId"));
                continentVW.setContinentCode(resultSet.getString("continentCode"));
                continentVW.setEnglishName(resultSet.getString("englishName"));
                continentVW.setNativeName(resultSet.getString("nativeName"));
                ///////////////   continentVW.setContinent(null);

                continentList.add(continentVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continentList;

    }

    @Override
    public Continent getContinentByContinentId(int continentId) {
        Continent continent = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE continentId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, continentId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continent = new Continent();
                continent.setContinentId(resultSet.getInt("continentId"));
                continent.setContinentCode(resultSet.getString("continentCode"));
                continent.setEnglishName(resultSet.getString("englishName"));
                continent.setNativeName(resultSet.getString("nativeName"));

                return continent;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continent;
    }

    @Override
    public ContinentVW getContinentVWByContinentId(int continentId) {
        ContinentVW continentVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE continentId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, continentId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continentVW = new ContinentVW();
                continentVW.setContinentId(resultSet.getInt("continentId"));
                continentVW.setContinentCode(resultSet.getString("continentCode"));
                continentVW.setEnglishName(resultSet.getString("englishName"));
                continentVW.setNativeName(resultSet.getString("nativeName"));

                return continentVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continentVW;
    }

    @Override
    public Continent getContinentByContinentCode(String continentCode) {
        Continent continent = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE continentCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, continentCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continent = new Continent();
                continent.setContinentId(resultSet.getInt("continentId"));
                continent.setContinentCode(resultSet.getString("continentCode"));
                continent.setEnglishName(resultSet.getString("englishName"));
                continent.setNativeName(resultSet.getString("nativeName"));

                return continent;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continent;
    }

    @Override
    public ContinentVW getContinentVWByContinentCode(String continentCode) {
        ContinentVW continentVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE continentCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, continentCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continentVW = new ContinentVW();
                continentVW.setContinentId(resultSet.getInt("continentId"));
                continentVW.setContinentCode(resultSet.getString("continentCode"));
                continentVW.setEnglishName(resultSet.getString("englishName"));
                continentVW.setNativeName(resultSet.getString("nativeName"));

                return continentVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continentVW;
    }

    @Override
    public Continent getContinentByEnglishName(String englishName) {
        Continent continent = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continent = new Continent();
                continent.setContinentId(resultSet.getInt("continentId"));
                continent.setContinentCode(resultSet.getString("continentCode"));
                continent.setEnglishName(resultSet.getString("englishName"));
                continent.setNativeName(resultSet.getString("nativeName"));

                return continent;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continent;
    }

    @Override
    public ContinentVW getContinentVWByEnglishName(String englishName) {
        ContinentVW continentVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continentVW = new ContinentVW();
                continentVW.setContinentId(resultSet.getInt("continentId"));
                continentVW.setContinentCode(resultSet.getString("continentCode"));
                continentVW.setEnglishName(resultSet.getString("englishName"));
                continentVW.setNativeName(resultSet.getString("nativeName"));


                return continentVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continentVW;
    }

    @Override
    public Continent getContinentByNativeName(String nativeName) {
        Continent continent = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continent = new Continent();
                continent.setContinentId(resultSet.getInt("continentId"));
                continent.setContinentCode(resultSet.getString("continentCode"));
                continent.setEnglishName(resultSet.getString("englishName"));
                continent.setNativeName(resultSet.getString("nativeName"));

                return continent;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continent;
    }

    @Override
    public ContinentVW getContinentVWByNativeName(String nativeName) {
        ContinentVW continentVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Continent WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                continentVW = new ContinentVW();
                continentVW.setContinentId(resultSet.getInt("continentId"));
                continentVW.setContinentCode(resultSet.getString("continentCode"));
                continentVW.setEnglishName(resultSet.getString("englishName"));
                continentVW.setNativeName(resultSet.getString("nativeName"));

                return continentVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return continentVW;
    }

    @Override
    public boolean addContinent(Continent continent) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO Continent(continentCode, englishName, nativeName, continentId) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, continent.getContinentCode());
            pstmt.setString(2, continent.getEnglishName());
            pstmt.setString(3, continent.getNativeName());
            pstmt.setInt(4, continent.getContinentId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Continent Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateContinent(Continent continent) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Continent SET continentCode=?, englishName=?, nativeName=? WHERE continentId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, continent.getContinentCode());
            pstmt.setString(2, continent.getEnglishName());
            pstmt.setString(3, continent.getNativeName());
            pstmt.setInt(4, continent.getContinentId());


            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Continent Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateContinent(ContinentVW continentVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Continent SET continentCode=?, englishName=?, nativeName=? WHERE continentId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, continentVW.getContinentCode());
            pstmt.setString(2, continentVW.getEnglishName());
            pstmt.setString(3, continentVW.getNativeName());
            pstmt.setInt(4, continentVW.getContinentId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Continent Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteContinent(int continentId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM Continent WHERE continentId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, continentId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Continent deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContinentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
