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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.helpdesk.HDeskImpact;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


@ManagedBean
@ViewScoped
public class HDeskImpactListView implements Serializable {

    private List<HDeskImpact> hDeskImpactList;

    private HDeskImpact selectedHDeskImpact;

    private List<HDeskImpact> filteredHDeskImpactList;

    @ManagedProperty("#{hDeskImpactService}")
    private HDeskImpactService service;

    @PostConstruct
    public void init() {
        hDeskImpactList = service.getHDeskImpactList(10);
    }

    public List<HDeskImpact> getHDeskImpactList() {
        return hDeskImpactList;
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskImpact hDeskImpact = (HDeskImpact) event.getObject();
        FacesMessage msg = new FacesMessage("HDeskImpact Edited", hDeskImpact.getImpact());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateHDeskImpact(hDeskImpact);
    }

    public void onRowCancel(RowEditEvent event) {
        HDeskImpact hDeskImpact = (HDeskImpact) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", hDeskImpact.getImpact());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteHDeskImpact(int impactId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskImpact hDeskImpact = service.getHDeskImpactByHDeskImpactId(impactId);
        if (hDeskImpact == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskImpact not found!"));
            return;
        }

        if (service.deleteHDeskImpact(hDeskImpact)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskImpact deleted successfully."));
            hDeskImpactList = service.getHDeskImpactList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskImpact not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskImpact Selected", ((HDeskImpact) event.getObject()).getImpact());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskImpact Unselected", ((HDeskImpact) event.getObject()).getImpact());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<HDeskImpact> getFilteredHDeskImpactList() {
        return filteredHDeskImpactList;
    }

    public void setFilteredHDeskImpactList(List<HDeskImpact> filteredHDeskImpactList) {
        this.filteredHDeskImpactList = filteredHDeskImpactList;
    }

    public void setService(HDeskImpactService service) {
        this.service = service;
    }

    public HDeskImpact getSelectedHDeskImpact() {
        return selectedHDeskImpact;
    }

    public void setSelectedHDeskImpact(HDeskImpact selectedHDeskImpact) {
        this.selectedHDeskImpact = selectedHDeskImpact;
    }
}
