package com.mycompany.g3studentmanagementsystem.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionString {
    public static final String URL = "jdbc:mysql://localhost:3306/g3studentmanagementsystem";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}