package dininghall.lazydomain.user;

import dininghall.service.user.LocalUserService;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.user.LocalUserVW;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LazyLocalUserDataModel extends LazyDataModel<LocalUserVW> implements Serializable {
    private static final long serialVersionUID = 1298390515223601060L;

    private LocalUserService localUserService;

    private List<LocalUserVW> localUserVWList;

    private Map<String, FilterMeta> initFilters;

    public LazyLocalUserDataModel(List<LocalUserVW> localUserVWList, LocalUserService localUserService) {
        this.localUserVWList = localUserVWList;
        this.localUserService = localUserService;
    }

    public LazyLocalUserDataModel(List<LocalUserVW> localUserVWList, LocalUserService localUserService, Map<String, FilterMeta> initFilters) {
        this.localUserVWList = localUserVWList;
        this.localUserService = localUserService;
        this.initFilters = initFilters;
    }

    @Override
    public String getRowKey(LocalUserVW localUserVW) {
        return "" + localUserVW.getLocalUserId();
    }

    @Override
    public LocalUserVW getRowData(String rowKey) {
        for (LocalUserVW localUserVW : localUserVWList) {
            if (localUserVW.getLocalUserId() == Long.parseLong(rowKey)) {
                return localUserVW;
            }
        }

        return null;
    }


    @Override
    public int count(Map<String, FilterMeta> map) {
        return 0;
    }

    @Override
    public List<LocalUserVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters,
                                  Map<String, FilterMeta> filters) {

        try {
            if (initFilters != null) {
                filters = initFilters;
            }

            int rowCount = localUserService.getLocalUserCount(filters);

            this.localUserVWList = localUserService.getLocalUserVWList(first, first + pageSize, null, filters);

            this.setRowCount(rowCount);


        } catch (Exception e) {
            this.setRowCount(0);

            System.out.println("Product Lazy Model LOAD method error" + e.getMessage());

            e.printStackTrace();
        }

        return localUserVWList;
    }


    public LocalUserService getLocalUserService() {
        return localUserService;
    }

    public void setLocalUserService(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }
}