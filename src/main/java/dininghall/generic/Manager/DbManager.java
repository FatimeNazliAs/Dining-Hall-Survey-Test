package dininghall.generic.Manager;


import dininghall.domain.survey.Survey;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import dininghall.generic.Service.DataService;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


// getName(manager) bir sonraki aşamada eğer getTableName yoksa >  getName(manager) çağıracak
public class DbManager
{
    ISqlTable manager;
    String tableName;
    DbManager dbManager;


    public String getTableName()
    {
        return tableName;
    }


    public DbManager(ISqlTable manager)
    {
        this.manager = manager;
        tableName = getName(manager);
    }

    public DbManager(Class<?> manager)
    {
        try
        {
            this.manager = (ISqlTable) manager.newInstance();
            tableName = getName(this.manager);

        } catch (InstantiationException e)
        {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }

    }

    public DbManager(String tableName)
    {
        this.tableName = tableName;
    }


    public <T> int Add()
    {
        return PostgreSqlManager.AddL(getName(manager), manager);
    }

    public <T> List<T> AddAll(List<T> list)
    {
        if (list == null || list.isEmpty())
            return null;
        String syntax = "insert into " + getName(manager) + "  ";
        boolean first = false;
        String parametres = "";
        for (var item : list)
        {
            if (first)
                syntax += " , ";
            if (!first)
                syntax += "  (" + PropertyManager.fieldToInsertNameValue(item)[0] + ")  values ";
            first = true;
            syntax += " (" + PropertyManager.fieldToInsertNameValue(item)[1] + " ) ";
        }
        syntax += " ON CONFLICT DO NOTHING RETURNING *;";
        return (List<T>) PostgreSqlManager.Read(manager, syntax);
    }


    public <T> ArrayList<T> Get(String... whereCommands)
    {
        String wh = " where ";
        for (String item : whereCommands)
        {
            wh += item + " ";
        }

        String sql = "Select * from " + getName(manager) +
                " " + wh;

        return (ArrayList<T>) PostgreSqlManager.get(manager, sql);

    }

    public <T> ArrayList<T> GetListWithFullCommand(String sql)
    {
        return (ArrayList<T>) PostgreSqlManager.get(manager, sql);
    }

    public <T> T GetFirst(String... whereCommands)
    {
        String wh = "";
        if (whereCommands.length > 0)
        {
            wh = " where ";
            for (String item : whereCommands)
            {
                wh += item + " ";
            }
        }
        String sql = "Select * from " + getName(manager) +
                " " + wh;
        ArrayList<ISqlTable> g = (ArrayList<ISqlTable>) PostgreSqlManager.get(manager, sql);
        if (g != null && g.size() > 0)
            return (T) g.get(0);
        else
            return (T) DataService.newInstance(manager);

    }


    public <T> ArrayList<T> GetAll()
    {
        String sql = "Select * from " + getName(manager);

        return (ArrayList<T>) PostgreSqlManager.get(manager, sql);
    }


    public <T> void Delete(String... whereCommandThenAndOrThenWherecommand)
    {
        if (whereCommandThenAndOrThenWherecommand != null && whereCommandThenAndOrThenWherecommand.length > 0)
            PostgreSqlManager.Delete(getName(manager), whereCommandThenAndOrThenWherecommand);
        else
            PostgreSqlManager.Delete(getName(manager), " id='" + DataService.getIdData(manager) + "'");
    }

    public <T> boolean DeleteLazy(String... query)
    {
        return PostgreSqlManager.DeleteLazy(getName(manager), query);
    }


    public <T> boolean Update()
    {
        String newData = PropertyManager.fieldToUpdateCommand(manager);
        return PostgreSqlManager.Update(getName(manager), newData);
    }

    public <T> boolean Update(T obj)
    {
        String newData = PropertyManager.fieldToUpdateCommand(obj);
        return PostgreSqlManager.Update(getName(manager), newData);
    }

    public <T> boolean UpdateMap(Map<String, Object> map, String primaryValue)
    {
        String newData = PropertyManager.fieldToUpdateCommandMap(map, DataService.getAnnationToFieldPrimary(manager.getClass()), primaryValue);
        return PostgreSqlManager.Update(getName(manager), newData);
    }

    public <T> boolean UpdateLazy(String query)
    {
        return PostgreSqlManager.Update(getName(manager), query);
    }


    public String globalFilterRun(Object value)
    {
        String prop = "( Cast(id as TEXT) ILIKE '%" + value + "%' OR " +



                /*   " Cast(date as TEXT) ILIKE '%" + value + "%' OR " +*/
                " Cast(type as TEXT) ILIKE '%" + value + "%' ) ";

        return prop;
    }


    public int GetCountRows(String command)
    {

        String prop = DataService.getAnnationToFieldPrimary(manager.getClass());
        if (prop == null)
            System.err.println("Hata primarykey yok. Çözüm : " + getTableName() + " nesnesine primary key ekleyin!!");
        return PostgreSqlManager.GetRow("Select count(" + prop + ") from " + getName(manager) + " " + command);
    }

    public <T> ArrayList<T> GetLazy(int first, int count)
    {
        String wh = "";

        AColumn column = DataService.getAnnationToFieldDefault(manager.getClass());

        String prop = "";

        if (column != null && column.name() != "")
        {
            prop = column.name() + " " + column.defaultvalue() != "" ? column.defaultvalue() : " asc " + " ";
        } else
        {
            AColumn column1 = DataService.getAnnationToFieldPrimaryField(manager.getClass());
            if (column1 != null)
            {
                prop = column1.name() + " " + column1.defaultvalue();
            }
        }

        if (prop != null && prop != "")
            wh = " ORDER BY " + prop + " ";
        wh += " OFFSET ('" + first + "') ROWS FETCH NEXT ('" + (count) + "') ROWS ONLY";
        String sql = "Select * from " + getName(manager) +
                " " + wh;

        return (ArrayList<T>) PostgreSqlManager.Read(manager, sql);
    }

    public <T> ArrayList<T> GetLazyFCQuery(int first, int count, String query)
    {
        String wh = "";

        AColumn column = DataService.getAnnationToFieldDefault(manager.getClass());

        String prop = "";

        if (column != null && column.name() != "")
        {
            prop = (column.name() + " ") + (column.defaultvalue() != "" ? column.defaultvalue() : " asc " + " ");
        } else
        {
            AColumn column1 = DataService.getAnnationToFieldPrimaryField(manager.getClass());
            if (column1 != null)
            {
                prop = column1.name() + " " + column1.defaultvalue();
            }
        }
        if (query.length() > 0)
            wh = " where " + query + " ";
        if (prop != null && prop != "")
            wh += " ORDER BY " + prop + " ";
        wh += " OFFSET ('" + first + "') ROWS FETCH NEXT ('" + (count) + "') ROWS ONLY";
        String sql = "Select * from " + getName(manager) +
                " " + wh;

        return (ArrayList<T>) PostgreSqlManager.Read(manager, sql);
    }

    public <T> ArrayList<T> GetLazy(int first, int count, String fieldAndSort)
    {
        String sql = getName(manager) + " order by " + FilterString(first, count, fieldAndSort);


        return (ArrayList<T>) PostgreSqlManager.getLazy(manager, sql);
    }

    public <T> ArrayList<T> GetLazyNoOrder(int first, int count, String fieldAndSort)
    {
        String sql = getName(manager) + "  " + FilterString(first, count, fieldAndSort);


        return (ArrayList<T>) PostgreSqlManager.getLazy(manager, sql);
    }

    public <T> ArrayList<T> GetLazyJson(int first, int count, String fieldAndSort)
    {
        String sql = getName(manager) + " " + FilterString(first, count, fieldAndSort);


        return (ArrayList<T>) PostgreSqlManager.getLazy(manager, sql);
    }

    public String getName(ISqlTable manager)
    {
        String tableName = null;
        if (this.tableName != null && this.tableName.isEmpty())
            this.tableName = manager.getClass().getAnnotation(ASqlTable.class).TableName();
        return this.tableName != null ? this.tableName : "";
//hata tablo adı bulunamadı
    }

    public String FilterString(int first, int count, String fieldAndSort)
    {
        return fieldAndSort + "  OFFSET ('" + first + "') ROWS FETCH NEXT ('" + (count) + "') ROWS ONLY";
    }

    public Class getISqlTableClass()
    {
        return manager.getClass();
    }
}
