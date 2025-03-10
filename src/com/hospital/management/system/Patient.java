package com.hospital.management.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter the Patient Name: ");
        scanner.nextLine(); // Consume newline left by previous nextInt()
        String name = scanner.nextLine(); // Use nextLine() to allow full names

        System.out.print("Enter the Patient Age: ");
        int age = scanner.nextInt();

        System.out.print("Enter the Patient Gender: ");
        scanner.nextLine(); // Consume newline
        String gender = scanner.nextLine();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int data = preparedStatement.executeUpdate();

            if (data > 0) {
                System.out.println("Patient data added successfully.");
            } else {
                System.out.println("Failed to add patient data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients List:");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                System.out.printf("| %-10d | %-18s | %-8d | %-10s |\n", id, name, age, gender);
            }
            System.out.println("+------------+--------------------+----------+------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT id FROM patients WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Returns true if the patient exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if exception occurs or no patient found
    }
}
