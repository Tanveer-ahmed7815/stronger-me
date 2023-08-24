package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class LeaderBoardUsersDto {

	private int position;

	private int userId;

	private String name;

	private String photo;

	private double coins;

	public LeaderBoardUsersDto() {
		super();
	}

	public LeaderBoardUsersDto(int position, int userId, String name, String photo, Double coins) {
		super();
		this.position = position;
		this.userId = userId;
		this.name = name;
		this.photo = photo;
		this.coins = coins;
	}

}
