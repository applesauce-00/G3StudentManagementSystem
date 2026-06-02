package com.mycompany.g3studentmanagementsystem;

public class Student {
    private String id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String section;
    private char sex;
    private String birthDate;
    private String email;
    private String password;

    public Student(String id, String lastName, String firstName, String middleName, 
                   String section, char sex, String birthDate, String email, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.section = section;
        this.sex = sex;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }

    public String getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getSection() { return section; }
    public char getSex() { return sex; }
    public String getBirthDate() { return birthDate; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}