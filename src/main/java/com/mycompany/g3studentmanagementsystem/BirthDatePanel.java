package com.mycompany.g3studentmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class BirthDatePanel extends JPanel {

    private JComboBox<Integer> cboDay;
    private JComboBox<String> cboMonth;
    private JComboBox<Integer> cboYear;

    public BirthDatePanel() {

        setLayout(null);
        setPreferredSize(new Dimension(200, 30));

		
		// MONTH
        String[] months = {
                "01","02","03","04","05","06",
                "07","08","09","10","11","12"
        };

        cboMonth = new JComboBox<>(months);
        cboMonth.setBounds(0, 0, 60, 30);
        add(cboMonth);
		
        // DAY
        cboDay = new JComboBox<>();
        for (int i = 1; i <= 31; i++) cboDay.addItem(i);
        cboDay.setBounds(70, 0, 70, 30);
        add(cboDay);
       
        // YEAR
        cboYear = new JComboBox<>();
        for (int y = Calendar.getInstance().get(Calendar.YEAR); y >= 1950; y--) {
            cboYear.addItem(y);
        }
        cboYear.setBounds(150, 0, 80, 30);
        add(cboYear);

        // auto adjust days
        cboMonth.addActionListener(e -> updateDays());
        cboYear.addActionListener(e -> updateDays());

        updateDays();
    }

    private void updateDays() {

        int year = (Integer) cboYear.getSelectedItem();
        int month = cboMonth.getSelectedIndex() + 1;

        int days;

        switch (month) {
            case 2:
                days = ((year % 4 == 0 && year % 100 != 0)
                        || (year % 400 == 0)) ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            default:
                days = 31;
        }

        cboDay.removeAllItems();
        for (int i = 1; i <= days; i++) {
            cboDay.addItem(i);
        }
    }

    // GET FINAL DATE STRING
    public String getBirthDate() {
        int day = (Integer) cboDay.getSelectedItem();
        String month = (String) cboMonth.getSelectedItem();
        int year = (Integer) cboYear.getSelectedItem();

        return year + "-" + month + "-" + String.format("%02d", day);
    }
}