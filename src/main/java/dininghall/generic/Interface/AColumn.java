package dininghall.generic.Interface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AColumn {
    String name();

    boolean primary() default false;

    boolean defaultby() default false;

    boolean databaseData() default true;

    String defaultvalue() default "asc";
 /*   String setMethodName();
    String getMethodName();*/
}
