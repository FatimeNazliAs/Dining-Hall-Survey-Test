package dininghall.lazydomain.common;

import org.primefaces.model.SortOrder;
import dininghall.domain.common.NewsletterVW;

import java.util.Comparator;

public class LazyNewsletterSorter implements Comparator<NewsletterVW> {

    private String sortField;

    private SortOrder sortOrder;

    public LazyNewsletterSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(NewsletterVW newsletterVW1, NewsletterVW newsletterVW2) {
        try {
            Object value1 = NewsletterVW.class.getField(this.sortField).get(newsletterVW1);
            Object value2 = NewsletterVW.class.getField(this.sortField).get(newsletterVW2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}