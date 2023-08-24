package com.tyss.strongameapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "language")
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "language_id")
	private int languageId;

	@NotNull
	@Column(name = "language")
	private String language;

	public Language() {
		super();
	}

	public Language(int languageId, @NotNull String language) {
		super();
		this.languageId = languageId;
		this.language = language;
	}

}
