package dininghall.view.survey;

import org.primefaces.model.SortOrder;

public class LazySorter  {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }



}