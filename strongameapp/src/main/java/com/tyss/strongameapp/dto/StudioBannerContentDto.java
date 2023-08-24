package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class StudioBannerContentDto {

	private int liveContentId;

	private String liveContentName;

	public StudioBannerContentDto(int liveContentId, String liveContentName) {
		super();
		this.liveContentId = liveContentId;
		this.liveContentName = liveContentName;
	}

}
