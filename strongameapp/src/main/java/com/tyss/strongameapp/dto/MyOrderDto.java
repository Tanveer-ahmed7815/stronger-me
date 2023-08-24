package com.tyss.strongameapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MyOrderDto {

	private int myOrderId;

	private String userName;

	private long phoneNumber;

	private String name;

	private double price;

	private String type;

	private String image;

	private String orderStatus;

	private Date deliveryDate;

	private String size;

	private Integer quantity;

	private double usedCoins;

	private double paidProductPrice;

	private String address;

	private Date orderDate;



}
