package com.example.demo.service.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.FileResponse;

public interface FileService {

	FileResponse uploadFile(MultipartFile file);

	List<FileResponse> getAllFiles();

	ResponseEntity<Resource> downloadFile(String fileName);

	void deleteFile(String fileName);

	FileResponse getFile(String fileName);
}