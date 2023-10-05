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
package dininghall.service.common;


import dininghall.dao.common.ScheduleEventDAO;
import dininghall.dao.common.ScheduleEventDAOImpl;
import dininghall.domain.common.ScheduleEvent;
import dininghall.domain.common.ScheduleEventVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "scheduleEventService")
@ApplicationScoped
public class ScheduleEventService {
    private final static ScheduleEventDAO scheduleEventDAO;

    static {
        scheduleEventDAO = new ScheduleEventDAOImpl();
    }

    public List<ScheduleEvent> getScheduleEventList(int size) {
        List<ScheduleEvent> list = scheduleEventDAO.getScheduleEventList();

        return list;
    }


    public boolean updateScheduleEvent(ScheduleEvent scheduleEvent) {
        return scheduleEventDAO.updateScheduleEvent(scheduleEvent);
    }

    public boolean deleteScheduleEvent(ScheduleEvent scheduleEvent) {
        return scheduleEventDAO.deleteScheduleEvent(scheduleEvent.getScheduleEventId());
    }

    public ScheduleEvent getScheduleEventByScheduleEventId(int scheduleEventId) {
        return scheduleEventDAO.getScheduleEventByScheduleEventId(scheduleEventId);
    }

    public boolean insertScheduleEvent(ScheduleEvent scheduleEvent) {
        return scheduleEventDAO.addScheduleEvent(scheduleEvent);
    }

    public List<ScheduleEventVW> getScheduleEventVWList(int count) {
        return scheduleEventDAO.getScheduleEventVWList();
    }
}
