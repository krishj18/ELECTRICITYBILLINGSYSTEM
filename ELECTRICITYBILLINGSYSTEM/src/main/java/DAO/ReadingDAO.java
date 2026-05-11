package DAO;
import MODEL.meterreading;
import UTIL.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ReadingDAO {
    // Save a new meter reading
    public boolean addReading(meterreading r) {
        String sql = "INSERT INTO meter_readings (customer_id, previous_reading, current_reading, reading_date) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getCustomerId());
            ps.setInt(2, r.getPreviousReading());
            ps.setInt(3, r.getCurrentReading());
            ps.setDate(4, new java.sql.Date(r.getReadingDate().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all readings
    public List<meterreading> getAllReadings() {
        List<meterreading> list = new ArrayList<>();
        String sql = "SELECT mr.id, mr.customer_id, c.name, c.meter_number, " +
                "mr.previous_reading, mr.current_reading, mr.reading_date " +
                "FROM meter_readings mr JOIN customers c ON mr.customer_id = c.id";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new meterreading(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("meter_number"),
                        rs.getInt("previous_reading"),
                        rs.getInt("current_reading"),
                        rs.getDate("reading_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get latest reading for a specific customer
    // (needed to auto-fill previous reading in the form)
    public meterreading getLatestReading(int customerId) {
        String sql = "SELECT mr.id, mr.customer_id, c.name, c.meter_number, " +
                "mr.previous_reading, mr.current_reading, mr.reading_date " +
                "FROM meter_readings mr JOIN customers c ON mr.customer_id = c.id " +
                "WHERE mr.customer_id = ? ORDER BY mr.reading_date DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new meterreading(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("meter_number"),
                        rs.getInt("previous_reading"),
                        rs.getInt("current_reading"),
                        rs.getDate("reading_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no previous reading found
    }
}
