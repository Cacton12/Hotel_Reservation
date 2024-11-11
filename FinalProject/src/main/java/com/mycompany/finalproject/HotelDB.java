/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotelDB {

    public static Connection GetConnection() {
        try {
            String user = "root";
            String password = "";
            String dbURL = "jdbc:mysql://localhost:3306/HotelDB";
            System.out.println("Connection made");
            return DriverManager.getConnection(dbURL, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void Disconnect(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean AddReservation(HotelReservation hr, Connection conn) {
        String sql = "INSERT INTO reservations(arrival_date, departure_date, num_nights, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(hr.getArrival_date()));
            pstmt.setDate(2, java.sql.Date.valueOf(hr.getDeparture_date()));
            pstmt.setInt(3, hr.getNumNights());
            pstmt.setDouble(4, hr.getPrice());
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static List<HotelReservation> GetReservations(ResultSet rs) throws SQLException {
        List<HotelReservation> reservations = new ArrayList<>();
        try {
            if (rs.first()) {
                do {
                    LocalDate arrival_date = rs.getDate("arrival_date").toLocalDate();
                    LocalDate departure_date = rs.getDate("departure_date").toLocalDate();
                    int num_nights = rs.getInt("num_nights");
                    double price = rs.getDouble("price");

                    HotelReservation reservation = new HotelReservation(arrival_date, departure_date, num_nights, price);
                    reservations.add(reservation);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (reservations.isEmpty()) {
            return null;
        }else{
            return reservations;
            }
    }

    public static ResultSet GetAllRecords(Connection conn) {
        String sql = "SELECT * FROM reservations";
        try {
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return s.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
