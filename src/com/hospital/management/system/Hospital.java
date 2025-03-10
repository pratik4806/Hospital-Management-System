package com.hospital.management.system;

import java.sql.*;
import java.util.Scanner;

public class Hospital {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Root@12345";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Load MySQL Driver before establishing connection
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Patient patient = new Patient(connection, sc);
                Doctor doctor = new Doctor(connection);

                while (true) {
                    System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                    System.out.println("1. Add Patient");
                    System.out.println("2. View Patients");
                    System.out.println("3. View Doctors");
                    System.out.println("4. Book Appointment");
                    System.out.println("5. View Appointments");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            patient.addPatient();
                            break;
                        case 2:
                            patient.viewPatients();
                            break;
                        case 3:
                            doctor.viewDoctors();
                            break;
                        case 4:
                            bookAppointment(patient, doctor, connection, sc);
                            break;
                        case 5:
                            viewAllAppointments(connection);
                            break;
                        case 6:
                            System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!");
                            return;
                        default:
                            System.out.println("Enter a valid choice!");
                    }
                    System.out.println(); // Adding space for better readability
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewAllAppointments(Connection connection) {
        String query = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.id " +
                "JOIN doctors d ON a.doctor_id = d.id";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nAppointments List:");
            System.out.println("+----+--------------------+--------------------+------------+");
            System.out.println("| ID | Patient Name       | Doctor Name        | Date       |");
            System.out.println("+----+--------------------+--------------------+------------+");

            boolean hasAppointments = false;
            while (rs.next()) {
                hasAppointments = true;
                int id = rs.getInt("id");
                String patientName = rs.getString("patient_name");
                String doctorName = rs.getString("doctor_name");
                String appointmentDate = rs.getString("appointment_date");

                System.out.printf("| %-2d | %-18s | %-18s | %-10s |\n", id, patientName, doctorName, appointmentDate);
            }

            if (!hasAppointments) {
                System.out.println("|        No appointments found!                |");
            }
            System.out.println("+----+--------------------+--------------------+------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner sc) {
        System.out.print("Enter Patient ID: ");
        int patientId = sc.nextInt();
        System.out.print("Enter Doctor ID: ");
        int doctorId = sc.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = sc.next();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(appointmentQuery)) {
                    pstmt.setInt(1, patientId);
                    pstmt.setInt(2, doctorId);
                    pstmt.setString(3, appointmentDate);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked Successfully!");
                    } else {
                        System.out.println("Failed to Book Appointment.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date.");
            }
        } else {
            System.out.println("Either the doctor or patient does not exist!");
        }
    }

    private static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, appointmentDate);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Doctor is available if no appointments exist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
