package com.tyss.strongameapp.dto;


import com.tyss.strongameapp.entity.TransformationDetails;
import com.tyss.strongameapp.entity.UserInformation;

import lombok.Data;

@Data
public class TransformationLikeDetailsDto {

	private int transformationLikeId;

	private boolean like;
	
	private UserInformation transformationLikeUser;
	
	private TransformationDetails transformationLike;
	
	private int totalLikes;

	public TransformationLikeDetailsDto(int transformationLikeId, boolean like, UserInformation transformationLikeUser,
			TransformationDetails transformationLike, int totalLikes) {
		super();
		this.transformationLikeId = transformationLikeId;
		this.like = like;
		this.transformationLikeUser = transformationLikeUser;
		this.transformationLike = transformationLike;
		this.totalLikes = totalLikes;
	}

	public TransformationLikeDetailsDto() {
		super();
	}
	
	
}
