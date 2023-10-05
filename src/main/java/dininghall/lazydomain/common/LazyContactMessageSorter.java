package dininghall.lazydomain.common;

import org.primefaces.model.SortOrder;
import dininghall.domain.common.ContactMessageVW;

import java.util.Comparator;

public class LazyContactMessageSorter implements Comparator<ContactMessageVW> {

    private String sortField;

    private SortOrder sortOrder;

    public LazyContactMessageSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(ContactMessageVW contactMessageVW1, ContactMessageVW contactMessageVW2) {
        try {
            Object value1 = ContactMessageVW.class.getField(this.sortField).get(contactMessageVW1);
            Object value2 = ContactMessageVW.class.getField(this.sortField).get(contactMessageVW2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}