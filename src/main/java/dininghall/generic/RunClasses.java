package dininghall.generic;


import dininghall.generic.Manager.PostgreSqlManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//gettAllClassToLazySupport(iteratorfolders)
public class RunClasses {


    public static void main(String[] args) throws ParseException, FileNotFoundException {
//getAllClass();
        getDbOAllTables();
    }

    public static void getDbOAllTables() {

        var list = PostgreSqlManager
                .Read(new information_schema(), "SELECT table_catalog,table_schema,table_name,column_name,data_type   FROM information_schema.columns WHERE table_schema = 'asstnavi_schema' order by table_name;");

        list.sort((Comparator.comparing(information_schema::getTable_name)));
        for (var item : list) {
            System.out.println(item.table_name + "\t" + item.column_name + "\t" + item.data_type.replace("character varying", "String").replace("timestamp without time zone", "Date").replace("numeric", "integer").replace("text", "String"));
        }

    }


    public static void getAllClass() {
        Locale.setDefault(Locale.ENGLISH);


        List<Class> classForNames = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("src\\main\\java\\com\\digiturkcouk\\domain"))) {

            var as = paths.filter(Files::isRegularFile);
            classForNames = new ArrayList<>();
            for (var a : as.collect(Collectors.toList())) {
                var ssd = a.getRoot();
                var ds = a.getFileName();
                var sadsad1 = a.getNameCount();
                var sadsad12 = a.getParent();

                if (!a.toString().endsWith(".java"))
                    continue;
//                return Class.forName(" uk.co.digiturk.asstpackages.domain.employee." + "Employee");
                var asddd = "\\";
                String aa = "uk.co.digiturk.domain." + a.toString().split("domain")[1].substring(1).replace("\\", ".").replace(".java", "");
                classForNames.add(Class.forName(aa));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        for (var ob : classForNames) {

            System.out.println("\n @ASqlTable(TableName = veritabanıtabloadıekleyin!!!) \n public class " + ob.getSimpleName() + " implements Serializable, ISqlTable { \n");
            for (Field field : ob.getDeclaredFields()) {
                String fieldName = field.getName();

                String datA = fieldName;
                int i = -1;

                String anew = datA;
                for (var c : datA.toCharArray()) {
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
                if (anew.length() > 4)
                    if (anew.substring(anew.length() - 4).equals("_Dtr") || anew.substring(anew.length() - 4).equals("_str"))
                        anew = anew.substring(0, anew.length() - 4) + anew.substring(anew.length() - 3);
                System.out.println("@AColumn(name=\"" + anew.toLowerCase(Locale.ENGLISH) + "\") \n" + "private " + field.getType().getName() + " " + field.getName() + ";");
            }
            System.out.println("\n}");
        }


    }

}