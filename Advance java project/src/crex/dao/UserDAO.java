package crex.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import crex.db.DBConnection;

public class UserDAO {
    private static int currentUserId = 1; 
    private static List<String> mockUsers = new ArrayList<>();

    public static int getCurrentUserId() { return currentUserId; }

    public static void registerUser(String name, String email) throws Exception {
        if (DBConnection.checkAvailability()) {
            try (Connection con = DBConnection.getConnection()) {
                String sql = "INSERT INTO users(name, email) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.executeUpdate();
            }
        } else {
            mockUsers.add(name + " (" + email + ")");
        }
    }

    public static boolean login(String username, String password) {
        if (DBConnection.checkAvailability()) {
            try (Connection con = DBConnection.getConnection()) {
                String sql = "SELECT * FROM users WHERE name=? AND email=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password); // Mocking password check as email
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    currentUserId = rs.getInt("id");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return !username.isEmpty() && !password.isEmpty();
    }
}
