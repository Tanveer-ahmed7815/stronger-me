package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ModuleDto {

	private List<StrongermeModuleDto> modules;

	private boolean isSubcribed;

}
