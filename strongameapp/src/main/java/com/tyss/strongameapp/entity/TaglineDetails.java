package com.tyss.strongameapp.entity;

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
@Table(name = "tagline_details")
public class TaglineDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tagline_details_id")
	private int taglineDetailsId;

	@Column(name = "title", length = 15)
	private String title;

	@Column(name = "tagline", length = 999)
	private String tagline;

	@Column(name = "type")
	private String type;

	@Exclude
	@JsonManagedReference(value = "tagline-package")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "packageTagline")
	private List<PackageDetails> tagPackageDetails;

	public TaglineDetails() {
		super();
	}

}
