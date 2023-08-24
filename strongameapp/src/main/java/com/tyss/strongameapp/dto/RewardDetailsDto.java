package com.tyss.strongameapp.dto;


import com.tyss.strongameapp.entity.UserInformation;

import lombok.Data;

@Data
public class RewardDetailsDto {
 
	private int rewardId;
	
	private double noOfSteps;

	private double rewardCoins;
		
	private UserInformation user;

	public RewardDetailsDto(int rewardId, double noOfSteps, double rewardCoins, UserInformation user) {
		super();
		this.rewardId = rewardId;
		this.noOfSteps = noOfSteps;
		this.rewardCoins = rewardCoins;
		this.user = user;
	}

}
