/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.common;

import dininghall.dao.user.LocalUserRoleDAO;
import dininghall.dao.user.LocalUserRoleDAOImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import dininghall.domain.user.LocalUserSession;
import dininghall.domain.user.RoleVW;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * @author Tolga
 */
@ManagedBean(name = "menuUtil")
@SessionScoped
public class MenuUtil implements Serializable {

    private static LocalUserRoleDAO userRoleDAO;
    private LocalUserSession localUserSession;


    public MenuUtil() {
        userRoleDAO = new LocalUserRoleDAOImpl();
        try {
            localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
        }
    }

    /**
     * @return the localUserSession
     */
    public LocalUserSession getLocalUserSession() {
        try {
            localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
        }

        return localUserSession;
    }

    public boolean checkUserPermission() {
        long userId = localUserSession.getUserId();
        RoleVW roleVW = userRoleDAO.getRoleViewByUserId(userId);

        if (roleVW.getRole().contains("ADMIN")) {
            return true;
        }

        return false;
    }


}
