package com.mycompany.g3studentmanagementsystem;

import java.util.ArrayList;
import java.sql.*;	

public class FacultyDataManager {

    public static ArrayList<Faculty> facultyList = new ArrayList<>();



    public static int validateLogin(String id, String password) {

    try {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/g3studentmgmtsystem",
                "root",
                ""
        );

        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM faculty WHERE faculty_id=? AND password=?"
        );

        ps.setString(1, id);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            con.close();
            return 0;
        }

        // Checking of ID if it exist
        ps = con.prepareStatement(
                "SELECT * FROM faculty WHERE faculty_id=?"
        );

        ps.setString(1, id);

        rs = ps.executeQuery();

        if (rs.next()) {
            con.close();
            return 1;
        }

        con.close();
        return 2;

    } catch (SQLException e) {
        e.printStackTrace();
        return 2;
    }
}
}