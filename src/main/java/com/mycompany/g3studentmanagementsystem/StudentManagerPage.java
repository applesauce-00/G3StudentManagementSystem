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
    private JTextField txtSearchId;

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
        txtSearchId = new JTextField();
        txtSearchId.setBounds(20, 100, 160, 35);
        add(txtSearchId);

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

        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "STUDENT ID",
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
        tableScroll = new JScrollPane(tblStudent);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        // Pull active entries straight from database
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
        String sql = "SELECT * FROM students"; 

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

                // Refill background StudentDataManager
                Student loadedStudent = new Student(id, lastName, firstName, middleName, section, sex, birthDate, email, password);
                StudentDataManager.addStudent(loadedStudent);

                // Show it to UI
                model.addRow(new Object[]{
                        id,       
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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {
            AddStudentPage asp = new AddStudentPage();
            asp.setVisible(true);
            this.setVisible(false);
        }

        else if (e.getSource() == btnDelete) {
            String id = txtSearchId.getText().trim();
			
			// Taking selected rows faculty wants to delete
			
            if (id.isEmpty() && tblStudent.getSelectedRow() != -1) {
                id = tblStudent.getValueAt(tblStudent.getSelectedRow(), 0).toString();
            }

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Student ID or select a row to delete.");
                return;
            }

            Student s = StudentDataManager.findStudent(id);

            if (s != null) {
                DeleteStudentPage dsp = new DeleteStudentPage(s);
                dsp.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == btnSearch) {
            String search = txtSearchId.getText().trim();
            boolean found = false;

			
			// Search then select
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(search)) {
                    tblStudent.setRowSelectionInterval(i, i);
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

        else if (e.getSource() == btnEdit) {
            String id = txtSearchId.getText().trim();

            // Fallback to table selection if the search box is empty
            if (id.isEmpty() && tblStudent.getSelectedRow() != -1) {
                id = tblStudent.getValueAt(tblStudent.getSelectedRow(), 0).toString();
            }

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Student ID or select a row to edit.");
                return;
            }

            Student s = StudentDataManager.findStudent(id);

            if (s != null) {
                // Instantiates EditStudentPage with current student data properties passed down
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
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
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