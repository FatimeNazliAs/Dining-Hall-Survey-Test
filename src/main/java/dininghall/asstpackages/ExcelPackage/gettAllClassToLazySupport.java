package dininghall.asstpackages.ExcelPackage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//gettAllClassToLazySupport(iteratorfolders)
public class gettAllClassToLazySupport
{

    public static void main(String[] args) throws ParseException
    {


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        long ts = dateFormat.parse("10/25/2022").getTime()/1000;
ts=ts;
    }
public void  getAllClass(){
    Locale.setDefault(Locale.ENGLISH);


    List<Class> classForNames=new ArrayList<>();
    try (Stream<Path> paths = Files.walk(Paths.get("src\\main\\java\\com\\asstnavi\\domain")))
    {

        var as = paths.filter(Files::isRegularFile);
        classForNames = new ArrayList<>();
        for (var a : as.collect(Collectors.toList()))
        {
            var ssd = a.getRoot();
            var ds = a.getFileName();
            var sadsad1 = a.getNameCount();
            var sadsad12 = a.getParent();

            if (!a.toString().endsWith(".java"))
                continue;
//                return Class.forName(" uk.co.digiturk.asstpackages.domain.employee." + "Employee");
            var asddd="\\";
            String aa=" uk.co.digiturk.asstpackages.domain."+a.toString().split("domain")[1].substring(1).replace("\\",".").replace(".java","");
            classForNames.add( Class.forName(aa));
        }
    } catch (IOException e)
    {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e)
    {
        throw new RuntimeException(e);
    }


    for (var ob:classForNames)
    {

        System.out.println("\n @ASqlTable(TableName = veritabanıtabloadıekleyin!!!) \n public class " + ob.getSimpleName() + "{ \n");
        for (Field field : ob.getDeclaredFields())
        {
            String fieldName = field.getName();

            String datA = fieldName;
            int i = -1;

            String anew = datA;
            for (var c : datA.toCharArray())
            {
                i++;

                boolean t = ((c + "").toLowerCase()).equals((c + ""));
                if (t)
                    continue;
                boolean a = (c + "").equals((c + "").toUpperCase());
                if (!a)
                    continue;
                anew = anew.substring(0, i) + "_" + anew.substring(i, anew.length());

                i++;

            }
            if (anew.length()>4)
                if (anew.substring(anew.length() - 4).equals("_Dtr"))
                    anew = anew.substring(0, anew.length() - 4) + anew.substring(anew.length() - 3);
            System.out.println("@AColumn(name=\"" + anew.toLowerCase(Locale.ENGLISH) +"\") \n" + "private " + field.getType().getName() + " " + field.getName() + ";");
        }
        System.out.println("\n}");
    }


}

}