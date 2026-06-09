package com.mycompany.g3studentmanagementsystem;

import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.util.ArrayList;
import java.sql.*;	

public class FacultyDataManager {

    public static int validateLogin(String id, String password) {
        
        String loginQuery = "SELECT * FROM faculty WHERE faculty_id=? AND password=?";
        String idCheckQuery = "SELECT * FROM faculty WHERE faculty_id=?";

 
        try (Connection con = ConnectionString.getConnection()) {
            
            // Check Login 
            try (PreparedStatement ps = con.prepareStatement(loginQuery)) {
                ps.setString(1, id);
                ps.setString(2, password);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return 0; // Login successful
                    }
                }
            }

            // Check if ID exists
            try (PreparedStatement ps = con.prepareStatement(idCheckQuery)) {
                ps.setString(1, id);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return 1; // ID exists, wrong password
                    }
                }
            }

            return 2; // ID does not exist

        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // Database error
        }
    }
}