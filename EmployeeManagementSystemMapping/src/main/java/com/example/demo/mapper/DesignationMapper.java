package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.demo.dto.request.DesignationRequest;
import com.example.demo.dto.response.DesignationResponse;
import com.example.demo.entity.Designation;

@Mapper(componentModel = "spring")
public interface DesignationMapper {

	Designation toEntity(DesignationRequest request);

	DesignationResponse toResponse(Designation designation);

	void updateEntity(DesignationRequest request, @MappingTarget Designation designation);

}