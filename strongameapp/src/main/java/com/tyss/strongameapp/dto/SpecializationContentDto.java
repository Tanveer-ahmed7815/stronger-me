package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class SpecializationContentDto {

	private int specializationContentId;

	private String specializationName;

	private String specializationImage;

	public SpecializationContentDto() {
		super();
	}

	public SpecializationContentDto(int specializationContentId, String specializationName,
			String specializationImage) {
		super();
		this.specializationContentId = specializationContentId;
		this.specializationName = specializationName;
		this.specializationImage = specializationImage;
	}

}
