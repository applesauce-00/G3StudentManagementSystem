package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FacultyLogInPage extends JFrame implements ActionListener{

    private JLabel lblIcon, lblTitle, lblSubtitle, lblId, lblPassword;
    private JTextField txtId;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnBack;

    FacultyLogInPage() {

        // Frame settings
        setTitle("UNIVERSITY FACULTY PORTAL");
        setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // Logo/Icon
        lblIcon = new JLabel("🎓", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(462, 100, 100, 60);
        add(lblIcon);

        // Title
        lblTitle = new JLabel("UNIVERSITY FACULTY PORTAL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(62, 200, 900, 30);
        add(lblTitle);

        // Subtitle
        lblSubtitle = new JLabel("Sign in to start your session.");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setBounds(372, 260, 280, 30);
        add(lblSubtitle);

        // Faculty ID Label
        lblId = new JLabel("Faculty ID No.");
        lblId.setFont(new Font("Arial", Font.PLAIN, 14));
        lblId.setBounds(390, 330, 250, 20);
        add(lblId);

        // Faculty ID Field
        txtId = new JTextField();
        txtId.setBounds(390, 355, 250, 40);
        txtId.setFont(new Font("Arial", Font.PLAIN, 14));
        add(txtId);

        // Password Label
        lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setBounds(390, 410, 250, 20);
        add(lblPassword);

        // Password Field
        txtPassword = new JPasswordField();
        txtPassword.setBounds(390, 435, 250, 40);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        add(txtPassword);

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(390, 500, 250, 40);
        btnLogin.setBackground(new Color(52, 168, 235));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        add(btnLogin);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(20, 20, 100, 50);
        btnBack.setBackground(Color.LIGHT_GRAY);
        btnBack.setForeground(Color.BLACK);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        add(btnBack);
		
		
		btnBack.addActionListener(this);
		btnLogin.addActionListener(this);
		
	}    

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBack){
			LandingPageGUI lp = new LandingPageGUI();
			FrameSizeNavigation.navigate(this, lp);
		} else if (e.getSource() == btnLogin) {

    String id = txtId.getText().trim();
    String pass = new String(txtPassword.getPassword()).trim();

    if (id.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields!");
        return;
    }

    int result = FacultyDataManager.validateLogin(id, pass);

    switch (result) {

        case 0:
            JOptionPane.showMessageDialog(this, "Login Successful!");

            StudentManagerPage smp = new StudentManagerPage();
            FrameSizeNavigation.navigate(this, smp);
            break;

        case 1:
            JOptionPane.showMessageDialog(this,
                    "Incorrect password for this Faculty ID!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            break;

        case 2:
            JOptionPane.showMessageDialog(this,
                    "Faculty ID not found!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            break;
    }
	}
}
}
