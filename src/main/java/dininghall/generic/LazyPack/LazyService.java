package dininghall.generic.LazyPack;


import dininghall.generic.Manager.DbManager;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.generic.Interface.ISqlTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LazyService {//static yapıda booleanlar kaldırılacak tek nesne üzerinden tüm işlem görülecek
    //global değer olmayacak
    boolean filter = false;
    boolean filterGlobal = false;

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean isFilterGlobal() {
        return filterGlobal;
    }

    public void setFilterGlobal(boolean filterGlobal) {
        this.filterGlobal = filterGlobal;
    }

    public <T> List<T> FilterOperation(LazyDataModel model, ISqlTable sqlTable, int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy, String globalFilter) {
        return FilterOperation(model, sqlTable, first, pageSize, sortBy, filterBy, globalFilter, "");
    }

    public <T> List<T> FilterOperation(LazyDataModel model, ISqlTable sqlTable, int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy, String globalFilter, String query) {
        DbManager manager = new DbManager(sqlTable);

        if (filterBy == null) {
            filterBy = new HashMap<>();
        }

        if (sortBy == null) {
            sortBy = new HashMap<>();
        }

        if (((sortBy.isEmpty() && filterBy.isEmpty())) || (sortBy.size() == 0 && filterBy.size() < 2)) {
            if (filterBy.get("globalFilter") != null) {

            } else if (filterBy.size() == 1 && filterBy.get("globalFilter") == null) {

            } else {
                List<T> list = manager.GetLazyFCQuery(first, pageSize, query);
                if (model != null) {

                    if (query.length() > 0)
                        query = " where " + query;
                    model.setRowCount(manager.GetCountRows(query));
                }
                return list;
            }
        }


        String globalFilterCommand = " ";
        String sortTemp = " ";
        String filterTemp = " ";
        String command = " ";
        //System.out.println("Sorder Başladı");

        sortTemp = new Sorter().setOrderBy(sortBy, manager);

        //System.out.println("Filter Başladı");

        String[] values = new Filter().setFilterBy(filterBy, globalFilter, manager, this);

        filterTemp = values[0];

        globalFilterCommand = values[1];
        //commandArray 0= all command 1 = just filter

        String[] commandArray = new LazyToSql().tableSqlCommand(filterTemp, globalFilterCommand, query, sortTemp, sortBy.isEmpty(), this);

        //System.out.println("Veritabanı Sorgusu gönderildi");

        List<T> lazyList = manager.GetLazyNoOrder(first, pageSize, commandArray[0]);

        if (model != null) {
            model.setRowCount(manager.GetCountRows(commandArray[1]));
        }

        //System.out.println("işlem başarılı bir şekilde sonlandı");

        return lazyList;


    }


}
