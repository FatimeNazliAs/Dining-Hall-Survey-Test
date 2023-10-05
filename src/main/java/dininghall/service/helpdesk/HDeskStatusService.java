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

import dininghall.dao.helpdesk.HDeskStatusDAO;
import dininghall.dao.helpdesk.HDeskStatusDAOImpl;
import dininghall.domain.helpdesk.HDeskStatus;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "hDeskStatusService")
@ApplicationScoped
public class HDeskStatusService {

    private final static HDeskStatusDAO hDeskStatusDAO;

    static {

        hDeskStatusDAO = new HDeskStatusDAOImpl();

    }

    public List<HDeskStatus> getHDeskStatusList(int size) {
        List<HDeskStatus> list = hDeskStatusDAO.getHDeskStatusList();

        return list;
    }


    public void updateHDeskStatus(HDeskStatus hDeskStatus) {
        hDeskStatusDAO.updateHDeskStatus(hDeskStatus);
    }

    public boolean deleteHDeskStatus(HDeskStatus hDeskStatus) {
        return hDeskStatusDAO.deleteHDeskStatus(hDeskStatus.getStatusId());
    }

    public HDeskStatus getHDeskStatusByHDeskStatusId(int statusId) {
        return hDeskStatusDAO.getHDeskStatusByHDeskStatusId(statusId);
    }

    public boolean insertHDeskStatus(HDeskStatus hDeskStatus) {
        return hDeskStatusDAO.addHDeskStatus(hDeskStatus);
    }
}
