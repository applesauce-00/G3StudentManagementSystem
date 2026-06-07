package com.mycompany.g3studentmanagementsystem.attendancedatamanager;

import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class AttendanceDataManager {

    /**
     * Reusable method to load records into any subject's table model.
     */
    public static boolean loadAttendance(DefaultTableModel tableModel, String subjectName) {
        String query = "SELECT * FROM student_attendance WHERE subject = ?";
        
        try (Connection conn = ConnectionString.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, subjectName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0); // Reset UI Table rows
                
                while (rs.next()) {
                    Object[] rowData = new Object[12];
                    rowData[0] = rs.getString("student_id");
                    rowData[1] = rs.getString("student_name");              
                    rowData[2] = rs.getString("week_1");
                    rowData[3] = rs.getString("week_2");
                    rowData[4] = rs.getString("week_3");
                    rowData[5] = rs.getString("week_4");
                    rowData[6] = rs.getString("week_5");
                    rowData[7] = rs.getString("week_6");
                    rowData[8] = rs.getString("week_7");
                    rowData[9] = rs.getString("week_8");
                    rowData[10] = rs.getString("week_9");
                    rowData[11] = rs.getString("week_10");
                    
                    tableModel.addRow(rowData);
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Reusable method to save batch changes from any subject table model.
     */
    public static boolean saveAttendance(DefaultTableModel tableModel, String subjectName) {
        String query = "UPDATE student_attendance SET week_1=?, week_2=?, week_3=?, week_4=?, week_5=?, "
                    + "week_6=?, week_7=?, week_8=?, week_9=?, week_10=? "
                    + "WHERE student_id=? AND subject=?";
        
        try (Connection conn = ConnectionString.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            int totalRows = tableModel.getRowCount();
            for (int r = 0; r < totalRows; r++) {
                pstmt.setString(1, tableModel.getValueAt(r, 2).toString());
                pstmt.setString(2, tableModel.getValueAt(r, 3).toString());
                pstmt.setString(3, tableModel.getValueAt(r, 4).toString());
                pstmt.setString(4, tableModel.getValueAt(r, 5).toString());
                pstmt.setString(5, tableModel.getValueAt(r, 6).toString());
                pstmt.setString(6, tableModel.getValueAt(r, 7).toString());
                pstmt.setString(7, tableModel.getValueAt(r, 8).toString());
                pstmt.setString(8, tableModel.getValueAt(r, 9).toString());
                pstmt.setString(9, tableModel.getValueAt(r, 10).toString());
                pstmt.setString(10, tableModel.getValueAt(r, 11).toString());
                pstmt.setString(11, tableModel.getValueAt(r, 0).toString()); 
                pstmt.setString(12, subjectName); // Binds "Science", "Math", or "English" here
                
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
