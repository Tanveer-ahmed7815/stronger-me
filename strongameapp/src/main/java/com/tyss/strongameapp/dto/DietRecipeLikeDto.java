package com.tyss.strongameapp.dto;

import java.io.Serializable;

import com.tyss.strongameapp.entity.DietRecipeDetails;
import com.tyss.strongameapp.entity.UserInformation;

import lombok.Data;

@Data
public class DietRecipeLikeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int dietLikeId;

	private boolean userLike;

	private UserInformation dietLikeUser;

	private DietRecipeDetails dietRecipe;

	private int totalLikes;

	private String cases;

	public DietRecipeLikeDto() {
		super();
	}

	public DietRecipeLikeDto(int dietLikeId, boolean userLike, UserInformation dietLikeUser,
			DietRecipeDetails dietRecipe, int totalLikes) {
		super();
		this.dietLikeId = dietLikeId;
		this.userLike = userLike;
		this.dietLikeUser = dietLikeUser;
		this.dietRecipe = dietRecipe;
		this.totalLikes = totalLikes;
	}

}
