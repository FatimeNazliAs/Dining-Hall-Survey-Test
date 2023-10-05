/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.common.ConnectionFactory;
import dininghall.domain.info.State;
import dininghall.domain.info.StateVW;

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
public class StateDAOImpl implements StateDAO, Serializable {
    private CityDAO cityDAO;

    public StateDAOImpl() {
        cityDAO = new CityDAOImpl();
    }

    @Override
    public List<State> getStateList() {
        List<State> stateList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                State state = new State();
                state.setStateId(resultSet.getInt("state_id"));
                state.setStateCode(resultSet.getString("state_code"));
                state.setEnglishName(resultSet.getString("english_name"));
                state.setNativeName(resultSet.getString("native_name"));
                state.setCityId(resultSet.getInt("city_id"));

                stateList.add(state);
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return stateList;

    }

    public List<State> getStateListByCityId(int cityId) {
        List<State> stateList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state WHERE city_id=? ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, cityId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                State state = new State();
                state.setStateId(resultSet.getInt("state_id"));
                state.setStateCode(resultSet.getString("state_code"));
                state.setEnglishName(resultSet.getString("english_name"));
                state.setNativeName(resultSet.getString("native_name"));
                state.setCityId(resultSet.getInt("city_id"));

                stateList.add(state);
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return stateList;

    }

    @Override
    public List<StateVW> getStateVWList() {
        List<StateVW> stateList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state_view ORDER BY english_name ASC";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                StateVW stateVW = new StateVW();
                stateVW.setStateId(resultSet.getInt("state_id"));
                stateVW.setStateCode(resultSet.getString("state_code"));
                stateVW.setEnglishName(resultSet.getString("english_name"));
                stateVW.setNativeName(resultSet.getString("native_name"));
                stateVW.setCityId(resultSet.getInt("city_id"));
                stateVW.setCity(resultSet.getString("city_english_name"));
                stateVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                stateVW.setSubDivision(resultSet.getString("sub_division_english_name"));
                stateVW.setCountryId(resultSet.getInt("country_id"));
                stateVW.setCountry(resultSet.getString("country_english_name"));
                stateVW.setContinentId(resultSet.getInt("continent_id"));
                stateVW.setContinent(resultSet.getString("continent_english_name"));

                stateList.add(stateVW);
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return stateList;

    }

    @Override
    public State getStateByStateId(int stateId) {
        State state = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state WHERE stateId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, stateId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                state = new State();
                state.setStateId(resultSet.getInt("state_id"));
                state.setStateCode(resultSet.getString("state_code"));
                state.setEnglishName(resultSet.getString("english_name"));
                state.setNativeName(resultSet.getString("native_name"));

                return state;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return state;
    }

    @Override
    public StateVW getStateVWByStateId(int stateId) {
        StateVW stateVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state_view WHERE stateId=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, stateId);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                stateVW = new StateVW();
                stateVW.setStateId(resultSet.getInt("state_id"));
                stateVW.setStateCode(resultSet.getString("state_code"));
                stateVW.setEnglishName(resultSet.getString("english_name"));
                stateVW.setNativeName(resultSet.getString("native_name"));
                stateVW.setCityId(resultSet.getInt("city_id"));
                stateVW.setCity(resultSet.getString("city_english_name"));
                stateVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                stateVW.setSubDivision(resultSet.getString("sub_division_english_name"));
                stateVW.setCountryId(resultSet.getInt("country_id"));
                stateVW.setCountry(resultSet.getString("country_english_name"));
                stateVW.setContinentId(resultSet.getInt("continent_id"));
                stateVW.setContinent(resultSet.getString("continent_english_name"));

                return stateVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return stateVW;
    }

    @Override
    public State getStateByStateCode(String stateCode) {
        State state = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state WHERE stateCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, stateCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                state = new State();
                state.setStateId(resultSet.getInt("state_id"));
                state.setStateCode(resultSet.getString("state_code"));
                state.setEnglishName(resultSet.getString("english_name"));
                state.setNativeName(resultSet.getString("native_name"));

                return state;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return state;
    }

    @Override
    public StateVW getStateVWByStateCode(String stateCode) {
        StateVW stateVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state_view WHERE stateCode=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, stateCode);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                stateVW = new StateVW();
                stateVW.setStateId(resultSet.getInt("state_id"));
                stateVW.setStateCode(resultSet.getString("state_code"));
                stateVW.setEnglishName(resultSet.getString("english_name"));
                stateVW.setNativeName(resultSet.getString("native_name"));
                stateVW.setCityId(resultSet.getInt("city_id"));
                stateVW.setCity(resultSet.getString("city_english_name"));
                stateVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                stateVW.setSubDivision(resultSet.getString("sub_division_english_name"));
                stateVW.setCountryId(resultSet.getInt("country_id"));
                stateVW.setCountry(resultSet.getString("country_english_name"));
                stateVW.setContinentId(resultSet.getInt("continent_id"));
                stateVW.setContinent(resultSet.getString("continent_english_name"));

                return stateVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return stateVW;
    }

    @Override
    public State getStateByEnglishName(String englishName) {
        State state = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                state = new State();
                state.setStateId(resultSet.getInt("state_id"));
                state.setStateCode(resultSet.getString("state_code"));
                state.setEnglishName(resultSet.getString("english_name"));
                state.setNativeName(resultSet.getString("native_name"));

                return state;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return state;
    }

    @Override
    public StateVW getStateVWByEnglishName(String englishName) {
        StateVW stateVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state_view WHERE englishName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, englishName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                stateVW = new StateVW();
                stateVW.setStateId(resultSet.getInt("state_id"));
                stateVW.setStateCode(resultSet.getString("state_code"));
                stateVW.setEnglishName(resultSet.getString("english_name"));
                stateVW.setNativeName(resultSet.getString("native_name"));
                stateVW.setCityId(resultSet.getInt("city_id"));
                stateVW.setCity(resultSet.getString("city_english_name"));
                stateVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                stateVW.setSubDivision(resultSet.getString("sub_division_english_name"));
                stateVW.setCountryId(resultSet.getInt("country_id"));
                stateVW.setCountry(resultSet.getString("country_english_name"));
                stateVW.setContinentId(resultSet.getInt("continent_id"));
                stateVW.setContinent(resultSet.getString("continent_english_name"));

                return stateVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return stateVW;
    }

    @Override
    public State getStateByNativeName(String nativeName) {
        State state = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                state = new State();
                state.setStateId(resultSet.getInt("state_id"));
                state.setStateCode(resultSet.getString("state_code"));
                state.setEnglishName(resultSet.getString("english_name"));
                state.setNativeName(resultSet.getString("native_name"));

                return state;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return state;
    }

    @Override
    public StateVW getStateVWByNativeName(String nativeName) {
        StateVW stateVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM state_view WHERE nativeName=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, nativeName);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                stateVW = new StateVW();
                stateVW.setStateId(resultSet.getInt("state_id"));
                stateVW.setStateCode(resultSet.getString("state_code"));
                stateVW.setEnglishName(resultSet.getString("english_name"));
                stateVW.setNativeName(resultSet.getString("native_name"));
                stateVW.setCityId(resultSet.getInt("city_id"));
                stateVW.setCity(resultSet.getString("city_english_name"));
                stateVW.setSubDivisionId(resultSet.getInt("sub_division_id"));
                stateVW.setSubDivision(resultSet.getString("sub_division_english_name"));
                stateVW.setCountryId(resultSet.getInt("country_id"));
                stateVW.setCountry(resultSet.getString("country_english_name"));
                stateVW.setContinentId(resultSet.getInt("continent_id"));
                stateVW.setContinent(resultSet.getString("continent_english_name"));

                return stateVW;
            }

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return stateVW;
    }

    @Override
    public boolean addState(State state) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "INSERT INTO State(stateCode, englishName, nativeName, cityId) VALUES(?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, state.getStateCode());
            pstmt.setString(2, state.getEnglishName());
            pstmt.setString(3, state.getNativeName());
            pstmt.setInt(4, state.getCityId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("State Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateState(State state) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE State SET stateCode=?, englishName=?, nativeName=?, city_id=? WHERE stateId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, state.getStateCode());
            pstmt.setString(2, state.getEnglishName());
            pstmt.setString(3, state.getNativeName());
            pstmt.setInt(4, state.getCityId());
            pstmt.setInt(5, state.getStateId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("State Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean updateState(StateVW stateVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE State SET stateCode=?, englishName=?, nativeName=?, city_id=? WHERE stateId=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, stateVW.getStateCode());
            pstmt.setString(2, stateVW.getEnglishName());
            pstmt.setString(3, stateVW.getNativeName());
            pstmt.setInt(4, stateVW.getCityId());
            pstmt.setInt(5, stateVW.getStateId());

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("State Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteState(int stateId) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM state WHERE stateId=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, stateId);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("State deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(StateDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

}
