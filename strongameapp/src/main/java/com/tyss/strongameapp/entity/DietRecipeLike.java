package com.tyss.strongameapp.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Component
@Entity
@Data
@Table(name = "diet_recipe_like")
public class DietRecipeLike implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "diet_like_id")
	private int dietLikeId;

	@Column(name = "user_like")
	private boolean userLike;

	@Exclude
	@JsonBackReference(value = "user-dietlike")
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation dietLikeUser;

	@Exclude
	@JsonBackReference(value = "diet-dietlike")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "diet_id")
	private DietRecipeDetails dietRecipe;

	public DietRecipeLike() {
		super();
	}

	public DietRecipeLike(int dietLikeId, boolean userLike) {
		super();
		this.dietLikeId = dietLikeId;
		this.userLike = userLike;
	}

}
