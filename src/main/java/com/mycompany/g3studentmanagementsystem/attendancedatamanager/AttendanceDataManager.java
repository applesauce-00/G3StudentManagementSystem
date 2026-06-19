package com.mycompany.g3studentmanagementsystem.attendancedatamanager;

import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class AttendanceDataManager {

	public static ArrayList<String> studentIds = new ArrayList<>();
	// Reusable method to load records into any subject's table model.
	// Uses a LEFT JOIN to guarantee every student shows up, even if they don't have an attendance row yet.

	public static boolean loadAttendance(DefaultTableModel tableModel, String subjectName) {
		// Query pulls directly from the master students list, then checks for existing attendance metrics.
		// COALESCE automatically fill 'Absent' if no record row exists yet.
		String query = "SELECT s.student_id, "
				+ "       CONCAT(s.last_name, ', ', s.first_name) AS full_name, "
				+ "       COALESCE(a.week_1, 'Absent') AS w1, "
				+ "       COALESCE(a.week_2, 'Absent') AS w2, "
				+ "       COALESCE(a.week_3, 'Absent') AS w3, "
				+ "       COALESCE(a.week_4, 'Absent') AS w4, "
				+ "       COALESCE(a.week_5, 'Absent') AS w5, "
				+ "       COALESCE(a.week_6, 'Absent') AS w6, "
				+ "       COALESCE(a.week_7, 'Absent') AS w7, "
				+ "       COALESCE(a.week_8, 'Absent') AS w8, "
				+ "       COALESCE(a.week_9, 'Absent') AS w9, "
				+ "       COALESCE(a.week_10, 'Absent') AS w10 "
				+ "FROM students s "
				+ "LEFT JOIN student_attendance a ON s.student_id = a.student_id AND a.subject = ?"
				+ "WHERE s.is_active = 1 "
				+ "ORDER BY s.last_name ASC";

		try (Connection conn = ConnectionString.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, subjectName);

			try (ResultSet rs = pstmt.executeQuery()) {
				tableModel.setRowCount(0); // Reset UI Table rows

				studentIds.clear();

				while (rs.next()) {
					studentIds.add(rs.getString("student_id"));

					Object[] rowData = new Object[11];
					rowData[0] = rs.getString("full_name");
					rowData[1] = rs.getString("w1");
					rowData[2] = rs.getString("w2");
					rowData[3] = rs.getString("w3");
					rowData[4] = rs.getString("w4");
					rowData[5] = rs.getString("w5");
					rowData[6] = rs.getString("w6");
					rowData[7] = rs.getString("w7");
					rowData[8] = rs.getString("w8");
					rowData[9] = rs.getString("w9");
					rowData[10] = rs.getString("w10");

					tableModel.addRow(rowData);
				}
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// Reusable method to save batch changes from any subject table model.
	// Uses an INSERT - ON DUPLICATE KEY UPDATE statement to handle new or existing records.
	public static boolean saveAttendance(DefaultTableModel tableModel, String subjectName) {
		// This query updates an existing row, OR inserts a brand new row if it's the first time saving this student's attendance.
		String query = "INSERT INTO student_attendance (student_id, student_name, subject, week_1, week_2, week_3, week_4, week_5, week_6, week_7, week_8, week_9, week_10) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE "
				+ "week_1=?, week_2=?, week_3=?, week_4=?, week_5=?, week_6=?, week_7=?, week_8=?, week_9=?, week_10=?";

		try (Connection conn = ConnectionString.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			int totalRows = tableModel.getRowCount();
			for (int r = 0; r < totalRows; r++) {
				String studentId = studentIds.get(r);
				String studentName = tableModel.getValueAt(r, 0).toString();

				String w1 = tableModel.getValueAt(r, 1).toString();
				String w2 = tableModel.getValueAt(r, 2).toString();
				String w3 = tableModel.getValueAt(r, 3).toString();
				String w4 = tableModel.getValueAt(r, 4).toString();
				String w5 = tableModel.getValueAt(r, 5).toString();
				String w6 = tableModel.getValueAt(r, 6).toString();
				String w7 = tableModel.getValueAt(r, 7).toString();
				String w8 = tableModel.getValueAt(r, 8).toString();
				String w9 = tableModel.getValueAt(r, 9).toString();
				String w10 = tableModel.getValueAt(r, 10).toString();

				// Bind parameters for the INSERT part
				pstmt.setString(1, studentId);
				pstmt.setString(2, studentName);
				pstmt.setString(3, subjectName);
				pstmt.setString(4, w1);
				pstmt.setString(5, w2);
				pstmt.setString(6, w3);
				pstmt.setString(7, w4);
				pstmt.setString(8, w5);
				pstmt.setString(9, w6);
				pstmt.setString(10, w7);
				pstmt.setString(11, w8);
				pstmt.setString(12, w9);
				pstmt.setString(13, w10);

				// Bind parameters for the ON DUPLICATE KEY UPDATE part
				pstmt.setString(14, w1);
				pstmt.setString(15, w2);
				pstmt.setString(16, w3);
				pstmt.setString(17, w4);
				pstmt.setString(18, w5);
				pstmt.setString(19, w6);
				pstmt.setString(20, w7);
				pstmt.setString(21, w8);
				pstmt.setString(22, w9);
				pstmt.setString(23, w10);

				pstmt.addBatch();
			}

			pstmt.executeBatch();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
