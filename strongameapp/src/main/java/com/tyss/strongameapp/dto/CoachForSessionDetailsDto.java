package com.tyss.strongameapp.dto;

import java.util.List;

import com.tyss.strongameapp.entity.SessionDetails;

import lombok.Data;

@Data
public class CoachForSessionDetailsDto {

	private int coachId;

	private String coachName;

	private List<String> certifications;

	private String coachDetails;

	private long phoneNumber;

	private String emailId;

	private String badge;

	private int experience;

	private String specialization;

	private String photo;

	private double coachRatings;

	private int trained;

	private int slotsAvailable;

	private String instagramLink;

	private String instagramName;

	private List<SessionDetails> sessions;

	public CoachForSessionDetailsDto() {
		super();
	}

}
