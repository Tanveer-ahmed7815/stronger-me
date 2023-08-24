package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class CoachDetailsDto {

	private int coachId;

	private String coachName;

	private List<String> certifications;

	private String coachDetails;

	private long phoneNumber;

	private String emailId;

	private String badge;

	private int experience;

	private List<String> specializations;

	private String photo;

	private double coachRatings;

	private int trained;

	private int slotsAvailable;

	private String instagramLink;

	private String instagramName;

	private List<String> languages;

	private List<CoachReviewDto> coachReview;

	private boolean topList;

}
