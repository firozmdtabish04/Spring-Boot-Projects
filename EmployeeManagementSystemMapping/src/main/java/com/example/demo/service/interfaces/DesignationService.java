package com.example.demo.service.interfaces;

import java.util.List;

import com.example.demo.dto.request.DesignationRequest;
import com.example.demo.dto.response.DesignationResponse;

public interface DesignationService {

	DesignationResponse createDesignation(DesignationRequest request);

	DesignationResponse getDesignationById(Long id);

	List<DesignationResponse> getAllDesignations();

	DesignationResponse updateDesignation(Long id, DesignationRequest request);

	void deleteDesignation(Long id);

}