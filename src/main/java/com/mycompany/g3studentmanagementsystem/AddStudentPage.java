package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class AddStudentPage extends JFrame implements ActionListener {

    private JLabel lblTitle, lblStudentId, lblName, lblSection, lblSex, lblBirthDate, lblEmail;
    private JTextField txtStudentId, txtName, txtSection, txtEmail;
    private JComboBox<String> cboSex;
    private JDateChooser dateChooserBirth;
    private JButton btnAdd, btnCancel;

    AddStudentPage() {

        // FRAME SETTINGS
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

        // NAME
        lblName = new JLabel("NAME:");
        lblName.setBounds(100, 170, 120, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(250, 170, 200, 30);
        add(txtName);

        // SECTION
        lblSection = new JLabel("SECTION:");
        lblSection.setBounds(100, 240, 120, 25);
        add(lblSection);

        txtSection = new JTextField();
        txtSection.setBounds(250, 240, 200, 30);
        add(txtSection);

        // SEX
        lblSex = new JLabel("SEX:");
        lblSex.setBounds(100, 310, 120, 25);
        add(lblSex);

        cboSex = new JComboBox<>(new String[]{"Male", "Female"});
        cboSex.setBounds(250, 310, 200, 30);
        add(cboSex);

        // BIRTHDATE
        lblBirthDate = new JLabel("BIRTH DATE:");
        lblBirthDate.setBounds(100, 380, 120, 25);
        add(lblBirthDate);

        dateChooserBirth = new JDateChooser();
        dateChooserBirth.setDateFormatString("yyyy-MM-dd");
        dateChooserBirth.setBounds(250, 380, 200, 30);
        add(dateChooserBirth);

        // EMAIL
        lblEmail = new JLabel("EMAIL ADDRESS:");
        lblEmail.setBounds(100, 450, 140, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(250, 450, 200, 30);
        add(txtEmail);

        // ADD BUTTON
        btnAdd = new JButton("ADD");
        btnAdd.setBounds(230, 650, 100, 40);
        btnAdd.setBackground(new Color(52, 168, 235));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        add(btnAdd);

        // CANCEL BUTTON
        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(360, 650, 100, 40);
        btnCancel.setBackground(new Color(224, 69, 52));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        add(btnCancel);

        // ACTIONS
        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    @Override
public void actionPerformed(ActionEvent e) {

    if (e.getSource() == btnAdd) {

        String studentId = txtStudentId.getText().trim();
        String name = txtName.getText().trim();
        String section = txtSection.getText().trim();
        String email = txtEmail.getText().trim();
        String sex = (String) cboSex.getSelectedItem();
        Date birthDate = dateChooserBirth.getDate();

        if (studentId.isEmpty() ||
            name.isEmpty() ||
            section.isEmpty() ||
            email.isEmpty() ||
            sex == null ||
            birthDate == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "All fields are required! Please fill in all information.",
                    "Missing Information",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email address! Must contain '@'",
                    "Email Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        StudentManagerPage smp = new StudentManagerPage();
        smp.setVisible(true);
        this.setVisible(false);

    } else if (e.getSource() == btnCancel) {

        StudentManagerPage smp = new StudentManagerPage();
        smp.setVisible(true);
        this.setVisible(false);
    }
}
}