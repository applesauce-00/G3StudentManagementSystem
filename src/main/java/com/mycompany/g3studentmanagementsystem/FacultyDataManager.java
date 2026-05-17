package com.mycompany.g3studentmanagementsystem;

import java.util.ArrayList;

public class FacultyDataManager {

    public static ArrayList<Faculty> facultyList = new ArrayList<>();

    // SEEDS FACULTY
    static {
        facultyList.add(new Faculty("admin", "admin123"));
        facultyList.add(new Faculty("prof1", "pass123"));
    }


    public static int validateLogin(String id, String password) {

        for (Faculty f : facultyList) {


            if (f.facultyId.equals(id)) {


                if (f.password.equals(password)) {
                    return 0; 
                }

                
                return 1; 
            }
        }

        return 2;
    }
}