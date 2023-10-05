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
import dininghall.domain.helpdesk.HDeskScore;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class HDeskScoreInsertView implements Serializable {
    private HDeskScore newHDeskScore;

    @ManagedProperty("#{hDeskScoreService}")
    private HDeskScoreService service;

    private String score;
    private String description;


    @PostConstruct
    public void init() {
        newHDeskScore = new HDeskScore();
    }

    public void insertHDeskScore() {
        newHDeskScore.setScore(score);
        newHDeskScore.setDescription(description);

        if (service.insertHDeskScore(newHDeskScore)) {
            FacesMessage msg = new FacesMessage("HDeskScore added successfully", newHDeskScore.getScore());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, HDeskScore not added.", newHDeskScore.getScore());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setService(HDeskScoreService service) {
        this.service = service;
    }

    public HDeskScore getNewHDeskScore() {
        return newHDeskScore;
    }

    public void setNewHDeskScore(HDeskScore newHDeskScore) {
        this.newHDeskScore = newHDeskScore;
    }


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
