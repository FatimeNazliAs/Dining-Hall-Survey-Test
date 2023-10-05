/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.offer;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import dininghall.common.ConnectionFactory;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.offer.Offer;
import dininghall.domain.offer.OfferVW;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class OfferDAOImpl implements OfferDAO, Serializable {

    public OfferDAOImpl() {

    }

    @Override
    public List<Offer> getOfferList() {
        List<Offer> offerList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM \"offer\"";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                Offer offer = new Offer();
                offer.setOfferId(resultSet.getInt("offer_id"));
                offer.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offer.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offer.setStartAt(resultSet.getTimestamp("start_date"));
                offer.setEndAt(resultSet.getTimestamp("end_date"));
                offer.setDiscountAmount(resultSet.getInt("discount_amount"));
                offer.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offer.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offer.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offer.setCode(resultSet.getString("code"));
                offer.setCodeDesc(resultSet.getString("code_desc"));
                offer.setMaxUse(resultSet.getInt("max_use"));
                offer.setMaxUseUser(resultSet.getInt("max_use_user"));
                offer.setEnabled(resultSet.getInt("enabled"));

                offerList.add(offer);
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

        return offerList;

    }

    @Override
    public List<OfferVW> getOfferVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<OfferVW> offerVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM offer_view";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                OfferVW offerVW = new OfferVW();
                offerVW.setOfferId(resultSet.getInt("offer_id"));
                offerVW.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offerVW.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offerVW.setStartAt(resultSet.getTimestamp("start_date"));
                offerVW.setEndAt(resultSet.getTimestamp("end_date"));
                offerVW.setDiscountAmount(resultSet.getInt("discount_amount"));
                offerVW.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offerVW.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offerVW.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offerVW.setCode(resultSet.getString("code"));
                offerVW.setCodeDesc(resultSet.getString("code_desc"));
                offerVW.setMaxUse(resultSet.getInt("max_use"));
                offerVW.setMaxUseUser(resultSet.getInt("max_use_user"));
                offerVW.setEnabled(resultSet.getInt("enabled"));


                offerVWList.add(offerVW);
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

        return offerVWList;

    }

    @Override
    public List<OfferVW> getTopOfferVWList(int size) {
        List<OfferVW> offerVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM offer_view ORDER BY code DESC LIMIT ?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, size);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                OfferVW offerVW = new OfferVW();
                offerVW.setOfferId(resultSet.getInt("offer_id"));
                offerVW.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offerVW.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offerVW.setStartAt(resultSet.getTimestamp("start_date"));
                offerVW.setEndAt(resultSet.getTimestamp("end_date"));
                offerVW.setDiscountAmount(resultSet.getInt("discount_amount"));
                offerVW.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offerVW.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offerVW.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offerVW.setCode(resultSet.getString("code"));
                offerVW.setCodeDesc(resultSet.getString("code_desc"));
                offerVW.setMaxUse(resultSet.getInt("max_use"));
                offerVW.setMaxUseUser(resultSet.getInt("max_use_user"));
                offerVW.setEnabled(resultSet.getInt("enabled"));


                offerVWList.add(offerVW);
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

        return offerVWList;

    }

    @Override
    public List<OfferVW> getTopPriceOfferVWList(int size) {
        List<OfferVW> offerVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM offer_view ORDER BY code DESC LIMIT ?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, size);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                OfferVW offerVW = new OfferVW();
                offerVW.setOfferId(resultSet.getInt("offer_id"));
                offerVW.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offerVW.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offerVW.setStartAt(resultSet.getTimestamp("start_date"));
                offerVW.setEndAt(resultSet.getTimestamp("end_date"));
                offerVW.setDiscountAmount(resultSet.getInt("discount_amount"));
                offerVW.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offerVW.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offerVW.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offerVW.setCode(resultSet.getString("code"));
                offerVW.setCodeDesc(resultSet.getString("code_desc"));
                offerVW.setMaxUse(resultSet.getInt("max_use"));
                offerVW.setMaxUseUser(resultSet.getInt("max_use_user"));
                offerVW.setEnabled(resultSet.getInt("enabled"));


                offerVWList.add(offerVW);
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

        return offerVWList;

    }

    @Override
    public List<OfferVW> getOfferVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<OfferVW> offerVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY code ASC) as row " +
                    "FROM offer_view WHERE 1=1 ";


            String globalFilter = "";
            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);


                    if (!filterProperty.equals("globalFilter")) {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue.toString().toUpperCase() + "%'";
                    } else {
                        globalFilter = " AND ( (code LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " OR (code_desc LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " )";

                        queryString += globalFilter;
                    }


                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }


            queryString += ") a WHERE row>? AND row<=?";

            System.out.println(queryString);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, first);
            pstmt.setInt(2, pageSize);


            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                OfferVW offerVW = new OfferVW();
                offerVW.setOfferId(resultSet.getInt("offer_id"));
                offerVW.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offerVW.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offerVW.setStartAt(resultSet.getTimestamp("start_date"));
                offerVW.setEndAt(resultSet.getTimestamp("end_date"));
                offerVW.setDiscountAmount(resultSet.getInt("discount_amount"));
                offerVW.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offerVW.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offerVW.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offerVW.setCode(resultSet.getString("code"));
                offerVW.setCodeDesc(resultSet.getString("code_desc"));
                offerVW.setMaxUse(resultSet.getInt("max_use"));
                offerVW.setMaxUseUser(resultSet.getInt("max_use_user"));
                offerVW.setEnabled(resultSet.getInt("enabled"));


                offerVWList.add(offerVW);
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

        return offerVWList;
    }

    @Override
    public List<OfferVW> getActiveOfferVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<OfferVW> offerVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY code ASC) as row " +
                    "FROM offer_view WHERE 1=1  ";


            String globalFilter = "";
            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);


                    if (!filterProperty.equals("globalFilter")) {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue.toString().toUpperCase() + "%'";
                    } else {
                        globalFilter = " AND ( (code LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " OR (code_desc LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " )";

                        queryString += globalFilter;
                    }


                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }


            queryString += ") a WHERE row>? AND row<=?";

            System.out.println(queryString);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, first);
            pstmt.setInt(2, pageSize);


            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                OfferVW offerVW = new OfferVW();
                offerVW.setOfferId(resultSet.getInt("offer_id"));
                offerVW.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offerVW.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offerVW.setStartAt(resultSet.getTimestamp("start_date"));
                offerVW.setEndAt(resultSet.getTimestamp("end_date"));
                offerVW.setDiscountAmount(resultSet.getInt("discount_amount"));
                offerVW.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offerVW.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offerVW.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offerVW.setCode(resultSet.getString("code"));
                offerVW.setCodeDesc(resultSet.getString("code_desc"));
                offerVW.setMaxUse(resultSet.getInt("max_use"));
                offerVW.setMaxUseUser(resultSet.getInt("max_use_user"));
                offerVW.setEnabled(resultSet.getInt("enabled"));


                offerVWList.add(offerVW);
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

        return offerVWList;
    }

    @Override
    public Offer getOfferByOfferId(int offer_id) {
        Offer offer = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM \"offer\" WHERE offer_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, offer_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                offer = new Offer();
                offer.setOfferId(resultSet.getInt("offer_id"));
                offer.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offer.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offer.setStartAt(resultSet.getTimestamp("start_date"));
                offer.setEndAt(resultSet.getTimestamp("end_date"));
                offer.setDiscountAmount(resultSet.getInt("discount_amount"));
                offer.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offer.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offer.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offer.setCode(resultSet.getString("code"));
                offer.setCodeDesc(resultSet.getString("code_desc"));
                offer.setMaxUse(resultSet.getInt("max_use"));
                offer.setMaxUseUser(resultSet.getInt("max_use_user"));
                offer.setEnabled(resultSet.getInt("enabled"));


                return offer;
            }

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return offer;
    }

    @Override
    public OfferVW getOfferVWByOfferId(int offer_id) {
        OfferVW offerVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM offer_view WHERE offer_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, offer_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                offerVW = new OfferVW();
                offerVW.setOfferId(resultSet.getInt("offer_id"));
                offerVW.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offerVW.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offerVW.setStartAt(resultSet.getTimestamp("start_date"));
                offerVW.setEndAt(resultSet.getTimestamp("end_date"));
                offerVW.setDiscountAmount(resultSet.getInt("discount_amount"));
                offerVW.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offerVW.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offerVW.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offerVW.setCode(resultSet.getString("code"));
                offerVW.setCodeDesc(resultSet.getString("code_desc"));
                offerVW.setMaxUse(resultSet.getInt("max_use"));
                offerVW.setMaxUseUser(resultSet.getInt("max_use_user"));
                offerVW.setEnabled(resultSet.getInt("enabled"));


                return offerVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return offerVW;
    }

    @Override
    public Offer getOfferByCode(String code) {
        Offer offer = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM \"offer\" WHERE code=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, code);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                offer = new Offer();
                offer.setOfferId(resultSet.getInt("offer_id"));
                offer.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offer.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offer.setStartAt(resultSet.getTimestamp("start_date"));
                offer.setEndAt(resultSet.getTimestamp("end_date"));
                offer.setDiscountAmount(resultSet.getInt("discount_amount"));
                offer.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offer.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offer.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offer.setCode(resultSet.getString("code"));
                offer.setCodeDesc(resultSet.getString("code_desc"));
                offer.setMaxUse(resultSet.getInt("max_use"));
                offer.setMaxUseUser(resultSet.getInt("max_use_user"));
                offer.setEnabled(resultSet.getInt("enabled"));

                return offer;
            }

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return offer;
    }

    @Override
    public OfferVW getOfferVWByCode(String code) {
        OfferVW offerVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM offer_view WHERE code=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, code);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                offerVW = new OfferVW();
                offerVW.setOfferId(resultSet.getInt("offer_id"));
                offerVW.setOfferTypeId(resultSet.getInt("offer_type_id"));
                offerVW.setOfferMethodId(resultSet.getInt("offer_method_id"));
                offerVW.setStartAt(resultSet.getTimestamp("start_date"));
                offerVW.setEndAt(resultSet.getTimestamp("end_date"));
                offerVW.setDiscountAmount(resultSet.getInt("discount_amount"));
                offerVW.setMinAmountInCart(resultSet.getInt("min_amount_in_cart"));
                offerVW.setMaxTotalUsage(resultSet.getInt("max_total_usage"));
                offerVW.setAllowedMaxDiscount(resultSet.getInt("allowed_max_discount"));
                offerVW.setCode(resultSet.getString("code"));
                offerVW.setCodeDesc(resultSet.getString("code_desc"));
                offerVW.setMaxUse(resultSet.getInt("max_use"));
                offerVW.setMaxUseUser(resultSet.getInt("max_use_user"));
                offerVW.setEnabled(resultSet.getInt("enabled"));


                return offerVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return offerVW;
    }

    @Override
    public boolean addOffer(Offer offer) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO \"offer\" (offer_type_id, offer_method_id, start_date, end_date, " +
                    "discount_amount, min_amount_in_cart, max_total_usage, allowed_max_discount, code, code_desc," +
                    "max_use, max_use_user, enabled) " +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, offer.getOfferTypeId());
            pstmt.setInt(2, offer.getOfferMethodId());
            pstmt.setTimestamp(3, new Timestamp(offer.getStartAt().getTime()));
            pstmt.setTimestamp(4, new Timestamp(offer.getEndAt().getTime()));
            pstmt.setInt(5, offer.getDiscountAmount());
            pstmt.setInt(6, offer.getMinAmountInCart());
            pstmt.setInt(7, offer.getMaxTotalUsage());
            pstmt.setInt(8, offer.getAllowedMaxDiscount());
            pstmt.setString(9, offer.getCode());
            pstmt.setString(10, offer.getCodeDesc());
            pstmt.setInt(11, offer.getMaxUse());
            pstmt.setInt(12, offer.getMaxUseUser());
            pstmt.setInt(13, offer.getEnabled());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating offer failed, no rows affected.");
            }

            connection.commit();

            System.out.println("Offer Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateOffer(Offer offer) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE \"offer\" SET offer_type_id=?, offer_method_id=?, start_date=?, end_date=?, " +
                    "discount_amount=?, min_amount_in_cart=?, max_total_usage=?, allowed_max_discount=?, code=?, code_desc=?, " +
                    "max_use=?, max_use_user=?, enabled=? " +
                    "WHERE offer_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, offer.getOfferTypeId());
            pstmt.setInt(2, offer.getOfferMethodId());
            pstmt.setTimestamp(3, new Timestamp(offer.getStartAt().getTime()));
            pstmt.setTimestamp(4, new Timestamp(offer.getEndAt().getTime()));
            pstmt.setInt(5, offer.getDiscountAmount());
            pstmt.setInt(6, offer.getMinAmountInCart());
            pstmt.setInt(7, offer.getMaxTotalUsage());
            pstmt.setInt(8, offer.getAllowedMaxDiscount());
            pstmt.setString(9, offer.getCode());
            pstmt.setString(10, offer.getCodeDesc());
            pstmt.setInt(11, offer.getMaxUse());
            pstmt.setInt(12, offer.getMaxUseUser());
            pstmt.setInt(13, offer.getEnabled());
            pstmt.setLong(14, offer.getOfferId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Offer Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean updateOffer(OfferVW offerVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE \"offer\" SET offer_type_id=?, offer_method_id=?, start_date=?, end_date=?, " +
                    "discount_amount=?, min_amount_in_cart=?, max_total_usage=?, allowed_max_discount=?, code=?, code_desc=?, " +
                    "max_use=?, max_use_user=?, enabled=? " +
                    "WHERE offer_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setInt(1, offerVW.getOfferTypeId());
            pstmt.setInt(2, offerVW.getOfferMethodId());
            pstmt.setTimestamp(3, new Timestamp(offerVW.getStartAt().getTime()));
            pstmt.setTimestamp(4, new Timestamp(offerVW.getEndAt().getTime()));
            pstmt.setInt(5, offerVW.getDiscountAmount());
            pstmt.setInt(6, offerVW.getMinAmountInCart());
            pstmt.setInt(7, offerVW.getMaxTotalUsage());
            pstmt.setInt(8, offerVW.getAllowedMaxDiscount());
            pstmt.setString(9, offerVW.getCode());
            pstmt.setString(10, offerVW.getCodeDesc());
            pstmt.setInt(11, offerVW.getMaxUse());
            pstmt.setInt(12, offerVW.getMaxUseUser());
            pstmt.setInt(13, offerVW.getEnabled());
            pstmt.setLong(14, offerVW.getOfferId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Offer Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteOffer(int offer_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM \"offer\" WHERE offer_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, offer_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Offer deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public int getOfferCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int offerCount = 0;

        try {
            String queryString = "SELECT COUNT(offer_id) as offerCount FROM offer_view WHERE 1=1 ";
            connection = ConnectionFactory.getInstance().getConnection();


            String globalFilter = "";
            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);


                    if (!filterProperty.equals("globalFilter")) {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue.toString().toUpperCase() + "%'";
                    } else {
                        globalFilter = " AND ( (\"code\" LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " OR (code_desc LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " )";

                        queryString += globalFilter;
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                offerCount = resultSet.getInt("offerCount");
            }

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return offerCount;
    }

    @Override
    public int getActiveOfferCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int offerCount = 0;

        try {
            String queryString = "SELECT COUNT(offer_id) as offerCount FROM offer_view WHERE 1=1 ";
            connection = ConnectionFactory.getInstance().getConnection();


            String globalFilter = "";
            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);


                    if (!filterProperty.equals("globalFilter")) {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue.toString().toUpperCase() + "%'";
                    } else {
                        globalFilter = " AND ( (\"code\" LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " OR (code_desc LIKE N'%" + filterValue.toString().toUpperCase() + "%')"
                                + " )";

                        queryString += globalFilter;
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                offerCount = resultSet.getInt("offerCount");
            }

        } catch (SQLException e) {
            Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(OfferDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return offerCount;
    }
}
