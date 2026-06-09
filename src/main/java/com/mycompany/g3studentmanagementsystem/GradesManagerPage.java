package com.mycompany.g3studentmanagementsystem;

import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

public class GradesManagerPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JButton btnAttendance, btnStudents, btnGrades, btnSignOut;
    private JButton btnSearch, btnEdit;
    private JTable tblGrades;
    private JScrollPane tableScroll;
    private JTextField txtSearchId;

    private DefaultTableModel model;

    public GradesManagerPage() {

        setTitle("FACULTY PORTAL - Grades Manager");
        setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        
        lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(10, 10, 60, 60);
        add(lblIcon);

        
        lblTitle = new JLabel("FACULTY PORTAL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(80, 20, 200, 40);
        add(lblTitle);

        
        btnAttendance = new JButton("ATTENDANCE");
        btnAttendance.setBounds(300, 20, 120, 40);
        btnAttendance.setBackground(new Color(52, 168, 235));
        btnAttendance.setForeground(Color.WHITE);
        add(btnAttendance);

        btnStudents = new JButton("STUDENTS");
        btnStudents.setBounds(430, 20, 120, 40);
        btnStudents.setBackground(new Color(52, 168, 235));
        btnStudents.setForeground(Color.WHITE);
        add(btnStudents);

        btnGrades = new JButton("GRADES");
        btnGrades.setEnabled(false);
        btnGrades.setBounds(560, 20, 120, 40);
        btnGrades.setBackground(new Color(52, 168, 235));
        btnGrades.setForeground(Color.WHITE);
        add(btnGrades);

        btnSignOut = new JButton("Sign Out");
        btnSignOut.setBounds(850, 20, 120, 40);
        btnSignOut.setBackground(new Color(224, 69, 52));
        btnSignOut.setForeground(Color.WHITE);
        add(btnSignOut);

        
        txtSearchId = new JTextField();
        txtSearchId.setBounds(20, 100, 160, 35);
        add(txtSearchId);

        btnSearch = new JButton("SEARCH STUDENT");
        btnSearch.setBounds(20, 140, 160, 40);
        btnSearch.setBackground(new Color(52, 168, 235));
        btnSearch.setForeground(Color.WHITE);
        add(btnSearch);

        
        btnEdit = new JButton("EDIT GRADES");
        btnEdit.setBounds(20, 200, 160, 40);
        btnEdit.setBackground(new Color(52, 168, 235));
        btnEdit.setForeground(Color.WHITE);
        add(btnEdit);

        
        String[] columns = {
                "STUDENT ID", "NAME", "SECTION",
                "MATH", "SCIENCE", "ENGLISH",
                "GWA", "GRADE STATUS"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tblGrades = new JTable(model);
        tableScroll = new JScrollPane(tblGrades);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        
        btnAttendance.addActionListener(this);
        btnStudents.addActionListener(this);
        btnSearch.addActionListener(this);
        btnEdit.addActionListener(this);
        btnSignOut.addActionListener(this);

       
        loadGradesFromDatabase();
    }

    
    public void loadGradesFromDatabase() {
        // Uses a LEFT JOIN to gather students master list combined with grades data rows
        // IF a student has no grade row yet, COALESCE will fill the cell
        String sql = "SELECT s.student_id, " +
                     "       CONCAT(s.last_name, ', ', s.first_name) AS full_name, " +
                     "       s.section, " +
                     "       COALESCE(g.math_grade, 0.0) AS math_grade, " +
                     "       COALESCE(g.science_grade, 0.0) AS science_grade, " +
                     "       COALESCE(g.english_grade, 0.0) AS english_grade, " +
                     "       COALESCE(g.gwa, 0.0) AS gwa, " +
                     "       COALESCE(g.grade_status, 'PENDING') AS grade_status " +
                     "FROM students s " +
                     "LEFT JOIN student_grades g ON s.student_id = g.student_id";

        try (Connection con = ConnectionString.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("section"),
                        String.format("%.2f", rs.getDouble("math_grade")),
                        String.format("%.2f", rs.getDouble("science_grade")),
                        String.format("%.2f", rs.getDouble("english_grade")),
                        String.format("%.2f", rs.getDouble("gwa")),
                        rs.getString("grade_status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading records from database: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnEdit) {

            int selectedRow = tblGrades.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a student first!");
                return;
            }

            GradesEditorPage gep =
                    new GradesEditorPage(this, selectedRow, model);

            gep.setVisible(true);
            setVisible(false);
        }

        else if (e.getSource() == btnSearch) {

            String search = txtSearchId.getText().trim();

            boolean found = false;

            for (int i = 0; i < model.getRowCount(); i++) {

                if (model.getValueAt(i, 0).toString().equals(search)) {

                    tblGrades.setRowSelectionInterval(i, i);
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, "Student Found!");
            } else {
                JOptionPane.showMessageDialog(this, "Student Not Found!");
            }
        }

        else if (e.getSource() == btnStudents) {
            new StudentManagerPage().setVisible(true);
            dispose();
        }
		else if (e.getSource() == btnAttendance) {
            AttendanceEnglishPage aep = new AttendanceEnglishPage();
            aep.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnSignOut) {
            new LandingPageGUI().setVisible(true);
            dispose();
        }
    }
}