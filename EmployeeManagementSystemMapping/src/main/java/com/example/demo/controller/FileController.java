package com.example.demo.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.FileResponse;
import com.example.demo.enums.DocumentType;
import com.example.demo.service.interfaces.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileResponse> uploadFile(@RequestPart("file") MultipartFile file) {

		return ResponseEntity.ok(fileService.uploadFile(file));
	}

	@PostMapping(value = "/upload/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<FileResponse>> uploadMultipleFiles(@RequestPart("files") MultipartFile[] files) {

		return ResponseEntity.ok(fileService.uploadFiles(files));
	}

	@GetMapping
	public ResponseEntity<List<FileResponse>> getAllFiles() {

		return ResponseEntity.ok(fileService.getAllFiles());
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<FileResponse> getFile(@PathVariable String fileName) {

		return ResponseEntity.ok(fileService.getFile(fileName));
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {

		return fileService.downloadFile(fileName);
	}

	@DeleteMapping("/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {

		fileService.deleteFile(fileName);

		return ResponseEntity.ok("File deleted successfully");
	}

	@PostMapping(value = "/employees/{employeeId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileResponse> uploadEmployeeDocument(@PathVariable Long employeeId,
			@RequestPart("file") MultipartFile file, @RequestParam("documentType") DocumentType documentType) {

		return ResponseEntity.ok(fileService.uploadEmployeeDocument(employeeId, file, documentType));
	}

	@GetMapping("/employees/{employeeId}/documents")
	public ResponseEntity<List<FileResponse>> getEmployeeDocuments(@PathVariable Long employeeId) {

		return ResponseEntity.ok(fileService.getEmployeeDocuments(employeeId));
	}
}