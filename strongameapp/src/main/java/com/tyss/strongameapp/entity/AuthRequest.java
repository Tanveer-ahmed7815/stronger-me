package com.tyss.strongameapp.entity;

import lombok.Data;

@Data
public class AuthRequest {

	private String email;
	private String password;
	private String deviceId;
	private String firebaseToken;
	
	public AuthRequest() {
		super();
	}
	public AuthRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
}