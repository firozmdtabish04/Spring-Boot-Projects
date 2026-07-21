package com.example.demo.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjectRequest {

	private String projectName;

	private String clientName;

	private Double budget;

	private LocalDate startDate;

	private LocalDate endDate;
}