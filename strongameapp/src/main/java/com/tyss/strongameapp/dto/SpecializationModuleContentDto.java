package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class SpecializationModuleContentDto {

	private int specializationContentId;

	private String specializationName;

	private String specializationImage;

	private List<LiveModuleContentDto> liveModuleContents;
}
