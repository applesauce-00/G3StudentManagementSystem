package com.mycompany.g3studentmanagementsystem;

import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditStudentPage extends JFrame implements ActionListener {

    private JLabel lblTitle, lblStudentId, lblLastName, lblFirstName, lblMiddleName, lblSection, lblSex, lblBirthDate, lblEmail, lblStatus;
    private JTextField txtStudentId, txtLastName, txtFirstName, txtMiddleName, txtSection, txtEmail;
    private JComboBox<String> cboSex;
    private BirthDatePanel birthDatePanel;
    private JCheckBox chkActive;
    private JButton btnEdit, btnCancel;

    public EditStudentPage(String studentId, String lastName, String firstName, String middleName,
                           String section, String sex, String birthDate, String email, int isActive) {

        setTitle("Edit Student");
        setSize(674, 980);
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

        txtStudentId = new JTextField(studentId);
        txtStudentId.setEditable(false);
        txtStudentId.setBounds(250, 100, 200, 30);
        add(txtStudentId);

        // LAST NAME
        lblLastName = new JLabel("LAST NAME:");
        lblLastName.setBounds(100, 160, 120, 25);
        add(lblLastName);

        txtLastName = new JTextField(lastName);
        txtLastName.setBounds(250, 160, 200, 30);
        add(txtLastName);

        // FIRST NAME
        lblFirstName = new JLabel("FIRST NAME:");
        lblFirstName.setBounds(100, 220, 120, 25);
        add(lblFirstName);

        txtFirstName = new JTextField(firstName);
        txtFirstName.setBounds(250, 220, 200, 30);
        add(txtFirstName);

        // MIDDLE NAME
        lblMiddleName = new JLabel("MIDDLE NAME:");
        lblMiddleName.setBounds(100, 280, 120, 25);
        add(lblMiddleName);

        txtMiddleName = new JTextField(middleName);
        txtMiddleName.setBounds(250, 280, 200, 30);
        add(txtMiddleName);

        // SECTION
        lblSection = new JLabel("SECTION:");
        lblSection.setBounds(100, 340, 120, 25);
        add(lblSection);

        txtSection = new JTextField(section);
        txtSection.setBounds(250, 340, 200, 30);
        add(txtSection);

        // SEX
        lblSex = new JLabel("SEX:");
        lblSex.setBounds(100, 400, 120, 25);
        add(lblSex);

        cboSex = new JComboBox<>(new String[]{"Male", "Female"});
        cboSex.setSelectedItem(sex);
        cboSex.setBounds(250, 400, 200, 30);
        add(cboSex);

        // BIRTH DATE
        lblBirthDate = new JLabel("BIRTH DATE:");
        lblBirthDate.setBounds(100, 460, 120, 25);
        add(lblBirthDate);

        birthDatePanel = new BirthDatePanel();
        birthDatePanel.setBirthDate(birthDate);
        birthDatePanel.setBounds(250, 460, 300, 30);
        add(birthDatePanel);

        // EMAIL
        lblEmail = new JLabel("EMAIL ADDRESS:");
        lblEmail.setBounds(100, 520, 140, 25);
        add(lblEmail);

        txtEmail = new JTextField(email);
        txtEmail.setBounds(250, 520, 200, 30);
        add(txtEmail);

        // ACTIVE STATUS CHECKBOX
        lblStatus = new JLabel("STATUS:");
        lblStatus.setBounds(100, 580, 120, 25);
        add(lblStatus);

        chkActive = new JCheckBox("Active Student");
        chkActive.setSelected(isActive == 1);
        chkActive.setBackground(new Color(235, 242, 250));
        chkActive.setFont(new Font("Arial", Font.PLAIN, 13));
        chkActive.setBounds(250, 578, 200, 30);
        add(chkActive);

        // BUTTONS
        btnEdit = new JButton("EDIT");
        btnEdit.setBounds(230, 660, 100, 40);
        btnEdit.setBackground(new Color(52, 168, 235));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFont(new Font("Arial", Font.BOLD, 14));
        btnEdit.setFocusPainted(false);
        add(btnEdit);

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(360, 660, 100, 40);
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
            String lastName   = txtLastName.getText().trim();
            String firstName  = txtFirstName.getText().trim();
            String middleName = txtMiddleName.getText().trim();
            String section    = txtSection.getText().trim();
            String email      = txtEmail.getText().trim();
            String sex        = (String) cboSex.getSelectedItem();
            String birthDate  = birthDatePanel.getBirthDate();
            int    isActive   = chkActive.isSelected() ? 1 : 0;

            // Validate required fields
            if (studentId.isEmpty() || lastName.isEmpty() || firstName.isEmpty() ||
                section.isEmpty() || email.isEmpty() || sex == null || birthDate.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "All fields are required! Please fill in all information.",
                        "Missing Information", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email address!");
                return;
            }

            // If marking inactive, ask for confirmation first
            if (isActive == 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "You are setting this student as Inactive.\n" +
                        "They will be moved to the Inactive Students list.\nContinue?",
                        "Confirm Inactive", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) return;
            }

            try (Connection con = ConnectionString.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                     "UPDATE students SET last_name=?, first_name=?, middle_name=?, " +
                     "section=?, sex=?, birth_date=?, email=?, is_active=? WHERE student_id=?")) {

                ps.setString(1, lastName);
                ps.setString(2, firstName);
                ps.setString(3, middleName);
                ps.setString(4, section);
                ps.setString(5, sex);
                ps.setString(6, birthDate);
                ps.setString(7, email);
                ps.setInt(8, isActive);
                ps.setString(9, studentId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Student updated successfully!");
                    new StudentManagerPage().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No student found with ID: " + studentId);
                }

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + sqlException.getMessage());
            }

        } else if (e.getSource() == btnCancel) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to cancel?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new StudentManagerPage().setVisible(true);
                this.dispose();
            }
        }
    }
}