package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class StudentLandingPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle, lblId, lblName, lblCourse, lblContactNumber, lblYearLevel;
    private JTextField txtId, txtName, txtContactNumber;
    private JButton btnSignOut, btnProfile, btnGrades, btnSubjects, btnReportCard, btnEnrollment, btnEnroll, btnDashBoard;
    private JPanel ProfilePanel, GradesPanel, SubjectsPanel, ReportCardPanel, Enrollment;
    private JComboBox<String> cmbCourse, cmbYearLevel;

    public StudentLandingPage() {

        // Frame Settings
        setTitle("STUDENT PORTAL - Grades");
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
        lblTitle = new JLabel("STUDENT PORTAL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(80, 20, 250, 40);
        add(lblTitle);

        // Sign Out 
        btnSignOut = new JButton("Sign Out");
        btnSignOut.setBounds(850, 20, 120, 40);
        btnSignOut.setBackground(new Color(224, 69, 52));
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.addActionListener(this);
        add(btnSignOut);

        //SideBar Panel 
          
        //Left sidebar button Profile
        btnProfile = new JButton("PROFILE");
        btnProfile.setBounds(20, 100, 180, 40);
        btnProfile.setBackground(new Color(52, 168, 235));
        btnProfile.setForeground(Color.WHITE);
        btnProfile.addActionListener(this);
        add(btnProfile);
        
        //Right sidebar Profile
        ProfilePanel = new JPanel(new BorderLayout());
        ProfilePanel.setBounds(250, 100, 700, 500);
        String[] profileColumns = {"STUDENT INFO", "DETAILS"};
        Object[][] profileData = {
        {"Name", "Woo Seok Byeon"},
        {"Student ID", "2025-001"},
        {"Course", "BSIT"},
        {"Year Level", "2nd Year"}};
        
        JTable profileTable = new JTable(new DefaultTableModel(profileData, profileColumns));
        ProfilePanel.add(new JScrollPane(profileTable), BorderLayout.CENTER);
        add(ProfilePanel);
        ProfilePanel.setVisible(true);
        
        //Left sidebar button Grades
        btnGrades = new JButton("GRADES");
        btnGrades.setBounds(20, 150, 180, 40);
        btnGrades.setBackground(new Color(52, 168, 235));
        btnGrades.setForeground(Color.WHITE);
        btnGrades.addActionListener(this);
        add(btnGrades);
        
        //Right sidebar Grades
        GradesPanel = new JPanel(new BorderLayout());
        GradesPanel.setBounds(250, 100, 700, 500);
        String[] gradeColumns = {"SUBJECT", "UNITS", "FINAL GRADE", "STATUS"};
        Object[][] gradeData = {
        {"English", "3", "1.50", "PASSED"},
        {"Math", "3", "1.75", "PASSED"},
        {"Science", "3", "2.00", "PASSED"}};
        
        JTable gradesTable = new JTable(new DefaultTableModel(gradeData, gradeColumns));
        GradesPanel.add(new JScrollPane(gradesTable), BorderLayout.CENTER);
        add(GradesPanel);
        GradesPanel.setVisible(false);
       
        //Left sidebar button Subjects
        btnSubjects = new JButton("SUBJECT");
        btnSubjects.setBounds(20, 200, 180, 40);
        btnSubjects.setBackground(new Color(52, 168, 235));
        btnSubjects.setForeground(Color.WHITE);
        btnSubjects.addActionListener(this);
        add(btnSubjects);
        
        // Right sidebar Panel Subjects
        SubjectsPanel = new JPanel(new BorderLayout());
        SubjectsPanel.setBounds(250, 100, 700, 500);
        String[] subjColumns = {"SUBJECT", "INSTRUCTOR", "SCHEDULE"};
        Object[][] subjData = {
        {"English", "Mr. Santos", "Mon 8AM"},
        {"Math", "Ms. Olviga", "Tue 10AM"},
        {"Science", "Mr. Nase", "Wed 1PM"}};

        JTable subjectsTable = new JTable(new DefaultTableModel(subjData, subjColumns));
        SubjectsPanel.add(new JScrollPane(subjectsTable), BorderLayout.CENTER);
        add(SubjectsPanel);
        SubjectsPanel.setVisible(false);
        
        //Left sidebar button ReportCard
        btnReportCard = new JButton("REPORT CARD");
        btnReportCard.setBounds(20, 250, 180, 40);
        btnReportCard.setBackground(new Color(52, 168, 235));
        btnReportCard.setForeground(Color.WHITE);
        btnReportCard.addActionListener(this);
        add(btnReportCard);
        
        //Right sidebar Panel ReportCard
        ReportCardPanel = new JPanel(new BorderLayout());
        ReportCardPanel.setBounds(250, 100, 700, 500);
        String[] reportColumns = {"SEMESTER", "AVERAGE", "STATUS", "HONORS"};
        Object[][] reportData = {
        {"1st Semester", "1.75", "PASSED", "Dean's List"},
        {"2nd Semester", "1.80", "PASSED", "None"}};
        
        JTable reportTable = new JTable(new DefaultTableModel(reportData, reportColumns));
        ReportCardPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        add(ReportCardPanel);
        ReportCardPanel.setVisible(false);
        
        //Left sidebar Button Enrollment
        btnEnrollment = new JButton("ENROLLMENT");
        btnEnrollment.setBounds(20, 300, 180, 40);
        btnEnrollment.setBackground(new Color(52, 168, 235));
        btnEnrollment.setForeground(Color.WHITE);
        btnEnrollment.addActionListener(this);
        add(btnEnrollment);
        
        //Right sidebar Panel Enrollment
        Enrollment = new JPanel();
        Enrollment.setLayout(null);
        Enrollment.setBounds(250, 100, 700, 500);

       lblId = new JLabel("Student ID:");
       lblId.setBounds(20, 20, 100, 25); 
       Enrollment.add(lblId);

       txtId = new JTextField();
       txtId.setBounds(150, 20, 400, 25);
       Enrollment.add(txtId);
       
       lblName = new JLabel("Student Name:");
       lblName.setBounds(20, 60, 100, 25);
       Enrollment.add(lblName);
       
       txtName = new JTextField();
       txtName.setBounds(150, 60, 400, 25);
       Enrollment.add(txtName);
       
       lblCourse = new JLabel("Course:");
       lblCourse.setBounds(20, 100, 200, 25);
       Enrollment.add(lblCourse);
       
       cmbCourse = new JComboBox<>(new String[]{"BSCS", "BSIT", "BSE"});
       cmbCourse.setBounds(150, 100, 200, 25);
       Enrollment.add(cmbCourse);
       
       lblYearLevel = new JLabel("Year Level:");
       lblYearLevel.setBounds(20, 140, 100, 25);
       Enrollment.add(lblYearLevel);
       
       cmbYearLevel = new JComboBox<>(new String[]{"1st Year", "2nd Year", "3rd Year", "4th Year"});
       cmbYearLevel.setBounds(150, 140, 200, 25);
       Enrollment.add(cmbYearLevel);
       
       lblContactNumber = new JLabel("Contact Number:");
       lblContactNumber.setBounds(20, 200, 100, 25);
       Enrollment.add(lblContactNumber);
       
       txtContactNumber = new JTextField();
       txtContactNumber.setBounds(150, 200, 400, 25);
       Enrollment.add(txtContactNumber);
      
       btnEnroll = new JButton("Enroll");
       btnEnroll.setBounds(250, 350, 200, 40);
       btnEnroll.setBackground(new Color(52, 168, 235));
       btnEnroll.setForeground(Color.WHITE);
       Enrollment.add(btnEnroll);
       add(Enrollment);
       Enrollment.setVisible(false);
             
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //To Hide all panels First
        ProfilePanel.setVisible(false);
        GradesPanel.setVisible(false);
        SubjectsPanel.setVisible(false);
        ReportCardPanel.setVisible(false);
        Enrollment.setVisible(false);
        
         if (e.getSource() == btnSignOut) {
            LandingPageGUI lp = new LandingPageGUI();
            lp.setVisible(true);
            this.setVisible(false); 
        }
        else if (e.getSource() == btnProfile) {
        ProfilePanel.setVisible(true);   
        }   
        else if (e.getSource() == btnGrades) {
        GradesPanel.setVisible(true);
        } 
        else if (e.getSource() == btnSubjects) {
        SubjectsPanel.setVisible(true);
        }
        else if (e.getSource() == btnReportCard) {
        ReportCardPanel.setVisible(true);
        } 
        else if (e.getSource() == btnEnrollment) {
        Enrollment.setVisible(true);
        }
}
}
