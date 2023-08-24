package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class SingleCoachDetailsDto {

	private int coachId;

	private String coachName;

	private List<String> certifications;

	private String coachDetails;

	private long phoneNumber;

	private String emailId;

	private String badge;

	private int experience;

	private int noOfUserTrained;

	private List<String> specializations;

	private String photo;

	private String cases;

	private boolean subscribed;

	private boolean topList;

	private List<String> languages;

	private List<CoachReviewDto> coachReview;

	private String instagramLink;

	private String instagramName;

	private int slotsAvailable;

	private int trained;

	private double coachRatings;

	private List<TransformationDetailsDto> transformations;

	public SingleCoachDetailsDto(int coachId, String coachName, List<String> certifications, String coachDetails,
			long phoneNumber, String emailId, String badge, int experience, int noOfUserTrained,
			List<String> specializations, String photo, boolean subscribed) {
		super();
		this.coachId = coachId;
		this.coachName = coachName;
		this.certifications = certifications;
		this.coachDetails = coachDetails;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.badge = badge;
		this.experience = experience;
		this.noOfUserTrained = noOfUserTrained;
		this.specializations = specializations;
		this.photo = photo;
		this.subscribed = subscribed;
	}

	public SingleCoachDetailsDto() {
		super();
	}

}
