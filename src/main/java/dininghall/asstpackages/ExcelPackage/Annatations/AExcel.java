package dininghall.asstpackages.ExcelPackage.Annatations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/**
 sheetName ve ClassAdı Aynı Olmak zorunda
 * */
public @interface AExcel {

    public int max() default 999;

    //   public Class updateId() ;
    /**update property name*/
    public String updateId();
    public String sheetName()  ;
    public String sqlTableName() ;


}
