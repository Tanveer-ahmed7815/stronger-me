package com.tyss.strongameapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentDetailsDto {

	private int paymentId;

	private String paymentDetail;

	private String paymentMode;

	private Date paymentDate;

	private double amount;

	
}
