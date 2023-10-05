/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.domain.common.ScheduleEvent;
import dininghall.domain.common.ScheduleEventVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface ScheduleEventDAO {
    public List<ScheduleEvent> getScheduleEventList();

    public List<ScheduleEventVW> getScheduleEventVWList();

    public ScheduleEvent getScheduleEventByScheduleEventId(int scheduleEventId);

    public boolean addScheduleEvent(ScheduleEvent scheduleEvent);

    public boolean updateScheduleEvent(ScheduleEvent scheduleEvent);

    public boolean updateScheduleEvent(ScheduleEventVW scheduleEventVW);

    public boolean deleteScheduleEvent(int scheduleEventId);

}
