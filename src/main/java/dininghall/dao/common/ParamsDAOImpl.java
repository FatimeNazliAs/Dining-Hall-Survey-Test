/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.common.ParamsVW;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.common.ConnectionFactory;
import dininghall.domain.common.Params;

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
public class ParamsDAOImpl implements ParamsDAO, Serializable {

    @Override
    public List<Params> getParamsList() {
        List<Params> paramsList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM params";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Params params = new Params();
                params.setParamId(resultSet.getInt("param_id"));
                params.setParamName(resultSet.getString("param_name"));
                params.setParamValue(resultSet.getString("param_value"));
                params.setParamDesc(resultSet.getString("param_desc"));

                paramsList.add(params);
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

        return paramsList;

    }

    @Override
    public List<ParamsVW> getParamsVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<ParamsVW> paramsVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY param_name ASC ) as row " +
                    "FROM params_view WHERE 1=1 ";


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
                ParamsVW paramsVW = new ParamsVW();
                paramsVW.setParamId(resultSet.getInt("param_id"));
                paramsVW.setParamName(resultSet.getString("param_name"));
                paramsVW.setParamValue(resultSet.getString("param_value"));
                paramsVW.setParamDesc(resultSet.getString("param_desc"));

                paramsVWList.add(paramsVW);
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

        return paramsVWList;
    }

    @Override
    public Params getParamsByParamsId(int params_id) {
        Params params = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM params WHERE param_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, params_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                params = new Params();
                params.setParamId(resultSet.getInt("param_id"));
                params.setParamName(resultSet.getString("param_name"));
                params.setParamValue(resultSet.getString("param_value"));
                params.setParamDesc(resultSet.getString("param_desc"));

                return params;
            }

        } catch (SQLException e) {
            Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return params;
    }

    @Override
    public Params getParamsByParamName(String paramName) {
        Params params = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM params WHERE param_name=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, paramName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                params = new Params();
                params.setParamId(resultSet.getInt("param_id"));
                params.setParamName(resultSet.getString("param_name"));
                params.setParamValue(resultSet.getString("param_value"));
                params.setParamDesc(resultSet.getString("param_desc"));

                return params;
            }

        } catch (SQLException e) {
            Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return params;
    }

    @Override
    public boolean addParams(Params params) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO params (param_name, param_value, param_desc) VALUES(?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, params.getParamName());
            pstmt.setString(2, params.getParamValue());
            pstmt.setString(3, params.getParamDesc());


            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Params Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateParams(Params params) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE params SET param_name=?, param_value=?, param_desc=? WHERE param_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, params.getParamName());
            pstmt.setString(2, params.getParamValue());
            pstmt.setString(3, params.getParamDesc());
            pstmt.setInt(4, params.getParamId());


            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Params Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateParams(ParamsVW paramsVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE params SET param_name=?, param_value=?, param_desc=? WHERE param_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, paramsVW.getParamName());
            pstmt.setString(2, paramsVW.getParamValue());
            pstmt.setString(3, paramsVW.getParamDesc());
            pstmt.setInt(4, paramsVW.getParamId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Params Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteParams(int params_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM params WHERE param_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, params_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Params deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public int getParamsCount(Map<String, Object> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int paramsCount = 0;

        try {
            String queryString = "SELECT COUNT(param_id) as paramsCount FROM params_view WHERE 1=1 ";
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
                paramsCount = resultSet.getInt("paramsCount");
            }

        } catch (SQLException e) {
            Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(ParamsDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return paramsCount;
    }

}
