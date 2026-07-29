package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

	boolean existsByDesignationNameIgnoreCase(String designationName);

	boolean existsByDesignationNameIgnoreCaseAndIdNot(String designationName, Long id);

	Page<Designation> findByDesignationNameContainingIgnoreCase(String keyword, Pageable pageable);

}