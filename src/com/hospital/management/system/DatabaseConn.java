package com.hospital.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital"; // Change to your DB
        String user = "root";
        String password = "Root@12345";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the MySQL database successfully!");
            }
        } catch (SQLException e) {
            System.out.println("MySQL Connection Failed!");
            e.printStackTrace();
        }
    }
}
