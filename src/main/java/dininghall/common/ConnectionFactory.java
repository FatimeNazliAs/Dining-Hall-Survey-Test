/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Tolga
 */
public class ConnectionFactory
{


    // Postgresql
    private final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "12345";
    private final String DB_CURRENT_SCHEMA = "dininghallsurvey_schema";
    private final String SSL_ENABLED = "false";

    private static ConnectionFactory connectionFactory = null;

    private ConnectionFactory()
    {
        try
        {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException
    {
        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        props.setProperty("ssl", SSL_ENABLED);
        props.setProperty("currentSchema", DB_CURRENT_SCHEMA);

        Connection conn = null;
        conn = DriverManager.getConnection(CONNECTION_URL, props);

        return conn;
    }

    public synchronized static ConnectionFactory getInstance()
    {

        if (connectionFactory == null)
        {
            connectionFactory = new ConnectionFactory();
        }

        return connectionFactory;
    }

    public synchronized void closeConnection(Connection conn)
    {
        if (conn != null)
        {
            try
            {
                conn.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public synchronized void closeStatement(Statement statement)
    {
        if (statement != null)
        {
            try
            {
                statement.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
