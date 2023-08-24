package com.tyss.strongameapp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "specialization_master")
public class SpecializationMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specialization_code")
	private int specializationCode;

	@Column(name = "specialization_name", unique = true)
	private String specializationName;
	
	@Column(name = "created_date", length = 50)
	@JsonFormat(pattern = "YYYY-MM-DD HH:MM:SS")
	private Date createdDate;

	@Column(name = "created_by")
	private int adminId;

	@Exclude
	@JsonManagedReference(value = "specialization-content")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specializationMaster")
	private List<SpecializationContent> specializationContent;


}
