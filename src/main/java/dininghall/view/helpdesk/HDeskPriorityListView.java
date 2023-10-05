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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.helpdesk.HDeskPriority;

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
public class HDeskPriorityListView implements Serializable {

    private List<HDeskPriority> hDeskPriorityList;

    private HDeskPriority selectedHDeskPriority;

    private List<HDeskPriority> filteredHDeskPriorityList;

    @ManagedProperty("#{hDeskPriorityService}")
    private HDeskPriorityService service;

    @PostConstruct
    public void init() {
        hDeskPriorityList = service.getHDeskPriorityList(10);
    }

    public List<HDeskPriority> getHDeskPriorityList() {
        return hDeskPriorityList;
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskPriority hDeskPriority = (HDeskPriority) event.getObject();
        FacesMessage msg = new FacesMessage("HDeskPriority Edited", hDeskPriority.getPriority());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateHDeskPriority(hDeskPriority);
    }

    public void onRowCancel(RowEditEvent event) {
        HDeskPriority hDeskPriority = (HDeskPriority) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", hDeskPriority.getPriority());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteHDeskPriority(int priorityId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskPriority hDeskPriority = service.getHDeskPriorityByHDeskPriorityId(priorityId);
        if (hDeskPriority == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskPriority not found!"));
            return;
        }

        if (service.deleteHDeskPriority(hDeskPriority)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskPriority deleted successfully."));
            hDeskPriorityList = service.getHDeskPriorityList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskPriority not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskPriority Selected", ((HDeskPriority) event.getObject()).getPriority());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskPriority Unselected", ((HDeskPriority) event.getObject()).getPriority());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<HDeskPriority> getFilteredHDeskPriorityList() {
        return filteredHDeskPriorityList;
    }

    public void setFilteredHDeskPriorityList(List<HDeskPriority> filteredHDeskPriorityList) {
        this.filteredHDeskPriorityList = filteredHDeskPriorityList;
    }

    public void setService(HDeskPriorityService service) {
        this.service = service;
    }

    public HDeskPriority getSelectedHDeskPriority() {
        return selectedHDeskPriority;
    }

    public void setSelectedHDeskPriority(HDeskPriority selectedHDeskPriority) {
        this.selectedHDeskPriority = selectedHDeskPriority;
    }
}
