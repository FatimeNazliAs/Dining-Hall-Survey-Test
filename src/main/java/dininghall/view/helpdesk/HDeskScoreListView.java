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

import dininghall.service.helpdesk.HDeskScoreService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.helpdesk.HDeskScore;

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
public class HDeskScoreListView implements Serializable {

    private List<HDeskScore> hDeskScoreList;

    private HDeskScore selectedHDeskScore;

    private List<HDeskScore> filteredHDeskScoreList;

    @ManagedProperty("#{hDeskScoreService}")
    private HDeskScoreService service;

    @PostConstruct
    public void init() {
        hDeskScoreList = service.getHDeskScoreList(10);
    }

    public List<HDeskScore> getHDeskScoreList() {
        return hDeskScoreList;
    }

    public void onRowEdit(RowEditEvent event) {
        HDeskScore hDeskScore = (HDeskScore) event.getObject();
        FacesMessage msg = new FacesMessage("HDeskScore Edited", hDeskScore.getScore());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        service.updateHDeskScore(hDeskScore);
    }

    public void onRowCancel(RowEditEvent event) {
        HDeskScore hDeskScore = (HDeskScore) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", hDeskScore.getScore());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteHDeskScore(int scoreId) {
        FacesContext context = FacesContext.getCurrentInstance();

        HDeskScore hDeskScore = service.getHDeskScoreByHDeskScoreId(scoreId);
        if (hDeskScore == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "HDeskScore not found!"));
            return;
        }

        if (service.deleteHDeskScore(hDeskScore)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "HDeskScore deleted successfully."));
            hDeskScoreList = service.getHDeskScoreList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, hDeskScore not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskScore Selected", ((HDeskScore) event.getObject()).getScore());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("HDeskScore Unselected", ((HDeskScore) event.getObject()).getScore());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<HDeskScore> getFilteredHDeskScoreList() {
        return filteredHDeskScoreList;
    }

    public void setFilteredHDeskScoreList(List<HDeskScore> filteredHDeskScoreList) {
        this.filteredHDeskScoreList = filteredHDeskScoreList;
    }

    public void setService(HDeskScoreService service) {
        this.service = service;
    }

    public HDeskScore getSelectedHDeskScore() {
        return selectedHDeskScore;
    }

    public void setSelectedHDeskScore(HDeskScore selectedHDeskScore) {
        this.selectedHDeskScore = selectedHDeskScore;
    }
}
