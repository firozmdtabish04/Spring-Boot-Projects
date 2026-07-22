package com.example.demo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.enums.EmployeeStatus;
import com.example.demo.enums.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Employee name is required")
	@Column(nullable = false)
	private String name;

	@Email(message = "Invalid email")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Phone is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits")
	@Column(nullable = false, unique = true)
	private String phone;

	@NotNull(message = "Salary is required")
	@Positive(message = "Salary must be positive")
	private Double salary;

	@PastOrPresent
	private LocalDate joiningDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EmployeeStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	@JsonBackReference
	private Department department;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id")
	@JsonBackReference
	private Designation designation;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	@JsonManagedReference
	private Address address;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "employee_project", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	@JsonManagedReference
	@Builder.Default
	private List<Project> projects = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FileDocument> documents = new ArrayList<>();

}