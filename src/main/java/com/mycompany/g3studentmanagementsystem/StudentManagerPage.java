package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

        // TOP BUTTONS
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

        // SEARCH FIELD
        txtSearchId = new JTextField();
        txtSearchId.setBounds(20, 100, 160, 35);
        add(txtSearchId);

        // BUTTONS
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
                        "NAME",
                        "SECTION",
                        "GENDER",
                        "BIRTH DATE",
                        "EMAIL"
                }
        );

        tblStudent = new JTable(model);
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


    private void loadStudents() {

        model.setRowCount(0);

        for (Student s : StudentDataManager.students) {
            model.addRow(new Object[]{
                    s.studentId,
                    s.name,
                    s.section,
                    s.gender,
                    s.birthDate,
                    s.email
            });
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {
            AddStudentPage asp = new AddStudentPage();
            asp.setVisible(true);
        }

        else if (e.getSource() == btnDelete) {

            String id = txtSearchId.getText().trim();

            if (StudentDataManager.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        }

        else if (e.getSource() == btnSearch) {

            String id = txtSearchId.getText().trim();
            Student s = StudentDataManager.findStudent(id);

            if (s != null) {
                JOptionPane.showMessageDialog(this,
                        "FOUND STUDENT:\n\n" +
                        "Name: " + s.name + "\n" +
                        "Section: " + s.section + "\n" +
                        "Email: " + s.email
                );
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        }

        else if (e.getSource() == btnEdit) {
            EditStudentPage esp = new EditStudentPage();
            esp.setVisible(true);
        }

        else if (e.getSource() == btnAttendance) {
            AttendanceMathPage amp = new AttendanceMathPage();
            FrameSizeNavigation.navigate(this, amp);
        }

        else if (e.getSource() == btnGrades) {
            GradesManagerPage gsp = new GradesManagerPage();
            FrameSizeNavigation.navigate(this, gsp);
        }

        else if (e.getSource() == btnSignOut) {
            LandingPageGUI lp = new LandingPageGUI();
            FrameSizeNavigation.navigate(this, lp);
        }
    }
}