package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class StudentLandingPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JButton btnSignOut;
    private JTable tblGrades;
    private JScrollPane tableScroll;

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
        add(btnSignOut);

        // Table Columns
        String[] columns = {
            "SUBJECT",
            "UNITS",
            "FINAL GRADE",
            "GRADE STATUS"
        };

        // Sample empty data
        Object[][] data = new Object[0][4];

        // Table
        tblGrades = new JTable(new DefaultTableModel(data, columns));
        tableScroll = new JScrollPane(tblGrades);
        tableScroll.setBounds(50, 100, 900, 550);
        add(tableScroll);

        btnSignOut.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSignOut) {
            LandingPageGUI lp = new LandingPageGUI();
            lp.setVisible(true);
            this.setVisible(false); 
        }
    }
}
