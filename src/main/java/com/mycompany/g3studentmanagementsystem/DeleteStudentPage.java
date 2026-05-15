package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeleteStudentPage extends JFrame implements ActionListener {

    private JLabel lblTitle, lblStudentId, lblName, lblSection, lblSex, lblBirthDate, lblEmail;
    private JLabel dpName, dpSection, dpSex, dpBirthDate, dpEmail;
    private JTextField txtStudentId;
    private JButton btnDelete, btnCancel;

    DeleteStudentPage() {

        setTitle("Delete Student");
        setSize(674, 924);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        // TITLE
        lblTitle = new JLabel("DELETE STUDENT");
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

        // NAME (DISPLAY ONLY)
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

        // SEX
        lblSex = new JLabel("SEX:");
        lblSex.setBounds(100, 310, 120, 25);
        add(lblSex);

        dpSex = new JLabel("");
        dpSex.setBounds(250, 310, 200, 30);
        add(dpSex);

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

        // DELETE BUTTON
        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(230, 650, 100, 40);
        btnDelete.setBackground(new Color(52, 168, 235));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        add(btnDelete);

        // CANCEL BUTTON
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

        if (e.getSource() == btnDelete) {

            String studentId = txtStudentId.getText().trim();

            // SIMPLE VALIDATION (same style as AddStudentPage)
            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Student ID is required!",
                        "Missing Information",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // CONFIRMATION (still simple, but important)
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
				//add function of deletion when done

                JOptionPane.showMessageDialog(
                        this,
                        "Student deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                StudentManagerPage smp = new StudentManagerPage();
                smp.setVisible(true);
                this.setVisible(false);
            }

        } else if (e.getSource() == btnCancel) {

            StudentManagerPage smp = new StudentManagerPage();
            smp.setVisible(true);
            this.setVisible(false);
        }
    }
}