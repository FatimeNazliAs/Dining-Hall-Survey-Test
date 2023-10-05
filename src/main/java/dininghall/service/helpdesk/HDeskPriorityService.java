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
package dininghall.service.helpdesk;

import dininghall.dao.helpdesk.HDeskPriorityDAO;
import dininghall.dao.helpdesk.HDeskPriorityDAOImpl;
import dininghall.domain.helpdesk.HDeskPriority;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "hDeskPriorityService")
@ApplicationScoped
public class HDeskPriorityService {

    private final static HDeskPriorityDAO hDeskPriorityDAO;

    static {

        hDeskPriorityDAO = new HDeskPriorityDAOImpl();

    }

    public List<HDeskPriority> getHDeskPriorityList(int size) {
        List<HDeskPriority> list = hDeskPriorityDAO.getHDeskPriorityList();

        return list;
    }


    public void updateHDeskPriority(HDeskPriority hDeskPriority) {
        hDeskPriorityDAO.updateHDeskPriority(hDeskPriority);
    }

    public boolean deleteHDeskPriority(HDeskPriority hDeskPriority) {
        return hDeskPriorityDAO.deleteHDeskPriority(hDeskPriority.getPriorityId());
    }

    public HDeskPriority getHDeskPriorityByHDeskPriorityId(int priorityId) {
        return hDeskPriorityDAO.getHDeskPriorityByHDeskPriorityId(priorityId);
    }

    public boolean insertHDeskPriority(HDeskPriority hDeskPriority) {
        return hDeskPriorityDAO.addHDeskPriority(hDeskPriority);
    }
}
