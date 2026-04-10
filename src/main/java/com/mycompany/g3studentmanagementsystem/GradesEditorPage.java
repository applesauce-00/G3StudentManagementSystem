package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GradesEditorPage extends JFrame implements ActionListener{

    private JLabel lblIcon, lblTitle;
    private JButton btnAttendance, btnStudents, btnGrades;
    private JButton btnSearch, btnEdit, btnSave, btnCancel;
    private JTable tblGrades;
    private JScrollPane tableScroll;
	private JTextField txtSearchId;

    GradesEditorPage() {
		
		// Frame Settings
        setTitle("FACULTY PORTAL - Grades Editor");
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
		btnAttendance.setEnabled(false);
        btnAttendance.setBounds(300, 20, 120, 40);
        btnAttendance.setBackground(new Color(52, 168, 235));
        btnAttendance.setForeground(Color.WHITE);
        add(btnAttendance);

        btnStudents = new JButton("STUDENTS");
		btnStudents.setEnabled(false);
        btnStudents.setBounds(430, 20, 120, 40);
        btnStudents.setBackground(new Color(35, 132, 189));
		btnStudents.setBackground(new Color(52, 168, 235));
        btnStudents.setForeground(Color.WHITE);
        add(btnStudents);

        btnGrades = new JButton("GRADES");
		btnGrades.setEnabled(false);
        btnGrades.setBounds(560, 20, 120, 40);
        btnGrades.setBackground(new Color(52, 168, 235));
        btnGrades.setForeground(Color.WHITE);
        add(btnGrades);

		
		

		
		
		
		
		
		
		
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


        // Edit Button
        btnEdit = new JButton("EDIT GRADES");
		btnEdit.setEnabled(false);
        btnEdit.setBounds(20, 200, 160, 40);
        btnEdit.setBackground(new Color(52, 168, 235));
        btnEdit.setForeground(Color.WHITE);
        add(btnEdit);
		
		// Save & Exit
		btnSave = new JButton("SAVE & EXIT");
        btnSave.setBounds(20, 550, 160, 40);
        btnSave.setBackground(new Color(52, 168, 235));
        btnSave.setForeground(Color.WHITE);
        add(btnSave);

        // Cancel
        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(20, 600, 160, 40);
        btnCancel.setBackground(new Color(224, 69, 52));
        btnCancel.setForeground(Color.WHITE);
        add(btnCancel);
       

        // Student Table 
        String[] columns = {"STUDENT ID", "NAME", "SECTION", "UNITS", "FINAL GRADE", "GRADE STATUS"};
        Object[][] data = new Object[0][6];

        tblGrades = new JTable(new DefaultTableModel(data, columns));
        tableScroll = new JScrollPane(tblGrades);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        

		btnCancel.addActionListener(this);
		btnEdit.addActionListener(this);
		btnSave.addActionListener(this);
		btnSearch.addActionListener(this);
		
		
		
		
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel){
			GradesManagerPage gmp = new GradesManagerPage();
			gmp.setVisible(true);
		} else if (e.getSource() == btnEdit){
//			EditStudentPage esp = new EditStudentPage();           on work
//			esp.setVisible(true);
		} else if (e.getSource() == btnSave){
			GradesManagerPage gmp = new GradesManagerPage();
			gmp.setVisible(true);
		} 
		else if (e.getSource() == btnSearch){
//			GradesManagerPage gmp = new GradesManagerPage();       on work
//			gmp.setVisible(true);
		} 
	}

    
}
