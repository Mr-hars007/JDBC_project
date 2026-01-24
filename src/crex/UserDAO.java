// Handles all database operations related to users
// Provides method to register a new user into the system
// Uses PreparedStatement to prevent SQL injection
// Catches duplicate user registrations gracefully

package crex;

import java.sql.*;
import java.util.Scanner;

public class UserDAO {

    public static void registerUser(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter name: ");
            String name = sc.next();

            System.out.print("Enter email: ");
            String email = sc.next();

            String sql = "INSERT INTO users(name, email) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);

            ps.executeUpdate();
            System.out.println("User registered successfully!");

        } catch (Exception e) {
            System.out.println("User already exists or error occurred.");
        }
    }
}
