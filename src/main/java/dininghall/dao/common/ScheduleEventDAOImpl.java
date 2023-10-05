/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.common.ConnectionFactory;
import dininghall.domain.common.ScheduleEvent;
import dininghall.domain.common.ScheduleEventVW;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class ScheduleEventDAOImpl implements ScheduleEventDAO, Serializable {

    @Override
    public List<ScheduleEvent> getScheduleEventList() {
        List<ScheduleEvent> scheduleEventList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM ScheduleEvent";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ScheduleEvent scheduleEvent = new ScheduleEvent();
                scheduleEvent.setScheduleEventId(resultSet.getInt("scheduleEventId"));
                scheduleEvent.setTitle(resultSet.getString("title"));
                scheduleEvent.setDescription(resultSet.getString("description"));
                scheduleEvent.setStartDate(resultSet.getTimestamp("startDate"));
                scheduleEvent.setEndDate(resultSet.getTimestamp("endDate"));
                scheduleEvent.setLocalUserId(resultSet.getLong("localUserId"));
                scheduleEvent.setCreatedDate(resultSet.getTimestamp("createdDate"));

                scheduleEventList.add(scheduleEvent);
            }

        } catch (SQLException e) {
            Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return scheduleEventList;

    }

    @Override
    public List<ScheduleEventVW> getScheduleEventVWList() {
        List<ScheduleEventVW> scheduleEventVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM ScheduleEventView";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ScheduleEventVW scheduleEventVW = new ScheduleEventVW();
                scheduleEventVW.setScheduleEventId(resultSet.getInt("scheduleEventId"));
                scheduleEventVW.setTitle(resultSet.getString("title"));
                scheduleEventVW.setDescription(resultSet.getString("description"));
                scheduleEventVW.setStartDate(resultSet.getTimestamp("startDate"));
                scheduleEventVW.setStartDateStr(resultSet.getString("startDateStr"));
                scheduleEventVW.setEndDate(resultSet.getTimestamp("endDate"));
                scheduleEventVW.setEndDateStr(resultSet.getString("endDateStr"));
                scheduleEventVW.setLocalUserId(resultSet.getLong("localUserId"));
                scheduleEventVW.setLocalUser(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
                scheduleEventVW.setCreatedDate(resultSet.getTimestamp("createdDate"));
                scheduleEventVW.setCreatedDateStr(resultSet.getString("createdDateStr"));

                scheduleEventVWList.add(scheduleEventVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return scheduleEventVWList;

    }

    @Override
    public ScheduleEvent getScheduleEventByScheduleEventId(int scheduleEventId) {
        ScheduleEvent scheduleEvent = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM ScheduleEvent WHERE scheduleEventId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, scheduleEventId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                scheduleEvent = new ScheduleEvent();
                scheduleEvent.setScheduleEventId(resultSet.getInt("scheduleEventId"));
                scheduleEvent.setTitle(resultSet.getString("title"));
                scheduleEvent.setDescription(resultSet.getString("description"));
                scheduleEvent.setStartDate(resultSet.getTimestamp("startDate"));
                scheduleEvent.setEndDate(resultSet.getTimestamp("endDate"));
                scheduleEvent.setLocalUserId(resultSet.getLong("localUserId"));
                scheduleEvent.setCreatedDate(resultSet.getTimestamp("createdDate"));

                return scheduleEvent;
            }

        } catch (SQLException e) {
            Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return scheduleEvent;
    }


    @Override
    public boolean addScheduleEvent(ScheduleEvent scheduleEvent) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO ScheduleEvent(title, description, startDate, endDate, localUserId, createdDate) VALUES(?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, scheduleEvent.getTitle());
            pstmt.setString(2, scheduleEvent.getDescription());
            pstmt.setTimestamp(3, new java.sql.Timestamp(scheduleEvent.getStartDate().getTime()));
            pstmt.setTimestamp(4, new java.sql.Timestamp(scheduleEvent.getEndDate().getTime()));
            pstmt.setLong(5, scheduleEvent.getLocalUserId());
            pstmt.setTimestamp(6, new java.sql.Timestamp((new Date()).getTime()));

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ScheduleEvent Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateScheduleEvent(ScheduleEvent scheduleEvent) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE ScheduleEvent SET title=?, description=?, startDate=?, endDate=?, localUserId=?, createdDate=? WHERE scheduleEventId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, scheduleEvent.getTitle());
            pstmt.setString(2, scheduleEvent.getDescription());
            pstmt.setTimestamp(3, new java.sql.Timestamp(scheduleEvent.getStartDate().getTime()));
            pstmt.setTimestamp(4, new java.sql.Timestamp(scheduleEvent.getEndDate().getTime()));
            pstmt.setLong(5, scheduleEvent.getLocalUserId());
            pstmt.setTimestamp(6, new java.sql.Timestamp(scheduleEvent.getCreatedDate().getTime()));
            pstmt.setLong(7, scheduleEvent.getScheduleEventId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ScheduleEvent Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateScheduleEvent(ScheduleEventVW scheduleEventVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE ScheduleEvent SET title=?, description=?, startDate=?, endDate=?, localUserId=?, createdDate=? WHERE scheduleEventId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, scheduleEventVW.getTitle());
            pstmt.setString(2, scheduleEventVW.getDescription());
            pstmt.setTimestamp(3, new java.sql.Timestamp(scheduleEventVW.getStartDate().getTime()));
            pstmt.setTimestamp(4, new java.sql.Timestamp(scheduleEventVW.getEndDate().getTime()));
            pstmt.setLong(5, scheduleEventVW.getLocalUserId());
            pstmt.setTimestamp(6, new java.sql.Timestamp(scheduleEventVW.getCreatedDate().getTime()));
            pstmt.setLong(7, scheduleEventVW.getScheduleEventId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ScheduleEvent Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteScheduleEvent(int scheduleEventId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM ScheduleEvent WHERE scheduleEventId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, scheduleEventId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("ScheduleEvent deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ScheduleEventDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
