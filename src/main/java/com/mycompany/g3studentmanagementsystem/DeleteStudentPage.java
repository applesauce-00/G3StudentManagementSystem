package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeleteStudentPage extends JFrame implements ActionListener{

    private JLabel lblTitle, lblStudentId, lblName, lblSection, lblGender, lblBirthDate, lblEmail, lblGrades;
    private JLabel dpName, dpSection, dpGender, dpBirthDate, dpEmail, dpGrades;                //dp = for display only
	private JTextField txtStudentId;
    private JButton btnDelete, btnCancel;

    DeleteStudentPage() {
        // Frame settings
        setTitle("Delete Student");
        setSize(674, 924);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // Title
        lblTitle = new JLabel("DELETE STUDENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 674, 40);
        add(lblTitle);

        // STUDENT ID
        lblStudentId = new JLabel("STUDENT ID:");
        lblStudentId.setBounds(100, 100, 120, 25);
        add(lblStudentId);

        txtStudentId = new JTextField("");
        txtStudentId.setBounds(250, 100, 200, 30);
        add(txtStudentId);

        // NAME
        lblName = new JLabel("NAME:");
        lblName.setBounds(100, 170, 120, 25);
        add(lblName);

        dpName = new JLabel("");
        dpName.setBounds(250, 170, 200, 30);
        add(dpName);

        // SECTION
        lblSection = new JLabel("SECTION:");
        lblSection.setBounds(100, 240, 120, 25);
        add(lblSection);

        dpSection = new JLabel("");
        dpSection.setBounds(250, 240, 200, 30);
        add(dpSection);

        // GENDER
        lblGender = new JLabel("GENDER:");
        lblGender.setBounds(100, 310, 120, 25);
        add(lblGender);

        dpGender = new JLabel("");
        dpGender.setBounds(250, 310, 200, 30);
        add(dpGender);

        // BIRTH DATE
        lblBirthDate = new JLabel("BIRTH DATE:");
        lblBirthDate.setBounds(100, 380, 120, 25);
        add(lblBirthDate);

        dpBirthDate = new JLabel("");
        dpBirthDate.setBounds(250, 380, 200, 30);
        add(dpBirthDate);

        // EMAIL
        lblEmail = new JLabel("EMAIL ADDRESS:");
        lblEmail.setBounds(100, 450, 140, 25);
        add(lblEmail);

        dpEmail = new JLabel("");
        dpEmail.setBounds(250, 450, 200, 30);
        add(dpEmail);

        // GRADES
        lblGrades = new JLabel("GRADES:");
        lblGrades.setBounds(100, 520, 120, 25);
        add(lblGrades);

        dpGrades = new JLabel("");
        dpGrades.setBounds(250, 520, 200, 30);
        add(dpGrades);

        // BUTTONS
        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(230, 650, 100, 40);
        btnDelete.setBackground(new Color(52, 168, 235));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        add(btnDelete);

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(360, 650, 100, 40);
        btnCancel.setBackground(new Color(224, 69, 52));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        add(btnCancel);
		
		
		btnDelete.addActionListener(this);		
		btnCancel.addActionListener(this);

        
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnDelete){
			StudentManagerPage smp = new StudentManagerPage();
			smp.setVisible(true);
			this.setVisible(false);
		} else if (e.getSource() == btnCancel){
			StudentManagerPage smp = new StudentManagerPage();
			smp.setVisible(true);
			this.setVisible(false);
		}
		
	}
}
