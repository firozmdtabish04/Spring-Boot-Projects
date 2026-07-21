package com.example.demo.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.enums.EmployeeStatus;
import com.example.demo.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class EmployeeRequest {

	@NotBlank
	private String name;

	@Email
	private String email;

	@Size(min = 10, max = 10)
	private String phone;

	@Positive
	private Double salary;

	private LocalDate joiningDate;

	private Gender gender;

	private EmployeeStatus status;

	@NotNull
	private Long departmentId;
	private List<Long> projectIds;
	@NotNull
	private Long designationId;

	private AddressRequest address;

	public AddressRequest getAddress() {
		return address;
	}

	public void setAddress(AddressRequest address) {
		this.address = address;
	}

	public EmployeeRequest(@NotBlank String name, @Email String email, @Size(min = 10, max = 10) String phone,
			@Positive Double salary, LocalDate joiningDate, Gender gender, EmployeeStatus status,
			@NotNull Long departmentId, @NotNull Long designationId, AddressRequest address) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.joiningDate = joiningDate;
		this.gender = gender;
		this.status = status;
		this.departmentId = departmentId;
		this.designationId = designationId;
		this.address = address;
	}

	public EmployeeRequest(@NotBlank String name, @Email String email, @Size(min = 10, max = 10) String phone,
			@Positive Double salary, LocalDate joiningDate, Gender gender, EmployeeStatus status,
			@NotNull Long departmentId, @NotNull Long designationId) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.joiningDate = joiningDate;
		this.gender = gender;
		this.status = status;
		this.departmentId = departmentId;
		this.designationId = designationId;
	}

	public EmployeeRequest(@NotBlank String name, @Email String email, @Size(min = 10, max = 10) String phone,
			@Positive Double salary, LocalDate joiningDate, Gender gender, EmployeeStatus status,
			@NotNull Long departmentId, List<Long> projectIds, @NotNull Long designationId, AddressRequest address) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.joiningDate = joiningDate;
		this.gender = gender;
		this.status = status;
		this.departmentId = departmentId;
		this.projectIds = projectIds;
		this.designationId = designationId;
		this.address = address;
	}

	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}

	public EmployeeRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
}