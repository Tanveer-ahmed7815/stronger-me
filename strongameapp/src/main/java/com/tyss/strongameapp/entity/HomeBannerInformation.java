package com.tyss.strongameapp.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "home_banner_information")
public class HomeBannerInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "home_banner_id")
	private int homeBannerId;

	@Column(name = "home_banner_image")
	private String homeBannerImage;
	
	@Exclude
    @JsonBackReference(value="diet-homebanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="diet_id")
	private DietRecipeDetails homeBannerDiet;
	
	@Exclude
    @JsonBackReference(value="coach-homebanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="coach_id")
	private CoachDetails homeBannerCoach;
	
	@Exclude
    @JsonBackReference(value="transformation-homebanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="transformation_id")
	private TransformationDetails homeBannerTransformation;
	
	@Column(name="home_banner_type")
	private String homeBannerType;
	
	
	@Exclude
    @JsonBackReference(value="module-homebanner")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="content_id")
	private ModuleContent homeBannerModule;

	public HomeBannerInformation() {
		super();
	}

}