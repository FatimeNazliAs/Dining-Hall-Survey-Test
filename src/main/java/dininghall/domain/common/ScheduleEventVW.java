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
public class ScheduleEventVW implements Serializable {
    private int scheduleEventId;
    private String title;
    private String description;
    private Date startDate;
    private String startDateStr;
    private Date endDate;
    private String endDateStr;
    private long localUserId;
    private String localUser;
    private Date createdDate;
    private String createdDateStr;


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


    public String getLocalUser() {
        return localUser;
    }

    public void setLocalUser(String localUser) {
        this.localUser = localUser;
    }


    public int getScheduleEventId() {
        return scheduleEventId;
    }

    public void setScheduleEventId(int scheduleEventId) {
        this.scheduleEventId = scheduleEventId;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
