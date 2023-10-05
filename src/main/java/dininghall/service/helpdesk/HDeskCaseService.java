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

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.dao.helpdesk.HDeskCaseDAO;
import dininghall.dao.helpdesk.HDeskCaseDAOImpl;
import dininghall.domain.helpdesk.HDeskCase;
import dininghall.domain.helpdesk.HDeskCaseVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "hDeskCaseService")
@ApplicationScoped
public class HDeskCaseService {

    private final static HDeskCaseDAO hDeskCaseDAO;

    static {

        hDeskCaseDAO = new HDeskCaseDAOImpl();

    }

    public List<HDeskCase> getHDeskCaseList(int size) {
        List<HDeskCase> list = hDeskCaseDAO.getHDeskCaseList();

        return list;
    }

    public List<HDeskCaseVW> getHDeskCaseVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        return hDeskCaseDAO.getHDeskCaseVWList(first, pageSize, sortFilters, filters);
    }


    public void updateHDeskCase(HDeskCase hDeskCase) {
        hDeskCaseDAO.updateHDeskCase(hDeskCase);
    }

    public boolean deleteHDeskCase(HDeskCase hDeskCase) {
        return hDeskCaseDAO.deleteHDeskCase(hDeskCase.getCaseId());
    }

    public HDeskCase getHDeskCaseByHDeskCaseId(long hDeskCaseId) {
        return hDeskCaseDAO.getHDeskCaseByHDeskCaseId(hDeskCaseId);
    }

    public boolean insertHDeskCase(HDeskCase hDeskCase) {
        return hDeskCaseDAO.addHDeskCase(hDeskCase);
    }

    public boolean updateHDeskCase(HDeskCaseVW hDeskCaseVW) {
        return hDeskCaseDAO.updateHDeskCase(hDeskCaseVW);
    }

    public HDeskCaseVW getHDeskCaseVWByHDeskCaseId(long hDeskCaseId) {
        return hDeskCaseDAO.getHDeskCaseVWByHDeskCaseId(hDeskCaseId);
    }

    public List<HDeskCaseVW> getHDeskCaseAssignedListVWByLocalUserId(long localUserId) {
        return hDeskCaseDAO.getHDeskCaseAssignedVWListByLocalUserId(localUserId);
    }

    public int getHDeskCaseCount(Map<String, Object> filters) {
        return hDeskCaseDAO.getHDeskCaseCount(filters);
    }

}
