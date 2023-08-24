package com.tyss.strongameapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserStepsStatsDto {

	public UserStepsStatsDto(int stepId, Date day, double week, double month,double distanceInKm,
			double caloriesBurent, int currentSteps, int targetSteps, double coinsEarned,
			int userId) {
		super();
		this.stepId = stepId;
		this.day = day;
		this.week = week;
		this.month = month;
		this.distanceInKm = distanceInKm;
		this.caloriesBurent = caloriesBurent;
		this.currentSteps = currentSteps;
		this.targetSteps = targetSteps;
		this.coinsEarned = coinsEarned;
		this.userId = userId;
	}

	public UserStepsStatsDto() {
		super();	
	}

	private int stepId;

	private Date day;

	private double week;

	private double month;

	private double distanceInKm;

	private double caloriesBurent;

	private int currentSteps;

	private int targetSteps;

	private double coinsEarned;

	private int userId;
	
	private String validationCases;

	
}
