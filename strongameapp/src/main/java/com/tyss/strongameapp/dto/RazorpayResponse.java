package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class RazorpayResponse {
	
	private String razorpay_payment_id;
	
	private String razorpay_order_id;
	
	private String razorpay_signature;
	
	private String order_id;

	public RazorpayResponse(String razorpay_payment_id, String razorpay_order_id, String razorpay_signature,
			String order_id) {
		super();
		this.razorpay_payment_id = razorpay_payment_id;
		this.razorpay_order_id = razorpay_order_id;
		this.razorpay_signature = razorpay_signature;
		this.order_id = order_id;
	}

	public RazorpayResponse() {
		super();
	}
	
	

}

