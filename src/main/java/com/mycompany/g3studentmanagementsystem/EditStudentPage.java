package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditStudentPage extends JFrame implements ActionListener {

    private JLabel lblTitle, lblStudentId, lblLastName, lblFirstName,
            lblMiddleName, lblSection, lblSex, lblBirthDate, lblEmail;

    private JTextField txtStudentId, txtLastName, txtFirstName,
            txtMiddleName, txtSection, txtEmail;

    private JComboBox<String> cboSex;

    private BirthDatePanel birthDatePanel;

    private JButton btnEdit, btnCancel;

    public EditStudentPage() {

        setTitle("Edit Student");
        setSize(674, 924);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // TITLE
        lblTitle = new JLabel("EDIT STUDENT");
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

        // BUTTONS
        btnEdit = new JButton("EDIT");
        btnEdit.setBounds(230, 700, 100, 40);
        btnEdit.setBackground(new Color(52, 168, 235));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFont(new Font("Arial", Font.BOLD, 14));
        btnEdit.setFocusPainted(false);
        add(btnEdit);

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(360, 700, 100, 40);
        btnCancel.setBackground(new Color(224, 69, 52));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        add(btnCancel);

        btnEdit.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnEdit) {

            String studentId = txtStudentId.getText().trim();
            String lastName = txtLastName.getText().trim();
            String firstName = txtFirstName.getText().trim();
            String middleName = txtMiddleName.getText().trim();
            String section = txtSection.getText().trim();
            String email = txtEmail.getText().trim();
            String sex = (String) cboSex.getSelectedItem();

            if (studentId.isEmpty() || lastName.isEmpty() ||
                firstName.isEmpty() || section.isEmpty() ||
                email.isEmpty() || sex == null) {

                JOptionPane.showMessageDialog(this,
                        "All fields are required!");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid email address!");
                return;
            }

            String birthDate = birthDatePanel.getBirthDate();

            StudentDataManager.updateStudent(
                    studentId,
                    lastName,
                    firstName,
                    middleName,
                    section,
                    sex.charAt(0),
                    birthDate,
                    email
            );

            JOptionPane.showMessageDialog(this,
                    "Student updated successfully!");

            new StudentManagerPage().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnCancel) {

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Cancel editing?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                new StudentManagerPage().setVisible(true);
                dispose();
            }
        }
    }
}
