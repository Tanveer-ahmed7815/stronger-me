package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class StudioBannerInformationDto {
	
	private int studioBannerId;

	private String studioBannerImage;

	private int id;

	private String name;
	
	private String validationMessage;
	
	public StudioBannerInformationDto() {
		super();
	}

	public StudioBannerInformationDto(int studioBannerId,
			String studioBannerImage, int id, String name, String validationMessage) {
		super();
		this.studioBannerId = studioBannerId;
		this.studioBannerImage = studioBannerImage;
		this.id = id;
		this.name = name;
		this.validationMessage = validationMessage;
	}
	
	
	
}
