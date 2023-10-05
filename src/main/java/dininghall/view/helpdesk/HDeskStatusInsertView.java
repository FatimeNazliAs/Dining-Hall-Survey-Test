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

import dininghall.service.helpdesk.HDeskStatusService;
import dininghall.domain.helpdesk.HDeskStatus;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class HDeskStatusInsertView implements Serializable {
    private HDeskStatus newHDeskStatus;

    @ManagedProperty("#{hDeskStatusService}")
    private HDeskStatusService service;

    private String status;
    private String description;


    @PostConstruct
    public void init() {
        newHDeskStatus = new HDeskStatus();
    }

    public void insertHDeskStatus() {
        newHDeskStatus.setStatus(status);
        newHDeskStatus.setDescription(description);

        if (service.insertHDeskStatus(newHDeskStatus)) {
            FacesMessage msg = new FacesMessage("HDeskStatus added successfully", newHDeskStatus.getStatus());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, HDeskStatus not added.", newHDeskStatus.getStatus());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setService(HDeskStatusService service) {
        this.service = service;
    }

    public HDeskStatus getNewHDeskStatus() {
        return newHDeskStatus;
    }

    public void setNewHDeskStatus(HDeskStatus newHDeskStatus) {
        this.newHDeskStatus = newHDeskStatus;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
