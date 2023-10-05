/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.City;
import dininghall.domain.info.CityVW;

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
public class CityDAOImpl implements CityDAO, Serializable {
    private CountryDAO countryDAO;
    private SubDivisionDAO subDivisionDAO;

    public CityDAOImpl() {
        countryDAO = new CountryDAOImpl();
        subDivisionDAO = new SubDivisionDAOImpl();
    }

    @Override
    public List<City> getCityList() {
        List<City> cityList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM city  ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                City city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityCode(resultSet.getString("city_code"));
                city.setEnglishName(resultSet.getString("english_name"));
                city.setNativeName(resultSet.getString("native_name"));

                cityList.add(city);
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityList;

    }

    @Override
    public List<City> getCityListBySubDivisionId(int subDivisionId) {
        List<City> cityList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM city WHERE sub_division_id=?  ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, subDivisionId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                City city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityCode(resultSet.getString("city_code"));
                city.setEnglishName(resultSet.getString("english_name"));
                city.setNativeName(resultSet.getString("native_name"));

                cityList.add(city);
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityList;

    }

    @Override
    public List<City> getCityListByCountryId(int countryId) {
        List<City> cityList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM city_view WHERE country_id=?  ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, countryId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                City city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityCode(resultSet.getString("city_code"));
                city.setEnglishName(resultSet.getString("english_name"));
                city.setNativeName(resultSet.getString("native_name"));

                cityList.add(city);
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityList;

    }

    @Override
    public List<CityVW> getCityVWList() {
        List<CityVW> cityList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CityView  ORDER BY englishName ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                CityVW cityVW = new CityVW();
                cityVW.setCityId(resultSet.getInt("city_id"));
                cityVW.setCityCode(resultSet.getString("city_code"));
                cityVW.setEnglishName(resultSet.getString("english_name"));
                cityVW.setNativeName(resultSet.getString("native_name"));
                cityVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                cityVW.setSubDivision(resultSet.getString("subDivisionEnglishName"));
                cityVW.setCountryId(resultSet.getInt("countryId"));
                cityVW.setCountry(resultSet.getString("countryEnglishName"));
                cityVW.setContinentId(resultSet.getInt("continentId"));
                cityVW.setContinent(resultSet.getString("continentEnglishName"));

                cityList.add(cityVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityList;

    }

    @Override
    public City getCityByCityId(int cityId) {
        City city = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM city WHERE cityId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, cityId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityCode(resultSet.getString("city_code"));
                city.setEnglishName(resultSet.getString("english_name"));
                city.setNativeName(resultSet.getString("native_name"));

                return city;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return city;
    }

    @Override
    public CityVW getCityVWByCityId(int cityId) {
        CityVW cityVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CityView WHERE cityId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, cityId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                cityVW = new CityVW();
                cityVW.setCityId(resultSet.getInt("city_id"));
                cityVW.setCityCode(resultSet.getString("city_code"));
                cityVW.setEnglishName(resultSet.getString("english_name"));
                cityVW.setNativeName(resultSet.getString("native_name"));
                cityVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                cityVW.setSubDivision(resultSet.getString("subDivisionEnglishName"));
                cityVW.setCountryId(resultSet.getInt("countryId"));
                cityVW.setCountry(resultSet.getString("countryEnglishName"));
                cityVW.setContinentId(resultSet.getInt("continentId"));
                cityVW.setContinent(resultSet.getString("continentEnglishName"));

                return cityVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityVW;
    }

    @Override
    public City getCityByCityCode(String cityCode) {
        City city = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM city WHERE cityCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, cityCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityCode(resultSet.getString("city_code"));
                city.setEnglishName(resultSet.getString("english_name"));
                city.setNativeName(resultSet.getString("native_name"));

                return city;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return city;
    }

    @Override
    public CityVW getCityVWByCityCode(String cityCode) {
        CityVW cityVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CityView WHERE cityCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, cityCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                cityVW = new CityVW();
                cityVW.setCityId(resultSet.getInt("city_id"));
                cityVW.setCityCode(resultSet.getString("city_code"));
                cityVW.setEnglishName(resultSet.getString("english_name"));
                cityVW.setNativeName(resultSet.getString("native_name"));
                cityVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                cityVW.setSubDivision(resultSet.getString("subDivisionEnglishName"));
                cityVW.setCountryId(resultSet.getInt("countryId"));
                cityVW.setCountry(resultSet.getString("countryEnglishName"));
                cityVW.setContinentId(resultSet.getInt("continentId"));
                cityVW.setContinent(resultSet.getString("continentEnglishName"));

                return cityVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityVW;
    }

    @Override
    public City getCityByEnglishName(String englishName) {
        City city = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM city WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityCode(resultSet.getString("city_code"));
                city.setEnglishName(resultSet.getString("english_name"));
                city.setNativeName(resultSet.getString("native_name"));

                return city;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return city;
    }

    @Override
    public CityVW getCityVWByEnglishName(String englishName) {
        CityVW cityVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CityView WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                cityVW = new CityVW();
                cityVW.setCityId(resultSet.getInt("city_id"));
                cityVW.setCityCode(resultSet.getString("city_code"));
                cityVW.setEnglishName(resultSet.getString("english_name"));
                cityVW.setNativeName(resultSet.getString("native_name"));
                cityVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                cityVW.setSubDivision(resultSet.getString("subDivisionEnglishName"));
                cityVW.setCountryId(resultSet.getInt("countryId"));
                cityVW.setCountry(resultSet.getString("countryEnglishName"));
                cityVW.setContinentId(resultSet.getInt("continentId"));
                cityVW.setContinent(resultSet.getString("continentEnglishName"));

                return cityVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityVW;
    }

    @Override
    public City getCityByNativeName(String nativeName) {
        City city = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM city WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                city = new City();
                city.setCityId(resultSet.getInt("city_id"));
                city.setCityCode(resultSet.getString("city_code"));
                city.setEnglishName(resultSet.getString("english_name"));
                city.setNativeName(resultSet.getString("native_name"));

                return city;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return city;
    }

    @Override
    public CityVW getCityVWByNativeName(String nativeName) {
        CityVW cityVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CityView WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                cityVW = new CityVW();
                cityVW.setCityId(resultSet.getInt("city_id"));
                cityVW.setCityCode(resultSet.getString("city_code"));
                cityVW.setEnglishName(resultSet.getString("english_name"));
                cityVW.setNativeName(resultSet.getString("native_name"));
                cityVW.setSubDivisionId(resultSet.getInt("subDivisionId"));
                cityVW.setSubDivision(resultSet.getString("subDivisionEnglishName"));
                cityVW.setCountryId(resultSet.getInt("countryId"));
                cityVW.setCountry(resultSet.getString("countryEnglishName"));
                cityVW.setContinentId(resultSet.getInt("continentId"));
                cityVW.setContinent(resultSet.getString("continentEnglishName"));


                return cityVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return cityVW;
    }

    @Override
    public boolean addCity(City city) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO City(cityCode, englishName, nativeName, subDivisionId) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, city.getCityCode());
            pstmt.setString(2, city.getEnglishName());
            pstmt.setString(3, city.getNativeName());
            pstmt.setInt(4, city.getSubDivisionId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("City Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateCity(City city) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE City SET cityCode=?, englishName=?, nativeName=?, subDivisionId=? WHERE cityId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, city.getCityCode());
            pstmt.setString(2, city.getEnglishName());
            pstmt.setString(3, city.getNativeName());
            pstmt.setInt(4, city.getSubDivisionId());
            pstmt.setInt(5, city.getCityId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("City Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateCity(CityVW cityVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE City SET cityCode=?, englishName=?, nativeName=?, subDivisionId=? WHERE cityId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, cityVW.getCityCode());
            pstmt.setString(2, cityVW.getEnglishName());
            pstmt.setString(3, cityVW.getNativeName());
            pstmt.setInt(4, cityVW.getSubDivisionId());
            pstmt.setInt(5, cityVW.getCityId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("City Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteCity(int cityId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM city WHERE cityId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, cityId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("City deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CityDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
