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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.helpdesk.HDeskStatus;

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
public class HDeskStatusListView implements Serializable {

    private List<HDeskStatus> hDeskStatusList;

    private HDeskStatus selectedHDeskStatus;

    private List<HDeskStatus> filteredHDeskStatusList;

    @ManagedProperty("#{hDeskStatusService}")
    private HDeskStatusService service;

    @PostConstruct
    public void init() {
        hDeskStatusList = service.getHDeskStatusList(10);
    }

    public List<HDeskStatus> getHDeskStatusList() {
        return hDeskStatusList;
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskStatus hDeskStatus = (HDeskStatus) event.getObject();
        FacesMessage msg = new FacesMessage("HDeskStatus Edited", hDeskStatus.getStatus());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateHDeskStatus(hDeskStatus);
    }

    public void onRowCancel(RowEditEvent event) {
        HDeskStatus hDeskStatus = (HDeskStatus) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", hDeskStatus.getStatus());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteHDeskStatus(int statusId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskStatus hDeskStatus = service.getHDeskStatusByHDeskStatusId(statusId);
        if (hDeskStatus == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskStatus not found!"));
            return;
        }

        if (service.deleteHDeskStatus(hDeskStatus)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskStatus deleted successfully."));
            hDeskStatusList = service.getHDeskStatusList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskStatus not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskStatus Selected", ((HDeskStatus) event.getObject()).getStatus());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskStatus Unselected", ((HDeskStatus) event.getObject()).getStatus());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<HDeskStatus> getFilteredHDeskStatusList() {
        return filteredHDeskStatusList;
    }

    public void setFilteredHDeskStatusList(List<HDeskStatus> filteredHDeskStatusList) {
        this.filteredHDeskStatusList = filteredHDeskStatusList;
    }

    public void setService(HDeskStatusService service) {
        this.service = service;
    }

    public HDeskStatus getSelectedHDeskStatus() {
        return selectedHDeskStatus;
    }

    public void setSelectedHDeskStatus(HDeskStatus selectedHDeskStatus) {
        this.selectedHDeskStatus = selectedHDeskStatus;
    }
}
