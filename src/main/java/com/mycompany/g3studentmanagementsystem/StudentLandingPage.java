package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;

public class StudentLandingPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JButton btnSignOut, btnProfile, btnGrades, btnSubjects, btnReportCard;
    private JPanel ProfilePanel, GradesPanel, SubjectsPanel, ReportCardPanel;

    // Table Models for Dynamic DB Updates
    private DefaultTableModel profileModel;
    private DefaultTableModel gradesModel;
    private DefaultTableModel subjectsModel;
    private DefaultTableModel reportCardModel;

    // Tracker variable for the active user
    private String loggedInStudentId;

    // Constructor accepts the validated login ID
    public StudentLandingPage(String studentId) {
        this.loggedInStudentId = studentId;

        // Frame Settings
        setTitle("STUDENT PORTAL");
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

        // Sign Out Button
        btnSignOut = new JButton("Sign Out");
        btnSignOut.setBounds(850, 20, 120, 40);
        btnSignOut.setBackground(new Color(224, 69, 52));
        btnSignOut.setForeground(Color.WHITE);
        btnSignOut.addActionListener(this);
        add(btnSignOut);

        
		// PROFILE PANEL SETTINGS
		
        btnProfile = new JButton("PROFILE");
        btnProfile.setBounds(20, 100, 180, 40);
        btnProfile.setBackground(new Color(52, 168, 235));
        btnProfile.setForeground(Color.WHITE);
        btnProfile.addActionListener(this);
        add(btnProfile);

        ProfilePanel = new JPanel(new BorderLayout());
        ProfilePanel.setBounds(250, 100, 700, 500);

        String[] profileColumns = {"STUDENT INFO", "DETAILS"};
        profileModel = new DefaultTableModel(profileColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable profileTable = new JTable(profileModel);
        ProfilePanel.add(new JScrollPane(profileTable), BorderLayout.CENTER);
        add(ProfilePanel);
        ProfilePanel.setVisible(true);

   
        // GRADES PANEL SETTINGS
		
        btnGrades = new JButton("GRADES");
        btnGrades.setBounds(20, 150, 180, 40);
        btnGrades.setBackground(new Color(52, 168, 235));
        btnGrades.setForeground(Color.WHITE);
        btnGrades.addActionListener(this);
        add(btnGrades);

        GradesPanel = new JPanel(new BorderLayout());
        GradesPanel.setBounds(250, 100, 700, 500);

        String[] gradeColumns = {"SUBJECT", "FINAL GRADE", "STATUS"};
        gradesModel = new DefaultTableModel(gradeColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable gradesTable = new JTable(gradesModel);
        GradesPanel.add(new JScrollPane(gradesTable), BorderLayout.CENTER);
        add(GradesPanel);
        GradesPanel.setVisible(false);

        // SUBJECT PANEL SETTINGS (LIVE DATABASE LIST + HARDCODED INSTRUCTOR DETAILS)
		
        btnSubjects = new JButton("SUBJECT");
        btnSubjects.setBounds(20, 200, 180, 40);
        btnSubjects.setBackground(new Color(52, 168, 235));
        btnSubjects.setForeground(Color.WHITE);
        btnSubjects.addActionListener(this);
        add(btnSubjects);

        SubjectsPanel = new JPanel(new BorderLayout());
        SubjectsPanel.setBounds(250, 100, 700, 500);

        String[] subjColumns = {"SUBJECT", "INSTRUCTOR", "SCHEDULE"};
        subjectsModel = new DefaultTableModel(subjColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable subjectsTable = new JTable(subjectsModel);
        SubjectsPanel.add(new JScrollPane(subjectsTable), BorderLayout.CENTER);
        add(SubjectsPanel);
        SubjectsPanel.setVisible(false);

        
        // REPORT CARD PANEL SETTINGS
        
        btnReportCard = new JButton("REPORT CARD");
        btnReportCard.setBounds(20, 250, 180, 40);
        btnReportCard.setBackground(new Color(52, 168, 235));
        btnReportCard.setForeground(Color.WHITE);
        btnReportCard.addActionListener(this);
        add(btnReportCard);

        ReportCardPanel = new JPanel(new BorderLayout());
        ReportCardPanel.setBounds(250, 100, 700, 500);

        String[] reportColumns = {"YEAR", "AVERAGE", "STATUS", "HONORS"};
        reportCardModel = new DefaultTableModel(reportColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable reportTable = new JTable(reportCardModel);
        ReportCardPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        add(ReportCardPanel);
        ReportCardPanel.setVisible(false);

        // Run dynamic database queries immmediately
        refreshStudentData();
    }

    // Reload
    private void refreshStudentData() {
        loadLiveProfile();
        loadLiveGradesAndData();
    }

    // Getting details dynamically directly from the database table matching the login ID
	
    private void loadLiveProfile() {
        profileModel.setRowCount(0);
        
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        System.out.println("DEBUG: Querying profile for ID: " + loggedInStudentId);
        
        try (Connection con = ConnectionString.getConnection()) {
            if (con == null) {
                System.out.println("ERROR: Database connection failed.");
                return;
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, loggedInStudentId);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String lastName = getColumnSafe(rs, "last_name");
                        String firstName = getColumnSafe(rs, "first_name");
                        String middleName = getColumnSafe(rs, "middle_name");
                        String section = getColumnSafe(rs, "section");
                        
                        String formattedName = lastName + ", " + firstName;
                        if (!middleName.equals("N/A") && !middleName.isEmpty()) {
                            formattedName += " " + middleName;
                        }
                        
                        profileModel.addRow(new Object[]{"Name", formattedName});
                        profileModel.addRow(new Object[]{"Student ID", loggedInStudentId});
                        profileModel.addRow(new Object[]{"Course / Year Level", section});
                        
                        profileModel.fireTableDataChanged();
                        System.out.println("DEBUG: Profile loaded successfully.");
                    } else {
                        System.out.println("DEBUG: No student found with ID: " + loggedInStudentId);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("❌ Database Profile Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void loadLiveGradesAndData() {
    gradesModel.setRowCount(0);
    subjectsModel.setRowCount(0);
    reportCardModel.setRowCount(0);

    String sql = "SELECT * FROM student_grades WHERE student_id = ?";

    try (Connection con = ConnectionString.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, loggedInStudentId);

        try (ResultSet rs = ps.executeQuery()) {
			
            // Default values if no record is found
            String math = "N/A", science = "N/A", english = "N/A";
            String mStatus = "N/A", sStatus = "N/A", eStatus = "N/A";
            String finalAvg = "N/A", honorStatus = "N/A", overallStatus = "N/A";

            if (rs.next()) {
                math = getColumnSafe(rs, "math_grade");
                science = getColumnSafe(rs, "science_grade");
                english = getColumnSafe(rs, "english_grade");

                mStatus = calculateStatus(math);
                sStatus = calculateStatus(science);
                eStatus = calculateStatus(english);

                // Add to Grades Table
                gradesModel.addRow(new Object[]{"English", english, eStatus});
                gradesModel.addRow(new Object[]{"Math", math, mStatus});
                gradesModel.addRow(new Object[]{"Science", science, sStatus});

                // Report Card Calculation
                try {
                    double m = Double.parseDouble(math);
                    double s = Double.parseDouble(science);
                    double e = Double.parseDouble(english);
                    double avg = (m + s + e) / 3.0;
                    finalAvg = String.format("%.2f", avg);

                    if (avg >= 1.00 && avg <= 1.45) honorStatus = "President's List";
                    else if (avg > 1.45 && avg <= 1.75) honorStatus = "Dean's List";
                    else honorStatus = "None";

					
					// Report Card Grade Status display
                    if (eStatus.equals("PENDING") || mStatus.equals("PENDING") || sStatus.equals("PENDING")) {
						overallStatus = "PENDING";
					} 
					else if (eStatus.equals("FAILED") || mStatus.equals("FAILED") || sStatus.equals("FAILED")) {
						overallStatus = "FAILED";
					} 
					else {
						overallStatus = "PASSED";
					}
					
                } catch (NumberFormatException nfe) { }
            } else {
                // If there's still no grade record, still show N/A in the grades table
                gradesModel.addRow(new Object[]{"English", "N/A", "N/A"});
                gradesModel.addRow(new Object[]{"Math", "N/A", "N/A"});
                gradesModel.addRow(new Object[]{"Science", "N/A", "N/A"});
            }

            // Hardcoded data
            reportCardModel.addRow(new Object[]{"Current Year", finalAvg, overallStatus, honorStatus});
            subjectsModel.addRow(new Object[]{"English", "Mr. Santos", "Mon 8AM"});
            subjectsModel.addRow(new Object[]{"Math", "Ms. Olviga", "Tue 10AM"});
            subjectsModel.addRow(new Object[]{"Science", "Mr. Nase", "Wed 1PM"});

            gradesModel.fireTableDataChanged();
            subjectsModel.fireTableDataChanged();
            reportCardModel.fireTableDataChanged();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
	
	private String calculateStatus(String gradeStr) {
    try {
        if (gradeStr == null || gradeStr.equals("N/A") || gradeStr.trim().isEmpty()) {
            return "PENDING";
        }

        double grade = Double.parseDouble(gradeStr);

        // If not yet encoded
        if (grade == 0.00) {
            return "PENDING";
        }

        // Failed condition
        if (grade >= 4.00) {
            return "FAILED";
        }

        return "PASSED";

    } catch (NumberFormatException e) {
        return "PENDING";
    }
	}

    private String getColumnSafe(ResultSet rs, String columnName) {
        try {
            String val = rs.getString(columnName);
            return (val != null) ? val : "N/A";
        } catch (SQLException e) {
            return "N/A";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    
        ProfilePanel.setVisible(false);
        GradesPanel.setVisible(false);
        SubjectsPanel.setVisible(false);
        ReportCardPanel.setVisible(false);

        if (e.getSource() == btnSignOut) {
            LandingPageGUI lp = new LandingPageGUI();
            lp.setVisible(true);
            this.dispose();
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