package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class LoginDto {
	
	private UserInformationDto userDto;
	
	private String token;
	
	public LoginDto() {
		super();
	}
	public LoginDto(UserInformationDto userDto, String token) {
		super();
		this.userDto = userDto;
		this.token = token;
	}
	

}
