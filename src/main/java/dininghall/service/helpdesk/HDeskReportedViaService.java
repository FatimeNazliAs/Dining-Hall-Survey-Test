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

import dininghall.dao.helpdesk.HDeskReportedViaDAO;
import dininghall.dao.helpdesk.HDeskReportedViaDAOImpl;
import dininghall.domain.helpdesk.HDeskReportedVia;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "hDeskReportedViaService")
@ApplicationScoped
public class HDeskReportedViaService {

    private final static HDeskReportedViaDAO hDeskReportedViaDAO;

    static {

        hDeskReportedViaDAO = new HDeskReportedViaDAOImpl();

    }

    public List<HDeskReportedVia> getHDeskReportedViaList(int size) {
        List<HDeskReportedVia> list = hDeskReportedViaDAO.getHDeskReportedViaList();

        return list;
    }


    public void updateHDeskReportedVia(HDeskReportedVia hDeskReportedVia) {
        hDeskReportedViaDAO.updateHDeskReportedVia(hDeskReportedVia);
    }

    public boolean deleteHDeskReportedVia(HDeskReportedVia hDeskReportedVia) {
        return hDeskReportedViaDAO.deleteHDeskReportedVia(hDeskReportedVia.getReportedViaId());
    }

    public HDeskReportedVia getHDeskReportedViaByHDeskReportedViaId(int reportedViaId) {
        return hDeskReportedViaDAO.getHDeskReportedViaByHDeskReportedViaId(reportedViaId);
    }

    public boolean insertHDeskReportedVia(HDeskReportedVia hDeskReportedVia) {
        return hDeskReportedViaDAO.addHDeskReportedVia(hDeskReportedVia);
    }
}
