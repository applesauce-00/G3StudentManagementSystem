package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class DeleteStudentPage extends JFrame implements ActionListener {

    private JLabel lblTitle, lblStudentId, lblLastName, lblFirstName, lblMiddleName, lblSection, lblSex, lblBirthDate, lblEmail;
    private JLabel dpLastName, dpFirstName, dpMiddleName, dpSection, dpSex, dpBirthDate, dpEmail;
    private JTextField txtStudentId;
    private JButton btnDelete, btnCancel;
    private String studentTargetId;

    public DeleteStudentPage(Student student) {
        this.studentTargetId = student.getId();

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

        txtStudentId = new JTextField(studentTargetId);
        txtStudentId.setBounds(250, 100, 200, 30);
        txtStudentId.setEditable(false);
        add(txtStudentId);

        // LAST NAME (DISPLAY ONLY)
        lblLastName = new JLabel("LAST NAME:");
        lblLastName.setBounds(100, 160, 120, 25);
        add(lblLastName);

        dpLastName = new JLabel(student.getLastName());
        dpLastName.setBounds(250, 160, 200, 30);
        add(dpLastName);

        // FIRST NAME (DISPLAY ONLY)
        lblFirstName = new JLabel("FIRST NAME:");
        lblFirstName.setBounds(100, 220, 120, 25);
        add(lblFirstName);

        dpFirstName = new JLabel(student.getFirstName());
        dpFirstName.setBounds(250, 220, 200, 30);
        add(dpFirstName);

        // MIDDLE NAME (DISPLAY ONLY)
        lblMiddleName = new JLabel("MIDDLE NAME:");
        lblMiddleName.setBounds(100, 280, 120, 25);
        add(lblMiddleName);

        dpMiddleName = new JLabel(student.getMiddleName().isEmpty() ? "None" : student.getMiddleName());
        dpMiddleName.setBounds(250, 280, 200, 30);
        add(dpMiddleName);

        // SECTION
        lblSection = new JLabel("SECTION:");
        lblSection.setBounds(100, 340, 120, 25);
        add(lblSection);

        dpSection = new JLabel(student.getSection());
        dpSection.setBounds(250, 340, 200, 30);
        add(dpSection);

        // SEX
        lblSex = new JLabel("SEX:");
        lblSex.setBounds(100, 400, 120, 25);
        add(lblSex);

        dpSex = new JLabel(student.getSex() == 'M' ? "Male" : "Female");
        dpSex.setBounds(250, 400, 200, 30);
        add(dpSex);

        // BIRTH DATE
        lblBirthDate = new JLabel("BIRTH DATE:");
        lblBirthDate.setBounds(100, 460, 120, 25);
        add(lblBirthDate);

        dpBirthDate = new JLabel(student.getBirthDate());
        dpBirthDate.setBounds(250, 460, 200, 30);
        add(dpBirthDate);

        // EMAIL
        lblEmail = new JLabel("EMAIL ADDRESS:");
        lblEmail.setBounds(100, 520, 140, 25);
        add(lblEmail);

        dpEmail = new JLabel(student.getEmail());
        dpEmail.setBounds(250, 520, 200, 30);
        add(dpEmail);

        // DELETE BUTTON
        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(230, 680, 100, 40);
        btnDelete.setBackground(new Color(52, 168, 235));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        add(btnDelete);

        // CANCEL BUTTON
        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(360, 680, 100, 40);
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

            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Student ID is required!",
                        "Missing Information",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean isDeleted = StudentDataManager.deleteStudent(studentId);

                if (isDeleted) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Student deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error: Student could not be found.",
                            "Deletion Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                
                
                try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/g3studentmanagementsystem","root", "");
            
                String sql  = "DELETE FROM students WHERE student_id = ?";
                
                
                 PreparedStatement student = con.prepareStatement(sql);

                    student.setString(1, studentId);
                    
                    student.executeUpdate();
                    con.close();


                StudentManagerPage smp = new StudentManagerPage();
                smp.setVisible(true);
                this.setVisible(false);
                this.dispose(); 
                
                 }catch (SQLException sqlException){
                sqlException.printStackTrace();

            }

            }

        } else if (e.getSource() == btnCancel) {
            StudentManagerPage smp = new StudentManagerPage();
            smp.setVisible(true);
            this.setVisible(false);
            this.dispose(); 
        }
    }
}