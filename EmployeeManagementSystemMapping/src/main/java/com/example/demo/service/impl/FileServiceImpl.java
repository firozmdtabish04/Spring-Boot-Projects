package com.example.demo.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.FileResponse;
import com.example.demo.entity.Employee;
import com.example.demo.entity.FileDocument;
import com.example.demo.enums.DocumentType;
import com.example.demo.exception.InvalidFileException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.FileRepository;
import com.example.demo.service.interfaces.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private static final String UPLOAD_DIR = "uploads";

	private static final long MAX_SIZE = 5 * 1024 * 1024;

	private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png", "application/pdf");

	private final FileRepository fileRepository;

	private final EmployeeRepository employeeRepository;

	@Override
	public FileResponse uploadFile(MultipartFile file) {

		try {

			if (file.isEmpty()) {
				throw new InvalidFileException("File is empty");
			}

			if (file.getSize() > MAX_SIZE) {
				throw new InvalidFileException("Maximum file size is 5 MB");
			}

			if (!ALLOWED_TYPES.contains(file.getContentType())) {
				throw new InvalidFileException("Only JPG, PNG and PDF files are allowed");
			}

			Path uploadPath = Paths.get(UPLOAD_DIR);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String originalName = file.getOriginalFilename();

			String extension = "";

			if (originalName != null && originalName.contains(".")) {
				extension = originalName.substring(originalName.lastIndexOf("."));
			}

			String storedFileName = UUID.randomUUID() + extension;

			Files.copy(file.getInputStream(), uploadPath.resolve(storedFileName), StandardCopyOption.REPLACE_EXISTING);

			FileDocument document = FileDocument.builder().originalFileName(originalName).storedFileName(storedFileName)
					.fileType(file.getContentType()).fileSize(file.getSize()).uploadedAt(LocalDateTime.now()).build();

			document = fileRepository.save(document);

			return mapToResponse(document);

		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public List<FileResponse> uploadFiles(MultipartFile[] files) {

		if (files == null || files.length == 0) {
			throw new InvalidFileException("No files selected");
		}

		return Arrays.stream(files).map(this::uploadFile).toList();
	}

	@Override
	public FileResponse uploadEmployeeDocument(Long employeeId, MultipartFile file, DocumentType documentType) {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + employeeId));

		try {

			if (file.isEmpty()) {
				throw new InvalidFileException("File is empty");
			}

			if (file.getSize() > MAX_SIZE) {
				throw new InvalidFileException("Maximum file size is 5 MB");
			}

			if (!ALLOWED_TYPES.contains(file.getContentType())) {
				throw new InvalidFileException("Only JPG, PNG and PDF files are allowed");
			}

			Path uploadPath = Paths.get(UPLOAD_DIR);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String originalName = file.getOriginalFilename();

			String extension = "";

			if (originalName != null && originalName.contains(".")) {
				extension = originalName.substring(originalName.lastIndexOf("."));
			}

			String storedFileName = UUID.randomUUID() + extension;

			Files.copy(file.getInputStream(), uploadPath.resolve(storedFileName), StandardCopyOption.REPLACE_EXISTING);

			FileDocument document = FileDocument.builder().originalFileName(originalName).storedFileName(storedFileName)
					.fileType(file.getContentType()).fileSize(file.getSize()).uploadedAt(LocalDateTime.now())
					.employee(employee).documentType(documentType).build();

			document = fileRepository.save(document);

			return mapToResponse(document);

		} catch (IOException e) {
			throw new RuntimeException("Failed to upload employee document", e);
		}
	}

	@Override
	public List<FileResponse> getEmployeeDocuments(Long employeeId) {

		employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + employeeId));

		return fileRepository.findByEmployeeId(employeeId).stream().map(this::mapToResponse).toList();
	}

	@Override
	public List<FileResponse> getAllFiles() {

		return fileRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	public FileResponse getFile(String fileName) {

		FileDocument document = fileRepository.findByStoredFileName(fileName)
				.orElseThrow(() -> new ResourceNotFoundException("File not found"));

		return mapToResponse(document);
	}

	@Override
	public ResponseEntity<Resource> downloadFile(String fileName) {

		try {

			FileDocument document = fileRepository.findByStoredFileName(fileName)
					.orElseThrow(() -> new ResourceNotFoundException("File not found"));

			Path filePath = Paths.get(UPLOAD_DIR).resolve(document.getStoredFileName());

			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists()) {
				throw new ResourceNotFoundException("File not found");
			}

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + document.getOriginalFileName() + "\"").body(resource);

		} catch (MalformedURLException e) {
			throw new RuntimeException("Unable to download file", e);
		}
	}

	@Override
	public void deleteFile(String fileName) {

		try {

			FileDocument document = fileRepository.findByStoredFileName(fileName)
					.orElseThrow(() -> new ResourceNotFoundException("File not found"));

			Path filePath = Paths.get(UPLOAD_DIR).resolve(document.getStoredFileName());

			Files.deleteIfExists(filePath);

			fileRepository.delete(document);

		} catch (IOException e) {
			throw new RuntimeException("Unable to delete file", e);
		}
	}

	private FileResponse mapToResponse(FileDocument document) {

		return FileResponse.builder().id(document.getId()).originalFileName(document.getOriginalFileName())
				.storedFileName(document.getStoredFileName()).fileType(document.getFileType())
				.fileSize(document.getFileSize()).uploadedAt(document.getUploadedAt())
				.downloadUrl("/api/files/download/" + document.getStoredFileName()).build();
	}
}