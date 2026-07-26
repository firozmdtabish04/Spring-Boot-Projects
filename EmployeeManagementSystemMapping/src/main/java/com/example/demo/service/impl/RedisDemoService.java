package com.example.demo.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisDemoService {

	private final RedisTemplate<String, Object> redisTemplate;

	// Save Employee
	public void saveEmployee(Employee employee) {
		redisTemplate.opsForValue().set("employee:" + employee.getId(), employee);
	}

	// Get Employee
	public Employee getEmployee(Long id) {
		return (Employee) redisTemplate.opsForValue().get("employee:" + id);
	}

	// Delete Employee
	public void deleteEmployee(Long id) {
		redisTemplate.delete("employee:" + id);
	}

	// Check Exists
	public boolean exists(Long id) {
		return redisTemplate.hasKey("employee:" + id);
	}
}