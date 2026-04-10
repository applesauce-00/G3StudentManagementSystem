package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagerPage extends JFrame implements ActionListener{

    private JLabel lblIcon, lblTitle;
    private JButton btnAttendance, btnStudents, btnGrades, btnSignOut;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;
    private JTable tblStudent;
    private JScrollPane tableScroll;
	private JTextField txtSearchId;

    StudentManagerPage() {
		
		// Frame Settings
        setTitle("FACULTY PORTAL - Student Manager");
        setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));



        // Logo
        lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(10, 10, 60, 60);
        add(lblIcon);

        // Title
        lblTitle = new JLabel("FACULTY PORTAL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(80, 20, 200, 40);
        add(lblTitle);

		
		
		
        // Top Panel
        btnAttendance = new JButton("ATTENDANCE");
        btnAttendance.setBounds(300, 20, 120, 40);
        btnAttendance.setBackground(new Color(52, 168, 235));
        btnAttendance.setForeground(Color.WHITE);
        add(btnAttendance);

        btnStudents = new JButton("STUDENTS");
		btnStudents.setEnabled(false);
        btnStudents.setBounds(430, 20, 120, 40);
        btnStudents.setBackground(new Color(35, 132, 189));
        btnStudents.setForeground(Color.WHITE);
        add(btnStudents);

        btnGrades = new JButton("GRADES");
        btnGrades.setBounds(560, 20, 120, 40);
        btnGrades.setBackground(new Color(52, 168, 235));
        btnGrades.setForeground(Color.WHITE);
        add(btnGrades);

		
		
		//Sign Out
        btnSignOut = new JButton("Sign Out");
        btnSignOut.setBounds(850, 20, 120, 40);
        btnSignOut.setBackground(new Color(224, 69, 52));
        btnSignOut.setForeground(Color.WHITE);
        add(btnSignOut);

		
		
		
		
		
		
		
		//Side Panel

        // Search Field 
        txtSearchId = new JTextField(" Search ID...");
        txtSearchId.setBounds(20, 100, 160, 35); 
        add(txtSearchId);

        // Search Button 
        btnSearch = new JButton("SEARCH STUDENT");
        btnSearch.setBounds(20, 140, 160, 40);
        btnSearch.setBackground(new Color(52, 168, 235));
        btnSearch.setForeground(Color.WHITE);
        add(btnSearch);

        // Add Button 
        btnAdd = new JButton("ADD STUDENT");
        btnAdd.setBounds(20, 200, 160, 40);
        btnAdd.setBackground(new Color(52, 168, 235));
        btnAdd.setForeground(Color.WHITE);
        add(btnAdd);

        // Edit Button
        btnEdit = new JButton("EDIT STUDENT");
        btnEdit.setBounds(20, 250, 160, 40);
        btnEdit.setBackground(new Color(52, 168, 235));
        btnEdit.setForeground(Color.WHITE);
        add(btnEdit);

        // Delete Button
        btnDelete = new JButton("DELETE STUDENT");
        btnDelete.setBounds(20, 300, 160, 40);
        btnDelete.setBackground(new Color(52, 168, 235));
        btnDelete.setForeground(Color.WHITE);
        add(btnDelete);

        // Student Table 
        String[] columns = {"STUDENT ID", "NAME", "SECTION", "GENDER", "BIRTH DATE", "EMAIL ADDRESS"};
        Object[][] data = new Object[0][6];

        tblStudent = new JTable(new DefaultTableModel(data, columns));
        tableScroll = new JScrollPane(tblStudent);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        
		
		btnAdd.addActionListener(this);
		btnAttendance.addActionListener(this);
		btnDelete.addActionListener(this);
		btnEdit.addActionListener(this);
		btnGrades.addActionListener(this);
		btnSearch.addActionListener(this);
		btnSignOut.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd){
			AddStudentPage asp = new AddStudentPage();
			asp.setVisible(true);
		} else if (e.getSource() == btnAttendance){
			EditStudentPage esp = new EditStudentPage();
			esp.setVisible(true);            //still working
			this.setVisible(false);
		} else if (e.getSource() == btnDelete){
			DeleteStudentPage dsp = new DeleteStudentPage();
			dsp.setVisible(true);
		} else if (e.getSource() == btnEdit){
			EditStudentPage esp = new EditStudentPage();
			esp.setVisible(true);
		} else if (e.getSource() == btnGrades){
			GradesManagerPage gsp = new GradesManagerPage();
			gsp.setVisible(true);
			this.setVisible(false);
		} else if (e.getSource() == btnSearch){
			GradesManagerPage gsp = new GradesManagerPage();
			gsp.setVisible(true);
		} else if (e.getSource() == btnSignOut){
			LandingPageGUI lp = new LandingPageGUI();
			lp.setVisible(true);
		} 
	}

    
}
