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
    private DefaultTableModel model;
    private GradesManagerPage parent;
    private int selectedRow;
    

    GradesEditorPage( GradesManagerPage parent,int selectedRow,DefaultTableModel model) {
        this.parent = parent;
        this.selectedRow = selectedRow;
        this.model = model;    
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

        DefaultTableModel editorModel =
                new DefaultTableModel(columns, 0) {

            @Override
            public  boolean isCellEditable(int row,int column){
            return column == 4 || column == 5;
            }
        };
            editorModel.addRow(new Object[]{
            model.getValueAt(selectedRow, 0),
            model.getValueAt(selectedRow, 1),
            model.getValueAt(selectedRow, 2),
            model.getValueAt(selectedRow, 3),
            model.getValueAt(selectedRow, 4),
            model.getValueAt(selectedRow, 5)
        });
        
        tblGrades = new JTable(editorModel);
        
        tableScroll = new JScrollPane(tblGrades);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        

		btnCancel.addActionListener(this);
		btnSave.addActionListener(this);
		btnSearch.addActionListener(this);
	
    }
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel){
			
			parent.setVisible(true);
                        dispose();
		} else if (e.getSource() == btnEdit){
                        int selectedRow = tblGrades.getSelectedRow();
                        if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this, "Please select a student." );
                        return;
                    }
                        JOptionPane.showMessageDialog(this, "You can edit the grades.");
//			EditStudentPage esp = new EditStudentPage();           on work
//			esp.setVisible(true);
		} else if (e.getSource() == btnSave){
                        DefaultTableModel editorModel = (DefaultTableModel) tblGrades.getModel();
                        model.setValueAt(editorModel.getValueAt(0, 4),selectedRow,4);
                        // Update Grade Status
                        model.setValueAt(editorModel.getValueAt(0, 5),selectedRow,5);
                        // Refresh table
                        model.fireTableRowsUpdated(selectedRow,selectedRow);
                        parent.setVisible(true);
                        JOptionPane.showMessageDialog(this, "Grades saved successfully!","Successfully",JOptionPane.INFORMATION_MESSAGE);                        
                        dispose();
		} else if (e.getSource() == btnSearch){
                    String searchId = txtSearchId.getText().trim();

                    String studentId = tblGrades.getValueAt(0, 0).toString();

                    if (studentId.equals(searchId)) {
                        tblGrades.setRowSelectionInterval(0, 0);
                        JOptionPane.showMessageDialog(this,"Student Found!","Information Found",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,"Student Not Found!","Error",JOptionPane.ERROR_MESSAGE);
                    }
            }
        }
                    
//			GradesManagerPage gmp = new GradesManagerPage();       on work
//			gmp.setVisible(true);
} 
       
