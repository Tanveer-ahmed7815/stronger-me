package com.tyss.strongameapp.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SessionDetailsDto {

	private int sessionId;

	private String sessionLink;

	private String sessionName;

	private Date sessionDate;

	private Time sessionTime;

	private String sessionCoachName;

	private double sessionDuration;

	private int slotsAvailable;

	private String photo;

	private int cases;

	private boolean sessionFlag;

	private boolean isUserSessionMapped;

	private String validationCase;
	
	private List<String> images;
	
	private int userCount;
	
	public SessionDetailsDto() {
		super();
	}

	public SessionDetailsDto(int sessionId, String sessionLink, String sessionName, Date sessionDate, Time sessionTime,
			String sessionCoachName, double sessionDuration, int slotsAvailable, String photo, int cases,
			boolean sessionFlag) {
		super();
		this.sessionId = sessionId;
		this.sessionLink = sessionLink;
		this.sessionName = sessionName;
		this.sessionDate = sessionDate;
		this.sessionTime = sessionTime;
		this.sessionCoachName = sessionCoachName;
		this.sessionDuration = sessionDuration;
		this.slotsAvailable = slotsAvailable;
		this.photo = photo;
		this.cases = cases;
		this.sessionFlag = sessionFlag;
	}

}
