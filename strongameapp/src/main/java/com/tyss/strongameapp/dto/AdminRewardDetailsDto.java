package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class AdminRewardDetailsDto {

	private int adminRewardId;

	private double adminRewardCoins;

	public AdminRewardDetailsDto() {
		super();
	}

	public AdminRewardDetailsDto(int adminRewardId, double adminRewardCoins) {
		super();
		this.adminRewardId = adminRewardId;
		this.adminRewardCoins = adminRewardCoins;
	}

}
