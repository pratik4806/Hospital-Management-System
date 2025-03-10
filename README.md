# Hospital Management System

## Overview
The **Hospital Management System** is a Java-based application that helps hospitals manage patients, doctors, and appointments efficiently.
It uses **JDBC** to interact with a **MySQL database**, allowing users to add patients, view patient and doctor records, and book appointments.

## Features
- Add new patients
- View all patients
- View all doctors
- Book appointments
- View scheduled appointments

## Technologies Used
- **Java** (Core Java, JDBC)
- **MySQL** (Database for storing hospital records)
- **JDBC Driver** (Connector for Java and MySQL)

## Prerequisites
To run this application, ensure you have the following installed:
- **Java Development Kit (JDK) 8+**
- **MySQL Database Server**
- **MySQL Connector/J** (JDBC Driver)

## Database Setup
1. Create a MySQL database named `hospital`:
   ```sql
   CREATE DATABASE hospital;
   ```
2. Create the required tables:
   ```sql
   CREATE TABLE patients (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       age INT NOT NULL,
       gender VARCHAR(10) NOT NULL
   );

   CREATE TABLE doctors (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       specialty VARCHAR(50) NOT NULL
   );

   CREATE TABLE appointments (
       id INT PRIMARY KEY AUTO_INCREMENT,
       patient_id INT,
       doctor_id INT,
       appointment_date DATE,
       FOREIGN KEY (patient_id) REFERENCES patients(id),
       FOREIGN KEY (doctor_id) REFERENCES doctors(id)
   );
   ```

## Installation and Execution
1. Clone or download the repository.
2. Open the project in an IDE (such as IntelliJ IDEA or Eclipse).
3. Update database connection details in `Hospital.java`:
   ```java
   private static final String url = "jdbc:mysql://localhost:3306/hospital";
   private static final String username = "root";
   private static final String password = "your_password";
   ```
4. Run the `Hospital.java` file.

## Usage
Upon running the application, the menu will display various options:
1. **Add Patient** - Allows adding a new patient.
2. **View Patients** - Displays all patient records.
3. **View Doctors** - Displays all doctor records.
4. **Book Appointment** - Book an appointment with a doctor.
5. **View Appointments** - Shows all scheduled appointments.
6. **Exit** - Closes the application.

