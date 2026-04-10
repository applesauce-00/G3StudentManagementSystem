//Name: SEDORO, Kirby T.
//Year & Section: BSIT 2-2
//Date: 

package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class LandingPageGUI extends JFrame implements ActionListener{
	
	private JLabel lblIcon, lblTitle, lblSubtitle;
    private JButton btnStudent, btnFaculty;

	 LandingPageGUI() {
		
        // Frame settings
        setTitle("University Student Management System");
		setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
		getContentPane().setBackground(new Color(235, 242, 250));



        // Logo/Icon
        lblIcon = new JLabel("🎓", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(480, 100, 100, 60);
        add(lblIcon);

        // Title
        lblTitle = new JLabel("UNIVERSITY STUDENT MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(90, 200, 900, 30);
        add(lblTitle);

        // Subtitle
        lblSubtitle = new JLabel("Please click your role");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setBounds(410, 320, 240, 30);
        add(lblSubtitle);

        // Student Button
        btnStudent = new JButton("Student");
        btnStudent.setBounds(445, 380, 170, 38);
        btnStudent.setBackground(new Color(52, 168, 235));
        btnStudent.setForeground(Color.WHITE);
        btnStudent.setFont(new Font("Arial", Font.BOLD, 14));
        btnStudent.setFocusPainted(false);
        btnStudent.setBorderPainted(false);
        add(btnStudent);

        // Faculty Button
        btnFaculty = new JButton("Faculty");
        btnFaculty.setBounds(445, 450, 170, 38);
        btnFaculty.setBackground(new Color(52, 168, 235));
        btnFaculty.setForeground(Color.WHITE);
        btnFaculty.setFont(new Font("Arial", Font.BOLD, 14));
        btnFaculty.setFocusPainted(false);
        btnFaculty.setBorderPainted(false);
        add(btnFaculty);

        
		btnFaculty.addActionListener(this);
		btnStudent.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnFaculty){
			FacultyLogInPage flp = new FacultyLogInPage();
			flp.setVisible(true);
			this.setVisible(false);
		} else if (e.getSource() == btnStudent){
			StudentLogInPage slp = new StudentLogInPage();
			slp.setVisible(true);
			this.setVisible(false);
		}
	}

	 
}
