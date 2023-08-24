package com.tyss.strongameapp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlanInformationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int planId;

	private String planName;

	private String planDetails;

	private double noOfWeeks;

	private double planPrice;

	public PlanInformationDto() {
		super();
	}

}
