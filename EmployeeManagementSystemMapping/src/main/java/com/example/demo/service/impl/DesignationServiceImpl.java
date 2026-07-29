package com.example.demo.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.DesignationRequest;
import com.example.demo.dto.response.DesignationResponse;
import com.example.demo.entity.Designation;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DesignationMapper;
import com.example.demo.repository.DesignationRepository;
import com.example.demo.service.interfaces.DesignationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DesignationServiceImpl implements DesignationService {

	private final DesignationRepository repository;
	private final DesignationMapper mapper;

	@Override
	public DesignationResponse createDesignation(DesignationRequest request) {

		log.info("Creating designation : {}", request.getDesignationName());

		if (repository.existsByDesignationNameIgnoreCase(request.getDesignationName())) {

			throw new DuplicateResourceException("Designation already exists.");
		}

		Designation designation = mapper.toEntity(request);

		designation = repository.save(designation);

		log.info("Designation created with id {}", designation.getId());

		return mapper.toResponse(designation);
	}

	@Override
	public DesignationResponse updateDesignation(Long id, DesignationRequest request) {

		log.info("Updating designation : {}", id);

		Designation designation = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		if (repository.existsByDesignationNameIgnoreCaseAndIdNot(request.getDesignationName(), id)) {

			throw new DuplicateResourceException("Designation already exists.");
		}

		mapper.updateEntity(request, designation);

		designation = repository.save(designation);

		log.info("Designation updated : {}", id);

		return mapper.toResponse(designation);
	}

	@Override
	@Transactional(readOnly = true)
	public DesignationResponse getDesignationById(Long id) {

		log.info("Fetching designation {}", id);

		Designation designation = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		return mapper.toResponse(designation);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DesignationResponse> getAllDesignations(String keyword, Pageable pageable) {

		log.info("Fetching designation list");

		Page<Designation> page;

		if (keyword == null || keyword.isBlank()) {

			page = repository.findAll(pageable);

		} else {

			page = repository.findByDesignationNameContainingIgnoreCase(keyword, pageable);
		}

		return page.map(mapper::toResponse);
	}

	@Override
	public void deleteDesignation(Long id) {

		log.info("Deleting designation {}", id);

		Designation designation = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		repository.delete(designation);

		log.info("Designation deleted {}", id);

	}

}