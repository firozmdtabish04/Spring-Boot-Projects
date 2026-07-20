package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {

	private final StudentRepository repository;

	// Constructor Injection
	public StudentService(StudentRepository repository) {
		this.repository = repository;
	}

	// CREATE
	public Student saveStudent(Student student) {

		return repository.save(student);
	}

	// GET ALL
	public List<Student> getAllStudents() {

		return repository.findAll();
	}

	// GET BY ID
	public Student getStudentById(Long id) {

		return repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
	}

	// UPDATE
	public Student updateStudent(Long id, Student student) {

		Student existingStudent = repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

		existingStudent.setName(student.getName());
		existingStudent.setMarks(student.getMarks());

		return repository.save(existingStudent);
	}

	// DELETE
	public void deleteStudent(Long id) {

		repository.deleteById(id);
	}
}