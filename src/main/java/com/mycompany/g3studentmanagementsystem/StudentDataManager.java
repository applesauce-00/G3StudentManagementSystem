package com.mycompany.g3studentmanagementsystem;

import java.util.ArrayList;

public class StudentDataManager {
	
    public static ArrayList<Student> students = new ArrayList<>();

    // ADD
    public static void addStudent(Student s) {
        students.add(s);
    }

    // FIND
    public static Student findStudent(String id) {
        for (Student s : students) {
            if (s.studentId.equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // DELETE
    public static boolean deleteStudent(String id) {
        Student s = findStudent(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }
}