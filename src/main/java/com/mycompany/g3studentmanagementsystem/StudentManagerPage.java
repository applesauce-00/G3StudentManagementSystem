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
    private JButton btnSearch, btnAdd, btnEdit, btnInactive;
    private JTable tblStudent;
    private JScrollPane tableScroll;
    private JTextField txtSearchName;

    private DefaultTableModel model;

    public StudentManagerPage() {

        setTitle("FACULTY PORTAL - Student Manager");
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

        // Button to open Inactive Students list
        btnInactive = new JButton("INACTIVE LIST");
        btnInactive.setBounds(20, 360, 160, 40);
        btnInactive.setBackground(new Color(224, 69, 52));
        btnInactive.setForeground(Color.WHITE);
        add(btnInactive);

        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "STUDENT ID",   // index 0 — hidden
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
        tblStudent.getColumnModel().getColumn(0).setMinWidth(0);
        tblStudent.getColumnModel().getColumn(0).setMaxWidth(0);
        tblStudent.getColumnModel().getColumn(0).setWidth(0);

        tableScroll = new JScrollPane(tblStudent);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        loadStudents();

        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        btnSearch.addActionListener(this);
        btnInactive.addActionListener(this);
        btnAttendance.addActionListener(this);
        btnGrades.addActionListener(this);
        btnSignOut.addActionListener(this);
    }

    public void loadStudents() {
        model.setRowCount(0);
        // Only load active students
        String sql = "SELECT * FROM students WHERE is_active = 1 ORDER BY last_name ASC";

        try (Connection con = ConnectionString.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            StudentDataManager.students.clear();

            while (rs.next()) {
                String id         = rs.getString("student_id");
                String lastName   = rs.getString("last_name");
                String firstName  = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String section    = rs.getString("section");

                String sexStr = rs.getString("sex");
                char sex = (sexStr != null && !sexStr.isEmpty()) ? sexStr.charAt(0) : 'M';

                String birthDate = rs.getString("birth_date");
                String email     = rs.getString("email");
                String password  = rs.getString("password");

                Student loadedStudent = new Student(id, lastName, firstName, middleName, section, sex, birthDate, email, password);
                StudentDataManager.addStudent(loadedStudent);

                model.addRow(new Object[]{
                        id, lastName, firstName, middleName,
                        section, String.valueOf(sex), birthDate, email
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load students: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Student findByLastName(String lastName) {
        for (Student s : StudentDataManager.students) {
            if (s.getLastName().equalsIgnoreCase(lastName)) return s;
        }
        return null;
    }

    private String getSelectedStudentId() {
        int row = tblStudent.getSelectedRow();
        return (row != -1) ? tblStudent.getValueAt(row, 0).toString() : "";
    }

    // Routes to the attendance page matching the logged-in faculty's subject 
    private void navigateToAttendance() {
        switch (SessionManager.subject.toUpperCase()) {
            case "MATH":
                FrameSizeNavigation.navigate(this, new AttendanceMathPage());
                break;
            case "SCIENCE":
                FrameSizeNavigation.navigate(this, new AttendanceSciencePage());
                break;
            case "ENGLISH":
            default:
                FrameSizeNavigation.navigate(this, new AttendanceEnglishPage());
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {
            new AddStudentPage().setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnSearch) {
            String search = txtSearchName.getText().trim();
            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a last name to search.");
                return;
            }
            boolean found = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 1).toString().equalsIgnoreCase(search)) {
                    tblStudent.setRowSelectionInterval(i, i);
                    tblStudent.scrollRectToVisible(tblStudent.getCellRect(i, 1, true));
                    found = true;
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, found ? "Student found!" : "Student not found!");
        }

        else if (e.getSource() == btnEdit) {
            String lastName = txtSearchName.getText().trim();
            Student s = null;

            if (!lastName.isEmpty()) {
                s = findByLastName(lastName);
            } else if (tblStudent.getSelectedRow() != -1) {
                s = StudentDataManager.findStudent(getSelectedStudentId());
            }

            if (s == null) {
                JOptionPane.showMessageDialog(this, "Please enter a last name or select a row to edit.");
                return;
            }

            // Pass is_active status - all students in this table are active (1)
            EditStudentPage esp = new EditStudentPage(
                    s.getId(), s.getLastName(), s.getFirstName(),
                    s.getMiddleName(), s.getSection(),
                    String.valueOf(s.getSex()), s.getBirthDate(),
                    s.getEmail(), 1
            );
            esp.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnInactive) {
            new InactiveStudentsPage().setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnAttendance) {
            navigateToAttendance();
        }

        else if (e.getSource() == btnGrades) {
            new GradesManagerPage().setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnSignOut) {
            SessionManager.clear(); // clear faculty session on logout
            new LandingPageGUI().setVisible(true);
            this.setVisible(false);
        }
    }
}