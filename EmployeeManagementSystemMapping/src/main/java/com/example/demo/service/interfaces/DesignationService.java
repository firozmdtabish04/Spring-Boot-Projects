package com.example.demo.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.DesignationRequest;
import com.example.demo.dto.response.DesignationResponse;

public interface DesignationService {

	DesignationResponse createDesignation(DesignationRequest request);

	DesignationResponse updateDesignation(Long id, DesignationRequest request);

	DesignationResponse getDesignationById(Long id);

	Page<DesignationResponse> getAllDesignations(String keyword, Pageable pageable);

	void deleteDesignation(Long id);

}