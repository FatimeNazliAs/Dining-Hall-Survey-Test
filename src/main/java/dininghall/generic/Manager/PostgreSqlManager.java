package dininghall.generic.Manager;


import dininghall.common.ConnectionFactory;
import dininghall.generic.Service.DataService;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostgreSqlManager {
    public static int Add = 1;
    public static int Update = 2;
    public static int Delete = 3;
    public static int Select = 4;

    public void CreateTables() {


    }

    public static Connection Connection() {
        try {
            return ConnectionFactory.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static int Add(String command) {

        Connection connection = Connection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            int key = -1;
            if (rs.next()) {
                key = rs.getInt(1);
            }
            stmt.close();
            connection.close();
            return key;

        } catch (SQLException e) {
            try {
                e.printStackTrace();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            return -1;
        }
    }

    public static Object AddError(String command) {

        Connection connection = Connection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            int key = -1;
            if (rs.next()) {
                key = rs.getInt(1);
            }
            stmt.close();
            connection.close();
            return key;

        } catch (SQLException e) {
            return e.getMessage();

        }
    }


    public static boolean Command(String command) {

        Connection connection = Connection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(command);


            stmt.close();
            connection.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static <T> ArrayList<T> Read(T obj, String command) {

        Connection connection = Connection();

        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            ResultSet set = stmt.executeQuery(command);
            ArrayList<Map<String, Object>> maps = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            while (set.next()) {
                map = new HashMap<>();
                ResultSetMetaData setMetaData = set.getMetaData();

                for (int i = 1; i <= setMetaData.getColumnCount(); i++) {
                    if (set.getObject(i) != null)
                        map.put(setMetaData.getColumnName(i).toLowerCase(), set.getObject(i));
                }

                maps.add(map);

            }

            stmt.close();
            connection.close();

            return DataService.ConvertData(obj, maps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //whereCommandThenAndOrThenWherecommand
    public static <T> void Delete(String tableName, String... whereCommandThenAndOrThenWherecommand) {
        if (whereCommandThenAndOrThenWherecommand != null) {
            String q = "";
            for (String item : whereCommandThenAndOrThenWherecommand) {
                q += " " + item;
            }
            String sql = "DELETE FROM " + tableName + " WHERE " + q + ";";

            Command(sql);
        }
    }

    public static <T> boolean DeleteLazy(String tableName, String... whereCommandThenAndOrThenWherecommand) {
        if (whereCommandThenAndOrThenWherecommand != null) {
            String q = "";
            for (String item : whereCommandThenAndOrThenWherecommand) {
                q += " " + item;
            }
            String sql = "DELETE FROM " + tableName + " " + q + ";";

            return Command(sql);
        }
        return false;
    }


    public static <T> boolean Update(String tableName, String newData) {

        //  String id = DataService.getIdData(obj);
        //  String oldaData = PropertyManager.InsertDataSyntax(Read(obj, "Select * from " + tableName + " where id=" + id));
        String sql = "Update " + tableName + " SET " + newData;
        //    new LogService().Update(newData, oldaData, tableName);
        return Command(sql);
    }


    public static <T> ArrayList<T> get(T obj, String sql) {
        return Read(obj, sql);
    }

    public static <T> ArrayList<T> getLazy(T obj, String sql, boolean notfilternot) {

        return Read(obj, sql);
    }

    public static <T> ArrayList<T> getDeclared(T obj, String tableName, int first, int count) {
        String wh = " ORDER BY id OFFSET ('" + first + "') ROWS FETCH NEXT ('" + (count) + "') ROWS ONLY";
        String sql = "Select * from " + tableName +
                " " + wh;
        return Read(obj, sql);
    }


    public static <T> ArrayList<T> getLazy(T item, String sql) {
        sql = "Select * from " + sql;
        return Read(item, sql);
    }

    public static int GetRow(String command) {
        Connection connection = Connection();
        Statement stmt = null;
        int count = 0;

        try {
            stmt = connection.createStatement();
            ResultSet set = stmt.executeQuery(command);
            while (set.next()) {

                ResultSetMetaData setMetaData = set.getMetaData();
                count = set.getInt("count");

            }

            stmt.close();
            connection.close();
            return count;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }


    public static <T> int AddL(String tableName, T obj) {
        // String data = PropertyManager.InsertDataSyntax(obj);
        //   new LogService().Add(data, tableName, Add);
        String sql = "Insert Into " + tableName + " " + PropertyManager.fieldToInsertCommand(obj);
        return Add(sql);
    }
    public static <T> String AddSyntax(String tableName, T obj) {
        // String data = PropertyManager.InsertDataSyntax(obj);
        //   new LogService().Add(data, tableName, Add);
        String sql = "Insert Into " + tableName + " " + PropertyManager.fieldToInsertCommand(obj)+";";
        return sql;
    }
}
