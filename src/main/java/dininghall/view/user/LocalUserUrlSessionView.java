/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.view.user;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * @author zxc
 */
@ManagedBean(name = "userUrlSession")
@SessionScoped
public class LocalUserUrlSessionView implements Serializable {

    private boolean loggedIn;
    private String originalURL;

    public void recordOriginalURL() {
        this.setOriginalURL(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("page"));
    }

    // Getters and Setters

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the originalURL
     */
    public String getOriginalURL() {
        return originalURL;
    }

    /**
     * @param originalURL the originalURL to set
     */
    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
}
