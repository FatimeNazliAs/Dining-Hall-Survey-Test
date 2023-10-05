/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.dao.user.LocalUserDAOImpl;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.common.ConnectionFactory;
import dininghall.domain.common.ContactMessage;
import dininghall.domain.common.ContactMessageVW;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class ContactMessageDAOImpl implements ContactMessageDAO, Serializable {

    @Override
    public List<ContactMessage> getContactMessageList() {
        List<ContactMessage> contactMessageList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM contact_message ORDER BY created_date DESC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ContactMessage contactMessage = new ContactMessage();
                contactMessage.setContactMessageId(resultSet.getLong("contact_message_id"));
                contactMessage.setFullName(resultSet.getString("full_name"));
                contactMessage.setEmail(resultSet.getString("email"));
                contactMessage.setMessageBody(resultSet.getString("message_body"));
                contactMessage.setCreatedDate(resultSet.getTimestamp("created_date"));
                contactMessage.setExpiryDate(resultSet.getTimestamp("expiry_date"));
                contactMessage.setSubject(resultSet.getString("subject"));

                contactMessageList.add(contactMessage);
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

        return contactMessageList;

    }

    @Override
    public List<ContactMessageVW> getContactMessageVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<ContactMessageVW> contactMessageVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY created_date DESC, email ASC ) as row " +
                    "FROM contact_message_view WHERE 1=1 ";


            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if (!filterProperty.equals("globalFilter")) {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue + "%'";
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
                ContactMessageVW contactMessageVW = new ContactMessageVW();
                contactMessageVW.setContactMessageId(resultSet.getLong("contact_message_id"));
                contactMessageVW.setFullName(resultSet.getString("full_name"));
                contactMessageVW.setEmail(resultSet.getString("email"));
                contactMessageVW.setMessageBody(resultSet.getString("message_body"));
                contactMessageVW.setCreatedDate(resultSet.getDate("created_date"));
                contactMessageVW.setCreatedDateStr(resultSet.getString("created_datestr"));
                contactMessageVW.setExpiryDate(resultSet.getDate("expiry_date"));
                contactMessageVW.setExpiryDateStr(resultSet.getString("expiry_datestr"));
                contactMessageVW.setSubject(resultSet.getString("subject"));

                contactMessageVWList.add(contactMessageVW);
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

        return contactMessageVWList;
    }

    @Override
    public ContactMessage getContactMessageByContactMessageId(long contact_message_id) {
        ContactMessage contactMessage = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM contact_message WHERE \"contact_message_id\"=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, contact_message_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                contactMessage = new ContactMessage();
                contactMessage.setContactMessageId(resultSet.getLong("contact_message_id"));
                contactMessage.setFullName(resultSet.getString("full_name"));
                contactMessage.setEmail(resultSet.getString("email"));
                contactMessage.setMessageBody(resultSet.getString("message_body"));
                contactMessage.setCreatedDate(resultSet.getTimestamp("created_date"));
                contactMessage.setExpiryDate(resultSet.getTimestamp("expiry_date"));
                contactMessage.setSubject(resultSet.getString("subject"));

                return contactMessage;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return contactMessage;
    }

    @Override
    public ContactMessage getContactMessageByEmail(String email) {
        ContactMessage contactMessage = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM contact_message WHERE email=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, email);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                contactMessage = new ContactMessage();
                contactMessage.setContactMessageId(resultSet.getLong("contact_message_id"));
                contactMessage.setFullName(resultSet.getString("full_name"));
                contactMessage.setEmail(resultSet.getString("email"));
                contactMessage.setMessageBody(resultSet.getString("message_body"));
                contactMessage.setCreatedDate(resultSet.getTimestamp("created_date"));
                contactMessage.setExpiryDate(resultSet.getTimestamp("expiry_date"));
                contactMessage.setSubject(resultSet.getString("subject"));

                return contactMessage;
            }

        } catch (SQLException e) {
            Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return contactMessage;
    }

    @Override
    public boolean addContactMessage(ContactMessage contactMessage) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            java.util.Date created_date = new java.util.Date();
            java.sql.Timestamp createdTime = new java.sql.Timestamp(created_date.getTime());

            java.sql.Timestamp expiryTime = new java.sql.Timestamp(contactMessage.getExpiryDate().getTime());

            String queryString = "INSERT INTO contact_message (full_name, email, message_body, created_date, expiry_date, subject) VALUES(?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, contactMessage.getFullName());
            pstmt.setString(2, contactMessage.getEmail());
            pstmt.setString(3, contactMessage.getMessageBody());
            pstmt.setTimestamp(4, createdTime);
            pstmt.setTimestamp(5, expiryTime);
            pstmt.setString(6, contactMessage.getSubject());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ContactMessage Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateContactMessage(ContactMessage contactMessage) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            java.sql.Timestamp createdTime = new java.sql.Timestamp(contactMessage.getCreatedDate().getTime());
            java.sql.Timestamp expiryTime = new java.sql.Timestamp(contactMessage.getExpiryDate().getTime());

            String queryString = "UPDATE contact_message SET full_name=?, email=?, message_body=?, created_date=?, expiry_date=, subject=? WHERE contact_message_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, contactMessage.getFullName());
            pstmt.setString(2, contactMessage.getEmail());
            pstmt.setString(3, contactMessage.getMessageBody());
            pstmt.setTimestamp(4, createdTime);
            pstmt.setTimestamp(5, expiryTime);
            pstmt.setString(6, contactMessage.getSubject());
            pstmt.setLong(7, contactMessage.getContactMessageId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ContactMessage Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateContactMessage(ContactMessageVW contactMessageVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            java.sql.Timestamp createdTime = new java.sql.Timestamp(contactMessageVW.getCreatedDate().getTime());
            java.sql.Timestamp expiryTime = new java.sql.Timestamp(contactMessageVW.getExpiryDate().getTime());

            String queryString = "UPDATE contact_message SET full_name=?, email=?, message_body=?, created_date=?, expiry_date=?, subject=? WHERE contact_message_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, contactMessageVW.getFullName());
            pstmt.setString(2, contactMessageVW.getEmail());
            pstmt.setString(3, contactMessageVW.getMessageBody());
            pstmt.setTimestamp(4, createdTime);
            pstmt.setTimestamp(5, expiryTime);
            pstmt.setString(6, contactMessageVW.getSubject());
            pstmt.setLong(7, contactMessageVW.getContactMessageId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ContactMessage Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteContactMessage(long contact_message_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM contact_message WHERE contact_message_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, contact_message_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ContactMessage deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public int getContactMessageCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int contactMessageCount = 0;

        try {
            String queryString = "SELECT COUNT(contact_message_id) as contact_message_count FROM contact_message_view WHERE 1=1 ";
            connection = ConnectionFactory.getInstance().getConnection();


            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    Object filterValue = filters.get(filterProperty);

                    if (!filterProperty.equals("globalFilter")) {
                        queryString += " AND " + filterProperty + " LIKE N'%" + filterValue + "%'";
                    }

                    System.out.println(filterProperty);
                    System.out.println(filterValue);
                }

            }

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                contactMessageCount = resultSet.getInt("contact_message_count");
            }

        } catch (SQLException e) {
            Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ContactMessageDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return contactMessageCount;
    }

}
