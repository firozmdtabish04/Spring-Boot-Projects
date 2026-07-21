package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

	Optional<Designation> findByDesignationName(String name);

	boolean existsByDesignationName(String name);

}