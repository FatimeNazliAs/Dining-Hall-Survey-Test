package dininghall.lazydomain.offer;

import org.primefaces.model.SortOrder;
import dininghall.domain.offer.OfferVW;

import java.util.Comparator;

public class LazyOfferSorter implements Comparator<OfferVW> {
    private String sortField;

    private SortOrder sortOrder;

    public LazyOfferSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(OfferVW offerVW1, OfferVW offerVW2) {
        try {
            Object value1 = OfferVW.class.getField(this.sortField).get(offerVW1);
            Object value2 = OfferVW.class.getField(this.sortField).get(offerVW2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}