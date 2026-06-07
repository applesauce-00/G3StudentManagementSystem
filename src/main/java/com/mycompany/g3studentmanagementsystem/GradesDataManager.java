package com.mycompany.g3studentmanagementsystem;

import java.sql.*;

public class GradesDataManager {

    public static boolean updateGrade(
            String studentId,
            double math,
            double science,
            double english,
            double gwa,
            String status) {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/g3studentmanagementsystem",
                    "root",
                    ""
            );

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE student_grades " +
                    "SET math_grade=?, science_grade=?, english_grade=?, gwa=?, grade_status=? " +
                    "WHERE student_id=?"
            );

            ps.setDouble(1, math);
            ps.setDouble(2, science);
            ps.setDouble(3, english);
            ps.setDouble(4, gwa);
            ps.setString(5, status);
            ps.setString(6, studentId);

            int rows = ps.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}