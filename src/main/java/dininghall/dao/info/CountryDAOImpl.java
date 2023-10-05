/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.Country;
import dininghall.domain.info.CountryVW;

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
public class CountryDAOImpl implements CountryDAO, Serializable {

    @Override
    public List<Country> getCountryList() {
        List<Country> countryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country  ORDER BY english_name  ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt("country_id"));
                country.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                country.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                country.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                country.setEnglishName(resultSet.getString("english_name"));
                country.setNativeName(resultSet.getString("native_name"));

                countryList.add(country);
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryList;

    }

    @Override
    public List<Country> getCountryListByContinentId(int continentId) {
        List<Country> countryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country WHERE continent_id=? ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, continentId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt("country_id"));
                country.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                country.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                country.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                country.setEnglishName(resultSet.getString("english_name"));
                country.setNativeName(resultSet.getString("native_name"));


                countryList.add(country);
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryList;

    }

    @Override
    public List<Country> getCountryVWListByEnglishName(String english_name) {
        List<Country> countryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country WHERE english_name LIKE N'%" + english_name + "%' ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt("country_id"));
                country.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                country.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                country.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                country.setEnglishName(resultSet.getString("english_name"));
                country.setNativeName(resultSet.getString("native_name"));


                countryList.add(country);
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryList;

    }


    @Override
    public List<CountryVW> getCountryVWList() {
        List<CountryVW> countryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country_view  ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                CountryVW countryVW = new CountryVW();
                countryVW.setCountryId(resultSet.getInt("country_id"));
                countryVW.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                countryVW.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                countryVW.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                countryVW.setEnglishName(resultSet.getString("english_name"));
                countryVW.setNativeName(resultSet.getString("native_name"));
                countryVW.setContinentId(resultSet.getInt("continentId"));
                countryVW.setContinent(resultSet.getString("continent_english_name"));

                countryList.add(countryVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryList;

    }

    @Override
    public Country getCountryByCountryId(int countryId) {
        Country country = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country WHERE country_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, countryId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                country = new Country();
                country.setCountryId(resultSet.getInt("country_id"));
                country.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                country.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                country.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                country.setEnglishName(resultSet.getString("english_name"));
                country.setNativeName(resultSet.getString("native_name"));

                return country;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return country;
    }

    @Override
    public CountryVW getCountryVWByCountryId(int countryId) {
        CountryVW countryVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country_view WHERE country_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, countryId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                countryVW = new CountryVW();
                countryVW.setCountryId(resultSet.getInt("country_id"));
                countryVW.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                countryVW.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                countryVW.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                countryVW.setEnglishName(resultSet.getString("english_name"));
                countryVW.setNativeName(resultSet.getString("native_name"));
                countryVW.setContinentId(resultSet.getInt("continentId"));
                countryVW.setContinent(resultSet.getString("continent_english_name"));

                return countryVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryVW;
    }

    @Override
    public Country getCountryByCountryAlpha3Code(String country_alpha3code) {
        Country country = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country WHERE country_alpha3code=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, country_alpha3code);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                country = new Country();
                country.setCountryId(resultSet.getInt("country_id"));
                country.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                country.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                country.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                country.setEnglishName(resultSet.getString("english_name"));
                country.setNativeName(resultSet.getString("native_name"));

                return country;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return country;
    }

    @Override
    public CountryVW getCountryVWByCountryAlpha3Code(String country_alpha3code) {
        CountryVW countryVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country_view WHERE country_alpha3code=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, country_alpha3code);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                countryVW = new CountryVW();
                countryVW.setCountryId(resultSet.getInt("country_id"));
                countryVW.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                countryVW.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                countryVW.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                countryVW.setEnglishName(resultSet.getString("english_name"));
                countryVW.setNativeName(resultSet.getString("native_name"));
                countryVW.setContinentId(resultSet.getInt("continentId"));
                countryVW.setContinent(resultSet.getString("continent_english_name"));

                return countryVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryVW;
    }

    @Override
    public Country getCountryByEnglishName(String english_name) {
        Country country = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country WHERE english_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, english_name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                country = new Country();
                country.setCountryId(resultSet.getInt("country_id"));
                country.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                country.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                country.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                country.setEnglishName(resultSet.getString("english_name"));
                country.setNativeName(resultSet.getString("native_name"));

                return country;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return country;
    }

    @Override
    public CountryVW getCountryVWByEnglishName(String english_name) {
        CountryVW countryVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country_view WHERE english_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, english_name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                countryVW = new CountryVW();
                countryVW.setCountryId(resultSet.getInt("country_id"));
                countryVW.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                countryVW.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                countryVW.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                countryVW.setEnglishName(resultSet.getString("english_name"));
                countryVW.setNativeName(resultSet.getString("native_name"));
                countryVW.setContinentId(resultSet.getInt("continentId"));
                countryVW.setContinent(resultSet.getString("continent_english_name"));


                return countryVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryVW;
    }

    @Override
    public Country getCountryByNativeName(String nativeName) {
        Country country = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country WHERE native_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                country = new Country();
                country.setCountryId(resultSet.getInt("country_id"));
                country.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                country.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                country.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                country.setEnglishName(resultSet.getString("english_name"));
                country.setNativeName(resultSet.getString("native_name"));


                return country;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return country;
    }

    @Override
    public CountryVW getCountryVWByNativeName(String nativeName) {
        CountryVW countryVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM country_view WHERE native_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                countryVW = new CountryVW();
                countryVW.setCountryId(resultSet.getInt("country_id"));
                countryVW.setCountryAlpha2Code(resultSet.getString("country_alpha2code"));
                countryVW.setCountryAlpha3Code(resultSet.getString("country_alpha3code"));
                countryVW.setCountryNumericCode(resultSet.getString("country_numeric_code"));
                countryVW.setEnglishName(resultSet.getString("english_name"));
                countryVW.setNativeName(resultSet.getString("native_name"));
                countryVW.setContinentId(resultSet.getInt("continentId"));
                countryVW.setContinent(resultSet.getString("continent_english_name"));

                return countryVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return countryVW;
    }

    @Override
    public boolean addCountry(Country country) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO Country(country_alpha2code, country_alpha3code, country_numeric_code, english_name, native_name, continent_id) " +
                    "VALUES(?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, country.getCountryAlpha2Code());
            pstmt.setString(2, country.getCountryAlpha3Code());
            pstmt.setString(3, country.getCountryNumericCode());
            pstmt.setString(4, country.getEnglishName());
            pstmt.setString(5, country.getNativeName());
            pstmt.setInt(6, country.getContinentId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Country Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateCountry(Country country) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Country SET country_alpha2code=?, country_alpha3code=?, country_numeric_code=?, english_name=?, native_name=?, continent_id=?" +
                    " WHERE country_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, country.getCountryAlpha2Code());
            pstmt.setString(2, country.getCountryAlpha3Code());
            pstmt.setString(3, country.getCountryNumericCode());
            pstmt.setString(4, country.getEnglishName());
            pstmt.setString(5, country.getNativeName());
            pstmt.setInt(6, country.getContinentId());
            pstmt.setInt(7, country.getCountryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Country Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateCountry(CountryVW countryVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Country SET country_alpha2code=?, country_alpha3code=?, country_numeric_code=?, english_name=?, native_name=?, continent_id=?" +
                    " WHERE country_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, countryVW.getCountryAlpha2Code());
            pstmt.setString(2, countryVW.getCountryAlpha3Code());
            pstmt.setString(3, countryVW.getCountryNumericCode());
            pstmt.setString(4, countryVW.getEnglishName());
            pstmt.setString(5, countryVW.getNativeName());
            pstmt.setInt(6, countryVW.getContinentId());
            pstmt.setInt(7, countryVW.getCountryId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Country Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteCountry(int countryId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM country WHERE country_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, countryId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Country deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CountryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
