package com.tyss.strongameapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedAddressDTO {

	private String label;

	private String userName;

	private long phoneNumber;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String state;

	private String country;

	private String pincode;

	private Object otherData;

	private String addressType;

}
