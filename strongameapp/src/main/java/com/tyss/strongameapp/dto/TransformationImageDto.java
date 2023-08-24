package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class TransformationImageDto {
	
	private int transformationImageId;
	
	private String transformationImage;

	
	public TransformationImageDto() {
		super();
	}

	public TransformationImageDto(int transformationImageId, String transformationImage) {
		super();
		this.transformationImageId = transformationImageId;
		this.transformationImage = transformationImage;
	}


}
