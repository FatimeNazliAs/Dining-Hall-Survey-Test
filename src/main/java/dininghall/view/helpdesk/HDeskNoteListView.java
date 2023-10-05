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

import dininghall.service.helpdesk.HDeskNoteService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.helpdesk.HDeskNote;

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
public class HDeskNoteListView implements Serializable {

    private List<HDeskNote> hDeskNoteList;

    private HDeskNote selectedHDeskNote;

    private List<HDeskNote> filteredHDeskNoteList;

    @ManagedProperty("#{hDeskNoteService}")
    private HDeskNoteService service;

    @PostConstruct
    public void init() {
        hDeskNoteList = service.getHDeskNoteList(10);
    }

    public List<HDeskNote> getHDeskNoteList() {
        return hDeskNoteList;
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskNote hDeskNote = (HDeskNote) event.getObject();
        FacesMessage msg = new FacesMessage("HDeskNote Edited", hDeskNote.getSubject());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateHDeskNote(hDeskNote);
    }

    public void onRowCancel(RowEditEvent event) {
        HDeskNote hDeskNote = (HDeskNote) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", hDeskNote.getSubject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteHDeskNote(int noteId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskNote hDeskNote = service.getHDeskNoteByHDeskNoteId(noteId);
        if (hDeskNote == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskNote not found!"));
            return;
        }

        if (service.deleteHDeskNote(hDeskNote)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskNote deleted successfully."));
            hDeskNoteList = service.getHDeskNoteList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskNote not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskNote Selected", ((HDeskNote) event.getObject()).getSubject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskNote Unselected", ((HDeskNote) event.getObject()).getSubject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<HDeskNote> getFilteredHDeskNoteList() {
        return filteredHDeskNoteList;
    }

    public void setFilteredHDeskNoteList(List<HDeskNote> filteredHDeskNoteList) {
        this.filteredHDeskNoteList = filteredHDeskNoteList;
    }

    public void setService(HDeskNoteService service) {
        this.service = service;
    }

    public HDeskNote getSelectedHDeskNote() {
        return selectedHDeskNote;
    }

    public void setSelectedHDeskNote(HDeskNote selectedHDeskNote) {
        this.selectedHDeskNote = selectedHDeskNote;
    }
}
