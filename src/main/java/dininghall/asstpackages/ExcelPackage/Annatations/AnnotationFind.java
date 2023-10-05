package dininghall.asstpackages.ExcelPackage.Annatations;

import dininghall.asstpackages.ExcelPackage.Services.DataService;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AnnotationFind implements Serializable
{
    public static <T> AExcel findAExcel(T clazz)
    {
        if (clazz == null)
            return null;
        var a = clazz.getClass().getAnnotation(AExcel.class);
        var ab = clazz.getClass().getAnnotation(AExcel.class);
        return a;
    }

    public static <T> AExcel findAExcelWithClass(Class clazz)
    {
        if (clazz == null)
            return null;
        var a = clazz.getAnnotation(AExcel.class);
        var ab = clazz.getAnnotation(AExcel.class);
        return (AExcel) a;
    }


    public static <T> AExcelColumn findAExcelColumn(T clazz, String fieldName)
    {
        if (findAExcel(clazz) == null || fieldName == null || fieldName.length() < 1)
            return null;
        var field = DataService.findFieldName(clazz, fieldName);
        if (field != null)
        {
            AExcelColumn aExcelColumn = field.getAnnotation(AExcelColumn.class);
            if (aExcelColumn != null)
                return aExcelColumn;
        }
        return null;

    }

    public static <T> AExcelColumn findAExcelColumnFindColumnName(T clazz, String columnName)
    {
        if (findAExcel(clazz) == null || columnName == null || columnName.length() < 1)
            return null;
        for (Field field : clazz.getClass().getDeclaredFields())
        {
            AExcelColumn column = field.getAnnotation(AExcelColumn.class);
            if (column != null && column.displayName().equals(columnName))
                return column;

        }
        return null;

    }

    public static <T> String findFieldNameFindColumnName(T clazz, String columnName)
    {
        if (findAExcel(clazz) == null || columnName == null || columnName.length() < 1)
            return null;
        for (var field : clazz.getClass().getDeclaredFields())
        {
            AExcelColumn column = field.getAnnotation(AExcelColumn.class);
            if (column != null && column.displayName().equals(columnName))
                return field.getName();
        }
      /*  var descriptors = PropertyUtils.getPropertyDescriptors(clazz);
        for (:)
        {

        }
        for (var field : descriptors)
        {
            AExcelColumn column = field.getPropertyType().getAnnotation(AExcelColumn.class);
            if (column != null && column.displayName().equals(columnName))
                return column;

        }*/
        return null;

    }

    public static <T> boolean isExcelColumn(T clazz, String fieldName)
    {
        if (findAExcel(clazz) == null || fieldName == null || fieldName.length() < 1)
            return false;
        var field = DataService.findFieldName(clazz, fieldName);
        if (field != null)
        {
            AExcelColumn aExcelColumn = field.getAnnotation(AExcelColumn.class);
            if (aExcelColumn != null)
                return true;
        }
        return false;

    }

    public static <T> Map<String, String> getAnnotationColumnNameAndFieldName(T clazz)
    {
        Map<String, String> names = new HashMap<>();
        for (Field field : clazz.getClass().getDeclaredFields())
        {
            AExcelColumn column = findAExcelColumn(clazz, field.getName());
            if (column != null)
                names.put(column.displayName(), field.getName());
        }
        return names;
    }


    public static <T> AExcelDbDistinct getAnnotationExcelDist(T clazz, String fieldName)
    {
        if (findAExcel(clazz) == null || fieldName == null || fieldName.length() < 1)
            return null;
        var field = DataService.findFieldName(clazz, fieldName);
        if (field != null)
        {
            AExcelDbDistinct aExcelDbDistinct = field.getAnnotation(AExcelDbDistinct.class);
            if (aExcelDbDistinct != null)
                return aExcelDbDistinct;
        }
        return null;
    }
    public static <T> AExcelDbDistinct getAnnotationExcelDistWithColumnKey(T clazz, String columnKey)
    {
        if (findAExcel(clazz) == null || columnKey == null || columnKey.length() < 1)
            return null;
        var field = DataService.findAexcelcolumnColumnKey(clazz, columnKey);
        if (field != null)
        {

            AExcelDbDistinct aExcelDbDistinct = field.getAnnotation(AExcelDbDistinct.class);
            if (aExcelDbDistinct != null)
                return aExcelDbDistinct;
        }
        return null;
    }


    public static <T> AExcelDbDistinct findAExcelDistFindColumnName(T clazz, String fieldName)
    {
        if (findAExcel(clazz) == null || fieldName == null || fieldName.length() < 1)
            return null;
        for (Field field : clazz.getClass().getDeclaredFields())
        {
            if (field.getName().equals(fieldName))
            {
                return field.getAnnotation(AExcelDbDistinct.class);
            }

        }
        return null;
    }


    public static <T> @Nullable Map<String, String> getAExcelMethodMap(T clazz )
    {
        Map<String, String> methods = new HashMap<>();
        Field[] fields = clazz.getClass().getDeclaredFields();
        if (findAExcel(clazz) == null )
            return methods;
        for (Field f : fields)
        {
            AExcelMethod method = f.getAnnotation(AExcelMethod.class);
            if (method != null)
                methods.put(f.getName(), method.methodName());

        }
        return methods;

    }
}
