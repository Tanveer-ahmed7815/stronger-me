package com.tyss.strongameapp.exception;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorDetails {
	
	private Date timestamp;
	
	private String message;
	
	private String details;
	
	private boolean error;

	public ErrorDetails(Date timestamp, String message, String details, boolean error) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;	
		this.error = error;
	}

	public ErrorDetails() {
		super();
	}
	
	
}
