package crex.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import crex.db.DBConnection;
import crex.model.Resource;

public class ResourceDAO {
    public static List<Resource> mockResources = new ArrayList<>();

    static {
        mockResources.add(new Resource(101, "Java Programming Book", 1, true));
        mockResources.add(new Resource(102, "Scientific Calculator", 2, true));
        mockResources.add(new Resource(103, "Lab Coat", 1, true));
    }

    public static void addResource(String title, int ownerId) throws Exception {
        if (DBConnection.checkAvailability()) {
            try (Connection con = DBConnection.getConnection()) {
                String sql = "INSERT INTO resources(title, owner_id) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, title);
                ps.setInt(2, ownerId);
                ps.executeUpdate();
            }
        } else {
            int newId = 100 + mockResources.size() + 1;
            mockResources.add(new Resource(newId, title, ownerId, true));
        }
    }

    public static List<Resource> getAvailableResources() throws Exception {
        if (DBConnection.checkAvailability()) {
            List<Resource> list = new ArrayList<>();
            try (Connection con = DBConnection.getConnection()) {
                String sql = "SELECT * FROM resources WHERE is_available=true";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    list.add(new Resource(rs.getInt("resource_id"), rs.getString("title"), rs.getInt("owner_id"), true));
                }
            }
            return list;
        } else {
            List<Resource> available = new ArrayList<>();
            for (Resource r : mockResources) {
                if (r.isAvailable()) available.add(r);
            }
            return available;
        }
    }

    public static List<Resource> getBorrowedByMe() throws Exception {
        if (DBConnection.checkAvailability()) {
            List<Resource> list = new ArrayList<>();
            try (Connection con = DBConnection.getConnection()) {
                String sql = "SELECT * FROM resources WHERE is_available=false";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    list.add(new Resource(rs.getInt("resource_id"), rs.getString("title"), rs.getInt("owner_id"), false));
                }
            }
            return list;
        } else {
            List<Resource> borrowed = new ArrayList<>();
            for (Resource r : mockResources) {
                if (!r.isAvailable()) borrowed.add(r);
            }
            return borrowed;
        }
    }
}
