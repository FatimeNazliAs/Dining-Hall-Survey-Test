package dininghall.generic.Service;


import dininghall.generic.Service.DataConverter.Primitives;
import org.ocpsoft.shade.org.apache.commons.beanutils.PropertyUtils;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class DataService {
    DataService() {
        Locale.setDefault(Locale.ENGLISH);
    }

    public static <T> @Nullable String getFieldAColumn(Class _class, String name) {

        Field[] fields = _class.getDeclaredFields();
        if (_class.getAnnotation(ASqlTable.class) == null)
            return null;
        for (Field f : fields)
            if (f.getName().equalsIgnoreCase(name))
                return f.getAnnotation(AColumn.class) == null
                        ? f.getName() :
                        f.getAnnotation(AColumn.class).name().toLowerCase(Locale.ENGLISH);
        return null;

    }

    public static <T> @Nullable String getAnnationToFieldPrimaryFieldName(Class _class) {

        Field[] fields = _class.getDeclaredFields();
        if (_class.getAnnotation(ASqlTable.class) == null)
            return null;
        for (Field f : fields)
            if (f.getAnnotation(AColumn.class) != null && f.getAnnotation(AColumn.class).primary() == true) {
                return f.getName();
            }

        return null;

    }

    public static <T> @Nullable String getAnnationToFieldName(Class _class, String name) {

        Field[] fields = _class.getDeclaredFields();
        if (_class.getAnnotation(ASqlTable.class) == null)
            return null;
        for (Field f : fields)
            if (f.getAnnotation(AColumn.class) != null && f.getAnnotation(AColumn.class).name().equalsIgnoreCase(name)) {
                return f.getName().toLowerCase(Locale.ENGLISH);
            }

        return null;

    }

    public static <T> @Nullable String getAnnationToFieldPrimary(Class _class) {

        Field[] fields = _class.getDeclaredFields();
        if (_class.getAnnotation(ASqlTable.class) == null)
            return null;
        for (Field f : fields)
            if (f.getAnnotation(AColumn.class) != null && f.getAnnotation(AColumn.class).primary() == true) {
                return f.getAnnotation(AColumn.class).name().toLowerCase(Locale.ENGLISH);
            }

        return null;

    }

    public static <T> @Nullable AColumn getAnnationToFieldPrimaryField(Class _class) {

        Field[] fields = _class.getDeclaredFields();
        if (_class.getAnnotation(ASqlTable.class) == null)
            return null;
        for (Field f : fields)
            if (f.getAnnotation(AColumn.class) != null && f.getAnnotation(AColumn.class).primary() == true) {
                return f.getAnnotation(AColumn.class);
            }

        return null;

    }

    public static <T> @Nullable AColumn getAnnationToFieldDefault(Class _class) {

        Field[] fields = _class.getDeclaredFields();
        if (_class.getAnnotation(ASqlTable.class) == null)
            return null;
        for (Field f : fields)
            if (f.getAnnotation(AColumn.class) != null && f.getAnnotation(AColumn.class).defaultby() == true) {
                return f.getAnnotation(AColumn.class);
            }

        return null;

    }

    public static <T> Class getFieldAColumnClass(Class _class, String name) {
        Field[] fields = _class.getDeclaredFields();

        for (Field f : fields)
            if (f.getAnnotation(AColumn.class) != null)
                if (f.getAnnotation(AColumn.class).name().equalsIgnoreCase(name))
                    return f.getType();
        return null;

    }

    public static <T> Map<String, Object> ConvertDataForObjectList(T obj) throws NoSuchFieldException {
        Locale.setDefault(Locale.ENGLISH);
        //  Map<String, Method> methods = getMethodsGet(obj);
        //   Field[] c = obj.getClass().getDeclaredFields();

        Map<String, Object> data_t = new HashMap<>();
        if (obj.getClass().getAnnotation(ASqlTable.class) == null) {
            System.err.println("Tablonun sql bağlantısı bulunaması / ASqlTable ");
            return null;
        }
        var sad = PropertyUtils.getPropertyDescriptors(obj);
        for (var item : PropertyUtils.getPropertyDescriptors(obj)) {
            //getAnnotation değilse veritabanı sutunu değildir
            if (item.getName().equalsIgnoreCase("class"))
                continue;
            AColumn column = obj.getClass().getDeclaredField(item.getName()).getAnnotation(AColumn.class);
            if (column == null)
                continue;

            try {
                Method getMethod = item.getReadMethod();
                if (getMethod != null) {
                    //item.getName() >item.getAnnotation(AColumn.class).name() olarak değiştirildi
                    //
                    data_t.put(column.name(), getMethod.invoke(obj));

                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return data_t;
    }


    public static String getIdData(Object obj) {
        String dataId = "";
        try {
            dataId = obj.getClass().getMethod("getId").invoke(obj).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return dataId;
    }


    public static <T> String getTableName(Class<T> class_) {

        return class_.getAnnotation(ASqlTable.class).TableName();

//hata tablo adı bulunamadı
    }


    public static <T> T ConvertData(T obj, Map<String, Object> data) {
        Locale.setDefault(Locale.ENGLISH);
        try {
            obj = (T) obj.getClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        //sonraki versiyonda komple mape dönüştürülecektir
        Field[] fields = obj.getClass().getDeclaredFields(); //System.out.println(item.get(obj).toString()+"");

        //Map<String, Method> setmethods = setMethodsGet(obj);
        for (var item : PropertyUtils.getPropertyDescriptors(obj)) {
            Method setMethod = item.getWriteMethod();


            try {
                if (setMethod != null && obj.getClass().getDeclaredField(item.getName()).getAnnotation(AColumn.class) != null) {
                    try {
                        obj = setFieldWithMethodInvoke(obj, setMethod, obj.getClass().getDeclaredField(item.getName()), data.get(obj.getClass().getDeclaredField(item.getName()).getAnnotation(AColumn.class).name().toLowerCase(Locale.ENGLISH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

        }
        //   documentIdSet(obj,  data.getId());
        return obj;
    }

    public static <T> ArrayList<T> ConvertData(T obj, ArrayList<Map<String, Object>> data) {
        ArrayList<T> allTArrayList = new ArrayList<>();
        for (Map<String, Object> snapshot : data) {
            allTArrayList.add(ConvertData(obj, snapshot));
        }
        return allTArrayList;
    }


    public static <T> T setFieldWithMethodInvoke(T obj, Method method, Field item, Object data) {
        Object obj2 = null;
        try {
            if (data != null) {

                obj2 = Primitives.Convert(item, data);
                if (obj2 != null)
                    method.invoke(obj, obj2);
                else
                    method.invoke(obj, data);
            }
        } catch (Exception e) {

            System.err.println("Veritabanı ve Java arası veri uyuşmazlığı: " + item.getName() + " " + item.getType().getName() + " " + data.getClass().getName());
            e.printStackTrace();
        }
        return obj;
    }


    public static <T> T newInstance(T obj) {
        try {
            return (T) obj.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


}
