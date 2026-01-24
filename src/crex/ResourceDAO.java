// Handles all database operations related to Resources
// Provides method to log resources into the system
// Uses Prepared Statement to prevent SQL injection
// Catches duplicate resources gracefully

package crex;

import java.sql.*;
import java.util.Scanner;

public class ResourceDAO {

    public static void addResource(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter resource title: ");
            sc.nextLine();
            String title = sc.nextLine();

            System.out.print("Enter owner user ID: ");
            int ownerId = sc.nextInt();

            String sql = "INSERT INTO resources(title, owner_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setInt(2, ownerId);

            ps.executeUpdate();
            System.out.println("Resource added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewAvailable() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT resource_id, title FROM resources WHERE is_available=true";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\nAvailable Resources:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("resource_id") + " - " +
                        rs.getString("title")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
