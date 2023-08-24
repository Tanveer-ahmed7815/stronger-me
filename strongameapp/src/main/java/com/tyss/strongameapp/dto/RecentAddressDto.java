package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class RecentAddressDto {

	private String address;

	private String city;

	private String state;

	private String country;

	private String pincode;

	private Object otherData;

}
