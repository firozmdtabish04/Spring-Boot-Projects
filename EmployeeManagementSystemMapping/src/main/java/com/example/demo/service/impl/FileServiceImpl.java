package com.example.demo.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.FileResponse;
import com.example.demo.exception.InvalidFileException;
import com.example.demo.service.interfaces.FileService;

@Service
public class FileServiceImpl implements FileService {

	private static final String UPLOAD_DIR = "uploads";

	private static final long MAX_SIZE = 5 * 1024 * 1024;

	private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png", "application/pdf");

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

			String extension = originalName.substring(originalName.lastIndexOf("."));

			String newFileName = UUID.randomUUID() + extension;

			Files.copy(file.getInputStream(), uploadPath.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);

			return new FileResponse(newFileName, file.getContentType(), file.getSize(),
					"/api/files/download/" + newFileName);

		} catch (IOException e) {
			throw new RuntimeException("Upload failed");
		}
	}

	@Override
	public List<FileResponse> getAllFiles() {

		try {

			Path uploadPath = Paths.get(UPLOAD_DIR);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			return Files.list(uploadPath).map(path -> {
				try {
					return new FileResponse(path.getFileName().toString(), Files.probeContentType(path),
							Files.size(path), "/api/files/download/" + path.getFileName().toString());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());

		} catch (IOException e) {
			throw new RuntimeException("Unable to read files");
		}
	}

	@Override
	public ResponseEntity<Resource> downloadFile(String fileName) {

		try {

			Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists()) {
				throw new RuntimeException("File not found");
			}

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

		} catch (MalformedURLException e) {
			throw new RuntimeException("File not found");
		}
	}

	@Override
	public void deleteFile(String fileName) {

		try {

			Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

			Files.deleteIfExists(filePath);

		} catch (IOException e) {
			throw new RuntimeException("Unable to delete file");
		}
	}

	@Override
	public FileResponse getFile(String fileName) {

		try {

			Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

			if (!Files.exists(filePath)) {
				throw new RuntimeException("File not found");
			}

			return new FileResponse(fileName, Files.probeContentType(filePath), Files.size(filePath),
					"/api/files/download/" + fileName);

		} catch (IOException e) {
			throw new RuntimeException("Unable to read file");
		}
	}
}