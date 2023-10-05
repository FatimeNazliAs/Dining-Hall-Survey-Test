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
public class HDeskCaseVW implements Serializable {
    private long caseId;
    private String subject;
    private String description;
    private int catId;
    private String category;
    private int subCatId;
    private String subCategory;
    private long creatorUserId;
    private String creatorUser;
    private long assignedUserId;
    private String assignedUser;
    private int reportedViaId;
    private String reportedVia;
    private int statusId;
    private String status;
    private int priorityId;
    private String priority;
    private int impactId;
    private String impact;
    private String resolution;
    private int scoreId;
    private String score;
    private Date createdTime;
    private String createdTimeStr;
    private long lastModifiedByUserId;
    private String lastModifiedByUser;
    private Date lastModifiedTime;
    private String lastModifiedTimeStr;


    public long getCaseId() {
        return caseId;
    }

    public void setCaseId(long caseId) {
        this.caseId = caseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(int subCatId) {
        this.subCatId = subCatId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(String creatorUser) {
        this.creatorUser = creatorUser;
    }

    public long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public int getReportedViaId() {
        return reportedViaId;
    }

    public void setReportedViaId(int reportedViaId) {
        this.reportedViaId = reportedViaId;
    }

    public String getReportedVia() {
        return reportedVia;
    }

    public void setReportedVia(String reportedVia) {
        this.reportedVia = reportedVia;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getImpactId() {
        return impactId;
    }

    public void setImpactId(int impactId) {
        this.impactId = impactId;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTimeStr() {
        return createdTimeStr;
    }

    public void setCreatedTimeStr(String createdTimeStr) {
        this.createdTimeStr = createdTimeStr;
    }

    public long getLastModifiedByUserId() {
        return lastModifiedByUserId;
    }

    public void setLastModifiedByUserId(long lastModifiedByUserId) {
        this.lastModifiedByUserId = lastModifiedByUserId;
    }

    public String getLastModifiedByUser() {
        return lastModifiedByUser;
    }

    public void setLastModifiedByUser(String lastModifiedByUser) {
        this.lastModifiedByUser = lastModifiedByUser;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedTimeStr() {
        return lastModifiedTimeStr;
    }

    public void setLastModifiedTimeStr(String lastModifiedTimeStr) {
        this.lastModifiedTimeStr = lastModifiedTimeStr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
