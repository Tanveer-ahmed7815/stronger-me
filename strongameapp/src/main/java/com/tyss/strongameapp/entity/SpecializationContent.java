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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "specialization_content")
public class SpecializationContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specialization_content_id")
	private int specializationContentId;

	@Column(name = "specialization_name")
	private String specializationName;

	@Column(name = "specialization_image")
	private String specializationImage;

	@Exclude
	@JsonBackReference(value = "livemodule-specialization")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "live_module_id")
	private LiveModule liveModule;

	@Exclude
	@JsonManagedReference(value = "specialization-livecontent")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specializationContent")
	private List<LiveModuleContent> liveModuleContents;
	
	@Exclude
	@JsonBackReference(value = "SpecializationMaster")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "specialization_code")
	private SpecializationMaster specializationMaster;

	public SpecializationContent() {
		super();
	}

}
