package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;

public class StudentManagerPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JButton btnAttendance, btnStudents, btnGrades, btnSignOut;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;
    private JTable tblStudent;
    private JScrollPane tableScroll;
    private JTextField txtSearchName; // renamed from txtSearchId

    private DefaultTableModel model;

    public StudentManagerPage() {

        // FRAME SETTINGS
        setTitle("FACULTY PORTAL - Student Manager");
        setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // ICON
        lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(10, 10, 60, 60);
        add(lblIcon);

        // TITLE
        lblTitle = new JLabel("FACULTY PORTAL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(80, 20, 200, 40);
        add(lblTitle);

        // TOP NAVIGATION BUTTONS
        btnAttendance = new JButton("ATTENDANCE");
        btnAttendance.setBounds(300, 20, 120, 40);
        btnAttendance.setBackground(new Color(52, 168, 235));
        btnAttendance.setForeground(Color.WHITE);
        add(btnAttendance);

        btnStudents = new JButton("STUDENTS");
        btnStudents.setEnabled(false);
        btnStudents.setBounds(430, 20, 120, 40);
        btnStudents.setBackground(new Color(35, 132, 189));
        btnStudents.setForeground(Color.WHITE);
        add(btnStudents);

        btnGrades = new JButton("GRADES");
        btnGrades.setBounds(560, 20, 120, 40);
        btnGrades.setBackground(new Color(52, 168, 235));
        btnGrades.setForeground(Color.WHITE);
        add(btnGrades);

        btnSignOut = new JButton("Sign Out");
        btnSignOut.setBounds(850, 20, 120, 40);
        btnSignOut.setBackground(new Color(224, 69, 52));
        btnSignOut.setForeground(Color.WHITE);
        add(btnSignOut);

        // LEFT BAR CONTROL COMPONENT
        txtSearchName = new JTextField();
        txtSearchName.setToolTipText("Search by last name");
        txtSearchName.setBounds(20, 100, 160, 35);
        add(txtSearchName);

        btnSearch = new JButton("SEARCH STUDENT");
        btnSearch.setBounds(20, 140, 160, 40);
        btnSearch.setBackground(new Color(52, 168, 235));
        btnSearch.setForeground(Color.WHITE);
        add(btnSearch);

        btnAdd = new JButton("ADD STUDENT");
        btnAdd.setBounds(20, 200, 160, 40);
        btnAdd.setBackground(new Color(52, 168, 235));
        btnAdd.setForeground(Color.WHITE);
        add(btnAdd);

        btnEdit = new JButton("EDIT STUDENT");
        btnEdit.setBounds(20, 250, 160, 40);
        btnEdit.setBackground(new Color(52, 168, 235));
        btnEdit.setForeground(Color.WHITE);
        add(btnEdit);

        btnDelete = new JButton("DELETE STUDENT");
        btnDelete.setBounds(20, 300, 160, 40);
        btnDelete.setBackground(new Color(52, 168, 235));
        btnDelete.setForeground(Color.WHITE);
        add(btnDelete);

        // Table columns — no Student ID shown
        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "STUDENT ID",   // hidden column at index 0 — holds ID for lookups
                        "LAST NAME",
                        "FIRST NAME",
                        "MIDDLE NAME",
                        "SECTION",
                        "SEX",
                        "BIRTH DATE",
                        "EMAIL"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblStudent = new JTable(model);

        // Hide the STUDENT ID column visually — still accessible via getValueAt(row, 0)
        tblStudent.getColumnModel().getColumn(0).setMinWidth(0);
        tblStudent.getColumnModel().getColumn(0).setMaxWidth(0);
        tblStudent.getColumnModel().getColumn(0).setWidth(0);

        tableScroll = new JScrollPane(tblStudent);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        loadStudents();

        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSearch.addActionListener(this);
        btnAttendance.addActionListener(this);
        btnGrades.addActionListener(this);
        btnSignOut.addActionListener(this);
    }

    public void loadStudents() {
        model.setRowCount(0);
        String sql = "SELECT * FROM students ORDER BY last_name ASC";

        try (Connection con = ConnectionString.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            StudentDataManager.students.clear();

            while (rs.next()) {
                String id = rs.getString("student_id");
                String lastName = rs.getString("last_name");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String section = rs.getString("section");

                String sexStr = rs.getString("sex");
                char sex = (sexStr != null && !sexStr.isEmpty()) ? sexStr.charAt(0) : 'M';

                String birthDate = rs.getString("birth_date");
                String email = rs.getString("email");
                String password = rs.getString("password");

                Student loadedStudent = new Student(id, lastName, firstName, middleName, section, sex, birthDate, email, password);
                StudentDataManager.addStudent(loadedStudent);

                model.addRow(new Object[]{
                        id,          // index 0 — hidden, used for lookups
                        lastName,
                        firstName,
                        middleName,
                        section,
                        String.valueOf(sex),
                        birthDate,
                        email
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load students from database: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Finds a student by last name (case-insensitive)
    private Student findByLastName(String lastName) {
        for (Student s : StudentDataManager.students) {
            if (s.getLastName().equalsIgnoreCase(lastName)) {
                return s;
            }
        }
        return null;
    }

    // Gets the student ID from the hidden column 0 of the selected table row
    private String getSelectedStudentId() {
        int row = tblStudent.getSelectedRow();
        if (row != -1) {
            return tblStudent.getValueAt(row, 0).toString();
        }
        return "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {
            AddStudentPage asp = new AddStudentPage();
            asp.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnSearch) {
            String search = txtSearchName.getText().trim();

            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a last name to search.");
                return;
            }

            boolean found = false;
            // Search column 1 (LAST NAME)
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 1).toString().equalsIgnoreCase(search)) {
                    tblStudent.setRowSelectionInterval(i, i);
                    tblStudent.scrollRectToVisible(tblStudent.getCellRect(i, 1, true));
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

        else if (e.getSource() == btnEdit) {
            String lastName = txtSearchName.getText().trim();
            Student s = null;

            if (!lastName.isEmpty()) {
                // Search by typed last name
                s = findByLastName(lastName);
            } else if (tblStudent.getSelectedRow() != -1) {
                // Fallback: use selected row — get ID from hidden column 0
                String id = getSelectedStudentId();
                s = StudentDataManager.findStudent(id);
            }

            if (s == null) {
                JOptionPane.showMessageDialog(this, "Please enter a last name or select a row to edit.");
                return;
            }

            EditStudentPage esp = new EditStudentPage(
                    s.getId(),
                    s.getLastName(),
                    s.getFirstName(),
                    s.getMiddleName(),
                    s.getSection(),
                    String.valueOf(s.getSex()),
                    s.getBirthDate(),
                    s.getEmail()
            );
            esp.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnDelete) {
            String lastName = txtSearchName.getText().trim();
            Student s = null;

            if (!lastName.isEmpty()) {
                // Search by typed last name
                s = findByLastName(lastName);
            } else if (tblStudent.getSelectedRow() != -1) {
                // Fallback: use selected row — get ID from hidden column 0
                String id = getSelectedStudentId();
                s = StudentDataManager.findStudent(id);
            }

            if (s == null) {
                JOptionPane.showMessageDialog(this, "Please enter a last name or select a row to delete.");
                return;
            }

            DeleteStudentPage dsp = new DeleteStudentPage(s);
            dsp.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnAttendance) {
            AttendanceEnglishPage aep = new AttendanceEnglishPage();
            aep.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnGrades) {
            GradesManagerPage gsp = new GradesManagerPage();
            gsp.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnSignOut) {
            LandingPageGUI lp = new LandingPageGUI();
            lp.setVisible(true);
            this.setVisible(false);
        }
    }
}