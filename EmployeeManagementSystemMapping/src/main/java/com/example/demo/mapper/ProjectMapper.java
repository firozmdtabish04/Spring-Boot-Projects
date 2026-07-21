package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.request.ProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

	private final ModelMapper modelMapper;

	public Project toEntity(ProjectRequest request) {
		return modelMapper.map(request, Project.class);
	}

	public ProjectResponse toResponse(Project project) {
		return modelMapper.map(project, ProjectResponse.class);
	}

}