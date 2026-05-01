package com.mycompany.g3studentmanagementsystem;

import StudentDataHandler.AddStudentData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddStudentPage extends JFrame implements ActionListener{

    public JLabel lblTitle, lblStudentId, lblName, lblSection, lblGender, lblBirthDate, lblEmail, lblGrades;
    public JTextField txtStudentId, txtName, txtSection, txtGender, txtBirthDate, txtEmail, txtGrades;
    public JButton btnAdd;
    public JButton btnCancel;

    AddStudentPage() {
        // Frame settings
        setTitle("Add Student");
        setSize(674, 924);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // Title
        lblTitle = new JLabel("ADD STUDENT");
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

        txtName = new JTextField("");
        txtName.setBounds(250, 170, 200, 30);
        add(txtName);

        // SECTION
        lblSection = new JLabel("SECTION:");
        lblSection.setBounds(100, 240, 120, 25);
        add(lblSection);

        txtSection = new JTextField("");
        txtSection.setBounds(250, 240, 200, 30);
        add(txtSection);

        // GENDER
        lblGender = new JLabel("GENDER:");
        lblGender.setBounds(100, 310, 120, 25);
        add(lblGender);

        txtGender = new JTextField("");
        txtGender.setBounds(250, 310, 200, 30);
        add(txtGender);

        // BIRTH DATE
        lblBirthDate = new JLabel("BIRTH DATE:");
        lblBirthDate.setBounds(100, 380, 120, 25);
        add(lblBirthDate);

        txtBirthDate = new JTextField("");
        txtBirthDate.setBounds(250, 380, 200, 30);
        add(txtBirthDate);

        // EMAIL
        lblEmail = new JLabel("EMAIL ADDRESS:");
        lblEmail.setBounds(100, 450, 140, 25);
        add(lblEmail);

        txtEmail = new JTextField("");
        txtEmail.setBounds(250, 450, 200, 30);
        add(txtEmail);

        // GRADES
        lblGrades = new JLabel("GRADES:");
        lblGrades.setBounds(100, 520, 120, 25);
        add(lblGrades);

        txtGrades = new JTextField("");
        txtGrades.setBounds(250, 520, 200, 30);
        add(txtGrades);

        // BUTTONS
        btnAdd = new JButton("ADD");
        btnAdd.setBounds(230, 650, 100, 40);
        btnAdd.setBackground(new Color(52, 168, 235));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        add(btnAdd);

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(360, 650, 100, 40);
        btnCancel.setBackground(new Color(224, 69, 52));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        add(btnCancel);

        AddStudentData add = new AddStudentData(this);

        btnAdd.addActionListener(add);
        btnCancel.addActionListener(add);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd){
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
