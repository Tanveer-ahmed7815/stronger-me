package com.tyss.strongameapp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserCoachReviewDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;

	private double rating;

}
