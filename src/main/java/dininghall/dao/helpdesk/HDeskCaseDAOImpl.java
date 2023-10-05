/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.dao.user.LocalUserDAOImpl;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskCase;
import dininghall.domain.helpdesk.HDeskCaseVW;

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
public class HDeskCaseDAOImpl implements HDeskCaseDAO, Serializable {

    @Override
    public List<HDeskCase> getHDeskCaseList() {
        List<HDeskCase> hDeskCaseList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCase ORDER BY createdTime DESC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                HDeskCase hDeskCase = new HDeskCase();
                hDeskCase.setCaseId(resultSet.getLong("caseId"));
                hDeskCase.setSubject(resultSet.getString("subject"));
                hDeskCase.setDescription(resultSet.getString("description"));
                hDeskCase.setCatId(resultSet.getInt("catId"));
                hDeskCase.setSubCatId(resultSet.getInt("subCatId"));
                hDeskCase.setCreatorUserId(resultSet.getLong("creatorUserId"));
                hDeskCase.setAssignedUserId(resultSet.getLong("assignedUserId"));
                hDeskCase.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskCase.setStatusId(resultSet.getInt("statusId"));
                hDeskCase.setPriorityId(resultSet.getInt("priorityId"));
                hDeskCase.setImpactId(resultSet.getInt("impactId"));
                hDeskCase.setResolution(resultSet.getString("resolution"));
                hDeskCase.setScoreId(resultSet.getInt("scoreId"));
                hDeskCase.setCreatedTime(resultSet.getDate("createdTime"));
                hDeskCase.setLastModifiedByUserId(resultSet.getLong("lastModifiedByUserId"));
                hDeskCase.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));

                hDeskCaseList.add(hDeskCase);
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

        return hDeskCaseList;

    }

    @Override
    public List<HDeskCaseVW> getHDeskCaseVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<HDeskCaseVW> hDeskCaseVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY [createdTime] DESC) as row " +
                    "FROM HDeskCaseView WHERE 1=1 ";


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
                HDeskCaseVW hDeskCaseVW = new HDeskCaseVW();
                hDeskCaseVW.setCaseId(resultSet.getLong("caseId"));
                hDeskCaseVW.setSubject(resultSet.getString("subject"));
                hDeskCaseVW.setDescription(resultSet.getString("description"));
                hDeskCaseVW.setCatId(resultSet.getInt("categoryId"));
                hDeskCaseVW.setCategory(resultSet.getString("category"));
                hDeskCaseVW.setSubCatId(resultSet.getInt("subCategoryId"));
                hDeskCaseVW.setSubCategory(resultSet.getString("subCategory"));
                hDeskCaseVW.setCreatorUserId(resultSet.getLong("creatorUserId"));
                hDeskCaseVW.setCreatorUser(resultSet.getString("creatorUserFirstName") + " " + resultSet.getString("creatorUserLastName"));
                hDeskCaseVW.setAssignedUserId(resultSet.getLong("assignedUserId"));
                hDeskCaseVW.setAssignedUser(resultSet.getString("assignedUserFirstName") + " " + resultSet.getString("assignedUserLastName"));
                hDeskCaseVW.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskCaseVW.setReportedVia(resultSet.getString("reportedVia"));
                hDeskCaseVW.setStatusId(resultSet.getInt("statusId"));
                hDeskCaseVW.setStatus(resultSet.getString("status"));
                hDeskCaseVW.setPriorityId(resultSet.getInt("priorityId"));
                hDeskCaseVW.setPriority(resultSet.getString("priority"));
                hDeskCaseVW.setImpactId(resultSet.getInt("impactId"));
                hDeskCaseVW.setResolution(resultSet.getString("resolution"));
                hDeskCaseVW.setImpact(resultSet.getString("impact"));
                hDeskCaseVW.setScoreId(resultSet.getInt("scoreId"));
                hDeskCaseVW.setScore(resultSet.getString("score"));
                hDeskCaseVW.setCreatedTime(resultSet.getDate("createdTime"));
                hDeskCaseVW.setCreatedTimeStr(resultSet.getString("createdTimeStr"));
                hDeskCaseVW.setLastModifiedByUserId(resultSet.getLong("lastModifiedByUserId"));
                hDeskCaseVW.setLastModifiedByUser(resultSet.getString("lastModifiedByUserFirstName") + " " + resultSet.getString("lastModifiedByUserLastName"));
                hDeskCaseVW.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));
                hDeskCaseVW.setLastModifiedTimeStr(resultSet.getString("lastModifiedTimeStr"));

                hDeskCaseVWList.add(hDeskCaseVW);
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

        return hDeskCaseVWList;
    }

    @Override
    public List<HDeskCaseVW> getHDeskCaseAssignedVWListByLocalUserId(long localUserId) {
        List<HDeskCaseVW> hDeskCaseList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCaseView WHERE ([status] != 'CLOSED' OR [status] IS NULL) AND assignedUserId=? ORDER BY createdTime DESC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, localUserId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                HDeskCaseVW hDeskCaseVW = new HDeskCaseVW();
                hDeskCaseVW.setCaseId(resultSet.getLong("caseId"));
                hDeskCaseVW.setSubject(resultSet.getString("subject"));
                hDeskCaseVW.setDescription(resultSet.getString("description"));
                hDeskCaseVW.setCatId(resultSet.getInt("categoryId"));
                hDeskCaseVW.setCategory(resultSet.getString("category"));
                hDeskCaseVW.setSubCatId(resultSet.getInt("subCategoryId"));
                hDeskCaseVW.setSubCategory(resultSet.getString("subCategory"));
                hDeskCaseVW.setCreatorUserId(resultSet.getLong("creatorUserId"));
                hDeskCaseVW.setCreatorUser(resultSet.getString("creatorUserFirstName") + " " + resultSet.getString("creatorUserLastName"));
                hDeskCaseVW.setAssignedUserId(resultSet.getLong("assignedUserId"));
                hDeskCaseVW.setAssignedUser(resultSet.getString("assignedUserFirstName") + " " + resultSet.getString("assignedUserLastName"));
                hDeskCaseVW.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskCaseVW.setReportedVia(resultSet.getString("reportedVia"));
                hDeskCaseVW.setStatusId(resultSet.getInt("statusId"));
                hDeskCaseVW.setStatus(resultSet.getString("status"));
                hDeskCaseVW.setPriorityId(resultSet.getInt("priorityId"));
                hDeskCaseVW.setPriority(resultSet.getString("priority"));
                hDeskCaseVW.setImpactId(resultSet.getInt("impactId"));
                hDeskCaseVW.setResolution(resultSet.getString("resolution"));
                hDeskCaseVW.setImpact(resultSet.getString("impact"));
                hDeskCaseVW.setScoreId(resultSet.getInt("scoreId"));
                hDeskCaseVW.setScore(resultSet.getString("score"));
                hDeskCaseVW.setCreatedTime(resultSet.getDate("createdTime"));
                hDeskCaseVW.setCreatedTimeStr(resultSet.getString("createdTimeStr"));
                hDeskCaseVW.setLastModifiedByUserId(resultSet.getLong("lastModifiedByUserId"));
                hDeskCaseVW.setLastModifiedByUser(resultSet.getString("lastModifiedByUserFirstName") + " " + resultSet.getString("lastModifiedByUserLastName"));
                hDeskCaseVW.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));
                hDeskCaseVW.setLastModifiedTimeStr(resultSet.getString("lastModifiedTimeStr"));

                hDeskCaseList.add(hDeskCaseVW);
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

        return hDeskCaseList;

    }

    @Override
    public HDeskCase getHDeskCaseByHDeskCaseId(long caseId) {
        HDeskCase hDeskCase = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCase WHERE caseId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, caseId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskCase = new HDeskCase();
                hDeskCase.setCaseId(resultSet.getLong("caseId"));
                hDeskCase.setSubject(resultSet.getString("subject"));
                hDeskCase.setDescription(resultSet.getString("description"));
                hDeskCase.setCatId(resultSet.getInt("catId"));
                hDeskCase.setSubCatId(resultSet.getInt("subCatId"));
                hDeskCase.setCreatorUserId(resultSet.getLong("creatorUserId"));
                hDeskCase.setAssignedUserId(resultSet.getLong("assignedUserId"));
                hDeskCase.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskCase.setStatusId(resultSet.getInt("statusId"));
                hDeskCase.setPriorityId(resultSet.getInt("priorityId"));
                hDeskCase.setImpactId(resultSet.getInt("impactId"));
                hDeskCase.setResolution(resultSet.getString("resolution"));
                hDeskCase.setScoreId(resultSet.getInt("scoreId"));
                hDeskCase.setCreatedTime(resultSet.getDate("createdTime"));
                hDeskCase.setLastModifiedByUserId(resultSet.getLong("lastModifiedByUserId"));
                hDeskCase.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));

                return hDeskCase;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCase;
    }

    @Override
    public HDeskCaseVW getHDeskCaseVWByHDeskCaseId(long caseId) {
        HDeskCaseVW hDeskCaseVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCaseView WHERE caseId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, caseId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskCaseVW = new HDeskCaseVW();
                hDeskCaseVW.setCaseId(resultSet.getLong("caseId"));
                hDeskCaseVW.setSubject(resultSet.getString("subject"));
                hDeskCaseVW.setDescription(resultSet.getString("description"));
                hDeskCaseVW.setCatId(resultSet.getInt("categoryId"));
                hDeskCaseVW.setCategory(resultSet.getString("category"));
                hDeskCaseVW.setSubCatId(resultSet.getInt("subCategoryId"));
                hDeskCaseVW.setSubCategory(resultSet.getString("subCategory"));
                hDeskCaseVW.setCreatorUserId(resultSet.getLong("creatorUserId"));
                hDeskCaseVW.setCreatorUser(resultSet.getString("creatorUserFirstName") + " " + resultSet.getString("creatorUserLastName"));
                hDeskCaseVW.setAssignedUserId(resultSet.getLong("assignedUserId"));
                hDeskCaseVW.setAssignedUser(resultSet.getString("assignedUserFirstName") + " " + resultSet.getString("assignedUserLastName"));
                hDeskCaseVW.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskCaseVW.setReportedVia(resultSet.getString("reportedVia"));
                hDeskCaseVW.setStatusId(resultSet.getInt("statusId"));
                hDeskCaseVW.setStatus(resultSet.getString("status"));
                hDeskCaseVW.setPriorityId(resultSet.getInt("priorityId"));
                hDeskCaseVW.setPriority(resultSet.getString("priority"));
                hDeskCaseVW.setImpactId(resultSet.getInt("impactId"));
                hDeskCaseVW.setResolution(resultSet.getString("resolution"));
                hDeskCaseVW.setImpact(resultSet.getString("impact"));
                hDeskCaseVW.setScoreId(resultSet.getInt("scoreId"));
                hDeskCaseVW.setScore(resultSet.getString("score"));
                hDeskCaseVW.setCreatedTime(resultSet.getDate("createdTime"));
                hDeskCaseVW.setCreatedTimeStr(resultSet.getString("createdTimeStr"));
                hDeskCaseVW.setLastModifiedByUserId(resultSet.getLong("lastModifiedByUserId"));
                hDeskCaseVW.setLastModifiedByUser(resultSet.getString("lastModifiedByUserFirstName") + " " + resultSet.getString("lastModifiedByUserLastName"));
                hDeskCaseVW.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));
                hDeskCaseVW.setLastModifiedTimeStr(resultSet.getString("lastModifiedTimeStr"));

                return hDeskCaseVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCaseVW;
    }

    @Override
    public HDeskCase getHDeskCaseByName(String name) {
        HDeskCase hDeskCase = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCase WHERE name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskCase = new HDeskCase();
                hDeskCase.setCaseId(resultSet.getLong("caseId"));
                hDeskCase.setSubject(resultSet.getString("subject"));
                hDeskCase.setDescription(resultSet.getString("description"));
                hDeskCase.setCatId(resultSet.getInt("catId"));
                hDeskCase.setSubCatId(resultSet.getInt("subCatId"));
                hDeskCase.setCreatorUserId(resultSet.getLong("creatorUserId"));
                hDeskCase.setAssignedUserId(resultSet.getLong("assignedUserId"));
                hDeskCase.setReportedViaId(resultSet.getInt("reportedViaId"));
                hDeskCase.setStatusId(resultSet.getInt("statusId"));
                hDeskCase.setPriorityId(resultSet.getInt("priorityId"));
                hDeskCase.setImpactId(resultSet.getInt("impactId"));
                hDeskCase.setResolution(resultSet.getString("resolution"));
                hDeskCase.setScoreId(resultSet.getInt("scoreId"));
                hDeskCase.setCreatedTime(resultSet.getDate("createdTime"));
                hDeskCase.setLastModifiedByUserId(resultSet.getLong("lastModifiedByUserId"));
                hDeskCase.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));

                return hDeskCase;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCase;
    }

    @Override
    public boolean addHDeskCase(HDeskCase hDeskCase) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp createdTime = new java.sql.Timestamp(utilDate.getTime());

            String queryString = "INSERT INTO HDeskCase(subject, description, catId, subCatId, creatorUserId, " +
                    "reportedViaId, priorityId, impactId,  " +
                    "createdTime) VALUES(?,?,?,?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskCase.getSubject());
            pstmt.setString(2, hDeskCase.getDescription());
            pstmt.setInt(3, hDeskCase.getCatId());
            pstmt.setInt(4, hDeskCase.getSubCatId());
            pstmt.setLong(5, hDeskCase.getCreatorUserId());
            pstmt.setInt(6, hDeskCase.getReportedViaId());
            pstmt.setInt(7, hDeskCase.getPriorityId());
            pstmt.setInt(8, hDeskCase.getImpactId());
            pstmt.setTimestamp(9, createdTime);


            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCase Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public void updateHDeskCase(HDeskCase hDeskCase) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp lastModifiedTime = new java.sql.Timestamp(utilDate.getTime());


            String queryString = "UPDATE HDeskCase SET subject=?, description=?, catId=?, subCatId=?, creatorUserId=?, assignedUserId=?, " +
                    "reportedViaId=?, statusId=?, priorityId=?, impactId=?, resolution=?, scoreId=?, createdTime=?, lastModifiedByUserId=?, lastModifiedTime=? " +
                    "WHERE caseId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskCase.getSubject());
            pstmt.setString(2, hDeskCase.getDescription());
            pstmt.setInt(3, hDeskCase.getCatId());
            pstmt.setInt(4, hDeskCase.getSubCatId());
            pstmt.setLong(5, hDeskCase.getCreatorUserId());

            if (hDeskCase.getAssignedUserId() == 0) {
                pstmt.setNull(6, Types.DECIMAL);
            } else {
                pstmt.setLong(6, hDeskCase.getAssignedUserId());
            }

            if (hDeskCase.getReportedViaId() == 0) {
                pstmt.setNull(7, Types.DECIMAL);
            } else {
                pstmt.setLong(7, hDeskCase.getReportedViaId());
            }

            if (hDeskCase.getStatusId() == 0) {
                pstmt.setNull(8, Types.DECIMAL);
            } else {
                pstmt.setLong(8, hDeskCase.getStatusId());
            }

            if (hDeskCase.getPriorityId() == 0) {
                pstmt.setNull(9, Types.DECIMAL);
            } else {
                pstmt.setLong(9, hDeskCase.getPriorityId());
            }

            if (hDeskCase.getImpactId() == 0) {
                pstmt.setNull(10, Types.DECIMAL);
            } else {
                pstmt.setLong(10, hDeskCase.getImpactId());
            }

            pstmt.setString(11, hDeskCase.getResolution());

            if (hDeskCase.getScoreId() == 0) {
                pstmt.setNull(12, Types.DECIMAL);
            } else {
                pstmt.setLong(12, hDeskCase.getScoreId());
            }

            pstmt.setTimestamp(13, new java.sql.Timestamp(hDeskCase.getCreatedTime().getTime()));

            if (hDeskCase.getLastModifiedByUserId() == 0) {
                pstmt.setNull(14, Types.DECIMAL);
            } else {
                pstmt.setLong(14, hDeskCase.getLastModifiedByUserId());
            }

            pstmt.setTimestamp(15, lastModifiedTime);
            pstmt.setLong(16, hDeskCase.getCaseId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCase Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }
    }

    @Override
    public boolean updateHDeskCase(HDeskCaseVW hDeskCaseVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp lastModifiedTime = new java.sql.Timestamp(utilDate.getTime());


            String queryString = "UPDATE HDeskCase SET subject=?, description=?, catId=?, subCatId=?, creatorUserId=?, assignedUserId=?, " +
                    "reportedViaId=?, statusId=?, priorityId=?, impactId=?, resolution=?, scoreId=?, createdTime=?, lastModifiedByUserId=?, lastModifiedTime=? " +
                    "WHERE caseId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskCaseVW.getSubject());
            pstmt.setString(2, hDeskCaseVW.getDescription());
            pstmt.setInt(3, hDeskCaseVW.getCatId());
            pstmt.setInt(4, hDeskCaseVW.getSubCatId());
            pstmt.setLong(5, hDeskCaseVW.getCreatorUserId());
            if (hDeskCaseVW.getAssignedUserId() == 0) {
                pstmt.setNull(6, Types.DECIMAL);
            } else {
                pstmt.setLong(6, hDeskCaseVW.getAssignedUserId());
            }

            if (hDeskCaseVW.getReportedViaId() == 0) {
                pstmt.setNull(7, Types.DECIMAL);
            } else {
                pstmt.setLong(7, hDeskCaseVW.getReportedViaId());
            }

            if (hDeskCaseVW.getStatusId() == 0) {
                pstmt.setNull(8, Types.DECIMAL);
            } else {
                pstmt.setLong(8, hDeskCaseVW.getStatusId());
            }

            if (hDeskCaseVW.getPriorityId() == 0) {
                pstmt.setNull(9, Types.DECIMAL);
            } else {
                pstmt.setLong(9, hDeskCaseVW.getPriorityId());
            }

            if (hDeskCaseVW.getImpactId() == 0) {
                pstmt.setNull(10, Types.DECIMAL);
            } else {
                pstmt.setLong(10, hDeskCaseVW.getImpactId());
            }

            pstmt.setString(11, hDeskCaseVW.getResolution());

            if (hDeskCaseVW.getScoreId() == 0) {
                pstmt.setNull(12, Types.DECIMAL);
            } else {
                pstmt.setLong(12, hDeskCaseVW.getScoreId());
            }

            pstmt.setTimestamp(13, new java.sql.Timestamp(hDeskCaseVW.getCreatedTime().getTime()));

            if (hDeskCaseVW.getLastModifiedByUserId() == 0) {
                pstmt.setNull(14, Types.DECIMAL);
            } else {
                pstmt.setLong(14, hDeskCaseVW.getLastModifiedByUserId());
            }

            pstmt.setTimestamp(15, lastModifiedTime);
            pstmt.setLong(16, hDeskCaseVW.getCaseId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCase Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteHDeskCase(long caseId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskCase WHERE caseId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, caseId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCase deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }


    @Override
    public int getHDeskCaseCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int hDeskCaseCount = 0;

        try {
            String queryString = "SELECT COUNT(caseId) as hDeskCaseCount FROM HDeskCaseView WHERE 1=1  ";
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
                hDeskCaseCount = resultSet.getInt("hDeskCaseCount");
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCaseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCaseCount;
    }

}
