package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    public static Connection getNewConnection() throws SQLException {
        String dbURL = "jdbc:sqlite:hospital-db.sqlite";
        Connection connection = DriverManager.getConnection(dbURL);

        if (connection.isValid(1)) {
            System.out.println("Connection successful");
        }

        return connection;
    }
}
