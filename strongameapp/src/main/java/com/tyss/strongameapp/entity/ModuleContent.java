package com.tyss.strongameapp.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "module_content")
public class ModuleContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private int contentId;

	@Column(name = "content_name", unique = true)
	private String contentName;

	@Column(name = "content_description")
	private String contentDescription;

	@Column(name = "image_content")
	private String imageContent;

	@Column(name = "video_content")
	private String videoContent;

	@Column(name = "duration")
	private double duration;

	@Column(name = "intensity")
	private String intensity;

	@Column(name = "level")
	private String level;

	@Column(name = "viewer_count")
	private int viewerCount;

	@Column(name = "image")
	private String image;

	@Column(name = "video")
	private String video;

	@Exclude
	@JsonManagedReference(value = "content-streamed")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "streamedContent")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<StreamedContent> contentStreamed;

	@Exclude
	@JsonBackReference(value = "module-content")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "module_id")
	private StrongermeModule strongermeModule;

	@Exclude
	@JsonManagedReference(value = "module-homebanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homeBannerModule")
	private List<HomeBannerInformation> moduleHomeBanner;

	@Exclude
	@JsonManagedReference(value = "content-toptrend")
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "toptrendContent", fetch = FetchType.LAZY)
	private TopTenTrend tenTrend;

}
