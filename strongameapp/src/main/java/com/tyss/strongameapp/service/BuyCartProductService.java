package com.tyss.strongameapp.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.dto.PlaceOrderDto;

/**
 * BuyCartProductService is implemented by BuyCartProductServiceImple class to
 * buy products.
 * 
 * @author Afridi
 *
 */
public interface BuyCartProductService {

	/**
	 * 
	 * @param orderDto
	 * @return placeCartOrder
	 * @throws FirebaseMessagingException
	 */
	String placeAnOrder(PlaceOrderDto placeOrderDto) throws FirebaseMessagingException;
}
