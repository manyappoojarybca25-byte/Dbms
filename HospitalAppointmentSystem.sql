CREATE DATABASE IF NOT EXISTS hospital_appointment_db;

USE hospital_appointment_db;

CREATE TABLE IF NOT EXISTS appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL,
    doctor_name VARCHAR(100) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time VARCHAR(20) NOT NULL,
    reason VARCHAR(200) NOT NULL
);

TRUNCATE TABLE appointments;

INSERT INTO appointments
(patient_name, age, gender, doctor_name,
 appointment_date, appointment_time, reason)
VALUES
('Spoorthi', 19, 'Female', 'Dr.Manjula',
 '2027-12-12', '11.40 AM', 'Cold'),
('Manya', 20, 'Female', 'Dr.Pooja',
 '2027-05-25', '4.00PM', 'Fever'),
('Neha', 19, 'Female', 'Dr.Lila',
 '2007-11-23', '7.30PM', 'Allergy');

SELECT * FROM appointments;