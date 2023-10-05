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

import dininghall.service.helpdesk.HDeskImpactService;
import dininghall.domain.helpdesk.HDeskImpact;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class HDeskImpactInsertView implements Serializable {
    private HDeskImpact newHDeskImpact;

    @ManagedProperty("#{hDeskImpactService}")
    private HDeskImpactService service;

    private String impact;
    private String description;


    @PostConstruct
    public void init() {
        newHDeskImpact = new HDeskImpact();
    }

    public void insertHDeskImpact() {
        newHDeskImpact.setImpact(impact);
        newHDeskImpact.setDescription(description);

        if (service.insertHDeskImpact(newHDeskImpact)) {
            FacesMessage msg = new FacesMessage("HDeskImpact added successfully", newHDeskImpact.getImpact());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, HDeskImpact not added.", newHDeskImpact.getImpact());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setService(HDeskImpactService service) {
        this.service = service;
    }

    public HDeskImpact getNewHDeskImpact() {
        return newHDeskImpact;
    }

    public void setNewHDeskImpact(HDeskImpact newHDeskImpact) {
        this.newHDeskImpact = newHDeskImpact;
    }


    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
