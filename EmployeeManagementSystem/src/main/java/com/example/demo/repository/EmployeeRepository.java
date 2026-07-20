package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Repository
public class EmployeeRepository {

	private List<Employee> employeeList = new ArrayList<>();

	public EmployeeRepository() {
		System.out.println("EmployeeRepository Object Created");
	}

	@PostConstruct
	public void init() {
		System.out.println("EmployeeRepository Initialized");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("EmployeeRepository Destroyed");
	}

	// Save Employee
	public void save(Employee employee) {
		employeeList.add(employee);
		System.out.println("Employee Added Successfully");
	}

	// Find All Employees
	public List<Employee> findAll() {
		return employeeList;
	}

	// Find Employee By Id
	public Employee findById(int id) {
		for (Employee emp : employeeList) {
			if (emp.getId() == id) {
				return emp;
			}
		}
		return null;
	}

	// Update Employee
	public void update(Employee employee) {

		Employee existingEmployee = findById(employee.getId());

		if (existingEmployee != null) {
			existingEmployee.setName(employee.getName());
			existingEmployee.setDepartment(employee.getDepartment());
			existingEmployee.setSalary(employee.getSalary());

			System.out.println("Employee Updated Successfully");
		} else {
			System.out.println("Employee Not Found");
		}
	}

	// Delete Employee
	public void delete(int id) {

		Employee employee = findById(id);

		if (employee != null) {
			employeeList.remove(employee);
			System.out.println("Employee Deleted Successfully");
		} else {
			System.out.println("Employee Not Found");
		}
	}
}
