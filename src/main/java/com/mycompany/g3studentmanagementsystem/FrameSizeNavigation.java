package com.mycompany.g3studentmanagementsystem;

import javax.swing.JFrame;

public class FrameSizeNavigation {

    public static void navigate(JFrame current, JFrame next) {
		next.setVisible(true);
		if (current.getSize().equals(next.getSize())) {
			current.dispose();
		} else {
			current.setVisible(false);
		}
	}
}
