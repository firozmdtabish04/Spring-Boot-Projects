package com.example.demo.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectResponse {

	private Long id;
	private String projectName;
	private String clientName;
	private Double budget;
	private LocalDate startDate;
	private LocalDate endDate;
}