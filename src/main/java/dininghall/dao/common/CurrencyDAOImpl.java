/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.common.ConnectionFactory;
import dininghall.domain.common.Currency;

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
public class CurrencyDAOImpl implements CurrencyDAO, Serializable {

    @Override
    public List<Currency> getCurrencyList() {
        List<Currency> currencyList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM currency";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setCurrencyId(resultSet.getInt("currency_id"));
                currency.setName(resultSet.getString("name"));
                currency.setBuyingPrice(resultSet.getDouble("buying_price"));
                currency.setSellingPrice(resultSet.getDouble("selling_price"));
                currency.setIsForex(resultSet.getInt("is_forex"));

                currencyList.add(currency);
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

        return currencyList;

    }

    @Override
    public Currency getCurrencyByCurrencyId(int currencyId) {
        Currency currency = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM currency WHERE currency_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, currencyId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                currency = new Currency();
                currency.setCurrencyId(resultSet.getInt("currency_id"));
                currency.setName(resultSet.getString("name"));
                currency.setBuyingPrice(resultSet.getDouble("buying_price"));
                currency.setSellingPrice(resultSet.getDouble("selling_price"));
                currency.setIsForex(resultSet.getInt("is_forex"));

                return currency;
            }

        } catch (SQLException e) {
            Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return currency;
    }

    @Override
    public Currency getCurrencyByName(String name) {
        Currency currency = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM currency WHERE [name]=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                currency = new Currency();
                currency.setCurrencyId(resultSet.getInt("currency_id"));
                currency.setName(resultSet.getString("name"));
                currency.setBuyingPrice(resultSet.getDouble("buying_price"));
                currency.setSellingPrice(resultSet.getDouble("selling_price"));
                currency.setIsForex(resultSet.getInt("is_forex"));

                return currency;
            }

        } catch (SQLException e) {
            Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return currency;
    }

    @Override
    public boolean addCurrency(Currency currency) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO currency([name], buying_price, selling_price, is_forex) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, currency.getName());
            pstmt.setDouble(2, currency.getBuyingPrice());
            pstmt.setDouble(3, currency.getSellingPrice());
            pstmt.setInt(4, currency.getIsForex());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Currency Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateCurrency(Currency currency) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE currency SET [name]=?, buying_price=?, selling_price=?, is_forex=? WHERE currency_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, currency.getName());
            pstmt.setDouble(2, currency.getBuyingPrice());
            pstmt.setDouble(3, currency.getSellingPrice());
            pstmt.setInt(4, currency.getIsForex());
            pstmt.setInt(5, currency.getCurrencyId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Currency Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteCurrency(int currencyId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM currency WHERE currency_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, currencyId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Currency deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(CurrencyDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
