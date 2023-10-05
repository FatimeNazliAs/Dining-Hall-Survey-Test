package dininghall.asstpackages.ExcelPackage.Annatations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AExcelDbDistinct  {
     String databaseTableName() ;

     String databaseDisplayColumnName();
     String databaseReferanceColumnName();
     String indirectTableName() default "";
     String distincColumnHeader() default "";
     String indirectKey()default "";
     String indirectName()default "";
     boolean indirect() default false;
     //sütundaki tüm datalar aynı mı
      boolean sameData() default false;
}
