package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class NotificationInformationDto {

	private int notificationId;

	private String notificationDetails;
	
	private String notificationImage;

	private String notificationName;
	
	public NotificationInformationDto() {
		super();
	}

	public NotificationInformationDto(int notificationId, String notificationDetails, String notificationImage,
			String notificationName) {
		super();
		this.notificationId = notificationId;
		this.notificationDetails = notificationDetails;
		this.notificationImage = notificationImage;
		this.notificationName = notificationName;
	}
	
}
