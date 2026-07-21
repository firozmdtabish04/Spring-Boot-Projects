package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.DesignationRequest;
import com.example.demo.dto.response.DesignationResponse;
import com.example.demo.entity.Designation;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DesignationMapper;
import com.example.demo.repository.DesignationRepository;
import com.example.demo.service.interfaces.DesignationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {

	private final DesignationRepository designationRepository;
	private final DesignationMapper designationMapper;

	@Override
	public DesignationResponse createDesignation(DesignationRequest request) {

		if (designationRepository.existsByDesignationName(request.getDesignationName())) {
			throw new DuplicateResourceException("Designation already exists.");
		}

		Designation designation = designationMapper.toEntity(request);

		return designationMapper.toResponse(designationRepository.save(designation));
	}

	@Override
	public DesignationResponse getDesignationById(Long id) {

		Designation designation = designationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		return designationMapper.toResponse(designation);

	}

	@Override
	public List<DesignationResponse> getAllDesignations() {

		return designationRepository.findAll().stream().map(designationMapper::toResponse).collect(Collectors.toList());

	}

	@Override
	public DesignationResponse updateDesignation(Long id, DesignationRequest request) {

		Designation designation = designationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		designation.setDesignationName(request.getDesignationName());
		designation.setGrade(request.getGrade());
		designation.setDescription(request.getDescription());

		return designationMapper.toResponse(designationRepository.save(designation));

	}

	@Override
	public void deleteDesignation(Long id) {

		Designation designation = designationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		designationRepository.delete(designation);

	}

}