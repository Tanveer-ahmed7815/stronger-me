package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class DietPlanDetailsDto {

	private int dietId;

	private String dietName;

	private Integer totalLikes;

	private Boolean flag;

	private double calories;

	private double protine;

	private double fat;

	private double carbs;

	private String dietDetails;

	private String dietImage;

	private String dietVideo;
	
	public DietPlanDetailsDto() {
	}


	
}
