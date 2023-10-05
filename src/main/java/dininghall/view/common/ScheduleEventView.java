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
package dininghall.view.common;

import dininghall.service.common.ScheduleEventService;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import dininghall.common.MenuUtil;
import dininghall.domain.common.ScheduleEvent;
import dininghall.domain.common.ScheduleEventVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class ScheduleEventView implements Serializable {

    @ManagedProperty("#{scheduleEventService}")
    private ScheduleEventService scheduleEventService;

    private MenuUtil menuUtil;

    private List<ScheduleEventVW> scheduleEventVWList;

    private ScheduleEvent newScheduleEvent;

    private ScheduleEvent selectedScheduleEvent;

    private ScheduleModel eventModel;

    private DefaultScheduleEvent event = new DefaultScheduleEvent();

    @PostConstruct
    public void init() {
        menuUtil = new MenuUtil();

        scheduleEventVWList = scheduleEventService.getScheduleEventVWList(100);

        eventModel = new DefaultScheduleModel();

        for (ScheduleEventVW seVW : scheduleEventVWList) {
            DefaultScheduleEvent defaultScheduleEvent = new DefaultScheduleEvent();
            defaultScheduleEvent.setTitle(seVW.getTitle());
            defaultScheduleEvent.setDescription(seVW.getDescription());
            //defaultScheduleEvent.setStartDate(seVW.getStartDate());
            //defaultScheduleEvent.setEndDate(seVW.getEndDate());

            eventModel.addEvent(defaultScheduleEvent);

            defaultScheduleEvent.setId(seVW.getScheduleEventId() + "");
        }

    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            newScheduleEvent = new ScheduleEvent();
            newScheduleEvent.setTitle(event.getTitle());
            newScheduleEvent.setDescription(event.getDescription());
            // newScheduleEvent.setStartDate(event.getStartDate());
            // newScheduleEvent.setEndDate(event.getEndDate());
            newScheduleEvent.setLocalUserId(menuUtil.getLocalUserSession().getUserId());

            if (scheduleEventService.insertScheduleEvent(newScheduleEvent)) {
                eventModel.addEvent(event);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event Added", "" + event.getTitle());

                addMessage(message);

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "System Error, Event Not Added", "" + event.getTitle());

                addMessage(message);
            }
        } else {
            ScheduleEvent scheduleEvent = scheduleEventService.getScheduleEventByScheduleEventId(Integer.parseInt(event.getId()));

            scheduleEvent.setTitle(event.getTitle());
            scheduleEvent.setDescription(event.getDescription());
            //scheduleEvent.setStartDate(event.getStartDate());
            //scheduleEvent.setEndDate(event.getEndDate());

            if (scheduleEventService.updateScheduleEvent(scheduleEvent)) {
                eventModel.updateEvent(event);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event Updated", "" + event.getTitle());

                addMessage(message);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "System Error, Event Not Updated", "" + event.getTitle());

                addMessage(message);
            }

        }

        event = new DefaultScheduleEvent();
    }

    public void deleteEvent() {
        ScheduleEvent se = scheduleEventService.getScheduleEventByScheduleEventId(Integer.parseInt(event.getId()));
        if (scheduleEventService.deleteScheduleEvent(se)) {
            eventModel.deleteEvent(event);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event Deleted", "" + event.getTitle());

            addMessage(message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "System Error, Event Not Deleted", "" + event.getTitle());

            addMessage(message);
        }
    }


    public void onEventSelect(SelectEvent selectEvent) {
        event = (DefaultScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        // event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Event moved", "Day delta:" +
                event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        /**
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Event resized"
         , "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());


         addMessage(message);
         */
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public ScheduleEventService getScheduleEventService() {
        return scheduleEventService;
    }

    public void setScheduleEventService(ScheduleEventService scheduleEventService) {
        this.scheduleEventService = scheduleEventService;
    }

    public List<ScheduleEventVW> getScheduleEventVWList() {
        return scheduleEventVWList;
    }

    public void setScheduleEventVWList(List<ScheduleEventVW> scheduleEventVWList) {
        this.scheduleEventVWList = scheduleEventVWList;
    }

    public ScheduleEvent getNewScheduleEvent() {
        return newScheduleEvent;
    }

    public void setNewScheduleEvent(ScheduleEvent newScheduleEvent) {
        this.newScheduleEvent = newScheduleEvent;
    }

    public DefaultScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(DefaultScheduleEvent event) {
        this.event = event;
    }

    public ScheduleEvent getSelectedScheduleEvent() {
        return selectedScheduleEvent;
    }

    public void setSelectedScheduleEvent(ScheduleEvent selectedScheduleEvent) {
        this.selectedScheduleEvent = selectedScheduleEvent;
    }
}
