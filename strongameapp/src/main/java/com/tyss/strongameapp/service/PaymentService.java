package com.tyss.strongameapp.service;

import com.razorpay.RazorpayException;
import com.tyss.strongameapp.dto.RazorpayResponse;

/**
 * PaymentService is implemented by PaymentServiceImple class, which is used to manage the payment.
 * @author Sushma Guttal
 *
 */
public interface PaymentService {

	/**
	 * This method is implemented by its implementation class, which is used to get the order id.
	 * @param amount
	 * @param currency
	 * @return String
	 * @throws RazorpayException 
	 */
	String getOrderId(double amount, String currency) throws RazorpayException;

	
	/**
	 * This method is implemented by its implementation class, which is used to verify the signature.
	 * @param data
	 * @return boolean
	 */
	boolean verifySignature(RazorpayResponse data);

}//End of PaymentService interface.
