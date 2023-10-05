package dininghall.lazydomain.common;


import dininghall.service.common.NewsletterService;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.NewsletterVW;

import java.util.List;
import java.util.Map;

public class LazyNewsletterDataModel extends LazyDataModel<NewsletterVW> {
    private static final long serialVersionUID = 1928300515443655570L;

    private NewsletterService newsletterService = new NewsletterService();

    private List<NewsletterVW> newsletterVWList;

    public LazyNewsletterDataModel(List<NewsletterVW> newsletterVWList) {
        this.newsletterVWList = newsletterVWList;
    }

    @Override
    public String getRowKey(NewsletterVW newsletterVW) {
        return "" + newsletterVW.getNewsletterId();
    }

    @Override
    public NewsletterVW getRowData(String rowKey) {
        try {
            for (NewsletterVW obj : newsletterVWList) {
                if (String.valueOf(obj.getNewsletterId()).equals(rowKey)) {
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
    public List<NewsletterVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        List<NewsletterVW> list = null;
        try {
            int rowCount = newsletterService.getNewsletterCount(null);

            this.setRowCount(rowCount);

            list = newsletterService.getNewsletterVWList(first, first + pageSize, sortFilters, filters);

        } catch (Exception e) {
            this.setRowCount(0);
            e.printStackTrace();
        }

        return list;
    }

}