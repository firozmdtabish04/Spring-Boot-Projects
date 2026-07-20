package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.notification.NotificationService;
import com.example.demo.repository.EmployeeRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class EmployeeService {
	private final EmployeeRepository repository;
	private final NotificationService notificationService;

	@Autowired
	public EmployeeService(EmployeeRepository repository, NotificationService notificationService) {
		this.repository = repository;
		this.notificationService = notificationService;
		System.out.println("Constructor Injection Executed");
	}

	@PostConstruct
	public void init() {
		System.out.println("EmployeeService Initialized");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("EmployeeService Destroyed");
	}

//Add Employee
	public void addEmployee(Employee employee) {
		if (employee.getSalary() < 0) {
			System.out.println("Salary cannot be Negative!");
			return;
		}
		repository.save(employee);

	}

//	View All Employee
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

//	View Employee By id
	public Employee getEmployeeById(int id) {
		return repository.findById(id);
	}

//	Update Employee
	public void updateEmployee(Employee employee) {
		repository.update(employee);
	}

//	Delete Employee
	public void deleteEmnployee(int id) {
		repository.delete(id);
	}

}
