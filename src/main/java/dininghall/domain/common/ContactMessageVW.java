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
public class ContactMessageVW implements Serializable {
    private long contactMessageId;
    private String fullName;
    private String email;
    private String messageBody;
    private Date createdDate;
    private String createdDateStr;
    private Date expiryDate;
    private String expiryDateStr;
    private String subject;

    public long getContactMessageId() {
        return contactMessageId;
    }

    public void setContactMessageId(long contactMessageId) {
        this.contactMessageId = contactMessageId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getExpiryDateStr() {
        return expiryDateStr;
    }

    public void setExpiryDateStr(String expiryDateStr) {
        this.expiryDateStr = expiryDateStr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
