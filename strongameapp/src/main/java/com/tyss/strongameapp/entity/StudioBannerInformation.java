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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "studio_banner_information")
public class StudioBannerInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studio_banner_id")
	private int studioBannerId;

	@Column(name = "studio_banner_image")
	private String studioBannerImage;

	@Exclude
	@JsonBackReference(value = "livecontent-studiobanner")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "live_content_id")
	private LiveModuleContent moduleContent;

	public StudioBannerInformation() {
		super();
	}

	public StudioBannerInformation(int studioBannerId,
			String studioBannerImage) {
		super();
		this.studioBannerId = studioBannerId;
		this.studioBannerImage = studioBannerImage;
	}

}
