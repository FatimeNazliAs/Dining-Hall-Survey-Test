/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.Street;
import dininghall.domain.info.StreetVW;

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
public class StreetDAOImpl implements StreetDAO, Serializable {

    private StateDAO stateDAO;

    public StreetDAOImpl() {
        stateDAO = new StateDAOImpl();
    }

    @Override
    public List<Street> getStreetList() {
        List<Street> streetList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street  ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Street street = new Street();
                street.setStreetId(resultSet.getInt("street_id"));
                street.setStreetCode(resultSet.getString("street_code"));
                street.setEnglishName(resultSet.getString("english_name"));
                street.setNativeName(resultSet.getString("native_name"));
                street.setStateId(resultSet.getInt("state_id"));

                streetList.add(street);
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return streetList;

    }

    @Override
    public List<StreetVW> getStreetVWList() {
        List<StreetVW> streetList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street_view  ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                StreetVW streetVW = new StreetVW();
                streetVW.setStreetId(resultSet.getInt("street_id"));
                streetVW.setStreetCode(resultSet.getString("street_code"));
                streetVW.setEnglishName(resultSet.getString("english_name"));
                streetVW.setNativeName(resultSet.getString("native_name"));
                streetVW.setStateId(resultSet.getInt("state_id"));
                streetVW.setStateEnglishName(resultSet.getString("state_english_name"));
                streetVW.setCityId(resultSet.getInt("city_id"));
                streetVW.setCityEnglishName(resultSet.getString("city_english_name"));
                streetVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                streetVW.setSubDivisionEnglishName(resultSet.getString("sub_division_english_name"));
                streetVW.setCountryId(resultSet.getInt("country_id"));
                streetVW.setCountryEnglishName(resultSet.getString("country_english_name"));
                streetVW.setContinentId(resultSet.getInt("continent_id"));
                streetVW.setContinentEnglishName(resultSet.getString("continent_english_name"));

                streetList.add(streetVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return streetList;

    }

    @Override
    public List<Street> getStreetListByStateId(int stateId) {
        List<Street> streetList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street_view WHERE state_id=?  ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, stateId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Street street = new Street();
                street.setStreetId(resultSet.getInt("street_id"));
                street.setStreetCode(resultSet.getString("street_code"));
                street.setEnglishName(resultSet.getString("english_name"));
                street.setNativeName(resultSet.getString("native_name"));
                street.setStateId(resultSet.getInt("state_id"));

                streetList.add(street);
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return streetList;

    }

    @Override
    public Street getStreetByStreetId(int streetId) {
        Street street = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street WHERE street_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, streetId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                street = new Street();
                street.setStreetId(resultSet.getInt("street_id"));
                street.setStreetId(resultSet.getInt("street_id"));
                street.setStreetCode(resultSet.getString("street_code"));
                street.setEnglishName(resultSet.getString("english_name"));
                street.setNativeName(resultSet.getString("native_name"));

                return street;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return street;
    }

    @Override
    public StreetVW getStreetVWByStreetId(int streetId) {
        StreetVW streetVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street_view WHERE street_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, streetId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                streetVW = new StreetVW();
                streetVW.setStreetId(resultSet.getInt("street_id"));
                streetVW.setStreetCode(resultSet.getString("street_code"));
                streetVW.setEnglishName(resultSet.getString("english_name"));
                streetVW.setNativeName(resultSet.getString("native_name"));
                streetVW.setStateId(resultSet.getInt("state_id"));
                streetVW.setStateEnglishName(resultSet.getString("state_english_name"));
                streetVW.setCityId(resultSet.getInt("city_id"));
                streetVW.setCityEnglishName(resultSet.getString("city_english_name"));
                streetVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                streetVW.setSubDivisionEnglishName(resultSet.getString("sub_division_english_name"));
                streetVW.setCountryId(resultSet.getInt("country_id"));
                streetVW.setCountryEnglishName(resultSet.getString("country_english_name"));
                streetVW.setContinentId(resultSet.getInt("continent_id"));
                streetVW.setContinentEnglishName(resultSet.getString("continent_english_name"));

                return streetVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return streetVW;
    }

    @Override
    public Street getStreetByStreetCode(String streetCode) {
        Street street = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street WHERE street_code=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, streetCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                street = new Street();
                street.setStreetId(resultSet.getInt("street_id"));
                street.setStreetCode(resultSet.getString("street_code"));
                street.setEnglishName(resultSet.getString("english_name"));
                street.setNativeName(resultSet.getString("native_name"));

                return street;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return street;
    }

    @Override
    public StreetVW getStreetVWByStreetCode(String streetCode) {
        StreetVW streetVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street_view WHERE street_code=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, streetCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                streetVW = new StreetVW();
                streetVW.setStreetId(resultSet.getInt("street_id"));
                streetVW.setStreetCode(resultSet.getString("street_code"));
                streetVW.setEnglishName(resultSet.getString("english_name"));
                streetVW.setNativeName(resultSet.getString("native_name"));
                streetVW.setStateId(resultSet.getInt("state_id"));
                streetVW.setStateEnglishName(resultSet.getString("state_english_name"));
                streetVW.setCityId(resultSet.getInt("city_id"));
                streetVW.setCityEnglishName(resultSet.getString("city_english_name"));
                streetVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                streetVW.setSubDivisionEnglishName(resultSet.getString("sub_division_english_name"));
                streetVW.setCountryId(resultSet.getInt("country_id"));
                streetVW.setCountryEnglishName(resultSet.getString("country_english_name"));
                streetVW.setContinentId(resultSet.getInt("continent_id"));
                streetVW.setContinentEnglishName(resultSet.getString("continent_english_name"));

                return streetVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return streetVW;
    }

    @Override
    public Street getStreetByEnglishName(String englishName) {
        Street street = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street WHERE english_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                street = new Street();
                street.setStreetId(resultSet.getInt("street_id"));
                street.setStreetCode(resultSet.getString("street_code"));
                street.setEnglishName(resultSet.getString("english_name"));
                street.setNativeName(resultSet.getString("native_name"));

                return street;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return street;
    }

    @Override
    public StreetVW getStreetVWByEnglishName(String englishName) {
        StreetVW streetVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street_view WHERE english_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                streetVW = new StreetVW();
                streetVW.setStreetId(resultSet.getInt("street_id"));
                streetVW.setStreetCode(resultSet.getString("street_code"));
                streetVW.setEnglishName(resultSet.getString("english_name"));
                streetVW.setNativeName(resultSet.getString("native_name"));
                streetVW.setStateId(resultSet.getInt("state_id"));
                streetVW.setStateEnglishName(resultSet.getString("state_english_name"));
                streetVW.setCityId(resultSet.getInt("city_id"));
                streetVW.setCityEnglishName(resultSet.getString("city_english_name"));
                streetVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                streetVW.setSubDivisionEnglishName(resultSet.getString("sub_division_english_name"));
                streetVW.setCountryId(resultSet.getInt("country_id"));
                streetVW.setCountryEnglishName(resultSet.getString("country_english_name"));
                streetVW.setContinentId(resultSet.getInt("continent_id"));
                streetVW.setContinentEnglishName(resultSet.getString("continent_english_name"));


                return streetVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return streetVW;
    }

    @Override
    public Street getStreetByNativeName(String nativeName) {
        Street street = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street WHERE native_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                street = new Street();
                street.setStreetId(resultSet.getInt("street_id"));
                street.setStreetCode(resultSet.getString("street_code"));
                street.setEnglishName(resultSet.getString("english_name"));
                street.setNativeName(resultSet.getString("native_name"));

                return street;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return street;
    }

    @Override
    public StreetVW getStreetVWByNativeName(String nativeName) {
        StreetVW streetVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM street_view WHERE native_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                streetVW = new StreetVW();
                streetVW.setStreetId(resultSet.getInt("street_id"));
                streetVW.setStreetCode(resultSet.getString("street_code"));
                streetVW.setEnglishName(resultSet.getString("english_name"));
                streetVW.setNativeName(resultSet.getString("native_name"));
                streetVW.setStateId(resultSet.getInt("state_id"));
                streetVW.setStateEnglishName(resultSet.getString("state_english_name"));
                streetVW.setCityId(resultSet.getInt("city_id"));
                streetVW.setCityEnglishName(resultSet.getString("city_english_name"));
                streetVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                streetVW.setSubDivisionEnglishName(resultSet.getString("sub_division_english_name"));
                streetVW.setCountryId(resultSet.getInt("country_id"));
                streetVW.setCountryEnglishName(resultSet.getString("country_english_name"));
                streetVW.setContinentId(resultSet.getInt("continent_id"));
                streetVW.setContinentEnglishName(resultSet.getString("continent_english_name"));

                return streetVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return streetVW;
    }

    @Override
    public boolean addStreet(Street street) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO Street(street_code, english_name, native_name, state_id) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, street.getStreetCode());
            pstmt.setString(2, street.getEnglishName());
            pstmt.setString(3, street.getNativeName());
            pstmt.setInt(4, street.getStateId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Street Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateStreet(Street street) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Street SET street_code=?, english_name=?, native_name=?, state_id=? WHERE street_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, street.getStreetCode());
            pstmt.setString(2, street.getEnglishName());
            pstmt.setString(3, street.getNativeName());
            pstmt.setInt(4, street.getStateId());
            pstmt.setInt(5, street.getStreetId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Street Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateStreet(StreetVW streetVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE street SET street_code=?, english_name=?, native_name=?, state_id=? WHERE street_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, streetVW.getStreetCode());
            pstmt.setString(2, streetVW.getEnglishName());
            pstmt.setString(3, streetVW.getNativeName());
            pstmt.setInt(4, streetVW.getStateId());
            pstmt.setInt(5, streetVW.getStreetId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Street Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteStreet(int streetId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM street WHERE street_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, streetId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Street deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StreetDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
