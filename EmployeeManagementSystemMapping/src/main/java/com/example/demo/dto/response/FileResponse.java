package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {

	private Long id;

	private String originalFileName;

	private String storedFileName;

	private String fileType;

	private Long fileSize;

	private LocalDateTime uploadedAt;

	private String downloadUrl;
}