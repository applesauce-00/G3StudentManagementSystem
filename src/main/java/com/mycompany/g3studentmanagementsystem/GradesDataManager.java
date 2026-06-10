package com.mycompany.g3studentmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;

public class GradesDataManager {

    public static boolean updateGrade(
            String studentId,
            String name,  
            String section,   
            double math,
            double science,
            double english,
            double gwa,
            String status) {

        if (math >= 4.00 || science >= 4.00 || english >= 4.00) {
        status = "FAILED";
		}
		else if (gwa <= 3.00) {
			status = "PASSED";
		}
		else {
			status = "FAILED";
		}

		String checkSql =
				"SELECT COUNT(*) FROM student_grades WHERE student_id = ?";

		String updateSql =
				"UPDATE student_grades " +
				"SET math_grade=?, science_grade=?, english_grade=?, gwa=?, grade_status=? " +
				"WHERE student_id=?";

		String insertSql =
				"INSERT INTO student_grades " +
				"(math_grade, science_grade, english_grade, gwa, grade_status, student_id, name, section) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection con = ConnectionString.getConnection()) {
            
            boolean recordExists = false;
            
            try (PreparedStatement psCheck = con.prepareStatement(checkSql)) {
                psCheck.setString(1, studentId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        recordExists = true;
                    }
                }
            }

            String finalSql = recordExists ? updateSql : insertSql;

            try (PreparedStatement ps = con.prepareStatement(finalSql)) {
                
                ps.setDouble(1, math);
                ps.setDouble(2, science);
                ps.setDouble(3, english);
                ps.setDouble(4, gwa);
                ps.setString(5, status);
                ps.setString(6, studentId);

                // If it's a brand new record, fill the extra name & section slots
                if (!recordExists) {
                    ps.setString(7, name);
                    ps.setString(8, section);
                }

                int rows = ps.executeUpdate();
                return rows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}