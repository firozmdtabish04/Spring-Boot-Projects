package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {

	private String fileName;
	private String fileType;
	private long size;
	private String downloadUrl;
}