package com.tyss.strongameapp.dto;

import java.util.Date;

import com.tyss.strongameapp.entity.TransformationDetails;
import com.tyss.strongameapp.entity.UserStepsStats;

import lombok.Data;

@Data
public class UserInformationDto {

	private Double coins;

	private int position;

	private String otp;

	private Date packageExpiryDate;

	private int notificationCount;

	private int userId;

	private String name;

	private Date dateOFBirth;

	private String email;

	private String password;

	private String confirmPassword;

	private long mobileNo;

	private double height;

	private double weight;

	private String gender;

	private String photo;

	private String token;

	private UserStepsStats steps;

	private TransformationDetails trans;

	private String cases;

	private String firebaseToken;

	private String friendRefereneCode;
	
	private String deviceId;

	public UserInformationDto() {

	}

	public UserInformationDto(Double coins, int position, String otp, Date packageExpiryDate, int notificationCount,
			int userId, String name, Date dateOFBirth, String email, String password, String confirmPassword,
			long mobileNo, double height, double weight, String gender, String photo, String token,
			UserStepsStats steps, TransformationDetails trans, String cases, String firebaseToken,
			String friendRefereneCode) {
		super();
		this.coins = coins;
		this.position = position;
		this.otp = otp;
		this.packageExpiryDate = packageExpiryDate;
		this.notificationCount = notificationCount;
		this.userId = userId;
		this.name = name;
		this.dateOFBirth = dateOFBirth;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.mobileNo = mobileNo;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.photo = photo;
		this.token = token;
		this.steps = steps;
		this.trans = trans;
		this.cases = cases;
		this.firebaseToken = firebaseToken;
		this.friendRefereneCode = friendRefereneCode;
	}

}
