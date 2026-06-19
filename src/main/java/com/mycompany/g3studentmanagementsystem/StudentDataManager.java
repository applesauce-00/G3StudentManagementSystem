package com.mycompany.g3studentmanagementsystem;

import com.mycompany.g3studentmanagementsystem.databaseconnection.ConnectionString;
import java.sql.*;
import java.util.*;

public class StudentDataManager {
    
    public static ArrayList<Student> students = new ArrayList<>();

    // ADD
    public static void addStudent(Student s) {
        students.add(s);
    }

    // FIND
    public static Student findStudent(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }
    
    // UPDATE
    public static boolean updateStudent(String studentId,
                                        String lastName,
                                        String firstName,
                                        String middleName,
                                        String section,
                                        char sex,
                                        String birthDate,
                                        String email) {

        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                s.setLastName(lastName);
                s.setFirstName(firstName);
                s.setMiddleName(middleName);
                s.setSection(section);
                s.setSex(sex);
                s.setBirthDate(birthDate);
                s.setEmail(email);
                return true;
            }
        }
        return false;
    }
    
    // VALIDATE LOGIN
    public static int validateLogin(String id, String password) {

    String loginQuery = "SELECT * FROM students WHERE student_id=? AND password=?";
    String idCheckQuery = "SELECT * FROM students WHERE student_id=?";
    String activeCheckQuery = "SELECT is_active FROM students WHERE student_id=?";

    try (Connection con = ConnectionString.getConnection()) {

        // 1. Check ID + password
        try (PreparedStatement ps = con.prepareStatement(loginQuery)) {
            ps.setString(1, id);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    // 2. CHECK IF ACTIVE
                    try (PreparedStatement ps2 = con.prepareStatement(activeCheckQuery)) {
                        ps2.setString(1, id);

                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next()) {
                                int isActive = rs2.getInt("is_active");

                                if (isActive == 0) {
                                    return 3; // ❌ INACTIVE ACCOUNT
                                }
                            }
                        }
                    }

                    return 0; // ✅ SUCCESS
                }
            }
        }

        // 3. Check if ID exists
        try (PreparedStatement ps = con.prepareStatement(idCheckQuery)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return 1; // wrong password
                }
            }
        }

        return 2; // ID not found

    } catch (SQLException e) {
        e.printStackTrace();
        return 2;
    }
}
    
	// Load data from database
    public static void loadStudentsFromDatabase() {
    students.clear(); 
    String query = "SELECT * FROM students";
    
    try (Connection con = ConnectionString.getConnection();
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
         
        while (rs.next()) {
            // Pull all 9 variables from the database
            String id = rs.getString("student_id");
            String lastName = rs.getString("last_name");
            String firstName = rs.getString("first_name");
            String middleName = rs.getString("middle_name");
            String section = rs.getString("section");
            
            char sex = 'M'; 
            String sexStr = rs.getString("sex");
            if (sexStr != null && !sexStr.isEmpty()) {
                sex = sexStr.charAt(0);
            }
            
            String birthDate = rs.getString("birth_date");
            String email = rs.getString("email");
            String password = rs.getString("password"); 
            
            // Initialize
            Student s = new Student(id, lastName, firstName, middleName, section, sex, birthDate, email, password);
            
            students.add(s);
        }
    } catch (SQLException e) {
        System.out.println("❌ Error caching student rows: " + e.getMessage());
    }
}
}