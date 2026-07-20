package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@Controller
public class EmployeeController {

	private final EmployeeService service;

	@Autowired
	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	public void addEmployee() {

		Employee employee = new Employee();

		employee.setId(102);
		employee.setName("Akhil");
		employee.setDepartment("Java Developer");
		employee.setSalary(65000);
		employee.setDesignation("Faculty");
		employee.setEmail("mdtabishfiroz04@gmail.com");
		employee.setMobile("8102946894");
		employee.setCompanyName("Lyient Solutions");

		service.addEmployee(employee);

		System.out.println("Employee Added From Controller");
	}

	public void showEmployees() {

		System.out.println("\nEmployee List");

		service.getAllEmployees().forEach(System.out::println);
	}

	public void searchEmployee(int id) {

		Employee employee = service.getEmployeeById(id);

		if (employee != null) {
			System.out.println(employee);
		} else {
			System.out.println("Employee Not Found");
		}
	}

	public void deleteEmployee(int id) {

		service.deleteEmnployee(id);
	}
}