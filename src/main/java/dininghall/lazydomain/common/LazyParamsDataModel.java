package dininghall.lazydomain.common;


import dininghall.service.common.ParamsService;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.ParamsVW;

import java.util.List;
import java.util.Map;

public class LazyParamsDataModel extends LazyDataModel<ParamsVW> {
    private static final long serialVersionUID = 1928455515443655570L;

    private ParamsService paramsService = new ParamsService();

    private List<ParamsVW> paramsVWList;

    public LazyParamsDataModel(List<ParamsVW> paramsVWList) {
        this.paramsVWList = paramsVWList;
    }

    @Override
    public String getRowKey(ParamsVW pa) {
        return "" + pa.getParamId();
    }

    @Override
    public ParamsVW getRowData(String rowKey) {
        try {
            for (ParamsVW obj : paramsVWList) {
                if (String.valueOf(obj.getParamId()).equals(rowKey)) {
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
    public List<ParamsVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<ParamsVW> list = null;
        try {
            int rowCount = paramsService.getParamsCount(null);

            this.setRowCount(rowCount);

            list = paramsService.getParamsVWList(first, first + pageSize, sortFilters, filters);

        } catch (Exception e) {
            this.setRowCount(0);
            e.printStackTrace();
        }

        return list;
    }

}