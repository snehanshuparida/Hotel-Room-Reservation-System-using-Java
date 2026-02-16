package hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT room_id, room_type, price_per_night, is_available FROM rooms WHERE is_available = TRUE";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("room_id"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getBoolean("is_available")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching available rooms: " + e.getMessage());
        }
        return rooms;
    }

    public Room getRoomById(int roomId) {
        String sql = "SELECT room_id, room_type, price_per_night, is_available FROM rooms WHERE room_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                            rs.getInt("room_id"),
                            rs.getString("room_type"),
                            rs.getDouble("price_per_night"),
                            rs.getBoolean("is_available")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room: " + e.getMessage());
        }
        return null;
    }

    public void updateRoomAvailability(int roomId, boolean available) {
        String sql = "UPDATE rooms SET is_available = ? WHERE room_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, available);
            ps.setInt(2, roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating room availability: " + e.getMessage());
        }
    }
}
