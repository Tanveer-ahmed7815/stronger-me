package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoachFilterDTO {
	
	private List<String> badges;
	
	private List<String> specializations;
	
	private List<String> languages;
	
	private String badge;
	
	private String specialization;
	
	private String language;

	public CoachFilterDTO(List<String> badges, List<String> specializations, List<String> languages) {
		super();
		this.badges = badges;
		this.specializations = specializations;
		this.languages = languages;
	}
	

}
