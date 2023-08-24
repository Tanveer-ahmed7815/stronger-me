package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ModuleContentDto {

	private int contentId;

	private int moduleId;

	private String contentName;

	private String contentDescription;

	private double duration;

	private String intensity;

	private String level;

	private int viewerCount;

	private String image;

	private String video;

	private List<RecentUser> boardUsers;

	private boolean isSubcribed;

	private String validationMessage;

	public ModuleContentDto() {
		super();
	}

}
