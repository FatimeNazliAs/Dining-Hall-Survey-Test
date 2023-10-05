/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.view.landing;

import dininghall.dao.user.LocalUserDAO;
import dininghall.dao.user.LocalUserDAOImpl;
import lombok.SneakyThrows;
import org.brickred.socialauth.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import dininghall.common.AppUtil;
import dininghall.common.MenuUtil;
import dininghall.spring.UserDetailsServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Tolga
 */
@ManagedBean(name = "loginView")
@ViewScoped
public class LoginView implements Serializable {

    /**
     * The authentication manager.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    private String username;
    private String password;
    private String rememberMe;

    private final LocalUserDAO userDAO;

    private String previousUrl;

    public LoginView() {
        userDAO = new LocalUserDAOImpl();
    }

    @SneakyThrows
    public void init() {

        AppUtil.validateLocalUserSession();

        String referrer = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");

        this.previousUrl = referrer;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        Map<String, Object> sessionMap = externalContext.getSessionMap();

        sessionMap.put("previousUrl", previousUrl);
        if (previousUrl != null)
            FacesContext.getCurrentInstance().getExternalContext().redirect(previousUrl);
        if (new MenuUtil().getLocalUserSession() != null)
            FacesContext.getCurrentInstance().getExternalContext().redirect("user-profile");
    }

    // Update user profile access token and login into site
    private void updateUserAndLogin(Profile profile, String code) {
        UserDetailsService service = new UserDetailsServiceImpl();

        UserDetails userDetails = service.loadUserByUsername(profile.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userDAO.updateLocalUserAccessToken(profile.getEmail(), code);
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the authentication manager.
     *
     * @return the authentication manager
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * Sets the authentication manager.
     *
     * @param authenticationManager the new authentication manager
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * @return the rememberMe
     */
    public String getRememberMe() {
        return rememberMe;
    }

    /**
     * @param rememberMe the rememberMe to set
     */
    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * @return the previousUrl
     */
    public String getPreviousUrl() {
        return previousUrl;
    }

    /**
     * @param previousUrl the previousUrl to set
     */
    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

}
