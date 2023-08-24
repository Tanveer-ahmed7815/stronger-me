package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "transformation_details")
public class TransformationDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transformation_id")
	private int transformationId;

	@Column(name = "transformation_details", length = 999)
	private String transformationDetail;

	@Column(name = "transformation_video")
	private String transformationVideo;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "plan")
	private String plan;

	@Column(name = "coach_name")
	private String coachName;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "transformation_id", referencedColumnName = "transformation_id")
	private List<TransformationImage> image;

	@Exclude
	@JsonBackReference(value = "coach-transformation")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "coach_id")
	private CoachDetails coach;

	@Exclude
	@JsonManagedReference(value = "trans-translike")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transformationLike")
	private List<TransformationLikeDetails> likeDetails;

	@Exclude
	@JsonManagedReference(value = "transformation-homebanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homeBannerTransformation")
	private List<HomeBannerInformation> transformationHomeBanner;

	public TransformationDetails(int transformationId, String transformationDetail, String transformationVideoUrl,
			String userName, String plan, String coachName, List<TransformationImage> image, CoachDetails coachDetails,
			List<TransformationLikeDetails> transformationLikeDetails) {
		super();
		this.transformationId = transformationId;
		this.transformationDetail = transformationDetail;
		this.transformationVideo = transformationVideoUrl;
		this.userName = userName;
		this.plan = plan;
		this.coachName = coachName;
		this.image = image;
		this.coach = coachDetails;
		this.likeDetails = transformationLikeDetails;
	}

	public TransformationDetails() {
		super();
	}

}
