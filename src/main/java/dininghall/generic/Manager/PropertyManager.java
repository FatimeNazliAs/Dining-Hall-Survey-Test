package dininghall.generic.Manager;


import dininghall.domain.survey.models.testModel;
import dininghall.generic.Service.DataService;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class PropertyManager implements Serializable {
    public static String ASCENDING = "asc";
    public static String DESCENDING = "desc";


    public static String fieldToInsertCommand(Object obj) {
        Locale.setDefault(Locale.ENGLISH);
        Map<String, Object> map = getFields(obj);
        if (map == null)
            return null;
        String mapNames = "";
        String mapValues = "";
        String primaryKey = DataService.getAnnationToFieldPrimaryField(obj.getClass()).name();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null)
                continue;
            if (!entry.getKey().equals(primaryKey)) {

                if ((entry.getValue() instanceof Date || entry.getValue() instanceof java.sql.Date) && entry.getValue() != null) {
                    entry.setValue(new Timestamp(((Date) entry.getValue()).getTime()));
                }
                if (entry.getValue() instanceof ArrayList || entry.getValue() instanceof Array) {
                    mapValues += "'" + entry.getValue().toString().replace("\'", "\'\'").replace("[", "{").replace("]", "}") + "',";
                } else {
                    mapValues += "'" + entry.getValue().toString().replace("\'", "\'\'") + "',";
                    //postgreSqlde büyük harfli bir sütun bulunuyor ise " işareti şarttır
                    //bu yüzden veriler kücük harfle girilmelidir Locale default En olarak seçilmeli
                }
                mapNames += entry.getKey().toLowerCase() + ",";
            }
        }
        mapValues = mapValues.substring(0, mapValues.length() - 1);
        mapNames = mapNames.substring(0, mapNames.length() - 1);
        String com = " (" + mapNames + ")" + " values (" + mapValues + ")";
        return com;
    }
    public static String[] fieldToInsertNameValue(Object obj) {
        Locale.setDefault(Locale.ENGLISH);
        Map<String, Object> map = getFields(obj);
        if (map == null)
            return null;
        String mapNames = "";
        String mapValues = "";
        String primaryKey = DataService.getAnnationToFieldPrimaryField(obj.getClass()).name();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null)
                continue;
            if (!entry.getKey().equals(primaryKey)) {

                if ((entry.getValue() instanceof Date || entry.getValue() instanceof java.sql.Date) && entry.getValue() != null) {
                    entry.setValue(new Timestamp(((Date) entry.getValue()).getTime()));
                }
                if (entry.getValue() instanceof ArrayList || entry.getValue() instanceof Array) {
                    mapValues += "'" + entry.getValue().toString().replace("\'", "\'\'").replace("[", "{").replace("]", "}") + "',";
                } else {
                    mapValues += "'" + entry.getValue().toString().replace("\'", "\'\'") + "',";
                    //postgreSqlde büyük harfli bir sütun bulunuyor ise " işareti şarttır
                    //bu yüzden veriler kücük harfle girilmelidir Locale default En olarak seçilmeli
                }
                mapNames += entry.getKey().toLowerCase() + ",";
            }
        }
        mapValues = mapValues.substring(0, mapValues.length() - 1);
        mapNames = mapNames.substring(0, mapNames.length() - 1);
        String[] str=new String[2];
        str[0]=mapNames.toString();
        str[1]=mapValues.toString();
       return str;
      //  String com = " (" + mapNames + ")" + " values (" + mapValues + ")";
        //return com;
    }


    public static String fieldToUpdateCommand(Object obj) {//güncellenecek hata var > idye göre değil primary'e göre olacka
        //şu anda lazy güncelleme için kullanılmyıor
        String com = " ";
        String primaryKey = DataService.getAnnationToFieldPrimaryField(obj.getClass()).name();
        Map<String, Object> map = getFields(obj);
        if (map == null)
            return null;
        String idv = " where " + primaryKey + "=";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null)
                continue;
            if (!entry.getKey().equals(primaryKey)) {
                if (entry.getValue() instanceof ArrayList || entry.getValue() instanceof Array) {
                    com += entry.getKey() + "=" + "'" + entry.getValue().toString().replace("\'", "\'\'").replace("[", "{").replace("]", "}") + "',";
                } else {
                    com += entry.getKey() + "=" + "'" + entry.getValue().toString().replace("\'", "\'\'") + "',";
                }
            } else
                idv += entry.getValue();
        }
        if (com.length() > 0)
            com = com.substring(0, com.length() - 1);
        com += idv;
        return com;
    }

    public static String fieldToUpdateCommandMap(Map<String, Object> map, String primaryKey, String primaryValue) {//güncellenecek hata var > idye göre değil primary'e göre olacka
        //şu anda lazy güncelleme için kullanılmyıor
        String com = " ";
        if (map == null)
            return null;
        String idv = " where " + primaryKey + "=" + primaryValue;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null)
                continue;
            com += entry.getKey() + "=" + "'" + entry.getValue().toString().replace("\'", "\'\'") + "',";
        }
        if (com.length() > 0)
            com = com.substring(0, com.length() - 1);
        com += idv;
        return com;
    }

    public static Map<String, Object> getFields(Object obj) {
        try {
            return DataService.ConvertDataForObjectList(obj);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static String InsertDataSyntax(Object obj) {
        String com = " ";
        Map<String, Object> map = getFields(obj);
        if (map == null)
            return null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() == null)
                continue;
            com += entry.getKey() + "=" + "" + entry.getValue().toString().replace("\'", "\'\'") + ",";


        }
        if (com.length() > 0)
            com = com.substring(0, com.length() - 1);

        return com;
    }

    public static Object InsertCommandWithASqlTableAndAExcel(Map<String, testModel> map, String tableName, Class clazz) {
        boolean sqlcommand = false;
        String mapValues = "";
        String mapNames = "";
        for (Map.Entry<String, testModel> item : map.entrySet()) {
            if (item.getValue().getValue() == null)
                continue;
            if (!item.getValue().isDbAdd())
                continue;
            String fieldName = DataService.getFieldAColumn(clazz, item.getValue().getColumnKey());
            //String fieldName = DataService.getFieldAColumn(clazz, item.getKey());

            sqlcommand = true;

            mapValues += "'" + item.getValue().getValue().toString().replace("\'", "\'\'") + "',";
            //postgreSqlde büyük harfli bir sütun bulunuyor ise " işareti şarttır
            //bu yüzden veriler kücük harfle girilmelidir Locale default En olarak seçilmeli
            try {
                mapNames += fieldName.toLowerCase(Locale.ENGLISH) + ",";

            } catch (Exception e) {
                if (item.getKey().contains("_"))
                    mapNames += item.getKey().toLowerCase(Locale.ENGLISH) + ",";

            }

        }
        if (!sqlcommand)
            return 1;
        mapValues = mapValues.substring(0, mapValues.length() - 1);
        mapNames = mapNames.substring(0, mapNames.length() - 1);
        String com = " (" + mapNames + ")" + " values (" + mapValues + ")";
        return new PostgreSqlManager().AddError("insert into " + tableName + " " + com);
    }
}
