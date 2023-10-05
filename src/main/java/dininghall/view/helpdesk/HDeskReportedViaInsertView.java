/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dininghall.view.helpdesk;

import dininghall.service.helpdesk.HDeskReportedViaService;
import dininghall.domain.helpdesk.HDeskReportedVia;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class HDeskReportedViaInsertView implements Serializable {
    private HDeskReportedVia newHDeskReportedVia;

    @ManagedProperty("#{hDeskReportedViaService}")
    private HDeskReportedViaService service;

    private String reportedVia;
    private String description;


    @PostConstruct
    public void init() {
        newHDeskReportedVia = new HDeskReportedVia();
    }

    public void insertHDeskReportedVia() {
        newHDeskReportedVia.setReportedVia(reportedVia);
        newHDeskReportedVia.setDescription(description);

        if (service.insertHDeskReportedVia(newHDeskReportedVia)) {
            FacesMessage msg = new FacesMessage("HDeskReportedVia added successfully", newHDeskReportedVia.getReportedVia());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, HDeskReportedVia not added.", newHDeskReportedVia.getReportedVia());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setService(HDeskReportedViaService service) {
        this.service = service;
    }

    public HDeskReportedVia getNewHDeskReportedVia() {
        return newHDeskReportedVia;
    }

    public void setNewHDeskReportedVia(HDeskReportedVia newHDeskReportedVia) {
        this.newHDeskReportedVia = newHDeskReportedVia;
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