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

import dininghall.dao.helpdesk.HDeskImpactDAO;
import dininghall.dao.helpdesk.HDeskImpactDAOImpl;
import dininghall.domain.helpdesk.HDeskImpact;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "hDeskImpactService")
@ApplicationScoped
public class HDeskImpactService {

    private final static HDeskImpactDAO hDeskImpactDAO;

    static {

        hDeskImpactDAO = new HDeskImpactDAOImpl();

    }

    public List<HDeskImpact> getHDeskImpactList(int size) {
        List<HDeskImpact> list = hDeskImpactDAO.getHDeskImpactList();

        return list;
    }


    public void updateHDeskImpact(HDeskImpact hDeskImpact) {
        hDeskImpactDAO.updateHDeskImpact(hDeskImpact);
    }

    public boolean deleteHDeskImpact(HDeskImpact hDeskImpact) {
        return hDeskImpactDAO.deleteHDeskImpact(hDeskImpact.getImpactId());
    }

    public HDeskImpact getHDeskImpactByHDeskImpactId(int impactId) {
        return hDeskImpactDAO.getHDeskImpactByHDeskImpactId(impactId);
    }

    public boolean insertHDeskImpact(HDeskImpact hDeskImpact) {
        return hDeskImpactDAO.addHDeskImpact(hDeskImpact);
    }
}
