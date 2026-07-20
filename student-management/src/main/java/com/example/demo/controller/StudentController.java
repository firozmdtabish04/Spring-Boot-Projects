package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;

	// Constructor Injection
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	// CREATE
	@PostMapping
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {

		Student savedStudent = studentService.saveStudent(student);

		return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
	}

	// GET ALL
	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {

		List<Student> students = studentService.getAllStudents();

		return ResponseEntity.ok(students);
	}

	// GET BY ID
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {

		Student student = studentService.getStudentById(id);

		return ResponseEntity.ok(student);
	}

	// UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {

		Student updatedStudent = studentService.updateStudent(id, student);

		return ResponseEntity.ok(updatedStudent);
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

		studentService.deleteStudent(id);

		return ResponseEntity.ok("Student deleted successfully");
	}
}