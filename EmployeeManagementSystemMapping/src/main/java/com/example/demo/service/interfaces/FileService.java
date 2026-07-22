package com.example.demo.service.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.FileResponse;
import com.example.demo.enums.DocumentType;

public interface FileService {

	// Upload Single File
	FileResponse uploadFile(MultipartFile file);

	// Upload Multiple Files
	List<FileResponse> uploadFiles(MultipartFile[] files);

	// Get All Files
	List<FileResponse> getAllFiles();

	// Get File Details
	FileResponse getFile(String fileName);

	// Download File
	ResponseEntity<Resource> downloadFile(String fileName);

	// Delete File
	void deleteFile(String fileName);

	// Upload Employee Document
	FileResponse uploadEmployeeDocument(Long employeeId, MultipartFile file, DocumentType documentType);

	// Get Employee Documents
	List<FileResponse> getEmployeeDocuments(Long employeeId);

}