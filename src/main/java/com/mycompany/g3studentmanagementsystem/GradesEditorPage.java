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
    private JTextField txtSearchName;

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

        txtSearchName = new JTextField();
        txtSearchName.setToolTipText("Search by last name");
        txtSearchName.setBounds(20, 100, 160, 35);
        add(txtSearchName);

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

        // Columns mirror GradesManagerPage exactly (index 0 = STUDENT ID hidden)
        String[] columns = {
                "STUDENT ID",  // index 0 — read-only, carries ID for DB update
                "NAME",        // index 1
                "SECTION",     // index 2
                "MATH",        // index 3 — editable
                "SCIENCE",     // index 4 — editable
                "ENGLISH",     // index 5 — editable
                "GWA",         // index 6 — auto-computed
                "STATUS"       // index 7 — auto-computed
        };

        DefaultTableModel editorModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // Only MATH, SCIENCE, ENGLISH are editable
                return col == 3 || col == 4 || col == 5;
            }
        };

        // Populate editor with the selected row from parent model (indices 0–7)
        editorModel.addRow(new Object[]{
                model.getValueAt(selectedRow, 0),  // STUDENT ID
                model.getValueAt(selectedRow, 1),  // NAME
                model.getValueAt(selectedRow, 2),  // SECTION
                model.getValueAt(selectedRow, 3),  // MATH
                model.getValueAt(selectedRow, 4),  // SCIENCE
                model.getValueAt(selectedRow, 5),  // ENGLISH
                model.getValueAt(selectedRow, 6),  // GWA
                model.getValueAt(selectedRow, 7)   // STATUS
        });

        tblGrades = new JTable(editorModel);

        // Hide STUDENT ID column — still readable via getValueAt(0, 0)
        tblGrades.getColumnModel().getColumn(0).setMinWidth(0);
        tblGrades.getColumnModel().getColumn(0).setMaxWidth(0);
        tblGrades.getColumnModel().getColumn(0).setWidth(0);

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

            // Read student info from editor table
            String studentId = editorModel.getValueAt(0, 0).toString();  // hidden col 0
            String name      = editorModel.getValueAt(0, 1).toString();
            String section   = editorModel.getValueAt(0, 2).toString();

            double math, science, english;

            try {
                math    = Double.parseDouble(editorModel.getValueAt(0, 3).toString());
                science = Double.parseDouble(editorModel.getValueAt(0, 4).toString());
                english = Double.parseDouble(editorModel.getValueAt(0, 5).toString());

                // Grades must be within the 1.00–5.00 Philippine grading scale
                if (math < 1.0 || math > 5.0 ||
                    science < 1.0 || science > 5.0 ||
                    english < 1.0 || english > 5.0) {
                    JOptionPane.showMessageDialog(this,
                            "Grades must be between 1.00 and 5.00!",
                            "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

            } catch (NumberFormatException | NullPointerException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid entry detected! Please type numeric grades only.\n" +
                        "Letters and special formatting characters are prohibited.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Auto-compute GWA and status
            double gwa = (math + science + english) / 3.0;

            String status;
            if (math >= 4.00 || science >= 4.00 || english >= 4.00) {
                status = "FAILED";
            } else if (gwa <= 3.00) {
                status = "PASSED";
            } else {
                status = "FAILED";
            }

            // Push computed values back into the editor table so the user sees them
            editorModel.setValueAt(String.format("%.2f", gwa), 0, 6);
            editorModel.setValueAt(status, 0, 7);

            // Sync updates back into the parent GradesManagerPage table model
            model.setValueAt(String.format("%.2f", math),    selectedRow, 3);
            model.setValueAt(String.format("%.2f", science), selectedRow, 4);
            model.setValueAt(String.format("%.2f", english), selectedRow, 5);
            model.setValueAt(String.format("%.2f", gwa),     selectedRow, 6);
            model.setValueAt(status,                          selectedRow, 7);

            // Persist to database
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

            parent.loadGradesFromDatabase();
            parent.setVisible(true);
            dispose();
        }

        else if (e.getSource() == btnSearch) {
            String search = txtSearchName.getText().trim();

            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a last name to search.");
                return;
            }

            // Column 1 is NAME formatted as "LastName, FirstName"
            String fullName = tblGrades.getValueAt(0, 1).toString();
            String lastName = fullName.split(",")[0].trim();

            if (lastName.equalsIgnoreCase(search)) {
                tblGrades.setRowSelectionInterval(0, 0);
                JOptionPane.showMessageDialog(this, "Student found!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        }
    }
}