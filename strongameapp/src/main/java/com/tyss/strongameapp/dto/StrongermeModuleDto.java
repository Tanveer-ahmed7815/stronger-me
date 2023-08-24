package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class StrongermeModuleDto {
	
	private int moduleId;

	private String moduleName;
	
	private List<ModuleHomePageDto> contents;
	
	private String validationMessage;

	public StrongermeModuleDto() {
		super();
	}

	
}
