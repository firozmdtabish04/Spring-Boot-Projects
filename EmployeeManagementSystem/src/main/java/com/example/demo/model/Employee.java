package com.example.demo.model;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	private int id;
	private String name;
	private String department;
	private double salary;
	private String designation;
	private String email;
	private String mobile;

	@Value("${company.name}")
	private String companyName;

	public void display() {
		System.out.println(companyName);
	}

	public Employee() {
		System.out.println("Employee Object Created Successfully!");
	}

	public Employee(int id, String name, String department, double salary, String designation, String email,
			String mobile) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.salary = salary;
		this.designation = designation;
		this.email = email;
		this.mobile = mobile;
		this.companyName = companyName;
	}

	@PostConstruct
	public void init() {
		System.out.println("Employee Bean Initialized");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("Employee Bean Destroyed");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", salary=" + salary
				+ ", designation=" + designation + ", email=" + email + ", mobile=" + mobile + ", companyName="
				+ companyName + "]";
	}

}
