package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class CoachReviewDto {

	private int reviewId;

	private int coachId;
	
	private int userId;

	private String name;

	private String description;

	private double rating;

	private String imageUrl;

	private String status;

}
