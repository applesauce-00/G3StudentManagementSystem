package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;

public class InactiveStudentsPage extends JFrame implements ActionListener {

    private JLabel lblIcon, lblTitle;
    private JButton btnBack, btnActivate;
    private JTable tblStudent;
    private JScrollPane tableScroll;
    private JTextField txtSearchName;
    private JButton btnSearch;

    private DefaultTableModel model;

    public InactiveStudentsPage() {

        setTitle("FACULTY PORTAL - Inactive Students");
        setSize(1024, 764);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 242, 250));

        lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setBounds(10, 10, 60, 60);
        add(lblIcon);

        lblTitle = new JLabel("INACTIVE STUDENTS");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(80, 20, 300, 40);
        add(lblTitle);

        // Back button in top right
        btnBack = new JButton("← BACK");
        btnBack.setBounds(850, 20, 120, 40);
        btnBack.setBackground(new Color(100, 100, 100));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 13));
        add(btnBack);

        // Search box
        txtSearchName = new JTextField();
        txtSearchName.setToolTipText("Search by last name");
        txtSearchName.setBounds(20, 100, 160, 35);
        add(txtSearchName);

        btnSearch = new JButton("SEARCH");
        btnSearch.setBounds(20, 140, 160, 40);
        btnSearch.setBackground(new Color(52, 168, 235));
        btnSearch.setForeground(Color.WHITE);
        add(btnSearch);

        // Activate button — sets is_active = 1 for selected student
        btnActivate = new JButton("ACTIVATE");
        btnActivate.setBounds(20, 200, 160, 40);
        btnActivate.setBackground(new Color(60, 180, 100));
        btnActivate.setForeground(Color.WHITE);
        btnActivate.setFont(new Font("Arial", Font.BOLD, 13));
        add(btnActivate);

        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "STUDENT ID",   // index 0 — hidden
                        "LAST NAME",
                        "FIRST NAME",
                        "MIDDLE NAME",
                        "SECTION",
                        "SEX",
                        "BIRTH DATE",
                        "EMAIL"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblStudent = new JTable(model);
        tblStudent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Hide STUDENT ID column
        tblStudent.getColumnModel().getColumn(0).setMinWidth(0);
        tblStudent.getColumnModel().getColumn(0).setMaxWidth(0);
        tblStudent.getColumnModel().getColumn(0).setWidth(0);

        tableScroll = new JScrollPane(tblStudent);
        tableScroll.setBounds(200, 100, 780, 550);
        add(tableScroll);

        btnBack.addActionListener(this);
        btnActivate.addActionListener(this);
        btnSearch.addActionListener(this);

        loadInactiveStudents();
    }

    public void loadInactiveStudents() {
        model.setRowCount(0);
        String sql = "SELECT * FROM students WHERE is_active = 0 ORDER BY last_name ASC";

        try (Connection con = ConnectionString.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id         = rs.getString("student_id");
                String lastName   = rs.getString("last_name");
                String firstName  = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String section    = rs.getString("section");

                String sexStr = rs.getString("sex");
                char sex = (sexStr != null && !sexStr.isEmpty()) ? sexStr.charAt(0) : 'M';

                String birthDate = rs.getString("birth_date");
                String email     = rs.getString("email");

                model.addRow(new Object[]{
                        id, lastName, firstName, middleName,
                        section, String.valueOf(sex), birthDate, email
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load inactive students: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnBack) {
            new StudentManagerPage().setVisible(true);
            this.dispose();
        }

        else if (e.getSource() == btnSearch) {
            String search = txtSearchName.getText().trim();
            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a last name to search.");
                return;
            }
            boolean found = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 1).toString().equalsIgnoreCase(search)) {
                    tblStudent.setRowSelectionInterval(i, i);
                    tblStudent.scrollRectToVisible(tblStudent.getCellRect(i, 1, true));
                    found = true;
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, found ? "Student found!" : "Student not found!");
        }

        else if (e.getSource() == btnActivate) {
            int selectedRow = tblStudent.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to activate.");
                return;
            }

            String studentId  = tblStudent.getValueAt(selectedRow, 0).toString();
            String lastName   = tblStudent.getValueAt(selectedRow, 1).toString();
            String firstName  = tblStudent.getValueAt(selectedRow, 2).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Activate " + firstName + " " + lastName + "?\n" +
                    "They will be moved back to the active student list.",
                    "Confirm Activate", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            String sql = "UPDATE students SET is_active = 1 WHERE student_id = ?";

            try (Connection con = ConnectionString.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, studentId);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this,
                            firstName + " " + lastName + " has been activated!");
                    loadInactiveStudents(); // refresh table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to activate student.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        }
    }
}