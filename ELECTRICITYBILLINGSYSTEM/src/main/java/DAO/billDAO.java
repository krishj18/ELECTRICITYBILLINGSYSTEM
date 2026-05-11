package DAO;
import UTIL.DBConnection;
import MODEL.bill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class billDAO {
    public billDAO() {
    }

    public boolean addBill(MODEL.bill b) {
        String sql = "INSERT INTO bills (customer_id, units_consumed, amount, bill_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, b.getcustomerid());
            ps.setInt(2, b.getunitsconsumed());
            ps.setDouble(3, b.getamount());
            ps.setDate(4, new java.sql.Date(b.getbilldate().getTime()));
            ps.setString(5, b.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all bills (with customer name by joining tables)
    public List<bill> getAllBills() {
        List<bill> list = new ArrayList<>();
        String sql = "SELECT b.id, b.customer_id, c.name, b.units_consumed, " +
                "b.amount, b.bill_date, b.status " +
                "FROM bills b JOIN customers c ON b.customer_id = c.id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getInt("units_consumed"),
                        rs.getDouble("amount"),
                        rs.getDate("bill_date"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get bills of a specific customer
    public List<billDAO> getBillsByCustomer(int customerId) {
        List<billDAO> list = new ArrayList<>();
        String sql = "SELECT b.id, b.customer_id, c.name, b.units_consumed, " +
                "b.amount, b.bill_date, b.status " +
                "FROM bills b JOIN customers c ON b.customer_id = c.id " +
                "WHERE b.customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new billDAO(
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Mark bill as paid
    public boolean updateBillStatus(int billId, String status) {
        String sql = "UPDATE bills SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a bill
    public boolean deleteBill(int billId) {
        String sql = "DELETE FROM bills WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

