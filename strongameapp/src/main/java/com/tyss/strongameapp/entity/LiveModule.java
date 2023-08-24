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

@Data
@Entity
@Table(name = "live_module")
public class LiveModule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "live_module_id")
	private int liveModuleId;

	@Column(name = "live_module_name", unique = true)
	private String liveModuleName;

	@Column(name = "formate_shape")
	private String formateShape;

	@Exclude
	@JsonManagedReference(value = "livemodule-specialization")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "liveModule")
	private List<SpecializationContent> specializationContents;

}
