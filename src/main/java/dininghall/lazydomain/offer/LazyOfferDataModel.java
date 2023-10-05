package dininghall.lazydomain.offer;

import dininghall.service.offer.OfferService;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.offer.OfferVW;

import java.util.List;
import java.util.Map;

public class LazyOfferDataModel extends LazyDataModel<OfferVW> {
    private static final long serialVersionUID = 1928300515223900075L;

    private OfferService offerService;

    private List<OfferVW> offerVWList;

    private boolean isCalled = false;

    public LazyOfferDataModel(List<OfferVW> offerVWList, OfferService offerService) {
        this.offerVWList = offerVWList;
        this.offerService = offerService;
    }

    @Override
    public String getRowKey(OfferVW offerVW) {
        return "" + offerVW.getOfferId();
    }

    @Override
    public OfferVW getRowData(String rowKey) {
        try {
            for (OfferVW obj : offerVWList) {
                if (String.valueOf(obj.getOfferId()).equals(rowKey)) {
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
    public List<OfferVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters,
                              Map<String, FilterMeta> filters) {
        List<OfferVW> list = null;
        try {
            int rowCount = offerService.getOfferCount(null);

            this.setRowCount(rowCount);

            list = offerService.getOfferVWList(first, first + pageSize, sortFilters, filters);

        } catch (Exception e) {
            this.setRowCount(0);
            System.out.println("Offer Lazy Model LOAD method error" + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public OfferService getOfferService() {
        return offerService;
    }

    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }
}