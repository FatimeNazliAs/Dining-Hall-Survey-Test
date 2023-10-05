/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.user;

import dininghall.domain.user.*;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dininghall.common.ConnectionFactory;

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

/**
 *
 * @author Tolga
 */
public class LocalUserDAOImpl implements LocalUserDAO, Serializable {

    private final LocalUserRoleDAO localUserRoleDAO;

    public LocalUserDAOImpl() {
        localUserRoleDAO = new LocalUserRoleDAOImpl();
    }

    @Override
    public List<LocalUser> getLocalUserList() {
        List<LocalUser> localUserList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                LocalUser localUser = new LocalUser();
                localUser.setLocalUserId(resultSet.getInt("local_user_id"));
                localUser.setFirstName(resultSet.getString("first_name"));
                localUser.setLastName(resultSet.getString("last_name"));
                localUser.setUsername(resultSet.getString("username"));
                localUser.setPassword(resultSet.getString("password"));
                localUser.setEmail(resultSet.getString("email"));
                localUser.setPhoneNumber(resultSet.getString("phone_number"));
                localUser.setGenderId(resultSet.getInt("gender_id"));
                localUser.setEnabled(resultSet.getString("enabled"));
                localUser.setAccessToken(resultSet.getString("access_token"));


                localUserList.add(localUser);
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

        return localUserList;

    }

    @Override
    public List<LocalUserVW> getLocalUserVWList(int start, int end, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<LocalUserVW> localUserList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY first_name ASC, last_name ASC) as row FROM local_user_view WHERE 1=1 ";

            connection = ConnectionFactory.getInstance().getConnection();

            String globalFilter = "";
            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    FilterMeta filterValue = filters.get(filterProperty);

                    if (!filterProperty.equals("globalFilter")) {
                        switch (filterProperty) {
                            case "first_name":
                            case "last_name":
                            case "email":
                            case "username":
                            case "phone_number":
                                queryString += " AND " + filterProperty + " LIKE N'%" + filterValue.getFilterValue() + "%'";
                                break;
                        }

                    } else {
                        globalFilter = " AND ( (first_name LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (last_name LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (email LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (username LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (phone_number LIKE N'%" + filterValue.getFilterValue() + "%')";

                        globalFilter += " )";

                        queryString += globalFilter;
                    }

                }

            }

            queryString += ") a WHERE row>? AND row<=?";

            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                LocalUserVW localUserVW = new LocalUserVW();
                localUserVW.setLocalUserId(resultSet.getInt("local_user_id"));
                localUserVW.setFirstName(resultSet.getString("first_name"));
                localUserVW.setLastName(resultSet.getString("last_name"));
                localUserVW.setUsername(resultSet.getString("username"));
                localUserVW.setPassword(resultSet.getString("password"));
                localUserVW.setEmail(resultSet.getString("email"));
                localUserVW.setPhoneNumber(resultSet.getString("phone_number"));
                localUserVW.setGenderId(resultSet.getInt("gender_id"));
                localUserVW.setGender(resultSet.getString("gender"));
                localUserVW.setEnabled(resultSet.getString("enabled"));
                localUserVW.setAccessToken(resultSet.getString("access_token"));
                localUserVW.setRegisteredDateStr(resultSet.getString("registered_datestr"));
                localUserVW.setLastLoginDateStr(resultSet.getString("last_login_datestr"));
                localUserVW.setLocalUserRole(localUserRoleDAO.getLocalUserRoleByUserId(localUserVW.getLocalUserId()));
                localUserVW.setRole(resultSet.getString("local_role"));
                localUserVW.setLocalRoleId(resultSet.getInt("local_role_id"));

                localUserList.add(localUserVW);
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

        return localUserList;

    }

    @Override
    public LocalUser loginLocalUser(String email, String password) {
        LocalUser localUser = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String queryString = "SELECT * FROM local_user WHERE email=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, email);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                if (encoder.matches(password, resultSet.getString("password"))) {
                    localUser = new LocalUser();
                    localUser.setLocalUserId(resultSet.getLong("local_user_id"));
                    localUser.setFirstName(resultSet.getString("first_name"));
                    localUser.setLastName(resultSet.getString("last_name"));
                    localUser.setGenderId(resultSet.getInt("gender_id"));
                    localUser.setUsername(resultSet.getString("username"));
                    localUser.setEmail(resultSet.getString("email"));
                    localUser.setPhoneNumber(resultSet.getString("phone_number"));
                    localUser.setImageName(resultSet.getString("image_name"));
                    localUser.setImagePath(resultSet.getString("image_path"));
                    localUser.setEnabled(resultSet.getString("enabled"));
                    localUser.setAccessToken(resultSet.getString("access_token"));

                    return localUser;
                }

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

        return localUser;
    }

    @Override
    public LocalUser getLocalUserByLocalUserId(long local_user_id) {
        LocalUser localUser = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user WHERE local_user_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                localUser = new LocalUser();
                localUser.setLocalUserId(resultSet.getLong("local_user_id"));
                localUser.setFirstName(resultSet.getString("first_name"));
                localUser.setLastName(resultSet.getString("last_name"));
                localUser.setGenderId(resultSet.getInt("gender_id"));
                localUser.setUsername(resultSet.getString("username"));
                localUser.setEmail(resultSet.getString("email"));
                localUser.setPhoneNumber(resultSet.getString("phone_number"));
                localUser.setImageName(resultSet.getString("image_name"));
                localUser.setImagePath(resultSet.getString("image_path"));
                localUser.setEnabled(resultSet.getString("enabled"));
                localUser.setAccessToken(resultSet.getString("access_token"));
                localUser.setLocalUserRole(localUserRoleDAO.getLocalUserRoleByUserId(localUser.getLocalUserId()));

                return localUser;
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

        return localUser;
    }

    @Override
    public LocalUserVW getLocalUserVWById(long userId) {
        LocalUserVW localUserVW = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user_view WHERE local_user_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, userId);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                localUserVW = new LocalUserVW();
                localUserVW.setLocalUserId(resultSet.getLong("local_user_id"));
                localUserVW.setFirstName(resultSet.getString("first_name"));
                localUserVW.setLastName(resultSet.getString("last_name"));
                localUserVW.setGenderId(resultSet.getInt("gender_id"));
                localUserVW.setUsername(resultSet.getString("username"));
                localUserVW.setEmail(resultSet.getString("email"));
                localUserVW.setPhoneNumber(resultSet.getString("phone_number"));
                localUserVW.setImageName(resultSet.getString("image_name"));
                localUserVW.setImagePath(resultSet.getString("image_path"));
                localUserVW.setEnabled(resultSet.getString("enabled"));
                localUserVW.setAccessToken(resultSet.getString("access_token"));
                localUserVW.setRegisteredDate(resultSet.getDate("registered_date"));
                localUserVW.setLastLoginDate(resultSet.getDate("last_login_date"));
                localUserVW.setLastUpdate(resultSet.getDate("last_update"));

                localUserVW.setRegisteredDateStr(resultSet.getString("registered_datestr"));
                localUserVW.setLastLoginDateStr(resultSet.getString("last_login_datestr"));
                localUserVW.setLastUpdateStr(resultSet.getString("last_updatestr"));

                localUserVW.setLocalUserRole(localUserRoleDAO.getLocalUserRoleByUserId(localUserVW.getLocalUserId()));

                localUserVW.setLocalRoleId(resultSet.getInt("local_role_id"));


                return localUserVW;
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

        return localUserVW;
    }

    @Override
    public LocalUser getLocalUserByUsername(String username) {
        LocalUser localUser = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user WHERE username=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                localUser = new LocalUser();
                localUser.setUsername(username);
                localUser.setPassword(resultSet.getString("password"));
                localUser.setEmail(resultSet.getString("email"));
                localUser.setLocalUserId(resultSet.getLong("local_user_id"));
                localUser.setFirstName(resultSet.getString("first_name"));
                localUser.setLastName(resultSet.getString("last_name"));
                localUser.setEnabled(resultSet.getString("enabled"));
                localUser.setAccessToken(resultSet.getString("access_token"));

                localUser.setRoleList(getRolePermissionVWList(localUser.getLocalUserId()));

                return localUser;
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

        return localUser;
    }

    @Override
    public LocalUser getLocalUserByEmail(String email) {
        LocalUser localUser = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user WHERE email=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                localUser = new LocalUser();
                localUser.setUsername(resultSet.getString("username"));
                localUser.setPassword(resultSet.getString("password"));
                localUser.setEmail(resultSet.getString("email"));
                localUser.setLocalUserId(resultSet.getLong("local_user_id"));
                localUser.setFirstName(resultSet.getString("first_name"));
                localUser.setLastName(resultSet.getString("last_name"));
                localUser.setEnabled(resultSet.getString("enabled"));
                localUser.setAccessToken(resultSet.getString("access_token"));

                localUser.setRoleList(getRolePermissionVWList(localUser.getLocalUserId()));

                return localUser;
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

        return localUser;
    }

    @Override
    public LocalUser getLocalUserByAccessToken(String accessToken) {
        LocalUser localUser = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_user WHERE access_token=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, accessToken);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                localUser = new LocalUser();
                localUser.setUsername(resultSet.getString("username"));
                localUser.setPassword(resultSet.getString("password"));
                localUser.setEmail(resultSet.getString("email"));
                localUser.setLocalUserId(resultSet.getLong("local_user_id"));
                localUser.setFirstName(resultSet.getString("first_name"));
                localUser.setLastName(resultSet.getString("last_name"));
                localUser.setEnabled(resultSet.getString("enabled"));
                localUser.setAccessToken(resultSet.getString("access_token"));


                localUser.setRoleList(getRolePermissionVWList(localUser.getLocalUserId()));

                return localUser;
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

        return localUser;
    }

    @Override
    public boolean addLocalUser(LocalUser localUser) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String queryString = "INSERT INTO "
                    + "local_user(first_name, last_name, gender_id, username, password, email, phone_number, registered_date, enabled, access_token) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, localUser.getFirstName());
            pstmt.setString(2, localUser.getLastName());
            if (localUser.getGenderId() == 0) {
                pstmt.setNull(3, localUser.getGenderId());
            } else {
                pstmt.setInt(3, localUser.getGenderId());
            }
            pstmt.setString(4, localUser.getUsername());
            pstmt.setString(5, encoder.encode(localUser.getPassword()));
            pstmt.setString(6, localUser.getEmail());
            pstmt.setString(7, localUser.getPhoneNumber());
            pstmt.setDate(8, new java.sql.Date(localUser.getRegisteredDate().getTime()));
            pstmt.setString(9, localUser.getEnabled());
            pstmt.setString(10, localUser.getAccessToken());

            pstmt.executeUpdate();

            connection.commit();

            LocalUser newUser = getLocalUserByEmail(localUser.getEmail());
            if (newUser == null) {
                return false;
            }

            localUser.getLocalUserRole().setLocalUserId(newUser.getLocalUserId());

            localUserRoleDAO.addLocalUserRole(localUser.getLocalUserRole());

            System.out.println("Local User Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean addLocalUser(LocalUserVW localUserVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String queryString = "INSERT INTO "
                    + "local_user (first_name, last_name, gender_id, username, password, email, phone_number, registered_date, enabled, access_token) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, localUserVW.getFirstName());
            pstmt.setString(2, localUserVW.getLastName());
            if (localUserVW.getGenderId() == 0) {
                pstmt.setNull(3, localUserVW.getGenderId());
            } else {
                pstmt.setInt(3, localUserVW.getGenderId());
            }
            pstmt.setString(4, localUserVW.getUsername());
            pstmt.setString(5, encoder.encode(localUserVW.getPassword()));
            pstmt.setString(6, localUserVW.getEmail());
            pstmt.setString(7, localUserVW.getPhoneNumber());
            pstmt.setDate(8, new java.sql.Date(localUserVW.getRegisteredDate().getTime()));
            pstmt.setString(9, localUserVW.getEnabled());
            pstmt.setString(10, localUserVW.getAccessToken());

            pstmt.executeUpdate();

            connection.commit();

            LocalUser newUser = getLocalUserByEmail(localUserVW.getEmail());
            if (newUser == null) {
                return false;
            }

            // Local User System Role
            LocalUserRole localUserRole = new LocalUserRole();
            localUserRole.setLocalUserId(newUser.getLocalUserId());
            localUserRole.setLocalRoleId(localUserVW.getLocalRoleId());

            localUserRoleDAO.addLocalUserRole(localUserRole);

            System.out.println("Local User Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean updateLocalUser(LocalUser localUser) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            String queryString = "UPDATE local_user SET first_name=?, last_name=?, "
                    + "gender_id=?, username=?, email=?, phone_number=?, enabled=?, access_token=?"
                    + " WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, localUser.getFirstName());
            pstmt.setString(2, localUser.getLastName());
            if (localUser.getGenderId() == 0) {
                pstmt.setNull(3, localUser.getGenderId());
            } else {
                pstmt.setInt(3, localUser.getGenderId());
            }
            pstmt.setString(4, localUser.getUsername());
            pstmt.setString(5, localUser.getEmail());
            pstmt.setString(6, localUser.getPhoneNumber());
            pstmt.setString(7, localUser.getEnabled());
            pstmt.setString(8, localUser.getAccessToken());

            pstmt.setLong(9, localUser.getLocalUserId());

            if (localUser.getLocalUserRole() != null) {
                localUserRoleDAO.updateLocalUserRole(localUser.getLocalUserRole());
            }

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("Table Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean updateLocalUser(LocalUserVW localUserVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {

            String queryString = "UPDATE local_user SET first_name=?, last_name=?, "
                    + "gender_id=?, username=?, email=?, phone_number=?, enabled=?, access_token=?"
                    + " WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, localUserVW.getFirstName());
            pstmt.setString(2, localUserVW.getLastName());
            if (localUserVW.getGenderId() == 0) {
                pstmt.setNull(3, localUserVW.getGenderId());
            } else {
                pstmt.setInt(3, localUserVW.getGenderId());
            }
            pstmt.setString(4, localUserVW.getUsername());
            pstmt.setString(5, localUserVW.getEmail());
            pstmt.setString(6, localUserVW.getPhoneNumber());
            pstmt.setString(7, localUserVW.getEnabled());
            pstmt.setString(8, localUserVW.getAccessToken());

            pstmt.setLong(9, localUserVW.getLocalUserId());

            LocalUserRole localUserRole = localUserVW.getLocalUserRole();
            localUserRole.setLocalRoleId(localUserVW.getLocalRoleId());

            localUserRoleDAO.updateLocalUserRole(localUserRole);

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("Local User Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean updateLocalUserWithPassword(LocalUserVW localUserVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String queryString = "UPDATE local_user SET first_name=?, last_name=?, "
                    + "gender_id=?, username=?, email=?, phone_number=?, password=?, enabled=?, access_token=?"
                    + " WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, localUserVW.getFirstName());
            pstmt.setString(2, localUserVW.getLastName());
            if (localUserVW.getGenderId() == 0) {
                pstmt.setNull(3, localUserVW.getGenderId());
            } else {
                pstmt.setInt(3, localUserVW.getGenderId());
            }
            pstmt.setString(4, localUserVW.getUsername());
            pstmt.setString(5, localUserVW.getEmail());
            pstmt.setString(6, localUserVW.getPhoneNumber());
            pstmt.setString(7, encoder.encode(localUserVW.getPassword()));
            pstmt.setString(8, localUserVW.getEnabled());
            pstmt.setString(9, localUserVW.getAccessToken());
            pstmt.setLong(10, localUserVW.getLocalUserId());

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("User Table Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean updateLocalUserInfo(LocalUser localUser) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            String queryString = "UPDATE local_user SET first_name=?, last_name=?, "
                    + "gender_id=?, phone_number=?"
                    + " WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, localUser.getFirstName());
            pstmt.setString(2, localUser.getLastName());
            if (localUser.getGenderId() == 0) {
                pstmt.setNull(3, localUser.getGenderId());
            } else {
                pstmt.setInt(3, localUser.getGenderId());
            }
            pstmt.setString(4, localUser.getPhoneNumber());
            pstmt.setLong(5, localUser.getLocalUserId());

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("Table Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean updateLocalUserVWInfo(LocalUserVW localUserVW) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp updatedDate = new java.sql.Timestamp(utilDate.getTime());

            String queryString = "UPDATE local_user SET first_name=?, last_name=?, "
                    + "gender_id=?, phone_number=?, last_update=?"
                    + " WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, localUserVW.getFirstName());
            pstmt.setString(2, localUserVW.getLastName());
            if (localUserVW.getGenderId() == 0) {
                pstmt.setNull(3, localUserVW.getGenderId());
            } else {
                pstmt.setInt(3, localUserVW.getGenderId());
            }
            pstmt.setString(4, localUserVW.getPhoneNumber());
            pstmt.setTimestamp(5, updatedDate);
            pstmt.setLong(6, localUserVW.getLocalUserId());

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("Table Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean updateLocalUserLoginDate(long local_user_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp updatedDate = new java.sql.Timestamp(utilDate.getTime());

            String queryString = "UPDATE local_user SET last_login_date=? WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setTimestamp(1, updatedDate);
            pstmt.setLong(2, local_user_id);

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("LocalUser Login Date updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean updateLocalUserAccessToken(String email, String code) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            String queryString = "UPDATE local_user SET access_token=? WHERE email=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, code);
            pstmt.setString(2, email);

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("LocalUser Access Token updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean updateLocalUserPassword(long localUserId, String password) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            java.util.Date utilDate = new java.util.Date();
            java.sql.Timestamp last_update = new java.sql.Timestamp(utilDate.getTime());

            String queryString = "UPDATE local_user SET last_update=?, password=? WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setTimestamp(1, last_update);
            pstmt.setString(2, encoder.encode(password));
            pstmt.setLong(3, localUserId);

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("LocalUser Password updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    public boolean updateLocalUserProfileImage(long local_user_id, String image_name, String image_path) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "UPDATE local_user SET image_name=?, image_path=? WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, image_name);
            pstmt.setString(2, image_path);
            pstmt.setLong(3, local_user_id);

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("LocalUser Profile Image Updated Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return false;
    }

    @Override
    public boolean deleteLocalUser(long local_user_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            String queryString = "DELETE FROM local_user WHERE local_user_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);
            pstmt.executeUpdate();

            connection.commit();

            System.out.println("User deleted Successfully");

            return true;

        } catch (Exception e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public boolean checkLocalUsername(String localUsername) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT username FROM local_user WHERE username=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, localUsername);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return true;
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

        return false;
    }

    @Override
    public boolean checkLocalUserEmail(String email) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT email FROM local_user WHERE email=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return true;
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

        return false;
    }

    @Override
    public boolean checkOldPassword(long local_user_id, String password) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String queryString = "SELECT password FROM local_user WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                if (encoder.matches(password, resultSet.getString("password"))) {
                    return true;
                }

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

        return false;
    }

    @Override
    public boolean checkLocalUserEnabled(long local_user_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT enabled FROM local_user WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("enabled") == null) {
                    return true;
                }

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

        return false;
    }


    @Override
    public List<RoleVW> getRolePermissionVWList(long local_user_id) {
        List<RoleVW> rolePermissionVWList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM local_role_view WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                RoleVW rolePermissionVW = new RoleVW();
                rolePermissionVW.setRole(resultSet.getString("local_role"));

                rolePermissionVWList.add(rolePermissionVW);
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

        return rolePermissionVWList;
    }

    @Override
    public String getLocalUserPassword(long local_user_id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT password FROM local_user WHERE local_user_id=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, local_user_id);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("password");
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

        return null;
    }

    @Override
    public int getLocalUserCount(Map<String, FilterMeta> filters) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int local_user_count = 0;

        try {
            String queryString = "SELECT COUNT(local_user_id) AS local_user_count FROM local_user_view WHERE 1=1 ";
            connection = ConnectionFactory.getInstance().getConnection();

            String globalFilter = "";
            if (filters != null && filters.size() > 0) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    String filterProperty = it.next();
                    FilterMeta filterValue = filters.get(filterProperty);

                    if (!filterProperty.equals("globalFilter")) {
                        switch (filterProperty) {
                            case "first_name":
                            case "last_name":
                            case "email":
                            case "username":
                            case "phone_number":
                                queryString += " AND " + filterProperty + " LIKE N'%" + filterValue.getFilterValue() + "%'";
                                break;
                        }

                    } else {
                        globalFilter = " AND ( (first_name LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (last_name LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (email LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (username LIKE N'%" + filterValue.getFilterValue() + "%')"
                                + " OR (phone_number LIKE N'%" + filterValue.getFilterValue() + "%')";

                        globalFilter += " )";

                        queryString += globalFilter;
                    }

                }

            }

            pstmt = connection.prepareStatement(queryString);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                local_user_count = resultSet.getInt("local_user_count");
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

        return local_user_count;
    }

    @Override
    public boolean updateKeasUser(LocalUser asstnaviUser) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {

            String queryString = "UPDATE local_user SET first_name=?, last_name=?, gender_id=?, email=? "
                    + " WHERE local_user_id=?";

            connection = ConnectionFactory.getInstance().getConnection();

            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, asstnaviUser.getFirstName());
            pstmt.setString(2, asstnaviUser.getLastName());
            if (asstnaviUser.getGenderId() == 0) {
                pstmt.setNull(3, asstnaviUser.getGenderId());
            } else {
                pstmt.setInt(3, asstnaviUser.getGenderId());
            }
            pstmt.setString(4, asstnaviUser.getEmail());
            pstmt.setLong(5, asstnaviUser.getLocalUserId());

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("KeasUser Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;

    }

    @Override
    public boolean checkKeasUserPassword(LocalUser asstnaviUser) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String queryString = "SELECT password FROM local_user WHERE local_user_id=?";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, asstnaviUser.getLocalUserId());

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                if (encoder.matches(asstnaviUser.getOldPassword(), resultSet.getString("password"))) {
                    return true;
                }
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

        return false;
    }

    @Override
    public boolean updateKeasUserPassword(LocalUser asstnaviUser) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String queryString = "UPDATE local_user SET password=?"
                    + " WHERE local_user_id=?";

            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setString(1, encoder.encode(asstnaviUser.getPassword()));
            pstmt.setLong(2, asstnaviUser.getLocalUserId());

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("KeasUser Password Updated Successfully");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }

    @Override
    public boolean insertForgetPasswordToken(ForgetPasswordToken forgetPasswordToken) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            String queryString = "INSERT INTO "
                    + "forget_password_token(local_user_id, token, enabled, expired, expiration_date) "
                    + "VALUES (?,?,?,?,?)";
            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);
            pstmt.setLong(1, forgetPasswordToken.getLocalUserId());
            pstmt.setString(2, forgetPasswordToken.getToken());
            pstmt.setBoolean(3, forgetPasswordToken.isEnabled());
            pstmt.setBoolean(4, forgetPasswordToken.isExpired());
            pstmt.setTimestamp(5, new java.sql.Timestamp(forgetPasswordToken.getExpirationDate().getTime()));

            pstmt.executeUpdate();

            connection.commit();

            System.out.println("Forget Password Token Added Successfully");

            return true;

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            }

        }

        return false;
    }

    @Override
    public ForgetPasswordToken getForgetPasswordToken(String token) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String queryString = "SELECT * FROM forget_password_token WHERE token=? ";
            connection = ConnectionFactory.getInstance().getConnection();

            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, token);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                ForgetPasswordToken forgetPasswordToken = new ForgetPasswordToken();
                forgetPasswordToken.setId(resultSet.getInt("id"));
                forgetPasswordToken.setLocalUserId(resultSet.getLong("local_user_id"));
                forgetPasswordToken.setToken(resultSet.getString("token"));
                forgetPasswordToken.setEnabled(resultSet.getBoolean("enabled"));
                forgetPasswordToken.setExpired(resultSet.getBoolean("expired"));
                forgetPasswordToken.setExpirationDate(resultSet.getTimestamp("expiration_date"));

                forgetPasswordToken.setToken(token);

                return forgetPasswordToken;
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

        return null;
    }

    @Override
    public boolean updateForgetPasswordToken(ForgetPasswordToken forgetPasswordToken) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean done = false;

        try {

            String queryString = "UPDATE forget_password_token SET enabled=?, expired=?"
                    + " WHERE id=?";

            connection = ConnectionFactory.getInstance().getConnection();
            connection.setAutoCommit(false);

            pstmt = connection.prepareStatement(queryString);

            pstmt.setBoolean(1, forgetPasswordToken.isEnabled());
            pstmt.setBoolean(2, forgetPasswordToken.isExpired());
            pstmt.setInt(3, forgetPasswordToken.getId());

            pstmt.executeUpdate();

            connection.commit();

            done = true;

            System.out.println("Forget Password Token Successfully Updated");

        } catch (SQLException e) {
            Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            } catch (Exception e) {
                Logger.getLogger(LocalUserDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            }
        }

        return done;
    }
}
