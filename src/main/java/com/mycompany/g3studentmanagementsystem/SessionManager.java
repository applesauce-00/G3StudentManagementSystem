package com.mycompany.g3studentmanagementsystem;

public class SessionManager {

    // The subject assigned to the logged-in faculty: "Math", "Science", or "English"
    public static String subject = "";

    // Store faculty name for display purposes
    public static String facultyName = "";

    // Call this on logout to clear session data 
    public static void clear() {
        subject = "";
        facultyName = "";
    }
}