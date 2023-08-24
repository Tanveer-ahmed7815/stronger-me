package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class LogoutDTO {
	
	private int userId;
	
	private String jwtToken;
	
	private String deviceId;

}
