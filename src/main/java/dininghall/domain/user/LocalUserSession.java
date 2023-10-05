/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author Tolga
 */
@ManagedBean(name = "localUserSession")
@SessionScoped
public class LocalUserSession extends User implements Serializable {
    private long userId;
    private LocalUserRole localUserRole;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private Collection<? extends GrantedAuthority> authorities;

    public LocalUserSession(String username, String password, boolean enabled, boolean accountNonExpired,
                            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public LocalUserSession(long userId, LocalUserRole localUserRole, String username, String password, String email, boolean enabled,
                            boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                            Collection<? extends GrantedAuthority> authorities, String fullname) {
        super(username, password, authorities);
        this.userId = userId;
        this.localUserRole = localUserRole;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
        this.fullname = fullname;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the localUserRole
     */
    public LocalUserRole getLocalUserRole() {
        return localUserRole;
    }

    /**
     * @param localUserRole the localUserRole to set
     */
    public void setLocalUserRole(LocalUserRole localUserRole) {
        this.localUserRole = localUserRole;
    }

}
