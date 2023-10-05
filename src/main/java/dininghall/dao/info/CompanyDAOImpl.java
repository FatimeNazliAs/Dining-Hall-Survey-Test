/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.Company;

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
public class CompanyDAOImpl implements CompanyDAO, Serializable {

    @Override
    public List<Company> getCompanyList() {
        List<Company> companyList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Company";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setCompanyId(resultSet.getLong("companyId"));
                company.setCompanyName(resultSet.getString("companyName"));
                company.setPhoneNumber(resultSet.getString("phoneNumber"));
                company.setWebsite(resultSet.getString("website"));
                company.setEmail(resultSet.getString("email"));
                company.setFaxNumber(resultSet.getString("faxNumber"));

                companyList.add(company);
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return companyList;

    }

    @Override
    public Company getCompanyByCompanyId(long companyId) {
        Company company = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Company WHERE companyId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, companyId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                company = new Company();
                company.setCompanyId(resultSet.getLong("companyId"));
                company.setCompanyName(resultSet.getString("companyName"));
                company.setPhoneNumber(resultSet.getString("phoneNumber"));
                company.setWebsite(resultSet.getString("website"));
                company.setEmail(resultSet.getString("email"));
                company.setFaxNumber(resultSet.getString("faxNumber"));

                return company;
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return company;
    }

    @Override
    public Company getCompanyByName(String name) {
        Company company = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM Company WHERE companyName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                company = new Company();
                company.setCompanyId(resultSet.getLong("companyId"));
                company.setCompanyName(resultSet.getString("companyName"));
                company.setPhoneNumber(resultSet.getString("phoneNumber"));
                company.setWebsite(resultSet.getString("website"));
                company.setEmail(resultSet.getString("email"));
                company.setFaxNumber(resultSet.getString("faxNumber"));

                return company;
            }

        } catch (SQLException e) {
            Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return company;
    }

    @Override
    public boolean addCompany(Company company) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO Company(companyName, phoneNumber, website, email, faxNumber) VALUES(?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, company.getCompanyName());
            pstmt.setString(2, company.getPhoneNumber());
            pstmt.setString(3, company.getWebsite());
            pstmt.setString(4, company.getEmail());
            pstmt.setString(5, company.getFaxNumber());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Company Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateCompany(Company company) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE Company SET companyName=?, phoneNumber=?, website=?, email=?, faxNumber=? WHERE companyId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, company.getCompanyName());
            pstmt.setString(2, company.getPhoneNumber());
            pstmt.setString(3, company.getWebsite());
            pstmt.setString(4, company.getEmail());
            pstmt.setString(5, company.getFaxNumber());
            pstmt.setLong(6, company.getCompanyId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Company Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteCompany(long companyId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM Company WHERE companyId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, companyId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Company deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CompanyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
