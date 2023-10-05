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
import dininghall.domain.helpdesk.HDeskNote;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class HDeskNoteInsertView implements Serializable {
    private HDeskNote newHDeskNote;

    @ManagedProperty("#{hDeskNoteService}")
    private HDeskNoteService service;

    private String subject;
    private String description;
    private long localUserId;

    @PostConstruct
    public void init() {
        newHDeskNote = new HDeskNote();
    }

    public void insertHDeskNote() {
        newHDeskNote.setSubject(subject);
        newHDeskNote.setDescription(description);
        newHDeskNote.setLocalUserId(localUserId);

        if (service.insertHDeskNote(newHDeskNote)) {
            FacesMessage msg = new FacesMessage("HDeskNote added successfully", newHDeskNote.getSubject());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, HDeskNote not added.", newHDeskNote.getSubject());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setService(HDeskNoteService service) {
        this.service = service;
    }

    public HDeskNote getNewHDeskNote() {
        return newHDeskNote;
    }

    public void setNewHDeskNote(HDeskNote newHDeskNote) {
        this.newHDeskNote = newHDeskNote;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLocalUserId() {
        return localUserId;
    }

    public void setLocalUserId(long localUserId) {
        this.localUserId = localUserId;
    }
}
