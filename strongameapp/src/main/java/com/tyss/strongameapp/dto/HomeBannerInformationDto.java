package com.tyss.strongameapp.dto;
import lombok.Data;

@Data
public class HomeBannerInformationDto {

	private int homeBannerId;

	private String homeBannerImage;
	
	private int id;
	
	private String homeBannerType;

	public HomeBannerInformationDto() {
		super();
	}

}

