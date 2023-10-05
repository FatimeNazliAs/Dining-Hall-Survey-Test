package dininghall.lazydomain.common;


import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.ContactMessageVW;
import dininghall.service.common.ContactMessageService;

import java.util.List;
import java.util.Map;

public class LazyContactMessageDataModel extends LazyDataModel<ContactMessageVW> {
    private static final long serialVersionUID = 1928300515443655570L;

    private ContactMessageService contactMessageService = new ContactMessageService();

    private List<ContactMessageVW> contactMessageVWList;

    public LazyContactMessageDataModel(List<ContactMessageVW> contactMessageVWList) {
        this.contactMessageVWList = contactMessageVWList;
    }

    @Override
    public String getRowKey(ContactMessageVW contactMessageVW) {
        return "" + contactMessageVW.getContactMessageId();
    }

    @Override
    public ContactMessageVW getRowData(String rowKey) {
        try {
            for (ContactMessageVW obj : contactMessageVWList) {
                if (String.valueOf(obj.getContactMessageId()).equals(rowKey)) {
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
    public List<ContactMessageVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters,
                                       Map<String, FilterMeta> filters) {
        List<ContactMessageVW> list = null;
        try {
            int rowCount = contactMessageService.getContactMessageCount(null);

            this.setRowCount(rowCount);

            list = contactMessageService.getContactMessageVWList(first, first + pageSize, sortFilters, filters);

        } catch (Exception e) {
            this.setRowCount(0);
            e.printStackTrace();
        }

        return list;
    }

}