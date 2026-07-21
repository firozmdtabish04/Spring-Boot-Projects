package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.ProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Project;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.service.interfaces.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;

	@Override
	public ProjectResponse createProject(ProjectRequest request) {

		if (projectRepository.existsByProjectName(request.getProjectName())) {
			throw new DuplicateResourceException("Project already exists.");
		}

		Project project = projectMapper.toEntity(request);
		Project savedProject = projectRepository.save(project);

		return projectMapper.toResponse(savedProject);
	}

	@Override
	public ProjectResponse getProject(Long id) {

		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found."));

		return projectMapper.toResponse(project);
	}

	@Override
	public List<ProjectResponse> getAllProjects() {

		return projectRepository.findAll().stream().map(projectMapper::toResponse).collect(Collectors.toList());
	}

	@Override
	public ProjectResponse updateProject(Long id, ProjectRequest request) {

		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found."));

		project.setProjectName(request.getProjectName());
		project.setClientName(request.getClientName());
		project.setBudget(request.getBudget());
		project.setStartDate(request.getStartDate());
		project.setEndDate(request.getEndDate());

		Project updatedProject = projectRepository.save(project);

		return projectMapper.toResponse(updatedProject);
	}

	@Override
	public void deleteProject(Long id) {

		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found."));

		projectRepository.delete(project);
	}
}