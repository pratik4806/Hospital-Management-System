package com.hospital.management.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Doctors:");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10d | %-18s | %-16s |\n", id, name, specialization);
            }

            System.out.println("+------------+--------------------+------------------+"); // Moved outside loop
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT id FROM doctors WHERE id = ?"; // Optimized query
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if doctor exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if exception occurs or doctor not found
    }
}
