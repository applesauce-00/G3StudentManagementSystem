package com.mycompany.g3studentmanagementsystem;

import com.mycompany.g3studentmanagementsystem.attendancedatamanager.AttendanceDataManager;
import com.mycompany.g3studentmanagementsystem.attendancedatamanager.TableandDataLogic;
import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class AttendanceSciencePage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JLabel lblSubject;
    private JButton btnAttendance, btnStudents, btnGrades, btnSignOut;
    private JButton btnMath, btnScience, btnEnglish, btnSave;
    private JTable tblStudent;
    private JScrollPane tableScroll;
    private JComboBox<String> attendanceBox;
    private DefaultTableModel tableModel;

    public AttendanceSciencePage() {

        setTitle("FACULTY PORTAL - Attendance (Science)");
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
        btnAttendance.setEnabled(false);
        btnAttendance.setBounds(300, 20, 120, 40);
        btnAttendance.setBackground(new Color(35, 132, 189));
        btnAttendance.setForeground(Color.WHITE);
        add(btnAttendance);

        btnStudents = new JButton("STUDENTS");
        btnStudents.setBounds(430, 20, 120, 40);
        btnStudents.setBackground(new Color(52, 168, 235));
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

        lblSubject = new JLabel("SUBJECTS");
        lblSubject.setFont(new Font("Arial", Font.BOLD, 18));
        lblSubject.setBounds(40, 90, 200, 40);
        add(lblSubject);

        String facultySubject = SessionManager.subject.toUpperCase();

        // MATH button — visible only to Math teacher
        btnMath = new JButton("MATH");
        btnMath.setBounds(20, 150, 160, 40);
        btnMath.setBackground(new Color(52, 168, 235));
        btnMath.setForeground(Color.WHITE);
        btnMath.setVisible(facultySubject.equals("MATH"));
        add(btnMath);

        // SCIENCE button — visible only to Science teacher, disabled since currently in
        btnScience = new JButton("SCIENCE");
        btnScience.setEnabled(false);
        btnScience.setBounds(20, 200, 160, 40);
        btnScience.setBackground(new Color(35, 132, 189));
        btnScience.setForeground(Color.WHITE);
        btnScience.setVisible(facultySubject.equals("SCIENCE"));
        add(btnScience);

        // ENGLISH button — visible only to English teacher
        btnEnglish = new JButton("ENGLISH");
        btnEnglish.setBounds(20, 250, 160, 40);
        btnEnglish.setBackground(new Color(52, 168, 235));
        btnEnglish.setForeground(Color.WHITE);
        btnEnglish.setVisible(facultySubject.equals("ENGLISH"));
        add(btnEnglish);

        btnSave = new JButton("SAVE CHANGES");
        btnSave.setBounds(20, 310, 160, 40);
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnSave);

        attendanceBox = new JComboBox<>();
        attendanceBox.addItem("Absent");
        attendanceBox.addItem("Present");
        attendanceBox.addItem("Excused");

        String[] columns = {"NAME", "WEEK 1", "WEEK 2", "WEEK 3", "WEEK 4", "WEEK 5",
                            "WEEK 6", "WEEK 7", "WEEK 8", "WEEK 9", "WEEK 10"};

        tableModel = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return column >= 1; }
        };

        tblStudent = new JTable(tableModel);
        tableScroll = new JScrollPane(tblStudent);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        TableandDataLogic.setupAttendanceTable(tblStudent, tableModel, attendanceBox);
        TableandDataLogic.loadData(tableModel, "Science", this);

        btnMath.addActionListener(this);
        btnScience.addActionListener(this);
        btnEnglish.addActionListener(this);
        btnStudents.addActionListener(this);
        btnGrades.addActionListener(this);
        btnSignOut.addActionListener(this);
        btnSave.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            TableandDataLogic.saveData(tblStudent, tableModel, "Science", this);
        } else if (e.getSource() == btnMath) {
            FrameSizeNavigation.navigate(this, new AttendanceMathPage());
        } else if (e.getSource() == btnEnglish) {
            FrameSizeNavigation.navigate(this, new AttendanceEnglishPage());
        } else if (e.getSource() == btnStudents) {
            FrameSizeNavigation.navigate(this, new StudentManagerPage());
        } else if (e.getSource() == btnGrades) {
            FrameSizeNavigation.navigate(this, new GradesManagerPage());
        } else if (e.getSource() == btnSignOut) {
            SessionManager.clear();
            FrameSizeNavigation.navigate(this, new LandingPageGUI());
        }
    }
}