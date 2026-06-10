-- phpMyAdmin SQL Dump
-- version 5.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT;
SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS;
SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION;
SET NAMES utf8mb4;

-- =========================================
-- DATABASE
-- =========================================

CREATE DATABASE IF NOT EXISTS g3studentmanagementsystem;
USE g3studentmanagementsystem;

-- =========================================
-- TABLE: FACULTY
-- =========================================

CREATE TABLE faculty (
  faculty_id varchar(50) NOT NULL,
  faculty_name varchar(100) NOT NULL,
  password varchar(50) NOT NULL,
  PRIMARY KEY (faculty_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO faculty (faculty_id, faculty_name, password)
VALUES ('admin', 'Miss Sci', 'admin123');

-- =========================================
-- TABLE: STUDENTS
-- =========================================

CREATE TABLE students (
  student_id varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  first_name varchar(50) NOT NULL,
  middle_name varchar(50) DEFAULT NULL,
  section varchar(20) NOT NULL,
  sex varchar(20) NOT NULL,
  birth_date date NOT NULL,
  email varchar(100) NOT NULL,
  password varchar(50) NOT NULL,
  PRIMARY KEY (student_id),
  UNIQUE KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================================
-- TABLE: STUDENT ATTENDANCE
-- =========================================

CREATE TABLE student_attendance (
  student_id varchar(50) NOT NULL,
  student_name varchar(100) NOT NULL,
  subject varchar(50) NOT NULL,
  week_1 varchar(10) DEFAULT 'Absent',
  week_2 varchar(10) DEFAULT 'Absent',
  week_3 varchar(10) DEFAULT 'Absent',
  week_4 varchar(10) DEFAULT 'Absent',
  week_5 varchar(10) DEFAULT 'Absent',
  week_6 varchar(10) DEFAULT 'Absent',
  week_7 varchar(10) DEFAULT 'Absent',
  week_8 varchar(10) DEFAULT 'Absent',
  week_9 varchar(10) DEFAULT 'Absent',
  week_10 varchar(10) DEFAULT 'Absent',
  PRIMARY KEY (student_id, subject),
  CONSTRAINT attendance_student_fk
    FOREIGN KEY (student_id)
    REFERENCES students(student_id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================================
-- TABLE: STUDENT GRADES
-- =========================================

CREATE TABLE student_grades (
  student_id varchar(50) NOT NULL,
  math_grade decimal(5,2) NOT NULL,
  science_grade decimal(5,2) NOT NULL,
  english_grade decimal(5,2) NOT NULL,
  name varchar(50) NOT NULL,
  section varchar(50) NOT NULL,
  grade_status varchar(50) NOT NULL,
  gwa decimal(5,2) NOT NULL,
  PRIMARY KEY (student_id),
  CONSTRAINT student_grades_fk
    FOREIGN KEY (student_id)
    REFERENCES students(student_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DELIMITER $$

CREATE TRIGGER after_student_insert
AFTER INSERT ON students
FOR EACH ROW
BEGIN
    INSERT INTO student_attendance (student_id, student_name, subject)
    VALUES (NEW.student_id, CONCAT(NEW.last_name, ', ', NEW.first_name), 'Math');

    INSERT INTO student_attendance (student_id, student_name, subject)
    VALUES (NEW.student_id, CONCAT(NEW.last_name, ', ', NEW.first_name), 'Science');

    INSERT INTO student_attendance (student_id, student_name, subject)
    VALUES (NEW.student_id, CONCAT(NEW.last_name, ', ', NEW.first_name), 'English');

    INSERT INTO student_grades (
        student_id,
        name,
        section,
        math_grade,
        science_grade,
        english_grade,
        grade_status,
        gwa
    )
    VALUES (
        NEW.student_id,
        CONCAT(NEW.last_name, ', ', NEW.first_name),
        NEW.section,
        0.00,
        0.00,
        0.00,
        'PENDING',
        0.00
    );
END$$

DELIMITER ;


SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT;
SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS;
SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION;

COMMIT;