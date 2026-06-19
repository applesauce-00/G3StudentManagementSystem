package com.mycompany.g3studentmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GradesEditorPage extends JFrame implements ActionListener {

	private JLabel lblIcon, lblTitle, lblEditing, lblSubject;
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

		// Label showing the faculty's subject currently editing
		JLabel lblEditing = new JLabel("Editing:");
		lblEditing.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEditing.setForeground(new Color(30, 100, 180));
		lblEditing.setBounds(20, 200, 160, 20); // Top part
		add(lblEditing);

		JLabel lblSubject = new JLabel(SessionManager.subject.toUpperCase());
		lblSubject.setFont(new Font("Arial", Font.BOLD, 12));
		lblSubject.setForeground(new Color(30, 100, 180));
		lblSubject.setBounds(20, 220, 160, 20); 
		add(lblSubject);

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
			"STUDENT ID", // index 0 — hidden
			"NAME", // index 1
			"SECTION", // index 2
			"MATH", // index 3
			"SCIENCE", // index 4
			"ENGLISH", // index 5
			"GWA", // index 6
			"STATUS" // index 7
		};

		DefaultTableModel editorModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				// Only allow editing the column matching the faculty's subject
				switch (SessionManager.subject.toUpperCase()) {
					case "MATH":
						return col == 3;
					case "SCIENCE":
						return col == 4;
					case "ENGLISH":
						return col == 5;
					default:
						return false;
				}
			}
		};

		// Populate editor with the selected row from parent model (indices 0–7)
		editorModel.addRow(new Object[]{
			model.getValueAt(selectedRow, 0), // STUDENT ID
			model.getValueAt(selectedRow, 1), // NAME
			model.getValueAt(selectedRow, 2), // SECTION
			model.getValueAt(selectedRow, 3), // MATH
			model.getValueAt(selectedRow, 4), // SCIENCE
			model.getValueAt(selectedRow, 5), // ENGLISH
			model.getValueAt(selectedRow, 6), // GWA
			model.getValueAt(selectedRow, 7) // STATUS
		});

		tblGrades = new JTable(editorModel);

		tblGrades.getColumnModel().getColumn(0).setMinWidth(0);
		tblGrades.getColumnModel().getColumn(0).setMaxWidth(0);
		tblGrades.getColumnModel().getColumn(0).setWidth(0);

		// Visually grey out columns the faculty cannot edit at first
		tblGrades.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int col) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
				boolean editable = table.getModel().isCellEditable(row, col);
				if (!isSelected) {
					c.setBackground(editable ? Color.WHITE : new Color(220, 220, 220));
					c.setForeground(editable ? Color.BLACK : new Color(140, 140, 140));
				}
				return c;
			}
		});

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

			String studentId = editorModel.getValueAt(0, 0).toString();
			String name = editorModel.getValueAt(0, 1).toString();
			String section = editorModel.getValueAt(0, 2).toString();

			double math, science, english;

			try {
				math = Double.parseDouble(editorModel.getValueAt(0, 3).toString());
				science = Double.parseDouble(editorModel.getValueAt(0, 4).toString());
				english = Double.parseDouble(editorModel.getValueAt(0, 5).toString());

				// Validate only the subject this faculty is responsible for
				String sub = SessionManager.subject.toUpperCase();
				double gradeToValidate = sub.equals("MATH") ? math
						: sub.equals("SCIENCE") ? science
						: english;

				if (gradeToValidate < 1.0 || gradeToValidate > 5.0) {
					JOptionPane.showMessageDialog(this,
							SessionManager.subject + " grade must be between 1.00 and 5.00!",
							"Validation Error", JOptionPane.WARNING_MESSAGE);
					return;
				}

			} catch (NumberFormatException | NullPointerException ex) {
				JOptionPane.showMessageDialog(this,
						"Invalid entry detected! Please type numeric grades only.\n"
						+ "Letters and special formatting characters are prohibited.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// GWA and status are only meaningful when all 3 grades are entered
			// If the other subjects are still 0.0 (PENDING), show a neutral status
			double gwa = (math + science + english) / 3.0;

			String status;
			if (math == 0.0 || science == 0.0 || english == 0.0) {
				status = "PENDING"; // not all grades are in yet
			}
			else if (math >= 4.00 || science >= 4.00 || english >= 4.00) {
				status = "FAILED";
			}
			else if (gwa <= 3.00) {
				status = "PASSED";
			}
			else {
				status = "FAILED";
			}

			// Push computed values back into the editor table
			editorModel.setValueAt(String.format("%.2f", gwa), 0, 6);
			editorModel.setValueAt(status, 0, 7);

			// Sync back into the parent GradesManagerPage table
			model.setValueAt(String.format("%.2f", math), selectedRow, 3);
			model.setValueAt(String.format("%.2f", science), selectedRow, 4);
			model.setValueAt(String.format("%.2f", english), selectedRow, 5);
			model.setValueAt(String.format("%.2f", gwa), selectedRow, 6);
			model.setValueAt(status, selectedRow, 7);

			boolean ok = GradesDataManager.updateGrade(
					studentId, name, section,
					math, science, english, gwa, status
			);

			if (ok) {
				JOptionPane.showMessageDialog(this, "Saved successfully!");
			}
			else {
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

			String fullName = tblGrades.getValueAt(0, 1).toString();
			String lastName = fullName.split(",")[0].trim();

			if (lastName.equalsIgnoreCase(search)) {
				tblGrades.setRowSelectionInterval(0, 0);
				JOptionPane.showMessageDialog(this, "Student found!");
			}
			else {
				JOptionPane.showMessageDialog(this, "Student not found!");
			}
		}
	}
}
