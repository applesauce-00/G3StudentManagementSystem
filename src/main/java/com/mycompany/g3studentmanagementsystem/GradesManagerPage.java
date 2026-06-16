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
    private JTextField txtSearchName;

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

        txtSearchName = new JTextField();
        txtSearchName.setToolTipText("Search by last name");
        txtSearchName.setBounds(20, 100, 160, 35);
        add(txtSearchName);

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

        // STUDENT ID at index 0 is hidden — keeps ID available for DB lookups
        String[] columns = {
                "STUDENT ID",    // index 0 — hidden
                "NAME",          // index 1
                "SECTION",       // index 2
                "MATH",          // index 3
                "SCIENCE",       // index 4
                "ENGLISH",       // index 5
                "GWA",           // index 6
                "GRADE STATUS"   // index 7
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tblGrades = new JTable(model);
        tblGrades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblGrades.setRowSelectionAllowed(true);

        // Hide STUDENT ID column visually — still readable via getValueAt(row, 0)
        tblGrades.getColumnModel().getColumn(0).setMinWidth(0);
        tblGrades.getColumnModel().getColumn(0).setMaxWidth(0);
        tblGrades.getColumnModel().getColumn(0).setWidth(0);

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
        String sql = "SELECT s.student_id, " +
                     "       CONCAT(s.last_name, ', ', s.first_name) AS full_name, " +
                     "       s.section, " +
                     "       COALESCE(g.math_grade, 0.0) AS math_grade, " +
                     "       COALESCE(g.science_grade, 0.0) AS science_grade, " +
                     "       COALESCE(g.english_grade, 0.0) AS english_grade, " +
                     "       COALESCE(g.gwa, 0.0) AS gwa, " +
                     "       COALESCE(g.grade_status, 'PENDING') AS grade_status " +
                     "FROM students s " +
                     "LEFT JOIN student_grades g ON s.student_id = g.student_id " +
                     "ORDER BY s.last_name ASC";

        try (Connection con = ConnectionString.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("student_id"),                               // index 0 — hidden
                        rs.getString("full_name"),                                // index 1
                        rs.getString("section"),                                  // index 2
                        String.format("%.2f", rs.getDouble("math_grade")),        // index 3
                        String.format("%.2f", rs.getDouble("science_grade")),     // index 4
                        String.format("%.2f", rs.getDouble("english_grade")),     // index 5
                        String.format("%.2f", rs.getDouble("gwa")),               // index 6
                        rs.getString("grade_status")                              // index 7
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading records from database: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnEdit) {
            int selectedRow = tblGrades.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student row first!");
                return;
            }

            GradesEditorPage gep = new GradesEditorPage(this, selectedRow, model);
            gep.setVisible(true);
            setVisible(false);
        }

        else if (e.getSource() == btnSearch) {
            String search = txtSearchName.getText().trim();

            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a last name to search.");
                return;
            }

            boolean found = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                // Column 1 is NAME formatted as "LastName, FirstName"
                String fullName = model.getValueAt(i, 1).toString();
                String lastName = fullName.split(",")[0].trim();

                if (lastName.equalsIgnoreCase(search)) {
                    tblGrades.setRowSelectionInterval(i, i);
                    tblGrades.scrollRectToVisible(tblGrades.getCellRect(i, 1, true));
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, "Student found!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
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