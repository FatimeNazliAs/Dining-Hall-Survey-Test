/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskNote;

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
public class HDeskNoteDAOImpl implements HDeskNoteDAO, Serializable {

    @Override
    public List<HDeskNote> getHDeskNoteList() {
        List<HDeskNote> hDeskNoteList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskNote";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                HDeskNote hDeskNote = new HDeskNote();
                hDeskNote.setSubject(resultSet.getString("subject"));
                hDeskNote.setDescription(resultSet.getString("description"));
                hDeskNote.setLocalUserId(resultSet.getLong("localUserId"));
                hDeskNote.setRecordDate(resultSet.getDate("recordDate"));

                hDeskNoteList.add(hDeskNote);
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

        return hDeskNoteList;

    }

    @Override
    public HDeskNote getHDeskNoteByHDeskNoteId(int noteId) {
        HDeskNote hDeskNote = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskNote WHERE noteId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, noteId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskNote = new HDeskNote();
                hDeskNote.setSubject(resultSet.getString("subject"));
                hDeskNote.setDescription(resultSet.getString("description"));
                hDeskNote.setLocalUserId(resultSet.getLong("localUserId"));
                hDeskNote.setRecordDate(resultSet.getDate("recordDate"));


                return hDeskNote;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskNote;
    }

    @Override
    public HDeskNote getHDeskNoteByName(String name) {
        HDeskNote hDeskNote = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskNote WHERE name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskNote = new HDeskNote();
                hDeskNote.setSubject(resultSet.getString("subject"));
                hDeskNote.setDescription(resultSet.getString("description"));
                hDeskNote.setLocalUserId(resultSet.getLong("localUserId"));
                hDeskNote.setRecordDate(resultSet.getDate("recordDate"));

                return hDeskNote;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskNote;
    }

    @Override
    public boolean addHDeskNote(HDeskNote hDeskNote) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO HDeskNote(subcject, description, localUserId, recordDate) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskNote.getSubject());
            pstmt.setString(2, hDeskNote.getDescription());
            pstmt.setLong(3, hDeskNote.getLocalUserId());
            pstmt.setTimestamp(4, new java.sql.Timestamp(hDeskNote.getRecordDate().getTime()));

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskNote Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateHDeskNote(HDeskNote hDeskNote) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskNote SET subject=?, description=?, localUserId=?, recordDate=? WHERE noteId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskNote.getSubject());
            pstmt.setString(2, hDeskNote.getDescription());
            pstmt.setLong(3, hDeskNote.getLocalUserId());
            pstmt.setTimestamp(4, new java.sql.Timestamp(hDeskNote.getRecordDate().getTime()));

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskNote Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean deleteHDeskNote(int noteId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskNote WHERE noteId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, noteId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskNote deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskNoteDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
