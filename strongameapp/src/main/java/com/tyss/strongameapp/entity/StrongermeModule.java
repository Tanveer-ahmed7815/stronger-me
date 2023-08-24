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
@Table(name = "strongerme_module")
public class StrongermeModule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "module_id")
	private int moduleId;

	@Column(name = "module_name", unique = true)
	private String moduleName;

	@Exclude
	@JsonManagedReference(value = "module-content")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "strongermeModule")
	private List<ModuleContent> moduleContent;

	public StrongermeModule() {
		super();
	}

	public StrongermeModule(int moduleId, String moduleName) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
	}
}
