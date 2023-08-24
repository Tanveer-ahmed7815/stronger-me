package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ModuleHomePageDto {

	private int contentId;

	private String contentName;

	private String contentDescription;

	private double duration;

	private int viewerCount;

	private String image;

	private String video;

	private List<RecentUser> boardUsers;

	public ModuleHomePageDto() {
		super();
	}

}
