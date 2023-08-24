package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class StudioModuleDto {

	private List<LiveModuleDto> liveModules;

	private boolean isSubcribed;
}
