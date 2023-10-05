/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.domain.helpdesk.HDeskCase;
import dininghall.domain.helpdesk.HDeskCaseVW;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface HDeskCaseDAO {
    public List<HDeskCase> getHDeskCaseList();

    public HDeskCase getHDeskCaseByHDeskCaseId(long hDeskCaseId);

    public HDeskCase getHDeskCaseByName(String name);

    public boolean addHDeskCase(HDeskCase hDeskCase);

    public void updateHDeskCase(HDeskCase hDeskCase);

    public boolean deleteHDeskCase(long hDeskCaseId);

    public List<HDeskCaseVW> getHDeskCaseVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);

    public boolean updateHDeskCase(HDeskCaseVW hDeskCaseVW);

    public HDeskCaseVW getHDeskCaseVWByHDeskCaseId(long hDeskCaseId);

    public List<HDeskCaseVW> getHDeskCaseAssignedVWListByLocalUserId(long localUserId);

    public int getHDeskCaseCount(Map<String, Object> filters);

}
