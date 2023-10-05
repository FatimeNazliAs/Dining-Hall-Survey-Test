/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.integration.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Tolga
 */
public class RemoteConnectionFactory {

    // MSSQL
    private final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final String CONNECTION_URL = "jdbc:sqlserver://88.247.67.58\\SQLEXPRESS:1433;database=BarkoDB_V1_ELMEDINA18_YENI;";
    private final String DB_USER = "digiturkcouk_sa";
    private final String DB_PASSWORD = "DigiturkCoUk2011!";
    private final String SSL_ENABLED = "true";

    private static RemoteConnectionFactory connectionFactory = null;

    private RemoteConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        props.setProperty("ssl", SSL_ENABLED);

        Connection conn = null;
        conn = DriverManager.getConnection(CONNECTION_URL, props);

        return conn;
    }

    public synchronized static RemoteConnectionFactory getInstance() {

        if (connectionFactory == null) {
            connectionFactory = new RemoteConnectionFactory();
        }

        return connectionFactory;
    }

    public synchronized void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
