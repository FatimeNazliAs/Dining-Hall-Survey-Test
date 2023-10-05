package dininghall.lazydomain.helpdesk;


import dininghall.service.helpdesk.HDeskCaseService;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.helpdesk.HDeskCaseVW;

import java.util.List;
import java.util.Map;

public class LazyHDeskCaseDataModel extends LazyDataModel<HDeskCaseVW> {
    private static final long serialVersionUID = 1928300515441100070L;

    private HDeskCaseService hDeskCaseService = new HDeskCaseService();

    private List<HDeskCaseVW> hDeskCaseVWList;

    public LazyHDeskCaseDataModel(List<HDeskCaseVW> hDeskCaseVWList) {
        this.hDeskCaseVWList = hDeskCaseVWList;
    }

    @Override
    public String getRowKey(HDeskCaseVW hDeskCaseVW) {
        return "" + hDeskCaseVW.getCaseId();
    }

    @Override
    public HDeskCaseVW getRowData(String rowKey) {
        try {
            for (HDeskCaseVW obj : hDeskCaseVWList) {
                if (String.valueOf(obj.getCaseId()).equals(rowKey)) {
                    return obj;
                }
            }

        } catch (Exception e) {

            System.out.println("getRowData method error" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public int count(Map<String, FilterMeta> map) {
        return 0;
    }

    @Override
    public List<HDeskCaseVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<HDeskCaseVW> list = null;
        try {
            int rowCount = hDeskCaseService.getHDeskCaseCount(null);

            this.setRowCount(rowCount);

            list = hDeskCaseService.getHDeskCaseVWList(first, first + pageSize, sortFilters, filters);

        } catch (Exception e) {
            this.setRowCount(0);

            e.printStackTrace();
        }

        return list;
    }

    public HDeskCaseService getHDeskCaseService() {
        return hDeskCaseService;
    }

    public void setHDeskCaseService(HDeskCaseService hDeskCaseService) {
        this.hDeskCaseService = hDeskCaseService;
    }
}