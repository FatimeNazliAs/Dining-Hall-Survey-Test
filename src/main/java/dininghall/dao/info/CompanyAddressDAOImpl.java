/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.CompanyAddress;
import dininghall.domain.info.CompanyAddressVW;

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
public class CompanyAddressDAOImpl implements CompanyAddressDAO, Serializable {
    private AddressDAO addressDAO = new AddressDAOImpl();

    @Override
    public List<CompanyAddress> getCompanyAddressList() {
        List<CompanyAddress> companyList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CompanyAddress";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                CompanyAddress companyAddress = new CompanyAddress();
                companyAddress.setCompanyAddressId(resultSet.getLong("companyAddressId"));
                companyAddress.setCompanyId(resultSet.getLong("companyId"));
                companyAddress.setAddressId(resultSet.getLong("addressId"));

                companyList.add(companyAddress);
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return companyList;

    }

    public List<CompanyAddressVW> getCompanyAddressListByCompanyId(long companyId) {
        List<CompanyAddressVW> companyVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CompanyAddressView WHERE companyId=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, companyId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                CompanyAddressVW companyAddressVW = new CompanyAddressVW();
                companyAddressVW.setCompanyId(resultSet.getLong("companyId"));
                companyAddressVW.setCompanyName(resultSet.getString("companyName"));
                companyAddressVW.setCompanyAddressId(resultSet.getLong("companyAddressId"));
                companyAddressVW.setAddressId(resultSet.getLong("addressId"));
                companyAddressVW.setLine1(resultSet.getString("line1"));
                companyAddressVW.setLine2(resultSet.getString("line2"));
                companyAddressVW.setLine3(resultSet.getString("line3"));
                companyAddressVW.setPostCode(resultSet.getString("postCode"));
                companyAddressVW.setContinent(resultSet.getString("continent"));
                companyAddressVW.setCountry(resultSet.getString("country"));
                companyAddressVW.setSubDivision(resultSet.getString("subDivision"));
                companyAddressVW.setCity(resultSet.getString("city"));
                companyAddressVW.setState(resultSet.getString("state"));
                companyAddressVW.setStreet(resultSet.getString("street"));

                companyVWList.add(companyAddressVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return companyVWList;

    }

    @Override
    public CompanyAddress getCompanyAddressByCompanyAddressId(long companyAddressId) {
        CompanyAddress companyAddress = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CompanyAddress WHERE companyAddressId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, companyAddressId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                companyAddress = new CompanyAddress();
                companyAddress.setCompanyAddressId(resultSet.getLong("companyAddressId"));
                companyAddress.setCompanyId(resultSet.getLong("companyId"));
                companyAddress.setAddressId(resultSet.getLong("addressId"));

                return companyAddress;
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return companyAddress;
    }

    @Override
    public CompanyAddress getCompanyAddressByCompanyId(long companyId) {
        CompanyAddress companyAddress = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CompanyAddress WHERE companyId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, companyId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                companyAddress = new CompanyAddress();
                companyAddress.setCompanyAddressId(resultSet.getLong("companyAddressId"));
                companyAddress.setCompanyId(resultSet.getLong("companyId"));
                companyAddress.setAddressId(resultSet.getLong("addressId"));

                return companyAddress;
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return companyAddress;
    }

    @Override
    public CompanyAddress getCompanyAddressByAddressId(long addressId) {
        CompanyAddress companyAddress = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM CompanyAddress WHERE addressId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, addressId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                companyAddress = new CompanyAddress();
                companyAddress.setCompanyAddressId(resultSet.getLong("companyAddressId"));
                companyAddress.setCompanyId(resultSet.getLong("companyId"));
                companyAddress.setAddressId(resultSet.getLong("addressId"));

                return companyAddress;
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return companyAddress;
    }

    @Override
    public boolean addCompanyAddress(CompanyAddress companyAddress) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        long addressId = 0;
/**
 try
 {
 addressId = addressDAO.addAddress(companyAddress.getAddress());
 if(addressId == 0)
 {
 return false;
 }

 String queryString = "INSERT INTO CompanyAddress(companyId, addressId) VALUES(?,?)";
 connection = ConnectionFactory.getInstance().getConnection();
 connection.setAutoCommit(false);

 pstmt = connection.prepareStatement(queryString);
 pstmt.setLong(1, companyAddress.getCompanyId());
 pstmt.setLong(2, addressId);

 pstmt.executeUpdate();

 connection.commit();

 System.out.println("Company Address Added Successfully");


 return true;

 } catch (SQLException e)
 {

 Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
 try
 {
 if (connection != null)
 {
 connection.rollback();
 }

 } catch (SQLException ex)
 {
 Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
 }
 } finally
 {
 try
 {
 if (pstmt != null)
 {
 pstmt.close();
 }
 if (connection != null)
 {
 connection.close();
 }
 } catch (SQLException e)
 {
 Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
 } catch (Exception e)
 {
 Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
 }

 }
 **/

        return false;

    }

    @Override
    public boolean updateCompanyAddress(CompanyAddress companyAddress) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Company SET companyId=?, addressId=? WHERE companyAddressId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, companyAddress.getCompanyId());
            pstmt.setLong(2, companyAddress.getAddressId());
            pstmt.setLong(3, companyAddress.getCompanyAddressId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Company Address Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteCompanyAddress(long companyAddressId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM CompanyAddress WHERE companyAddressId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, companyAddressId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Company Address deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyAddressDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }


}
