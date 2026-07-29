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
public class DesignationResponse {

	private Long id;

	private String designationName;

	private String description;

	private Boolean active;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}