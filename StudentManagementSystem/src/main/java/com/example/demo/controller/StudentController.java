package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StudentDTO;
import com.example.demo.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

	@Autowired
	private StudentService studentService;

	// Create Student
	@PostMapping
	public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {

		StudentDTO savedStudent = studentService.createStudent(studentDTO);

		return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
	}

	// Get All Students
	@GetMapping
	public ResponseEntity<List<StudentDTO>> getAllStudents() {

		return ResponseEntity.ok(studentService.getAllStudents());

	}

	// Get Student By ID
	@GetMapping("/{id}")
	public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {

		return ResponseEntity.ok(studentService.getStudentById(id));

	}

	// Update Student
	@PutMapping("/{id}")
	public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {

		return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));

	}

	// Delete Student
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

		studentService.deleteStudent(id);

		return ResponseEntity.ok("Student deleted successfully.");

	}

	// Search Student By Email
	@GetMapping("/email")
	public ResponseEntity<StudentDTO> getStudentByEmail(@RequestParam String email) {

		return ResponseEntity.ok(studentService.getStudentByEmail(email));

	}

	// Search Students By Course
	@GetMapping("/course")
	public ResponseEntity<List<StudentDTO>> getStudentsByCourse(@RequestParam String course) {

		return ResponseEntity.ok(studentService.getStudentsByCourse(course));

	}

	// Search Students By Department
	@GetMapping("/department")
	public ResponseEntity<List<StudentDTO>> getStudentsByDepartment(@RequestParam String department) {

		return ResponseEntity.ok(studentService.getStudentsByDepartment(department));

	}

}
