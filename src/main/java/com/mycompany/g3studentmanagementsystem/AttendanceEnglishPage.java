package com.mycompany.g3studentmanagementsystem;

import com.mycompany.g3studentmanagementsystem.attendancedatamanager.AttendanceDataManager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class AttendanceEnglishPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JLabel lblSubject;
    private JButton btnAttendance, btnStudents, btnGrades, btnSignOut;
    private JButton btnMath, btnScience, btnEnglish, btnSave; 
    private JTable tblStudent;
    private JScrollPane tableScroll;
    private JComboBox<String> attendanceBox;
    private DefaultTableModel tableModel; 

    public AttendanceEnglishPage() {
        
        // Frame Settings
        setTitle("FACULTY PORTAL - Attendance (English)");
        setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // Logo
        lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(10, 10, 60, 60);
        add(lblIcon);

        // Title
        lblTitle = new JLabel("FACULTY PORTAL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(80, 20, 200, 40);
        add(lblTitle);

        // Top Panel
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

        // Sign Out
        btnSignOut = new JButton("Sign Out");
        btnSignOut.setBounds(850, 20, 120, 40);
        btnSignOut.setBackground(new Color(224, 69, 52));
        btnSignOut.setForeground(Color.WHITE);
        add(btnSignOut);

        // Side Panel
        lblSubject = new JLabel("SUBJECTS");
        lblSubject.setFont(new Font("Arial", Font.BOLD, 18));
        lblSubject.setBounds(40, 90, 200, 40);
        add(lblSubject);

        btnMath = new JButton("MATH");
        btnMath.setBounds(20, 150, 160, 40);
        btnMath.setBackground(new Color(52, 168, 235));
        btnMath.setForeground(Color.WHITE);
        add(btnMath);

        btnScience = new JButton("SCIENCE");
        btnScience.setBounds(20, 200, 160, 40);
        btnScience.setBackground(new Color(52, 168, 235));
        btnScience.setForeground(Color.WHITE);
        add(btnScience);

        btnEnglish = new JButton("ENGLISH");
        btnEnglish.setEnabled(false);
        btnEnglish.setBounds(20, 250, 160, 40);
        btnEnglish.setBackground(new Color(35, 132, 189));
        btnEnglish.setForeground(Color.WHITE);
        add(btnEnglish);

        btnSave = new JButton("SAVE CHANGES");
        btnSave.setBounds(20, 310, 160, 40);
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnSave);

        attendanceBox = new JComboBox<>();
        attendanceBox.addItem("Present");
        attendanceBox.addItem("Absent");
        attendanceBox.addItem("Excused");
        
        String[] columns = {"ID", "NAME", "WEEK 1", "WEEK 2", "WEEK 3", "WEEK 4", "WEEK 5", "WEEK 6", "WEEK 7", "WEEK 8", "WEEK 9", "WEEK 10"};
        
        tableModel = new DefaultTableModel(null, columns);
        tblStudent = new JTable(tableModel);
        tableScroll = new JScrollPane(tblStudent);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);
        
        // Loop for columns
        for (int i = 2; i < columns.length; i++) {
            TableColumn weekColumn = tblStudent.getColumnModel().getColumn(i);
            weekColumn.setCellEditor(new DefaultCellEditor(attendanceBox));
        }

        btnScience.addActionListener(this);
        btnMath.addActionListener(this);        
        btnStudents.addActionListener(this);
        btnGrades.addActionListener(this);
        btnSignOut.addActionListener(this);
        btnSave.addActionListener(this); 

        // Automatically collect records upon loading screen UI
        loadAttendanceFromDatabase();
    }

    private void loadAttendanceFromDatabase() {
        boolean success = AttendanceDataManager.loadAttendance(tableModel, "English");
        if (!success) {
            JOptionPane.showMessageDialog(this, "Failed to load English database records!");
        }
    }

    private void saveAttendanceToDatabase() {
        if (tblStudent.isEditing()) {
            tblStudent.getCellEditor().stopCellEditing();
        }

        boolean success = AttendanceDataManager.saveAttendance(tableModel, "English");
        if (success) {
            JOptionPane.showMessageDialog(this, "English Attendance saved safely to phpMyAdmin!");
        } else {
            JOptionPane.showMessageDialog(this, "Error while processing English database updates.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) { 
            saveAttendanceToDatabase();
        } else if (e.getSource() == btnScience) {
            AttendanceSciencePage asp = new AttendanceSciencePage();
            FrameSizeNavigation.navigate(this, asp);
        } else if (e.getSource() == btnStudents) {
            StudentManagerPage smp = new StudentManagerPage();
            FrameSizeNavigation.navigate(this, smp);
        } else if (e.getSource() == btnMath) {
            AttendanceMathPage amp = new AttendanceMathPage();
            FrameSizeNavigation.navigate(this, amp);
        } else if (e.getSource() == btnGrades) {
            GradesManagerPage gsp = new GradesManagerPage();
            FrameSizeNavigation.navigate(this, gsp);
        } else if (e.getSource() == btnSignOut) {
            LandingPageGUI lp = new LandingPageGUI();
            FrameSizeNavigation.navigate(this, lp);
        } 
    }
}