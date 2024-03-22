package main;

import static utils.JDBCUtils.*;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnection {
    public static void main(String[] args) {
        try (Connection connection = getNewConnection()) {

        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }
    }
}
