package com.tyss.strongameapp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseDto implements Serializable{

	
	private static final long serialVersionUID = -3103934031359382927L;
		
	private boolean error;
		
	private transient Object data;
	
	private String message;

	public ResponseDto(boolean error, Object data, String message) {
		super();
		this.error = error;
		this.data = data;
		this.message = message;
	}

	public ResponseDto() {
		super();
	}
	
	

}
