package com.tyss.strongameapp.dto;

import java.util.List;

import com.tyss.strongameapp.entity.TransformationImage;

import lombok.Data;

@Data
public class TransformationDetailsDto {

	private int transformationId;

	private String transformationDetail;

	private String transformationVideo;
	
	private String userName;
	
	private String coachName;
	
	private String plan;
	
	private Integer totalLikes;
	
	private List<TransformationImage> image;
	
	private Boolean flag;
	
	private String coachImage;

	public TransformationDetailsDto(int transformationId, String transformationDetail, String transformationVideo,
			String userName, String coachName, String plan, Integer totalLikes, List<TransformationImage> image,
			Boolean flag, String coachImage) {
		super();
		this.transformationId = transformationId;
		this.transformationDetail = transformationDetail;
		this.transformationVideo = transformationVideo;
		this.userName = userName;
		this.coachName = coachName;
		this.plan = plan;
		this.totalLikes = totalLikes;
		this.image = image;
		this.flag = flag;
		this.coachImage = coachImage;
	}

	public TransformationDetailsDto() {
		super();
	}
	
	
}
