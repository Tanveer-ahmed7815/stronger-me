package com.tyss.strongameapp.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TaglineDetailsDto {

	private int taglineDetailsId;

	private String tagline;

	private int userCount;

	private List<String> userImages = new ArrayList<String>(3);

	public TaglineDetailsDto() {
		super();
	}

	public TaglineDetailsDto(int taglineDetailsId, String tagline, int userCount, List<String> userImages) {
		super();
		this.taglineDetailsId = taglineDetailsId;
		this.tagline = tagline;
		this.userCount = userCount;
		this.userImages = userImages;
	}

}
