package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	// Find all active employees
	@Query("SELECT e FROM Employee e WHERE e.status='ACTIVE'")
	List<Employee> findActiveEmployees();

	// Employees by Department Name
	@Query("""
			SELECT e
			FROM Employee e
			WHERE e.department.departmentName = :departmentName
			""")
	List<Employee> findEmployeesByDepartment(String departmentName);

	// Employees whose salary is greater than a value
	@Query("""
			SELECT e
			FROM Employee e
			WHERE e.salary > :salary
			""")
	List<Employee> findEmployeesWithSalaryGreaterThan(Double salary);

	// Employees ordered by salary descending
	@Query("""
			SELECT e
			FROM Employee e
			ORDER BY e.salary DESC
			""")
	List<Employee> findEmployeesOrderBySalary();

}