package com.tyss.strongameapp.dto;

import java.util.List;

import com.tyss.strongameapp.entity.AdvertisementInformation;

import lombok.Data;

@Data
public class AddsAndBannersDto {
	
	private List<ShoppingBannerInformationDto> shopBannerList;
	
	private List<AdvertisementInformation> advertisementList;

	public AddsAndBannersDto() {
		super();
	}
	
	

}
