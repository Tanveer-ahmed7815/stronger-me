package com.tyss.strongameapp.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class TodaysLiveSessionDto {
	
	private Date date;
	
	private List<SessionDetailsDto> sessionList;
	
	private boolean flag;
	
	private String cases;

	public TodaysLiveSessionDto() {
		super();
	}

	public TodaysLiveSessionDto(List<SessionDetailsDto> sessionList, boolean flag, String cases) {
		super();
		this.sessionList = sessionList;
		this.flag = flag;
		this.cases = cases;
	}
	

}
