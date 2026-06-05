package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class GradesManagerPage extends JFrame implements ActionListener{

    private JLabel lblIcon, lblTitle;
    private JButton btnAttendance, btnStudents, btnGrades, btnSignOut;
    private JButton btnSearch, btnEdit;
    private JTable tblGrades;
    private JScrollPane tableScroll;
    private JTextField txtSearchId;

    GradesManagerPage() {
		
		// Frame Settings
        setTitle("FACULTY PORTAL - Grades Manager");
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


        // Edit Button
        btnEdit = new JButton("EDIT GRADES");
        btnEdit.setBounds(20, 200, 160, 40);
        btnEdit.setBackground(new Color(52, 168, 235));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setEnabled(true);
        add(btnEdit);
       

        // Student Table 
        String[] columns = {"STUDENT ID", "NAME", "SECTION", "UNITS", "FINAL GRADE", "GRADE STATUS"};
        Object[][] data = new Object[0][6];

        tblGrades = new JTable(new DefaultTableModel(data, columns));
        tableScroll = new JScrollPane(tblGrades);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);
        
        addStudentGrade("2024-00001-BN-0","Pedro De Guzman", "BSCPE 3-1","3","1.50", "PASSED");
        addStudentGrade("2024-00002-BN-0","Jay Saragon", "BSIT 1-1", "3", "2.00", "PASSED");
        addStudentGrade("2024-00003-BN-0","Jun Capuz", "BSBA 4-1", "3", "5.00", "FAILED");
        
        btnAttendance.addActionListener(this);
		btnEdit.addActionListener(this);
		btnSearch.addActionListener(this);
		btnSignOut.addActionListener(this);        
		btnStudents.addActionListener(this);


    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAttendance){               // on work
			
		} else if (e.getSource() == btnEdit){
			int selectedRow = tblGrades.getSelectedRow();

                    if(selectedRow == -1){
                    JOptionPane.showMessageDialog(this, "Please select a student first!","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                    DefaultTableModel model = (DefaultTableModel) tblGrades.getModel();
                    GradesEditorPage gep = new GradesEditorPage(this, selectedRow, model);
                    gep.setVisible(true);
                    setVisible(false);
		} else if (e.getSource() == btnSearch){
                    String search = txtSearchId.getText().trim();
                    DefaultTableModel model = (DefaultTableModel) tblGrades.getModel();
                        boolean found = false;
                for(int i = 0; i < model.getRowCount(); i++){
                    String studentId = model.getValueAt(i, 0).toString();
                if(studentId.equals(search)){
                        tblGrades.setRowSelectionInterval(i, i);
                        found = true;
                        break;
                    }
                }
                if(found){
                    btnEdit.setEnabled(true);
                    JOptionPane.showMessageDialog(this, "Student Found!","Information Found",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Student Not Found!","Error",JOptionPane.ERROR_MESSAGE);
            }
		} else if (e.getSource() == btnSignOut){
			LandingPageGUI lp = new LandingPageGUI();
			lp.setVisible(true);
                        dispose();
		} else if (e.getSource() == btnStudents){
			StudentManagerPage smp = new StudentManagerPage();
			smp.setVisible(true);
                        dispose();
		} 
            }
        
         public void addStudentGrade(String id, String name, String section,String units, String finalGrade, String status){
                        DefaultTableModel model = (DefaultTableModel) tblGrades.getModel();
                        model.addRow(new Object[]{
                        id,
                        name,
                        section,
                        units,
                        finalGrade,
                        status
                    });
    }

    
}
