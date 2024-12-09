package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class myJDBC {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://cis3270project.mysql.database.azure.com:3306/project3270",
                    "username",
                    "password123$"
            );
        } catch (Exception e) {
            System.out.println("Cannot connect to the database: " + e.getMessage());
        }
        return connection;
    }
}