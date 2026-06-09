package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GradesEditorPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JButton btnAttendance, btnStudents, btnGrades;
    private JButton btnSave, btnCancel, btnSearch;
    private JTable tblGrades;
    private JScrollPane tableScroll;
    private JTextField txtSearchId;

    private DefaultTableModel model;
    private GradesManagerPage parent;
    private int selectedRow;

    GradesEditorPage(GradesManagerPage parent, int selectedRow, DefaultTableModel model) {

        this.parent = parent;
        this.selectedRow = selectedRow;
        this.model = model;

        setTitle("FACULTY PORTAL - Grades Editor");
        setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(10, 10, 60, 60);
        add(lblIcon);

        lblTitle = new JLabel("FACULTY PORTAL");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(80, 20, 200, 40);
        add(lblTitle);

        btnAttendance = new JButton("ATTENDANCE");
        btnAttendance.setEnabled(false);
        btnAttendance.setBounds(300, 20, 120, 40);
        add(btnAttendance);

        btnStudents = new JButton("STUDENTS");
        btnStudents.setEnabled(false);
        btnStudents.setBounds(430, 20, 120, 40);
        add(btnStudents);

        btnGrades = new JButton("GRADES");
        btnGrades.setEnabled(false);
        btnGrades.setBounds(560, 20, 120, 40);
        add(btnGrades);

        txtSearchId = new JTextField();
        txtSearchId.setBounds(20, 100, 160, 35);
        add(txtSearchId);

        btnSearch = new JButton("SEARCH");
        btnSearch.setBounds(20, 140, 160, 40);
        btnSearch.setBackground(new Color(52, 168, 235));
        btnSearch.setForeground(Color.WHITE);
        add(btnSearch);

        btnSave = new JButton("SAVE & EXIT");
        btnSave.setBounds(20, 550, 160, 40);
        btnSave.setBackground(new Color(52, 168, 235));
        btnSave.setForeground(Color.WHITE);
        add(btnSave);

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(20, 600, 160, 40);
        btnCancel.setBackground(new Color(224, 69, 52));
        btnCancel.setForeground(Color.WHITE);
        add(btnCancel);

        String[] columns = {
                "STUDENT ID", "NAME", "SECTION",
                "MATH", "SCIENCE", "ENGLISH",
                "GWA", "STATUS"
        };

        DefaultTableModel editorModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 3 || col == 4 || col == 5;
            }
        };

        editorModel.addRow(new Object[]{
                model.getValueAt(selectedRow, 0),
                model.getValueAt(selectedRow, 1),
                model.getValueAt(selectedRow, 2),
                model.getValueAt(selectedRow, 3),
                model.getValueAt(selectedRow, 4),
                model.getValueAt(selectedRow, 5),
                model.getValueAt(selectedRow, 6),
                model.getValueAt(selectedRow, 7)
        });

        tblGrades = new JTable(editorModel);
        tableScroll = new JScrollPane(tblGrades);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);
        btnSearch.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnCancel) {
            parent.setVisible(true);
            dispose();
        }

        else if (e.getSource() == btnSave) {

            if (tblGrades.isEditing()) {
                tblGrades.getCellEditor().stopCellEditing();
            }

            DefaultTableModel editorModel = (DefaultTableModel) tblGrades.getModel();

            // Extract core student information safely
            String studentId = editorModel.getValueAt(0, 0).toString();
            String name = editorModel.getValueAt(0, 1).toString();
            String section = editorModel.getValueAt(0, 2).toString();

            double math, science, english;

            // Try to parse subject entries. Non-numeric input drops cleanly into catch block.
            try {
                math = Double.parseDouble(editorModel.getValueAt(0, 3).toString());
                science = Double.parseDouble(editorModel.getValueAt(0, 4).toString());
                english = Double.parseDouble(editorModel.getValueAt(0, 5).toString());
                
                // Optional sanity check: Ensure input grades stay within rational limits (e.g., 0-100 or 1.0-5.0)
                if (math < 0 || science < 0 || english < 0) {
                     JOptionPane.showMessageDialog(this, "Grades cannot be negative numbers!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                     return;
                }
                
            } catch (NumberFormatException | NullPointerException ex) {
                // Notifies the user, stops the save transaction, and preserves window state
                JOptionPane.showMessageDialog(this, 
                        "Invalid entry detected! Please type numeric grades only.\nLetters and special formatting characters are prohibited.", 
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);
                return; 
            }

            // Run standard evaluation procedures (using standard Philippine grading scale 1.00 - 5.00)
            double gwa = (math + science + english) / 3;
            String status = (gwa <= 3.0) ? "PASSED" : "FAILED";

            // Map safe variables back into parent table model elements
            model.setValueAt(math, selectedRow, 3);
            model.setValueAt(science, selectedRow, 4);
            model.setValueAt(english, selectedRow, 5);
            model.setValueAt(String.format("%.2f", gwa), selectedRow, 6);
            model.setValueAt(status, selectedRow, 7);

            // Execute database validation
            boolean ok = GradesDataManager.updateGrade(
                    studentId,
                    name,
                    section,
                    math,
                    science,
                    english,
                    gwa,
                    status
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Saved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Database update failed! Check console for detailed errors.");
            }

            // Reload background table values and pass visibility priority back to parent window
            parent.loadGradesFromDatabase();
            parent.setVisible(true);
            dispose();
        }

        else if (e.getSource() == btnSearch) {

            String searchId = txtSearchId.getText().trim();
            String currentId = tblGrades.getValueAt(0, 0).toString();

            if (searchId.equals(currentId)) {
                tblGrades.setRowSelectionInterval(0, 0);
                JOptionPane.showMessageDialog(this, "Student Found!");
            } else {
                JOptionPane.showMessageDialog(this, "Student Not Found!");
            }
        }
    }
}