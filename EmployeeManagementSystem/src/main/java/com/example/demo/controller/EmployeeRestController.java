package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeRestController {

	private final EmployeeService service;

	@Autowired
	public EmployeeRestController(EmployeeService service) {
		this.service = service;
	}

	public List<Employee> getEmployees() {
		return service.getAllEmployees();
	}

	public Employee getEmployee(int id) {
		return service.getEmployeeById(id);
	}

}