/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.Address;
import dininghall.domain.user.LocalUserAddressVW;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class AddressDAOImpl implements AddressDAO, Serializable {

    @Override
    public List<Address> getAddressList() {
        List<Address> addressList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM address";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Address address = new Address();
                address.setAddressId(resultSet.getLong("address_id"));
                address.setLine1(resultSet.getString("line1"));
                address.setLine2(resultSet.getString("line2"));
                address.setLine3(resultSet.getString("line3"));
                address.setContinentId(resultSet.getInt("continent_id"));
                address.setCountryId(resultSet.getInt("country_id"));
                address.setSubDivisionId(resultSet.getInt("sub_division_id"));
                address.setCityId(resultSet.getInt("city_id"));
                address.setStateId(resultSet.getInt("state_id"));
                address.setStreetId(resultSet.getInt("street_id"));
                address.setPostCode(resultSet.getString("post_code"));

                address.setFirstName(resultSet.getString("first_name"));
                address.setLastName(resultSet.getString("last_name"));
                address.setHeader(resultSet.getString("header"));
                address.setPhoneNumber(resultSet.getString("phone_number"));
                address.setCity(resultSet.getString("city"));


                addressList.add(address);
            }

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return addressList;

    }

    @Override
    public Address getAddressByAddressId(long address_id) {
        Address address = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM address WHERE address_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, address_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                address = new Address();
                address.setAddressId(resultSet.getLong("address_id"));
                address.setLine1(resultSet.getString("line1"));
                address.setLine2(resultSet.getString("line2"));
                address.setLine3(resultSet.getString("line3"));
                address.setContinentId(resultSet.getInt("continent_id"));
                address.setCountryId(resultSet.getInt("country_id"));
                address.setSubDivisionId(resultSet.getInt("sub_division_id"));
                address.setCityId(resultSet.getInt("city_id"));
                address.setStateId(resultSet.getInt("state_id"));
                address.setStreetId(resultSet.getInt("street_id"));
                address.setPostCode(resultSet.getString("post_code"));

                address.setFirstName(resultSet.getString("first_name"));
                address.setLastName(resultSet.getString("last_name"));
                address.setHeader(resultSet.getString("header"));
                address.setPhoneNumber(resultSet.getString("phone_number"));
                address.setCity(resultSet.getString("city"));


                return address;
            }

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return address;
    }

    @Override
    public boolean addAddress(Address address) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        long address_id = 0;

        try {
            String queryString = "INSERT INTO address(line1, line2, line3, continent_id, country_id, sub_division_id, city_id, state_id, street_id, " +
                    "post_code, first_name, last_name, header, phone_number, city) " +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, address.getLine1());
            pstmt.setString(2, address.getLine2());
            pstmt.setString(3, address.getLine3());
            pstmt.setInt(4, address.getContinentId());
            pstmt.setInt(5, address.getCountryId());
            pstmt.setInt(6, address.getSubDivisionId());
            pstmt.setInt(7, address.getCityId());
            pstmt.setInt(8, address.getStateId());
            pstmt.setInt(9, address.getStreetId());
            pstmt.setString(10, address.getPostCode());
            pstmt.setString(11, address.getFirstName());
            pstmt.setString(12, address.getLastName());
            pstmt.setString(13, address.getHeader());
            pstmt.setString(14, address.getPhoneNumber());
            pstmt.setString(15, address.getCity());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                address_id = generatedKeys.getLong(1);
            }

            if (address_id > 0) {
                connection.commit();
            } else {
                return false;
            }

            if (!addLocalUserAddress(address.getLocalUserId(), address_id)) {
                return false;
            }


            System.out.println("Address Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean addAddress(LocalUserAddressVW localUserAddressVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        long address_id = 0;

        try {
            String queryString = "INSERT INTO address(line1, line2, line3, continent_id, country_id, sub_division_id, city_id, state_id, street_id, " +
                    "post_code, first_name, last_name, header, phone_number, city) " +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, localUserAddressVW.getLine1());
            pstmt.setString(2, localUserAddressVW.getLine2());
            pstmt.setString(3, localUserAddressVW.getLine3());
            pstmt.setInt(4, localUserAddressVW.getContinentId());
            pstmt.setInt(5, localUserAddressVW.getCountryId());
            pstmt.setInt(6, localUserAddressVW.getSubDivisionId());
            pstmt.setInt(7, localUserAddressVW.getCityId());
            pstmt.setInt(8, localUserAddressVW.getStateId());
            pstmt.setInt(9, localUserAddressVW.getStreetId());
            pstmt.setString(10, localUserAddressVW.getPostCode());
            pstmt.setString(11, localUserAddressVW.getFirstName());
            pstmt.setString(12, localUserAddressVW.getLastName());
            pstmt.setString(13, localUserAddressVW.getHeader());
            pstmt.setString(14, localUserAddressVW.getPhoneNumber());
            pstmt.setString(15, localUserAddressVW.getCity());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                address_id = generatedKeys.getLong(1);
            }

            if (address_id > 0) {
                connection.commit();
            } else {
                return false;
            }

            if (!addLocalUserAddress(localUserAddressVW.getLocalUserId(), address_id)) {
                return false;
            }


            System.out.println("Address Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean addLocalUserAddress(long localUserId, long addressId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO local_user_address(local_user_id, address_id) " + " VALUES(?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setLong(1, localUserId);
            pstmt.setLong(2, addressId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            connection.commit();

            System.out.println("Local User Address Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateAddress(Address address) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Address SET line1=?, line2=?, line3=?, continent_id=?, country_id=?, sub_division_id=?, " +
                    "city_id=?, state_id=?, street_id=?, post_code=?, first_name=?, last_name=?, header=?, phone_number=?, city=? WHERE address_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, address.getLine1());
            pstmt.setString(2, address.getLine2());
            pstmt.setString(3, address.getLine3());
            pstmt.setInt(4, address.getContinentId());
            pstmt.setInt(5, address.getCountryId());
            pstmt.setInt(6, address.getSubDivisionId());
            pstmt.setInt(7, address.getCityId());
            pstmt.setInt(8, address.getStateId());
            pstmt.setInt(9, address.getStreetId());
            pstmt.setString(10, address.getPostCode());
            pstmt.setString(11, address.getFirstName());
            pstmt.setString(12, address.getLastName());
            pstmt.setString(13, address.getHeader());
            pstmt.setString(14, address.getPhoneNumber());
            pstmt.setString(15, address.getCity());
            pstmt.setLong(16, address.getAddressId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Address Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateAddress(LocalUserAddressVW localUserAddressVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE address SET line1=?, line2=?, line3=?, continent_id=?, country_id=?, sub_division_id=?, " +
                    "city_id=?, state_id=?, street_id=?, post_code=?, first_name=?, last_name=?, header=?, phone_number=?, city=? WHERE address_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, localUserAddressVW.getLine1());
            pstmt.setString(2, localUserAddressVW.getLine2());
            pstmt.setString(3, localUserAddressVW.getLine3());
            pstmt.setInt(4, localUserAddressVW.getContinentId());
            pstmt.setInt(5, localUserAddressVW.getCountryId());
            pstmt.setInt(6, localUserAddressVW.getSubDivisionId());
            pstmt.setInt(7, localUserAddressVW.getCityId());
            pstmt.setInt(8, localUserAddressVW.getStateId());
            pstmt.setInt(9, localUserAddressVW.getStreetId());
            pstmt.setString(10, localUserAddressVW.getPostCode());
            pstmt.setString(11, localUserAddressVW.getFirstName());
            pstmt.setString(12, localUserAddressVW.getLastName());
            pstmt.setString(13, localUserAddressVW.getHeader());
            pstmt.setString(14, localUserAddressVW.getPhoneNumber());
            pstmt.setString(15, localUserAddressVW.getCity());
            pstmt.setLong(16, localUserAddressVW.getAddressId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Address Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }


    @Override
    public boolean deleteAddress(long address_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM address WHERE address_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, address_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Address deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public List<LocalUserAddressVW> getLocalUserAddressVWListByLocalUserId(long localUserId) {
        List<LocalUserAddressVW> localUserAddressVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user_address_view WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, localUserId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                LocalUserAddressVW localUserAddressVW = new LocalUserAddressVW();
                localUserAddressVW.setLocalUserId(resultSet.getLong("local_user_id"));
                localUserAddressVW.setAddressId(resultSet.getInt("address_id"));

                localUserAddressVW.setLine1(resultSet.getString("line1"));
                localUserAddressVW.setLine2(resultSet.getString("line2"));
                localUserAddressVW.setLine3(resultSet.getString("line3"));

                localUserAddressVW.setContinentId(resultSet.getInt("continent_id"));
                localUserAddressVW.setCountryId(resultSet.getInt("country_id"));
                localUserAddressVW.setCountryName(resultSet.getString("country_name"));
                localUserAddressVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                localUserAddressVW.setCityId(resultSet.getInt("city_id"));
                localUserAddressVW.setCityName(resultSet.getString("city_name"));
                localUserAddressVW.setStateId(resultSet.getInt("state_id"));
                localUserAddressVW.setStateName(resultSet.getString("state_name"));
                localUserAddressVW.setStreetId(resultSet.getInt("street_id"));
                localUserAddressVW.setStreetName(resultSet.getString("street_name"));
                localUserAddressVW.setPostCode(resultSet.getString("post_code"));

                localUserAddressVW.setFirstName(resultSet.getString("first_name"));
                localUserAddressVW.setLastName(resultSet.getString("last_name"));
                localUserAddressVW.setHeader(resultSet.getString("header"));
                localUserAddressVW.setPhoneNumber(resultSet.getString("phone_number"));
                localUserAddressVW.setCity(resultSet.getString("city"));


                localUserAddressVWList.add(localUserAddressVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return localUserAddressVWList;

    }

    @Override
    public LocalUserAddressVW getLocalUserAddressVWByAddressId(long addressId) {
        LocalUserAddressVW localUserAddressVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user_address_view WHERE address_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, addressId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                localUserAddressVW = new LocalUserAddressVW();
                localUserAddressVW.setLocalUserId(resultSet.getLong("local_user_id"));
                localUserAddressVW.setAddressId(resultSet.getInt("address_id"));

                localUserAddressVW.setLine1(resultSet.getString("line1"));
                localUserAddressVW.setLine2(resultSet.getString("line2"));
                localUserAddressVW.setLine3(resultSet.getString("line3"));

                localUserAddressVW.setContinentId(resultSet.getInt("continent_id"));
                localUserAddressVW.setCountryId(resultSet.getInt("country_id"));
                localUserAddressVW.setCountryName(resultSet.getString("country_name"));
                localUserAddressVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                localUserAddressVW.setCityId(resultSet.getInt("city_id"));
                localUserAddressVW.setCityName(resultSet.getString("city_name"));
                localUserAddressVW.setStateId(resultSet.getInt("state_id"));
                localUserAddressVW.setStateName(resultSet.getString("state_name"));
                localUserAddressVW.setStreetId(resultSet.getInt("street_id"));
                localUserAddressVW.setStreetName(resultSet.getString("street_name"));
                localUserAddressVW.setPostCode(resultSet.getString("post_code"));

                localUserAddressVW.setFirstName(resultSet.getString("first_name"));
                localUserAddressVW.setLastName(resultSet.getString("last_name"));
                localUserAddressVW.setHeader(resultSet.getString("header"));
                localUserAddressVW.setPhoneNumber(resultSet.getString("phone_number"));
                localUserAddressVW.setCity(resultSet.getString("city"));


            }

        } catch (SQLException e) {
            Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(AddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return localUserAddressVW;

    }

}
