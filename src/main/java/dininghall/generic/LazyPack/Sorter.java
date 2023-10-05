package dininghall.generic.LazyPack;


import dininghall.generic.Interface.AColumn;
import dininghall.generic.Manager.DbManager;
import dininghall.generic.Manager.PropertyManager;
import dininghall.generic.Service.DataService;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.Map;

public class Sorter {
    public String setOrderBy(Map<String, SortMeta> sortBy, DbManager manager) {
        final String[] temp = {" "};
        temp[0] = " ";
        if (!sortBy.isEmpty()) {
            System.out.println("Sorder 1");
            sortBy.forEach((key, value) -> {
                String k_ = DataService.getFieldAColumn(manager.getISqlTableClass(), key);
                if (k_ != null)
                    temp[0] = " " + k_ + " " + (value.getOrder() == SortOrder.ASCENDING ? PropertyManager.ASCENDING : PropertyManager.DESCENDING);
            });
            System.out.println("Sorder 2");

            temp[0] = " ORDER by " + temp[0];
        }
        if (temp[0].length() < 5) {
            AColumn column = DataService.getAnnationToFieldDefault(manager.getISqlTableClass());
            String prop = "";
            if (column != null)
                prop = " ORDER by " + column.name() + " " + column.defaultvalue() + " ";
            else {
                String getP = DataService.getAnnationToFieldPrimary(manager.getISqlTableClass());
                if (getP != null)
                    prop = " ORDER by " + getP + " asc";

            }
            if (prop.length() > 4)
                temp[0] = prop;
        }
        System.out.println("Sorder 3");
        return temp[0];
    }
}
