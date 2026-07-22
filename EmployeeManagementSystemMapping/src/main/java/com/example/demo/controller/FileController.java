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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.FileResponse;
import com.example.demo.service.interfaces.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) {

		return ResponseEntity.ok(fileService.uploadFile(file));
	}

	@GetMapping
	public ResponseEntity<List<FileResponse>> getAllFiles() {
		return ResponseEntity.ok(fileService.getAllFiles());
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

	@GetMapping("/{fileName}")
	public ResponseEntity<FileResponse> getFile(@PathVariable String fileName) {

		return ResponseEntity.ok(fileService.getFile(fileName));
	}

	@PostMapping(value = "/upload/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<FileResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

		return ResponseEntity.ok(fileService.uploadFiles(files));
	}
}