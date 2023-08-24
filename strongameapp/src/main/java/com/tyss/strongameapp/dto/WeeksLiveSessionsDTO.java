package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class WeeksLiveSessionsDTO {
	
	private List<TodaysLiveSessionDto> daySessionList;
	
	private boolean packageFlag;

}
