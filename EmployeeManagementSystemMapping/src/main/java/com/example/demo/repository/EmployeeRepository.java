package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	Page<Employee> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

	Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

	@Query("SELECT e FROM Employee e WHERE e.status='ACTIVE'")
	List<Employee> findActiveEmployees();

	@Query("SELECT e FROM Employee e WHERE e.department.departmentName = :departmentName")
	List<Employee> findEmployeesByDepartment(@Param("departmentName") String departmentName);

	@Query("SELECT e FROM Employee e WHERE e.salary > :salary")
	List<Employee> findEmployeesWithSalaryGreaterThan(@Param("salary") Double salary);
}