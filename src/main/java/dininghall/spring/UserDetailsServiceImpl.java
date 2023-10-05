/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.spring;

import dininghall.dao.user.LocalUserDAO;
import dininghall.dao.user.LocalUserDAOImpl;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserSession;
import dininghall.domain.user.RoleVW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by spring controller to authenticate and authorize user
 * modified this class to user our Database and defined user roles
 *
 * @author Tolga
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public static SessionRegistry sessionRegistry;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    private LocalUserDAO localUserDAO;

    public UserDetailsServiceImpl() {
        localUserDAO = new LocalUserDAOImpl();
    }

    // this class is used by spring controller to authenticate and authorize user
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        String ip = getClientIP();

        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("Too many invalid login attempts, your ip address blocked.");
        }

        LocalUser localUser;
        try {
            localUser = localUserDAO.getLocalUserByEmail(username);
            if (localUser == null) {
                localUser = localUserDAO.getLocalUserByAccessToken(username);
                if (localUser == null) {
                    throw new UsernameNotFoundException("User name not found");
                }
            }

            if (!localUserDAO.updateLocalUserLoginDate(localUser.getLocalUserId())) {
                throw new UsernameNotFoundException("User name not found");
            }

        } catch (Exception e) {
            throw new UsernameNotFoundException("database error ");
        }

        return buildUserFromUserEntity(localUser);
    }

    private User buildUserFromUserEntity(LocalUser localUser) {
        // convert model user to spring security user
        long userId = localUser.getLocalUserId();
        String username = localUser.getEmail();
        String password = localUser.getPassword();
        String email = localUser.getEmail();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleVW rpvw : localUser.getRoleList()) {
            GrantedAuthority[] authorities = new GrantedAuthorityImpl[1];
            authorities[0] = new GrantedAuthorityImpl(rpvw.getRole());
            grantedAuthorities.add(authorities[0]);
        }

        LocalUserSession localUserSession = new LocalUserSession(userId, null, username, password, email, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, grantedAuthorities, localUser.getFirstName() + " " + localUser.getLastName());

        return localUserSession;
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    /**
     * @return the localUserDAO
     */
    public LocalUserDAO getLocalUserDAO() {
        return localUserDAO;
    }

    /**
     * @param localUserDAO the localUserDAO to set
     */
    public void setLocalUserDAO(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }
}
