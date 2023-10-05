package dininghall.asstpackages.ExcelPackage.Services;

//import org.apache.commons.beanutils.PropertyUtils;
import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import dininghall.asstpackages.ExcelPackage.Annatations.AnnotationFind;
import dininghall.asstpackages.ExcelPackage.Excel.ExcelModel;
//eklendi
import org.ocpsoft.shade.org.apache.commons.beanutils.PropertyUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
        import java.util.*;


public class DataService implements Serializable
{
    public static <T> List<ExcelModel> getData(T obj) {
        List<ExcelModel> map = new ArrayList<>();

        int i = 0;
        var descriptors = PropertyUtils.getPropertyDescriptors(obj);
        for (var field : descriptors) {
            if (!AnnotationFind.isExcelColumn(obj, field.getName()))
                continue;
            ExcelModel model = new ExcelModel();
            AExcelColumn column= AnnotationFind.findAExcelColumn(obj,field.getName());
            model.setDisplayName(column.displayName());
            model.setFieldName(field.getName());
            model.setColumnName(column.displayName());
            model.setDisplayOrder(column.displayOrder());
            model.setListTitle(!column.listTitle().equals("") ?column.listTitle():field.getName());
            model.setAExcelColumn(column);
            try {
                model.setValue(field.getReadMethod().invoke(obj));
                if (model.getValue() == null)
                    model.setValue("");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            map.add(model);


        }
        map.sort(Comparator.comparing(ExcelModel::getDisplayOrder));
        return map;

    }

    public static <T> List<String> getColumnNames(T clazz) {
        int temp_ = 0;
        List<String> colNames =new ArrayList<>();
        if (AnnotationFind.findAExcel(clazz) == null)
            return null;

        for (var field : clazz.getClass().getDeclaredFields()) {
            if (!AnnotationFind.isExcelColumn(clazz,field.getName()))
                continue;
            colNames.add( getSimpleColumnName(clazz, field));
        }
        return colNames;
    }

    public static <T> String getSimpleColumnName(T clazz, Field field) {
        if (AnnotationFind.findAExcelColumn(clazz, field.getName()) != null) {

            var objd = AnnotationFind.findAExcelColumn(clazz, field.getName());
            if (objd != null)
                return objd.displayName();
            else
                return field.getName();
        }
        return null;
    }

    public static <T> String getExcelColumnName(T clazz, String fieldName) {
        if (AnnotationFind.findAExcelColumn(clazz, fieldName) != null) {

            var objd = AnnotationFind.findAExcelColumn(clazz, fieldName);
            if (objd != null)
                return objd.displayName();
            else
                return fieldName;
        }
        return null;
    }


    public static <T> String getExcelColumnDisplayName(T clazz, String fieldName) {
        if (AnnotationFind.findAExcelColumn(clazz, fieldName) != null) {

            var objd = AnnotationFind.findAExcelColumn(clazz, fieldName);
            if (objd != null)
                return objd.displayName();
            else
                return fieldName;
        }
        return null;
    }

    public static <T> Field findFieldName(T clazz_, String fieldName) {
        for (Field field : clazz_.getClass().getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(fieldName))
                return field;
        }
        return null;
    }    public static <T> Field findAexcelcolumnColumnKey(T clazz_, String columnKey) {
    for (Field field : clazz_.getClass().getDeclaredFields()) {
        var fieldAnnatation=field.getAnnotation(AExcelColumn.class);
        if(fieldAnnatation!=null)
        {
            if (fieldAnnatation.columnKey().equals(columnKey))
                return field;
        }
    }
    return null;
}


    public static <T> String getInvokeToString(T clazz_, String fieldName) {
        return "";
    }
}
