package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class LiveModuleContentDto {

	private int liveContentId;

	private String liveContentName;

	private String contentDescription;

	private String imageContent;

	private String videoContent;

	private double duration;

	private String intensity;

	private String level;

	private int viewerCount;

	private boolean isSubcribed;

	private List<RecentUser> boardUsers;

}
