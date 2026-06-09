package com.mycompany.g3studentmanagementsystem;

import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class AddStudentPage extends JFrame implements ActionListener {

    private JLabel lblTitle, lblStudentId, lblLastName, lblFirstName,
            lblMiddleName, lblSection, lblSex, lblBirthDate,
            lblEmail, lblPassword;
    private JTextField txtStudentId, txtLastName, txtFirstName, txtMiddleName, txtSection, txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cboSex;
    private BirthDatePanel birthDatePanel;
    private JButton btnAdd, btnCancel;
    public AddStudentPage() {

        setTitle("Add Student");
        setSize(674, 924);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // TITLE
        lblTitle = new JLabel("ADD STUDENT");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 674, 40);
        add(lblTitle);

        // STUDENT ID
        lblStudentId = new JLabel("STUDENT ID:");
        lblStudentId.setBounds(100, 100, 120, 25);
        add(lblStudentId);

        txtStudentId = new JTextField();
        txtStudentId.setBounds(250, 100, 200, 30);
        add(txtStudentId);

        // LAST NAME
        lblLastName = new JLabel("LAST NAME:");
        lblLastName.setBounds(100, 160, 120, 25);
        add(lblLastName);

        txtLastName = new JTextField();
        txtLastName.setBounds(250, 160, 200, 30);
        add(txtLastName);

        // FIRST NAME
        lblFirstName = new JLabel("FIRST NAME:");
        lblFirstName.setBounds(100, 220, 120, 25);
        add(lblFirstName);

        txtFirstName = new JTextField();
        txtFirstName.setBounds(250, 220, 200, 30);
        add(txtFirstName);

        // MIDDLE NAME
        lblMiddleName = new JLabel("MIDDLE NAME:");
        lblMiddleName.setBounds(100, 280, 120, 25);
        add(lblMiddleName);

        txtMiddleName = new JTextField();
        txtMiddleName.setBounds(250, 280, 200, 30);
        add(txtMiddleName);

        // SECTION
        lblSection = new JLabel("SECTION:");
        lblSection.setBounds(100, 340, 120, 25);
        add(lblSection);

        txtSection = new JTextField();
        txtSection.setBounds(250, 340, 200, 30);
        add(txtSection);

        // SEX
        lblSex = new JLabel("SEX:");
        lblSex.setBounds(100, 400, 120, 25);
        add(lblSex);

        cboSex = new JComboBox<>(new String[]{"Male", "Female"});
        cboSex.setBounds(250, 400, 200, 30);
        add(cboSex);

        // BIRTH DATE
        lblBirthDate = new JLabel("BIRTH DATE:");
        lblBirthDate.setBounds(100, 460, 120, 25);
        add(lblBirthDate);

        birthDatePanel = new BirthDatePanel();
        birthDatePanel.setBounds(250, 460, 300, 30);
        add(birthDatePanel);

        // EMAIL
        lblEmail = new JLabel("EMAIL ADDRESS:");
        lblEmail.setBounds(100, 520, 140, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(250, 520, 200, 30);
        add(txtEmail);

        // PASSWORD
        lblPassword = new JLabel("PASSWORD:");
        lblPassword.setBounds(100, 580, 140, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(250, 580, 200, 30);
        add(txtPassword);

        // BUTTONS
        btnAdd = new JButton("ADD");
        btnAdd.setBounds(230, 700, 100, 40);
        btnAdd.setBackground(new Color(52, 168, 235));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setFocusPainted(false);
        add(btnAdd);

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(360, 700, 100, 40);
        btnCancel.setBackground(new Color(224, 69, 52));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        add(btnCancel);

        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {

            String studentId = txtStudentId.getText().trim();
            String lastName = txtLastName.getText().trim();
            String firstName = txtFirstName.getText().trim();
            String middleName = txtMiddleName.getText().trim();
            String section = txtSection.getText().trim();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String sexStr = (String) cboSex.getSelectedItem();

            // Validation
            if (studentId.isEmpty() || lastName.isEmpty() ||
                firstName.isEmpty() || section.isEmpty() ||
                email.isEmpty() || password.isEmpty() || sexStr == null) {

                JOptionPane.showMessageDialog(this, "Please fill all required fields!");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email address! Must contain '@'");
                return;
            }

            // Get Data from components safely
            String birthDate = birthDatePanel.getBirthDate();
            char sexChar = (sexStr.equalsIgnoreCase("Male")) ? 'M' : 'F';

            // Database operation
			String sql = "INSERT INTO students (student_id, last_name, first_name, middle_name, section, sex, birth_date, email, password) "
					   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (Connection con = ConnectionString.getConnection();
				 PreparedStatement student = con.prepareStatement(sql)) {

				student.setString(1, studentId);
				student.setString(2, lastName);
				student.setString(3, firstName);
				student.setString(4, middleName);
				student.setString(5, section);
				student.setString(6, String.valueOf(sexChar));
				student.setString(7, birthDate);
				student.setString(8, email);
				student.setString(9, password);

				student.executeUpdate();

				// Update Memory Manager Class
				Student s = new Student(studentId, lastName, firstName, middleName, section, sexChar, birthDate, email, password);
				StudentDataManager.addStudent(s);

				JOptionPane.showMessageDialog(this, "Student Added Successfully!");

				new StudentManagerPage().setVisible(true);
				this.dispose();

			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				JOptionPane.showMessageDialog(this, "Database Error: " + sqlException.getMessage());
			}      
		}
		if (e.getSource() == btnCancel) {
            new StudentManagerPage().setVisible(true);
            dispose();
        }
	}
}