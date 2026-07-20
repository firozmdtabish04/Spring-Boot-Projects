package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	public StudentDTO createStudent(StudentDTO studentDTO) {

		if (studentRepository.existsByEmail(studentDTO.getEmail())) {
			throw new RuntimeException("Email already exists.");
		}

		if (studentRepository.existsByPhone(studentDTO.getPhone())) {
			throw new RuntimeException("Phone number already exists.");
		}

		Student student = mapToEntity(studentDTO);

		Student savedStudent = studentRepository.save(student);

		return mapToDTO(savedStudent);
	}

	@Override
	public List<StudentDTO> getAllStudents() {

		return studentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());

	}

	@Override
	public StudentDTO getStudentById(Long id) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with ID : " + id));

		return mapToDTO(student);

	}

	@Override
	public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with ID : " + id));

		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setEmail(studentDTO.getEmail());
		student.setPhone(studentDTO.getPhone());
		student.setCourse(studentDTO.getCourse());
		student.setDepartment(studentDTO.getDepartment());
		student.setAddress(studentDTO.getAddress());
		student.setGender(studentDTO.getGender());
		student.setDob(studentDTO.getDob());

		Student updatedStudent = studentRepository.save(student);

		return mapToDTO(updatedStudent);

	}

	@Override
	public void deleteStudent(Long id) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with ID : " + id));

		studentRepository.delete(student);

	}

	@Override
	public StudentDTO getStudentByEmail(String email) {

		Student student = studentRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with email : " + email));

		return mapToDTO(student);

	}

	@Override
	public List<StudentDTO> getStudentsByCourse(String course) {

		return studentRepository.findByCourse(course).stream().map(this::mapToDTO).collect(Collectors.toList());

	}

	@Override
	public List<StudentDTO> getStudentsByDepartment(String department) {

		return studentRepository.findByDepartment(department).stream().map(this::mapToDTO).collect(Collectors.toList());

	}

	// ============================
	// Entity -> DTO
	// ============================

	private StudentDTO mapToDTO(Student student) {

		StudentDTO dto = new StudentDTO();

		dto.setId(student.getId());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setEmail(student.getEmail());
		dto.setPhone(student.getPhone());
		dto.setCourse(student.getCourse());
		dto.setDepartment(student.getDepartment());
		dto.setAddress(student.getAddress());
		dto.setGender(student.getGender());
		dto.setDob(student.getDob());

		return dto;
	}

	// ============================
	// DTO -> Entity
	// ============================

	private Student mapToEntity(StudentDTO dto) {

		Student student = new Student();

		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		student.setPhone(dto.getPhone());
		student.setCourse(dto.getCourse());
		student.setDepartment(dto.getDepartment());
		student.setAddress(dto.getAddress());
		student.setGender(dto.getGender());
		student.setDob(dto.getDob());

		return student;
	}

}