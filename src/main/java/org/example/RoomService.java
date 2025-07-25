package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelkey";
    private static final String USER = "root";
    private static final String PASSWORD = "drowningBlueNile13!?";

    public void saveRoom(Room room) throws SQLException {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = con.prepareStatement(
                    "INSERT INTO rooms_db (id, type, rate, booked, bed_count) " +
                            "VALUES (?, ?, ?, ?, ?) AS new " +
                            "ON DUPLICATE KEY UPDATE " +
                            "type = new.type, rate = new.rate, booked = new.booked, bed_count = new.bed_count"
            );

            ps.setString(1, room.getId());
            ps.setString(2, room.getType());
            ps.setDouble(3, room.getRate());
            ps.setBoolean(4, room.isBooked());
            ps.setInt(5, room.getBedCount());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    public Room getRoomById(String roomId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM rooms_db WHERE id = ?");
            ps.setString(1, roomId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Room room = new Room(rs.getString("id"), rs.getString("type"), rs.getDouble("rate"));
                if (rs.getBoolean("booked")) room.book();
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return null;
    }

    public List<Room> getAllRooms() throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        List<Room> rooms = new ArrayList<>();
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = con.prepareStatement("SELECT * FROM rooms_db");
            rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room(rs.getString("id"), rs.getString("type"), rs.getDouble("rate"));
                if (rs.getBoolean("booked")) room.book();
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return rooms;
    }


    public void updateRoomById(String roomId, Room newRoom) throws SQLException {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = con.prepareStatement("UPDATE rooms_db SET type = ?, rate = ?, booked = ?, bed_count = ? WHERE id = ?");
            ps.setString(1, newRoom.getType());
            ps.setDouble(2, newRoom.getRate());
            ps.setBoolean(3, newRoom.isBooked());
            ps.setInt(4, newRoom.getBedCount());
            ps.setString(5, roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }


    public void deleteRoomById(String roomId) throws SQLException {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = con.prepareStatement("DELETE FROM rooms_db WHERE id = ?");
            ps.setString(1, roomId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }
}
