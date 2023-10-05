package dininghall.generic.LazyPack;

import dininghall.generic.CustomGlobalFilter;
import dininghall.generic.Manager.DbManager;
import dininghall.generic.Service.DataService;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

import java.util.Map;

public class Filter extends LazyService {
    public String[] setFilterBy(Map<String, FilterMeta> filterBy, String globalFilter, DbManager manager, LazyService service) {
        String[] strings = new String[2];
        strings[0] = " ";

        strings[1] = " ";
        if (!filterBy.isEmpty()) {
            System.out.println("Filter 1");
            for (Map.Entry<String, FilterMeta> pbk : filterBy.entrySet()) {
                FilterMeta value = pbk.getValue();
              /*  if (value.isActive()==null && !value.isActive())
                    continue;*/
                if (value == null)
                    continue;

                if (value.getFilterValue() == null)
                    continue;
                if (value.getFilterValue().toString().length() > 0) {

                    System.out.println("Filter 2");
                    String valueField = DataService.getFieldAColumn(manager.getISqlTableClass(), pbk.getKey());

                    if (valueField == null || valueField.isEmpty())
                        valueField = value.getField();
                    if (valueField == null) {
                        System.err.println(" FILTER HATASI 0001");
                        continue;
                    }
                    Object fieldValue = value.getFilterValue();
                    String[] fieldItems;
                    if (fieldValue instanceof String[] || StringArrayControl(fieldValue)) {
                        fieldItems = (String[]) fieldValue;
                    } else
                        fieldItems = new String[]{fieldValue.toString()
                        };

                    if (value.getMatchMode() == MatchMode.EQUALS || value.getMatchMode() == MatchMode.EXACT) {
                        boolean control = false;
                        if (fieldItems.length > 0)
                            strings[0] += " ( ";
                        for (String item : fieldItems) {
                            service.setFilter(true);
                            control = true;
                            strings[0] += " " + valueField + "='" + item.replace("\'", "\'\'").toString() + "' Or ";
                        }
                        if (control) {
                            strings[0] = strings[0].substring(0, strings[0].length() - 4);
                            strings[0] += " ) And ";
                        }
                    }
                    if (value.getMatchMode() == MatchMode.BETWEEN) {

                        Class _class = DataService.getFieldAColumnClass(manager.getISqlTableClass(), valueField);
                        if (_class != null) {
                            strings[0] += " " + valueField + " BETWEEN '" + fieldItems[0].replace("\'", "\'\'") +
                                    "' AND '" + fieldItems[1].replace("\'", "\'\'") + "' And";
                            service.setFilter(true);
                        }
                     /*   else
                        {

                            service.setFilter( true);

                            strings[0] += " " + valueField + " BETWEEN '"+fieldItems[0] +"' and "+fieldItems[1]+"' And";
                        }*/


                    }
                    if (value.getMatchMode() == MatchMode.GLOBAL) {

                        String data_temp = CustomGlobalFilter.getGlobalFilter(globalFilter, fieldValue.toString());
                        if (data_temp != "" && data_temp.length() > 0) {
                            service.setFilterGlobal(true);
                            strings[1] = " AND " + data_temp + " ";
                        }
                    }
                    if (value.getMatchMode() == MatchMode.ENDS_WITH) {
                        //foreach ile güncellenecektir
                        //StringArrayControl kontrolü yapılacak gelen veri veya verileri sınıflayacak
                        //'% ... ' > olarak ayaralanacak
                        service.setFilterGlobal(true);
                        strings[0] += "   CAST(" + valueField + " AS TEXT) ILIKE '%" + fieldValue.toString().replace("\'", "\'\'") + "' And ";
                        service.setFilter(true);
                    }
                    if (value.getMatchMode() == MatchMode.STARTS_WITH) {
                        //foreach ile güncellenecektir
                        //StringArrayControl kontrolü yapılacak gelen veri veya verileri sınıflayacak
                        //'% ... ' > olarak ayaralanacak
                        strings[0] += " starts_with ( CAST( " + valueField + "  AS TEXT), '" + fieldValue.toString().replace("\'", "\'\'") + "' ) And ";
                        service.setFilter(true);
                    }
                    if (value.getMatchMode() == MatchMode.CONTAINS) {

                        String val = " (";
                        boolean f_ = false;
                        for (String b_ : fieldItems) {
                            val += " CAST(" + valueField + " AS TEXT) ILIKE '%" + b_.replace("\'", "\'\'") + "%' OR";
                            f_ = true;
                        }
                        if (f_) {
                            val = val.substring(0, val.length() - 2) + ") And ";
                        }
                        strings[0] += val;
                        service.setFilter(true);
                    }
                }
            }

        }
        System.out.println("Filter 3");
        return strings;
    }


    private boolean StringArrayControl(Object obj) {
        try {

            if (obj.getClass().getTypeName().toString().equals("java.lang.String[]"))
                return true;
            else
                return false;

        } catch (Exception e) {
            return false;
        }


    }
    //test


}
