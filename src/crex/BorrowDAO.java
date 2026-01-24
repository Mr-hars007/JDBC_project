// Handles all database operations related to borrowed resources
// Provides method log borrowed resources into the system
// Uses PreparedStatement to prevent SQL injection
// Catches duplicate borrow request gracefully


package crex;

import java.sql.*;
import java.util.Scanner;

public class BorrowDAO {

    public static void borrowResource(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter resource ID: ");
            int resId = sc.nextInt();

            System.out.print("Enter your user ID: ");
            int userId = sc.nextInt();

            String check = "SELECT is_available FROM resources WHERE resource_id=?";
            PreparedStatement cps = con.prepareStatement(check);
            cps.setInt(1, resId);

            ResultSet rs = cps.executeQuery();

            if (rs.next() && rs.getBoolean(1)) {

                String insert =
                        "INSERT INTO borrow_records(resource_id, borrower_id) VALUES (?, ?)";
                PreparedStatement ips = con.prepareStatement(insert);
                ips.setInt(1, resId);
                ips.setInt(2, userId);
                ips.executeUpdate();

                String update =
                        "UPDATE resources SET is_available=false WHERE resource_id=?";
                PreparedStatement ups = con.prepareStatement(update);
                ups.setInt(1, resId);
                ups.executeUpdate();

                System.out.println("Resource borrowed successfully!");

            } else {
                System.out.println("Resource not available.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void returnResource(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter resource ID: ");
            int resId = sc.nextInt();

            String updateRecord =
                    "UPDATE borrow_records SET return_date=CURRENT_TIMESTAMP " +
                    "WHERE resource_id=? AND return_date IS NULL";
            PreparedStatement ps1 = con.prepareStatement(updateRecord);
            ps1.setInt(1, resId);
            ps1.executeUpdate();

            String updateResource =
                    "UPDATE resources SET is_available=true WHERE resource_id=?";
            PreparedStatement ps2 = con.prepareStatement(updateResource);
            ps2.setInt(1, resId);
            ps2.executeUpdate();

            System.out.println("Resource returned successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
