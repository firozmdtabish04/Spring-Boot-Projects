package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	// Find student by email
	Optional<Student> findByEmail(String email);

	// Check if email already exists
	boolean existsByEmail(String email);

	// Check if phone already exists
	boolean existsByPhone(String phone);

	// Find students by course
	List<Student> findByCourse(String course);

	// Find students by department
	List<Student> findByDepartment(String department);

	// Find students by first name
	List<Student> findByFirstNameContainingIgnoreCase(String firstName);

}
