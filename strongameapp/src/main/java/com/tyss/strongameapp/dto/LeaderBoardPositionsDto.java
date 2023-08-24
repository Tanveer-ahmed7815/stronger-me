package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class LeaderBoardPositionsDto {

	List<LeaderBoardUsersDto> boardUsers;

	private int currentUserPosition;
}
