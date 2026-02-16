package hotel;

import java.sql.*;

public class BookingDAO {

    public int createBooking(String customerName, int roomId, int days, double totalAmount) {
        String sql = "INSERT INTO bookings (customer_name, room_id, number_of_days, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customerName);
            ps.setInt(2, roomId);
            ps.setInt(3, days);
            ps.setDouble(4, totalAmount);

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);   // booking_id
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating booking: " + e.getMessage());
        }
        return -1;
    }

    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
            return false;
        }
    }

    public Booking getBookingById(int bookingId) {
        String sql = "SELECT booking_id, customer_name, room_id, number_of_days, total_amount FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                            rs.getInt("booking_id"),
                            rs.getString("customer_name"),
                            rs.getInt("room_id"),
                            rs.getInt("number_of_days"),
                            rs.getDouble("total_amount")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching booking: " + e.getMessage());
        }
        return null;
    }
}
