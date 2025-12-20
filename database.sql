CREATE DATABASE IF NOT EXISTS attendance_db;
USE attendance_db;

-- Users table for authentication
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- Students table
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    rollNo VARCHAR(50) NOT NULL UNIQUE
);

-- Attendance records
CREATE TABLE IF NOT EXISTS attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    studentId INT,
    studentName VARCHAR(100),
    rollNo VARCHAR(50),
    date DATE,
    status VARCHAR(20),
    FOREIGN KEY (studentId) REFERENCES students(id)
);

-- Default admin user
INSERT INTO users (username, password) VALUES ('admin', 'admin123');
