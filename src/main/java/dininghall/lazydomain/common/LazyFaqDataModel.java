package dininghall.lazydomain.common;


import dininghall.service.common.FaqService;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.FaqVW;

import java.util.List;
import java.util.Map;

public class LazyFaqDataModel extends LazyDataModel<FaqVW> {
    private static final long serialVersionUID = 1928355515443655570L;

    private FaqService faqService = new FaqService();

    private List<FaqVW> faqVWList;

    public LazyFaqDataModel(List<FaqVW> faqVWList) {
        this.faqVWList = faqVWList;
    }

    @Override
    public String getRowKey(FaqVW fa) {
        return "" + fa.getCreatedAt();
    }

    @Override
    public FaqVW getRowData(String rowKey) {
        try {
            for (FaqVW obj : faqVWList) {
                if (String.valueOf(obj.getFaqId()).equals(rowKey)) {
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
    public List<FaqVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<FaqVW> list = null;
        try {
            int rowCount = faqService.getFaqCount(null);

            this.setRowCount(rowCount);

            list = faqService.getFaqVWList(first, first + pageSize, sortFilters, filters);

        } catch (Exception e) {
            this.setRowCount(0);
            e.printStackTrace();
        }

        return list;
    }

}