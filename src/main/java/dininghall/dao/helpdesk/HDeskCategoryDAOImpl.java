/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.common.ConnectionFactory;
import dininghall.domain.helpdesk.HDeskCategory;
import dininghall.domain.helpdesk.HDeskCategoryVW;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tolga
 */
public class HDeskCategoryDAOImpl implements HDeskCategoryDAO, Serializable {

    @Override
    public List<HDeskCategory> getHDeskCategoryList() {
        List<HDeskCategory> hDeskCategoryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCategory ORDER BY parentCatId, [name]";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                HDeskCategory hDeskCategory = new HDeskCategory();
                hDeskCategory.setCatId(resultSet.getInt("catId"));
                hDeskCategory.setName(resultSet.getString("name"));
                hDeskCategory.setDescription(resultSet.getString("description"));
                hDeskCategory.setParentCatId(resultSet.getInt("parentCatId"));

                hDeskCategoryList.add(hDeskCategory);
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCategoryList;

    }

    @Override
    public List<HDeskCategoryVW> getHDeskCategoryVWList() {
        List<HDeskCategoryVW> hDeskCategoryVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCategoryView ORDER BY catId, catName";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                HDeskCategoryVW hDeskCategoryVW = new HDeskCategoryVW();
                hDeskCategoryVW.setCatId(resultSet.getInt("catId"));
                hDeskCategoryVW.setCatName(resultSet.getString("catName"));
                hDeskCategoryVW.setCatDesc(resultSet.getString("catDesc"));
                hDeskCategoryVW.setParentCatId(resultSet.getInt("parentCatId"));
                hDeskCategoryVW.setParentCatName(resultSet.getString("parentCatName"));
                hDeskCategoryVW.setParentCatDesc(resultSet.getString("parentCatDesc"));

                hDeskCategoryVWList.add(hDeskCategoryVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCategoryVWList;

    }

    @Override
    public List<HDeskCategory> getHDeskCategorySubListByParentCatId(int parentCatId) {
        List<HDeskCategory> hDeskCategoryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCategory WHERE parentCatId=?";
            connection = ConnectionFactory.getInstance().getConnection();

            if (parentCatId != 0) {
                pstmt = connection.prepareStatement(queryString);
                pstmt.setInt(1, parentCatId);
            } else {
                queryString = "SELECT * FROM HDeskCategory WHERE parentCatId IS NULl";
                pstmt = connection.prepareStatement(queryString);
            }

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                HDeskCategory hDeskCategory = new HDeskCategory();
                hDeskCategory.setCatId(resultSet.getInt("catId"));
                hDeskCategory.setName(resultSet.getString("name"));
                hDeskCategory.setParentCatId(resultSet.getInt("parentCatId"));
                hDeskCategory.setDescription(resultSet.getString("description"));

                hDeskCategoryList.add(hDeskCategory);
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCategoryList;

    }

    @Override
    public HDeskCategory getHDeskCategoryByHDeskCategoryId(int catId) {
        HDeskCategory hDeskCategory = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCategory WHERE catId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, catId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskCategory = new HDeskCategory();
                hDeskCategory.setCatId(resultSet.getInt("catId"));
                hDeskCategory.setName(resultSet.getString("name"));
                hDeskCategory.setParentCatId(resultSet.getInt("parentCatId"));
                hDeskCategory.setDescription(resultSet.getString("description"));

                return hDeskCategory;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCategory;
    }

    @Override
    public HDeskCategory getHDeskCategoryByName(String name) {
        HDeskCategory hDeskCategory = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM HDeskCategory WHERE name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, name);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                hDeskCategory = new HDeskCategory();
                hDeskCategory.setCatId(resultSet.getInt("catId"));
                hDeskCategory.setName(resultSet.getString("name"));
                hDeskCategory.setParentCatId(resultSet.getInt("parentCatId"));
                hDeskCategory.setDescription(resultSet.getString("description"));

                return hDeskCategory;
            }

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return hDeskCategory;
    }

    @Override
    public boolean addHDeskCategory(HDeskCategory hDeskCategory) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO HDeskCategory([name], parentCatId, [description]) VALUES(?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskCategory.getName());
            if (hDeskCategory.getParentCatId() != 0) {
                pstmt.setInt(2, hDeskCategory.getParentCatId());
            } else {
                pstmt.setNull(2, Types.DECIMAL);
            }
            pstmt.setString(3, hDeskCategory.getDescription());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCategory Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateHDeskCategory(HDeskCategory hDeskCategory) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskCategory SET name=?, parentId=?, description=? WHERE catId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskCategory.getName());
            pstmt.setInt(2, hDeskCategory.getParentCatId());
            pstmt.setString(3, hDeskCategory.getDescription());
            pstmt.setLong(4, hDeskCategory.getCatId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCategory Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateHDeskCategory(HDeskCategoryVW hDeskCategoryVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE HDeskCategory SET [name]=?, parentCatId=?, [description]=? WHERE catId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, hDeskCategoryVW.getCatName());
            pstmt.setInt(2, hDeskCategoryVW.getParentCatId());
            pstmt.setString(3, hDeskCategoryVW.getCatDesc());
            pstmt.setInt(4, hDeskCategoryVW.getCatId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCategory Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }


    @Override
    public boolean deleteHDeskCategory(int catId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM HDeskCategory WHERE catId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, catId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("HDeskCategory deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(HDeskCategoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
