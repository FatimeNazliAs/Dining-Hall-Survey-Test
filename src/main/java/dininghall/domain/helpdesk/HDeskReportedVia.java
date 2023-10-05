/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.helpdesk;

import java.io.Serializable;

/**
 * @author Tolga
 */
public class HDeskReportedVia implements Serializable {
    private int reportedViaId;
    private String reportedVia;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
