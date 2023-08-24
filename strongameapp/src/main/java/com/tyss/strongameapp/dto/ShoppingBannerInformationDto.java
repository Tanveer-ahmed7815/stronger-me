package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class ShoppingBannerInformationDto {

	private int shoppingBannerId;

	private String shoppingBannerImage;
	
	private int id;

	public ShoppingBannerInformationDto() {
		super();
	}

	public ShoppingBannerInformationDto(int shoppingBannerId,
			String shoppingBannerImage) {
		super();
		this.shoppingBannerId = shoppingBannerId;
		this.shoppingBannerImage = shoppingBannerImage;
	}

}
