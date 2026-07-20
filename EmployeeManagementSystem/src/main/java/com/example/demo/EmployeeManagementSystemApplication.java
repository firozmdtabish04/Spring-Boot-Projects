package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.controller.EmployeeController;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(EmployeeController controller) {
		return args -> {

			controller.addEmployee();

			controller.showEmployees();

			controller.searchEmployee(102);

			controller.deleteEmployee(102);

			controller.showEmployees();

		};
	}
}