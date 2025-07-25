package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestService {
    public void saveGuest(Guest guest) throws SQLException {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelkey", "root", "drowningBlueNile13!?");
            ps = con.prepareStatement("INSERT INTO guests (guest_name, guest_lastname, guest_email) VALUES (?, ?, ?)");
            ps.setString(1, guest.getGuestName());
            ps.setString(2, guest.getGuestLastname());
            ps.setString(3, guest.getGuestEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    public Guest getGuestById(String guestId) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelkey", "root", "drowningBlueNile13!?"
            );
            ps = con.prepareStatement("SELECT * FROM guests WHERE guest_id = ?");
            ps.setString(1, guestId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Guest guest = new Guest("2","Taylor","Swift","taylorswift89@gmail.com","Singer");
                guest.setGuestId(rs.getString("guest_id"));
                guest.setGuestName(rs.getString("guest_name"));
                guest.setGuestLastname(rs.getString("guest_lastname"));
                guest.setGuestEmail(rs.getString("guest_email"));
                return guest;
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

    public List<Guest> getAllGuests() throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        List<Guest> guests = new ArrayList<>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelkey", "root", "drowningBlueNile13!?"
            );
            ps = con.prepareStatement("SELECT * FROM guests");
            rs = ps.executeQuery();
            while (rs.next()) {
                Guest guest = new Guest("1", "Diellza", "Behadini", "diellzab2003@gmail.com", "Intern");
                guest.setGuestId(rs.getString("guest_id"));
                guest.setGuestName(rs.getString("guest_name"));
                guest.setGuestLastname(rs.getString("guest_lastname"));
                guest.setGuestEmail(rs.getString("guest_email"));
                guests.add(guest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return guests;
    }
}
