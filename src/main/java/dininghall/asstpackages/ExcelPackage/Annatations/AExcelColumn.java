package dininghall.asstpackages.ExcelPackage.Annatations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AExcelColumn {

    String displayName();


    String Indirect() default "";

    boolean IndirectColumn() default false;
    boolean fake() default false;
    String cellType();
    boolean nonZero()default false;

    int displayOrder();
    boolean hide() default true;

    String displayColor() default "";

    boolean database() default false;
    boolean required() default false;
    //şimdilik listeye veriler name:value olarak eklenecek
    //daha sonra gson olarak name value olarak sıralanacak
    String[] getlist() default {};

    String listTitle() default "";



    String columnKey() default "";
}
