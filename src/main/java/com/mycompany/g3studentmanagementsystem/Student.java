package com.mycompany.g3studentmanagementsystem;

public class Student {

    String studentId;
    String name;
    String section;
    String gender;
    String birthDate;
    String email;
    String password;

    public Student(String studentId, String name, String section,
                   String gender, String birthDate,
                   String email, String password) {

        this.studentId = studentId;
        this.name = name;
        this.section = section;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }	
}