-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 19, 2026 at 06:00 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `g3studentmanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
  `faculty_id` varchar(50) NOT NULL,
  `faculty_name` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `subject` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`faculty_id`, `faculty_name`, `password`, `subject`) VALUES
('eng', 'Mr. Santos', 'eng', 'English'),
('math', 'Ms. Olviga', 'math', 'Math'),
('sci', 'Mr. Nasem', 'sci', 'Science');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `section` varchar(20) NOT NULL,
  `sex` varchar(20) NOT NULL,
  `birth_date` date NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `is_active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `students`
--
DELIMITER $$
CREATE TRIGGER `after_student_insert` AFTER INSERT ON `students` FOR EACH ROW BEGIN
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
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `student_attendance`
--

CREATE TABLE `student_attendance` (
  `student_id` varchar(50) NOT NULL,
  `student_name` varchar(100) NOT NULL,
  `subject` varchar(50) NOT NULL,
  `week_1` varchar(10) DEFAULT 'Absent',
  `week_2` varchar(10) DEFAULT 'Absent',
  `week_3` varchar(10) DEFAULT 'Absent',
  `week_4` varchar(10) DEFAULT 'Absent',
  `week_5` varchar(10) DEFAULT 'Absent',
  `week_6` varchar(10) DEFAULT 'Absent',
  `week_7` varchar(10) DEFAULT 'Absent',
  `week_8` varchar(10) DEFAULT 'Absent',
  `week_9` varchar(10) DEFAULT 'Absent',
  `week_10` varchar(10) DEFAULT 'Absent'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `student_grades`
--

CREATE TABLE `student_grades` (
  `student_id` varchar(50) NOT NULL,
  `math_grade` decimal(5,2) NOT NULL,
  `science_grade` decimal(5,2) NOT NULL,
  `english_grade` decimal(5,2) NOT NULL,
  `name` varchar(50) NOT NULL,
  `section` varchar(50) NOT NULL,
  `grade_status` varchar(50) NOT NULL,
  `gwa` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`faculty_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `student_attendance`
--
ALTER TABLE `student_attendance`
  ADD PRIMARY KEY (`student_id`,`subject`);

--
-- Indexes for table `student_grades`
--
ALTER TABLE `student_grades`
  ADD PRIMARY KEY (`student_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `student_attendance`
--
ALTER TABLE `student_attendance`
  ADD CONSTRAINT `attendance_student_fk` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE;

--
-- Constraints for table `student_grades`
--
ALTER TABLE `student_grades`
  ADD CONSTRAINT `student_grades_fk` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
