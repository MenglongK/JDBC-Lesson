package co.istad.jdbc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static Connection conn;

    // get connection object for next step
    public static Connection getConn() {
        return conn;
    }

    // initialize connection at the first time
    public static void init(){
        if (conn == null) {

            // Step 1. load driver of PostgresSQL
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Step 2. define connection URL
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Properties info = new Properties();
            info.put("user", "postgres");
            info.put("password", "qwer");

            // Step 3. establish connection
            try {
                conn = DriverManager.getConnection(url, info);
                System.out.println(conn.getSchema());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
