package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class PaymentRequestDto {
	
	private double amount;
	
	private String currency;
	
	public PaymentRequestDto(double amount, String currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}
	
	public PaymentRequestDto() {
		super();
	}


}
