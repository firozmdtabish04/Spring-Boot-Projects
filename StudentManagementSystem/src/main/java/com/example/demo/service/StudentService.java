package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StudentDTO;

public interface StudentService {

	// Create a new student
	StudentDTO createStudent(StudentDTO studentDTO);

	// Get all students
	List<StudentDTO> getAllStudents();

	// Get student by ID
	StudentDTO getStudentById(Long id);

	// Update complete student
	StudentDTO updateStudent(Long id, StudentDTO studentDTO);

	// Delete student
	void deleteStudent(Long id);

	// Search by email
	StudentDTO getStudentByEmail(String email);

	// Search by course
	List<StudentDTO> getStudentsByCourse(String course);

	// Search by department
	List<StudentDTO> getStudentsByDepartment(String department);

}
