package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tyss.strongameapp.constants.ListToStringConverter;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "coach_information")
public class CoachDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coach_id")
	private int coachId;

	@NotNull
	@Column(name = "coach_name")
	private String coachName;

	@Column(name = "certifications")
	@Convert(converter = ListToStringConverter.class)
	private List<String> certifications;

	@Column(name = "coach_details", length = 999)
	private String coachDetails;

	@NotNull
	@Column(name = "phone_number")
	private long phoneNumber;

	@NotNull
	@Column(name = "email_id")
	private String emailId;

	@NotNull
	private String badge;

	@Column(name = "experience")
	private int experience;

	@Column(name = "specialization")
	@Convert(converter = ListToStringConverter.class)
	private List<String> specializations;

	@Column(name = "photo")
	private String photo;

	@Column(name = "coach_ratings")
	private double coachRatings;

	@Column(name = "trained")
	private int trained;

	@Column(name = "slots_available")
	private int slotsAvailable;

	@Column(name = "instagram_link")
	private String instagramLink;

	@Column(name = "instagram_name")
	private String instagramName;

	@Column(name = "top_list")
	private boolean topList;
	
	@Column(name = "languages")
	@Convert(converter = ListToStringConverter.class)
	private List<String> languages;

	/*
	 * @JsonManagedReference(value = "coach-language")
	 * 
	 * @JsonIgnore
	 * 
	 * @ManyToMany(cascade = CascadeType.PERSIST)
	 * 
	 * @JoinTable(name = "coach_language", joinColumns = { @JoinColumn(name =
	 * "coach_id") }, inverseJoinColumns = {
	 * 
	 * @JoinColumn(name = "language_id") }) private List<Language> language;
	 */

	@Exclude
	@JsonManagedReference(value = "coach-coachreview")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reviewCoach")
	private List<CoachReview> coachReview;

	@Exclude
	@JsonManagedReference(value = "coach-plan")
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "coach_plan", joinColumns = { @JoinColumn(name = "coach_id") }, inverseJoinColumns = {
			@JoinColumn(name = "plan_id") })
	private List<PlanInformation> coachPlans;

	@Exclude
	@JsonManagedReference(value = "coach-session")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "coachForSession")
	private List<SessionDetails> sessions;

	@Exclude
	@JsonManagedReference(value = "coach-transformation")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "coach")
	private List<TransformationDetails> transformations;

	@Exclude
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "userCoach")
	private List<UserInformation> users;

	@Exclude
	@JsonManagedReference(value = "coach-homebanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homeBannerCoach")
	private List<HomeBannerInformation> coachHomeBanner;

	public CoachDetails() {
		super();
	}

	public CoachDetails(int coachId, @NotNull String coachName, List<String> certifications, String coachDetails,
			@NotNull long phoneNumber, @NotNull String emailId, @NotNull String badge, int experience,
			List<String> specializations, String photo, double coachRatings, int trained, int slotsAvailable,
			List<PlanInformation> coachPlans, List<TransformationDetails> transformations, List<UserInformation> users,
			List<HomeBannerInformation> coachHomeBanner) {
		super();
		this.coachId = coachId;
		this.coachName = coachName;
		this.certifications = certifications;
		this.coachDetails = coachDetails;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.badge = badge;
		this.experience = experience;
		this.specializations = specializations;
		this.photo = photo;
		this.coachRatings = coachRatings;
		this.trained = trained;
		this.slotsAvailable = slotsAvailable;
		this.coachPlans = coachPlans;
		this.transformations = transformations;
		this.users = users;
		this.coachHomeBanner = coachHomeBanner;
	}

}
