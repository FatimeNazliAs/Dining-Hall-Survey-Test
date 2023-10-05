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
public class ScheduleEvent implements Serializable {
    private int scheduleEventId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private long localUserId;
    private Date createdDate;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getLocalUserId() {
        return localUserId;
    }

    public void setLocalUserId(long localUserId) {
        this.localUserId = localUserId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getScheduleEventId() {
        return scheduleEventId;
    }

    public void setScheduleEventId(int scheduleEventId) {
        this.scheduleEventId = scheduleEventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
