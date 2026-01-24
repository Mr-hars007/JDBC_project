// Provides a centralized method to create and return a MySQL database connection
// Loads the MySQL JDBC driver explicitly
// Keeps database credentials in one place for easy maintenance
// Used by all DAO classes to avoid repeated connection code

package crex;

import java.sql.*;

public class DBConnection {

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/practice";
        String user = "root";
        String password = "root";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
