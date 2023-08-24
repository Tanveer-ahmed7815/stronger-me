package com.tyss.strongameapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PlaceOrderDto {

	private int userId;

	private int addressId;

	private Date orderDate;

	public PlaceOrderDto() {
		super();
	}

}
