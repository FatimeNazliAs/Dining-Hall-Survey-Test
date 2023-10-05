package dininghall.generic;

import java.util.HashMap;
import java.util.Map;

public class CustomGlobalFilter {

    static Map<String, String[]> list = new HashMap<>();

    public static Map<String, String[]> getCustomFilters() {
        if (!list.isEmpty() && list.size() > 0)
            return list;

        String[] strings = new String[]{"title", "price", "enabled", "category_name", "brand_name", "barcode", "shelf_number"};
        list.put("product_view_code_admin_product_list", strings);
        return list;
    }

    public static String getGlobalFilter(String filterCode, String value) {
        var list = getCustomFilters().get(filterCode);

        String t = " ( ";
        for (var l : list) {
            t += " Cast(" + l + " as text) ILIKE '" + value.replace("'%", "\'\'") + "%' Or";
        }
        if (list.length > 0)
            return t.substring(0, t.length() - 2) + " ) ";
        else
            return "";
    }

}
