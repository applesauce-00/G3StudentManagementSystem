package com.mycompany.g3studentmanagementsystem.attendancedatamanager;

import com.mycompany.g3studentmanagementsystem.attendancedatamanager.AttendanceDataManager;
import javax.swing.*;
import javax.swing.table.*;

public class TableandDataLogic {

    public static void setupAttendanceTable(JTable table, DefaultTableModel model, JComboBox<String> box) {
        table.setModel(model);
        for (int i = 2; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(box));
        }
    }

    public static void loadData(DefaultTableModel model, String subject, JFrame parent) {
        if (!AttendanceDataManager.loadAttendance(model, subject)) {
            JOptionPane.showMessageDialog(parent, "Failed to load " + subject + " records!");
        }
    }

    public static void saveData(JTable table, DefaultTableModel model, String subject, JFrame parent) {
        if (table.isEditing()) table.getCellEditor().stopCellEditing();
        
        if (AttendanceDataManager.saveAttendance(model, subject)) {
            JOptionPane.showMessageDialog(parent, subject + " Attendance saved successfully!");
        } else {
            JOptionPane.showMessageDialog(parent, "Error while processing " + subject + " database updates.");
        }
    }
}