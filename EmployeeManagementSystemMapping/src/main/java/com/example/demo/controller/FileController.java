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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.FileResponse;
import com.example.demo.service.interfaces.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	/**
	 * Upload Single File
	 */
	@Operation(summary = "Upload Single File")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FileResponse> uploadFile(

			@Parameter(description = "File", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(type = "string", format = "binary"))) @RequestPart("file") MultipartFile file) {

		return ResponseEntity.ok(fileService.uploadFile(file));
	}

	/**
	 * Upload Multiple Files
	 */
	@Operation(summary = "Upload Multiple Files")
	@PostMapping(value = "/upload/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FileResponse>> uploadMultipleFiles(

			@Parameter(description = "Files", content = @Content(array = @ArraySchema(schema = @Schema(type = "string", format = "binary")))) @RequestPart("files") MultipartFile[] files) {

		return ResponseEntity.ok(fileService.uploadFiles(files));
	}

	/**
	 * Get All Files
	 */
	@Operation(summary = "Get All Files")
	@GetMapping
	public ResponseEntity<List<FileResponse>> getAllFiles() {

		return ResponseEntity.ok(fileService.getAllFiles());
	}

	/**
	 * Get File Details
	 */
	@Operation(summary = "Get File Details")
	@GetMapping("/{fileName}")
	public ResponseEntity<FileResponse> getFile(@PathVariable String fileName) {

		return ResponseEntity.ok(fileService.getFile(fileName));
	}

	/**
	 * Download File
	 */
	@Operation(summary = "Download File")
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {

		return fileService.downloadFile(fileName);
	}

	/**
	 * Delete File
	 */
	@Operation(summary = "Delete File")
	@DeleteMapping("/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {

		fileService.deleteFile(fileName);

		return ResponseEntity.ok("File deleted successfully");
	}

}