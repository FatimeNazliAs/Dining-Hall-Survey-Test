/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.helpdesk;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Tolga
 */
public class HDeskNote implements Serializable {

    private int noteId;
    private String subject;
    private String description;
    private long localUserId;
    private Date recordDate;


    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLocalUserId() {
        return localUserId;
    }

    public void setLocalUserId(long localUserId) {
        this.localUserId = localUserId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
