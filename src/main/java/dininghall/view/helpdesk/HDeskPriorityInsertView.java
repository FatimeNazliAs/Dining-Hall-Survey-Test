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

import dininghall.service.helpdesk.HDeskPriorityService;
import dininghall.domain.helpdesk.HDeskPriority;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class HDeskPriorityInsertView implements Serializable {
    private HDeskPriority newHDeskPriority;

    @ManagedProperty("#{hDeskPriorityService}")
    private HDeskPriorityService service;

    private String priority;
    private String description;


    @PostConstruct
    public void init() {
        newHDeskPriority = new HDeskPriority();
    }

    public void insertHDeskPriority() {
        newHDeskPriority.setPriority(priority);
        newHDeskPriority.setDescription(description);

        if (service.insertHDeskPriority(newHDeskPriority)) {
            FacesMessage msg = new FacesMessage("HDeskPriority added successfully", newHDeskPriority.getPriority());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, HDeskPriority not added.", newHDeskPriority.getPriority());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setService(HDeskPriorityService service) {
        this.service = service;
    }

    public HDeskPriority getNewHDeskPriority() {
        return newHDeskPriority;
    }

    public void setNewHDeskPriority(HDeskPriority newHDeskPriority) {
        this.newHDeskPriority = newHDeskPriority;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
