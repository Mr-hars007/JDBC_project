package crex.db;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/practice";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static Boolean isAvailable = null;

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean checkAvailability() {
        if (isAvailable != null) return isAvailable;
        try (Connection conn = getConnection()) {
            isAvailable = true;
            return true;
        } catch (Exception e) {
            System.out.println("Real database not found. Falling back to Mock mode.");
            isAvailable = false;
            return false;
        }
    }
}
