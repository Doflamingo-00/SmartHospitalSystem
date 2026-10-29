-- Smart Hospital Appointment & Patient Management System
-- Database Schema

CREATE DATABASE IF NOT EXISTS smart_hospital;
USE smart_hospital;

-- Patients table
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Doctors table
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    consultation_fee DECIMAL(10,2) NOT NULL DEFAULT 500.00,
    active BOOLEAN DEFAULT TRUE
);

-- Doctor Schedules table
CREATE TABLE doctor_schedules (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT NOT NULL,
    schedule_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    slot_duration INT DEFAULT 30,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
);

-- Appointments table
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    schedule_id INT,
    appointment_time DATETIME NOT NULL,
    status ENUM('PENDING','CONFIRMED','COMPLETED','CANCELLED') DEFAULT 'PENDING',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
    FOREIGN KEY (schedule_id) REFERENCES doctor_schedules(schedule_id)
);

-- Receipts table
CREATE TABLE receipts (
    receipt_id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL UNIQUE,
    receipt_number VARCHAR(50) UNIQUE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_status ENUM('PENDING','PAID','REFUNDED') DEFAULT 'PENDING',
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);

-- Sample Doctors
INSERT INTO doctors (name, specialization, phone, consultation_fee) VALUES
('Dr. Sharma', 'Cardiology', '9876543210', 700.00),
('Dr. Verma', 'Dermatology', '9876543211', 500.00),
('Dr. Patel', 'Orthopedics', '9876543212', 600.00),
('Dr. Khan', 'Pediatrics', '9876543213', 450.00),
('Dr. Reddy', 'Neurology', '9876543214', 800.00);

-- Sample Schedules (next few days)
INSERT INTO doctor_schedules (doctor_id, schedule_date, start_time, end_time, slot_duration) VALUES
(1, CURDATE() + INTERVAL 1 DAY, '09:00:00', '13:00:00', 30),
(1, CURDATE() + INTERVAL 2 DAY, '09:00:00', '13:00:00', 30),
(1, CURDATE() + INTERVAL 3 DAY, '14:00:00', '18:00:00', 30),
(2, CURDATE() + INTERVAL 1 DAY, '10:00:00', '14:00:00', 20),
(2, CURDATE() + INTERVAL 2 DAY, '10:00:00', '14:00:00', 20),
(3, CURDATE() + INTERVAL 1 DAY, '11:00:00', '15:00:00', 30),
(3, CURDATE() + INTERVAL 3 DAY, '09:00:00', '12:00:00', 30),
(4, CURDATE() + INTERVAL 1 DAY, '09:00:00', '13:00:00', 20),
(4, CURDATE() + INTERVAL 2 DAY, '14:00:00', '17:00:00', 20),
(5, CURDATE() + INTERVAL 2 DAY, '10:00:00', '14:00:00', 30);
