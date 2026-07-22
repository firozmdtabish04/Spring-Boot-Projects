package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FileDocument;

public interface FileRepository extends JpaRepository<FileDocument, Long> {

	List<FileDocument> findByEmployeeId(Long employeeId);

	Optional<FileDocument> findByStoredFileName(String storedFileName);

}