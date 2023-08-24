package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "diet_recipe_details")
public class DietRecipeDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "diet_id")
	private int dietId;

	@Column(name = "diet_name")
	private String dietName;

	@Column(name = "calories")
	private double calories;

	@Column(name = "protine")
	private double protine;

	@Column(name = "fat")
	private double fat;

	@Column(name = "carbs")
	private double carbs;

	@Column(name = "diet_details", length = 999)
	private String dietDetails;

	@Column(name = "steps")
	private int steps;

	@Column(name = "ingredients")
	private String ingredients;

	@Exclude
	@JsonManagedReference(value = "diet-dietlike")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dietRecipe")
	private List<DietRecipeLike> likes;

	@Exclude
	@JsonManagedReference(value = "diet-homebanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homeBannerDiet")
	private List<HomeBannerInformation> dietHomeBanner;

	@Column(name = "diet_image")
	private String dietImage;

	@Column(name = "diet_video")
	private String dietVideo;

	public DietRecipeDetails() {
		super();
	}

	public List<DietRecipeLike> getLikes() {
		return likes;
	}

	public void setLikes(List<DietRecipeLike> likes) {
		this.likes = likes;
	}

	public DietRecipeDetails(int dietId, String dietName, double calories, double protine, double fat, double carbs,
			String dietDetails, int steps, String ingredients, List<DietRecipeLike> likes, String dietImage,
			String dietVideo) {
		super();
		this.dietId = dietId;
		this.dietName = dietName;
		this.calories = calories;
		this.protine = protine;
		this.fat = fat;
		this.carbs = carbs;
		this.dietDetails = dietDetails;
		this.steps = steps;
		this.ingredients = ingredients;
		this.likes = likes;
		this.dietImage = dietImage;
		this.dietVideo = dietVideo;
	}
}
