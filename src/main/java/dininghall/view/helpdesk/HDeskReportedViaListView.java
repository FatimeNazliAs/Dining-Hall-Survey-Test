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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.helpdesk.HDeskReportedVia;

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
public class HDeskReportedViaListView implements Serializable {

    private List<HDeskReportedVia> hDeskReportedViaList;

    private HDeskReportedVia selectedHDeskReportedVia;

    private List<HDeskReportedVia> filteredHDeskReportedViaList;

    @ManagedProperty("#{hDeskReportedViaService}")
    private HDeskReportedViaService service;

    @PostConstruct
    public void init() {
        hDeskReportedViaList = service.getHDeskReportedViaList(10);
    }

    public List<HDeskReportedVia> getHDeskReportedViaList() {
        return hDeskReportedViaList;
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskReportedVia hDeskReportedVia = (HDeskReportedVia) event.getObject();
        FacesMessage msg = new FacesMessage("HDeskReportedVia Edited", hDeskReportedVia.getReportedVia());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateHDeskReportedVia(hDeskReportedVia);
    }

    public void onRowCancel(RowEditEvent event) {
        HDeskReportedVia hDeskReportedVia = (HDeskReportedVia) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", hDeskReportedVia.getReportedVia());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteHDeskReportedVia(int reportedViaId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskReportedVia hDeskReportedVia = service.getHDeskReportedViaByHDeskReportedViaId(reportedViaId);
        if (hDeskReportedVia == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskReportedVia not found!"));
            return;
        }

        if (service.deleteHDeskReportedVia(hDeskReportedVia)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskReportedVia deleted successfully."));
            hDeskReportedViaList = service.getHDeskReportedViaList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskReportedVia not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskReportedVia Selected", ((HDeskReportedVia) event.getObject()).getReportedVia());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskReportedVia Unselected", ((HDeskReportedVia) event.getObject()).getReportedVia());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<HDeskReportedVia> getFilteredHDeskReportedViaList() {
        return filteredHDeskReportedViaList;
    }

    public void setFilteredHDeskReportedViaList(List<HDeskReportedVia> filteredHDeskReportedViaList) {
        this.filteredHDeskReportedViaList = filteredHDeskReportedViaList;
    }

    public void setService(HDeskReportedViaService service) {
        this.service = service;
    }

    public HDeskReportedVia getSelectedHDeskReportedVia() {
        return selectedHDeskReportedVia;
    }

    public void setSelectedHDeskReportedVia(HDeskReportedVia selectedHDeskReportedVia) {
        this.selectedHDeskReportedVia = selectedHDeskReportedVia;
    }
}
