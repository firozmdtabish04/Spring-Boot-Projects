package com.example.demo.service.interfaces;

import java.util.List;

import com.example.demo.dto.request.ProjectRequest;
import com.example.demo.dto.response.ProjectResponse;

public interface ProjectService {

	ProjectResponse createProject(ProjectRequest request);

	ProjectResponse getProject(Long id);

	List<ProjectResponse> getAllProjects();

	ProjectResponse updateProject(Long id, ProjectRequest request);

	void deleteProject(Long id);

}