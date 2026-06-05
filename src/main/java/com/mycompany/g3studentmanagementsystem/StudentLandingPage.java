package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class StudentLandingPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JButton btnSignOut, btnProfile, btnGrades, btnSubjects, btnReportCard;
    private JPanel ProfilePanel, GradesPanel, SubjectsPanel, ReportCardPanel;

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

        // PROFILE BUTTON
        btnProfile = new JButton("PROFILE");
        btnProfile.setBounds(20, 100, 180, 40);
        btnProfile.setBackground(new Color(52, 168, 235));
        btnProfile.setForeground(Color.WHITE);
        btnProfile.addActionListener(this);
        add(btnProfile);

        // PROFILE PANEL
        ProfilePanel = new JPanel(new BorderLayout());
        ProfilePanel.setBounds(250, 100, 700, 500);

        String[] profileColumns = {"STUDENT INFO", "DETAILS"};
        Object[][] profileData = {
                {"Name", "Woo Seok Byeon"},
                {"Student ID", "2025-001"},
                {"Course", "BSIT"},
                {"Year Level", "2nd Year"}
        };

        JTable profileTable = new JTable(new DefaultTableModel(profileData, profileColumns));
        ProfilePanel.add(new JScrollPane(profileTable), BorderLayout.CENTER);
        add(ProfilePanel);
        ProfilePanel.setVisible(true);

        // GRADES BUTTON
        btnGrades = new JButton("GRADES");
        btnGrades.setBounds(20, 150, 180, 40);
        btnGrades.setBackground(new Color(52, 168, 235));
        btnGrades.setForeground(Color.WHITE);
        btnGrades.addActionListener(this);
        add(btnGrades);

        // GRADES PANEL
        GradesPanel = new JPanel(new BorderLayout());
        GradesPanel.setBounds(250, 100, 700, 500);

        String[] gradeColumns = {"SUBJECT", "FINAL GRADE", "STATUS"};
        Object[][] gradeData = {
                {"English", "1.50", "PASSED"},
                {"Math", "1.75", "PASSED"},
                {"Science", "2.00", "PASSED"}
        };

        JTable gradesTable = new JTable(new DefaultTableModel(gradeData, gradeColumns));
        GradesPanel.add(new JScrollPane(gradesTable), BorderLayout.CENTER);
        add(GradesPanel);
        GradesPanel.setVisible(false);

        // SUBJECT BUTTON
        btnSubjects = new JButton("SUBJECT");
        btnSubjects.setBounds(20, 200, 180, 40);
        btnSubjects.setBackground(new Color(52, 168, 235));
        btnSubjects.setForeground(Color.WHITE);
        btnSubjects.addActionListener(this);
        add(btnSubjects);

        // SUBJECT PANEL
        SubjectsPanel = new JPanel(new BorderLayout());
        SubjectsPanel.setBounds(250, 100, 700, 500);

        String[] subjColumns = {"SUBJECT", "INSTRUCTOR", "SCHEDULE"};
        Object[][] subjData = {
                {"English", "Mr. Santos", "Mon 8AM"},
                {"Math", "Ms. Olviga", "Tue 10AM"},
                {"Science", "Mr. Nase", "Wed 1PM"}
        };

        JTable subjectsTable = new JTable(new DefaultTableModel(subjData, subjColumns));
        SubjectsPanel.add(new JScrollPane(subjectsTable), BorderLayout.CENTER);
        add(SubjectsPanel);
        SubjectsPanel.setVisible(false);

        // REPORT CARD BUTTON
        btnReportCard = new JButton("REPORT CARD");
        btnReportCard.setBounds(20, 250, 180, 40);
        btnReportCard.setBackground(new Color(52, 168, 235));
        btnReportCard.setForeground(Color.WHITE);
        btnReportCard.addActionListener(this);
        add(btnReportCard);

        // REPORT CARD PANEL
        ReportCardPanel = new JPanel(new BorderLayout());
        ReportCardPanel.setBounds(250, 100, 700, 500);

        String[] reportColumns = {"SEMESTER", "AVERAGE", "STATUS", "HONORS"};
        Object[][] reportData = {
                {"1st Semester", "1.75", "PASSED", "Dean's List"},
                {"2nd Semester", "1.80", "PASSED", "None"}
        };

        JTable reportTable = new JTable(new DefaultTableModel(reportData, reportColumns));
        ReportCardPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        add(ReportCardPanel);
        ReportCardPanel.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Hide all panels first
        ProfilePanel.setVisible(false);
        GradesPanel.setVisible(false);
        SubjectsPanel.setVisible(false);
        ReportCardPanel.setVisible(false);

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
    }
}