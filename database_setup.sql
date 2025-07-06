-- Maternal Care Database Setup Script
-- Run this script in your MySQL database to create all required tables

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS maternadb;
USE maternadb;

-- Main Maternal Records Table
CREATE TABLE maternal_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id VARCHAR(50) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    husband_name VARCHAR(255),
    delivery_type VARCHAR(100),
    gender VARCHAR(10),
    remarks TEXT,
    form_timestamp DATETIME,
    address TEXT,
    purok VARCHAR(100),
    contact_number VARCHAR(20),
    email VARCHAR(255),
    blood_pressure VARCHAR(50),
    chief_complaint TEXT,
    pulse_rate VARCHAR(20),
    respiratory_rate VARCHAR(20),
    last_menstrual_period DATE,
    expected_delivery_date DATE,
    para VARCHAR(50),
    abortion VARCHAR(50),
    living_children VARCHAR(50),
    age_of_gestation DOUBLE,
    weight DOUBLE,
    height DOUBLE,
    fetal_heart_tone INT,
    presentation VARCHAR(100),
    fundal_height DOUBLE,
    next_appointment DATE,
    term VARCHAR(50),
    preterm VARCHAR(50),
    high_risk BOOLEAN DEFAULT FALSE,
    barangay_residency_number VARCHAR(100),
    manual_gravida VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_patient_id (patient_id)
);

-- Pregnancy History Table
CREATE TABLE pregnancy_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    maternal_record_id INT NOT NULL,
    pregnancy_number INT NOT NULL,
    delivery_type VARCHAR(100),
    gender VARCHAR(10),
    place_of_delivery VARCHAR(255),
    year_delivered INT,
    attended_by VARCHAR(255),
    status VARCHAR(50),
    birth_date DATE,
    tt_injection VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (maternal_record_id) REFERENCES maternal_records(id) ON DELETE CASCADE,
    UNIQUE KEY unique_pregnancy (maternal_record_id, pregnancy_number)
);

-- Child Details Table
CREATE TABLE child_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    maternal_record_id INT NOT NULL,
    delivery_type VARCHAR(100),
    gender VARCHAR(10),
    place_of_delivery VARCHAR(255),
    year_delivered VARCHAR(10),
    attended_by VARCHAR(255),
    status VARCHAR(50),
    birthdate DATE,
    tetanus_status VARCHAR(50),
    tetanus_year VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (maternal_record_id) REFERENCES maternal_records(id) ON DELETE CASCADE
);

-- Follow-up Vital Signs Table
CREATE TABLE vital_signs_entries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    maternal_record_id INT NOT NULL,
    date DATE NOT NULL,
    blood_pressure VARCHAR(50),
    pulse_rate VARCHAR(20),
    respiratory_rate VARCHAR(20),
    remarks TEXT,
    aog VARCHAR(50),
    height VARCHAR(20),
    weight VARCHAR(20),
    fundal_height VARCHAR(20),
    fht VARCHAR(20),
    presentation VARCHAR(100),
    chief_complaint TEXT,
    to_come_back DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (maternal_record_id) REFERENCES maternal_records(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_maternal_records_patient_id ON maternal_records(patient_id);
CREATE INDEX idx_maternal_records_full_name ON maternal_records(full_name);
CREATE INDEX idx_pregnancy_history_maternal_record_id ON pregnancy_history(maternal_record_id);
CREATE INDEX idx_child_details_maternal_record_id ON child_details(maternal_record_id);
CREATE INDEX idx_vital_signs_maternal_record_id ON vital_signs_entries(maternal_record_id);
CREATE INDEX idx_vital_signs_date ON vital_signs_entries(date);

-- Show table creation confirmation
SELECT 'Database setup completed successfully!' as status;
SHOW TABLES; 