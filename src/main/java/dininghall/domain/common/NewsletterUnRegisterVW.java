/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Tolga
 */
public class NewsletterUnRegisterVW implements Serializable {
    private int unRegisterId;
    private String email;
    private Date unRegisterDate;
    private String comment;
    private String unRegisterDateStr;


    public int getUnRegisterId() {
        return unRegisterId;
    }

    public void setUnRegisterId(int unRegisterId) {
        this.unRegisterId = unRegisterId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUnRegisterDate() {
        return unRegisterDate;
    }

    public void setUnRegisterDate(Date unRegisterDate) {
        this.unRegisterDate = unRegisterDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUnRegisterDateStr() {
        return unRegisterDateStr;
    }

    public void setUnRegisterDateStr(String unRegisterDateStr) {
        this.unRegisterDateStr = unRegisterDateStr;
    }
}
