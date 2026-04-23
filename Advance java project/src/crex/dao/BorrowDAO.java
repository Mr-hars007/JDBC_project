package crex.dao;

import java.sql.*;
import crex.db.DBConnection;
import crex.model.Resource;

public class BorrowDAO {
    public static boolean borrowResource(int resId, int userId) throws Exception {
        if (DBConnection.checkAvailability()) {
            try (Connection con = DBConnection.getConnection()) {
                String sql = "UPDATE resources SET is_available=false WHERE resource_id=? AND is_available=true";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, resId);
                return ps.executeUpdate() > 0;
            }
        } else {
            for (Resource r : ResourceDAO.mockResources) {
                if (r.getResourceId() == resId && r.isAvailable()) {
                    r.setAvailable(false);
                    return true;
                }
            }
            return false;
        }
    }

    public static void returnResource(int resId) throws Exception {
        if (DBConnection.checkAvailability()) {
            try (Connection con = DBConnection.getConnection()) {
                String sql = "UPDATE resources SET is_available=true WHERE resource_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, resId);
                ps.executeUpdate();
            }
        } else {
            for (Resource r : ResourceDAO.mockResources) {
                if (r.getResourceId() == resId) {
                    r.setAvailable(true);
                }
            }
        }
    }
}
