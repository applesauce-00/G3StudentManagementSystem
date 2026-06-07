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
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }
	
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