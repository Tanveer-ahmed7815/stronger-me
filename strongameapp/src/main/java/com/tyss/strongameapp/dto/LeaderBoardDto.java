package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class LeaderBoardDto {
	
	private List<UserInformationDto> userList;
	
	private int yourPosition;
	
	public LeaderBoardDto(List<UserInformationDto> userList, int yourPosition) {
		super();
		this.userList = userList;
		this.yourPosition = yourPosition;
	}
	public LeaderBoardDto() {
		super();
	}
}
