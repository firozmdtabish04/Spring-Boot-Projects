package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.request.DesignationRequest;
import com.example.demo.dto.response.DesignationResponse;
import com.example.demo.entity.Designation;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DesignationMapper {

	private final ModelMapper modelMapper;

	public Designation toEntity(DesignationRequest request) {
		return modelMapper.map(request, Designation.class);
	}

	public DesignationResponse toResponse(Designation designation) {
		return modelMapper.map(designation, DesignationResponse.class);
	}

}