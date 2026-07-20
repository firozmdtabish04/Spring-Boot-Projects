package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StudentDTO {

	private Long id;

	@NotBlank(message = "First name is required")
	@Size(min = 2, max = 50)
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(min = 2, max = 50)
	private String lastName;

	@Email(message = "Please enter a valid email")
	@NotBlank(message = "Email is required")
	private String email;

	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits")
	private String phone;

	@NotBlank(message = "Course is required")
	private String course;

	@NotBlank(message = "Department is required")
	private String department;

	private String address;

	private String gender;

	private LocalDate dob;

	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentDTO(Long id, @NotBlank(message = "First name is required") @Size(min = 2, max = 50) String firstName,
			@NotBlank(message = "Last name is required") @Size(min = 2, max = 50) String lastName,
			@Email(message = "Please enter a valid email") @NotBlank(message = "Email is required") String email,
			@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits") String phone,
			@NotBlank(message = "Course is required") String course,
			@NotBlank(message = "Department is required") String department, String address, String gender,
			LocalDate dob) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.course = course;
		this.department = department;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

}
