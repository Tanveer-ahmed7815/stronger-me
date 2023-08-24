package com.tyss.strongameapp.service;

import com.tyss.strongameapp.dto.CartDto;
import com.tyss.strongameapp.dto.CartProductDto;
import com.tyss.strongameapp.dto.UpdateCartProductDto;

/**
 * This is the service interface for Cart . Here you find abstract methods for
 * saving, updating , fetching and deleting the cart product
 * 
 * @author Afridi
 * 
 */
public interface CartService {

	/**
	 * This method is implemented by its implementation class to view Cart products
	 * 
	 * @param userId
	 * @return CartDto
	 */
	CartDto viewCart(int userId);

	/**
	 * This method is implemented by its implementation class to update Cart product
	 * 
	 * @param updateCartProductDto
	 * @param userId
	 * @return UpdateCartProductDto
	 */
	UpdateCartProductDto updateCart(UpdateCartProductDto updateCartProductDto, int userId);

	/**
	 * This method is implemented by its implementation class to add Cart product
	 * 
	 * @param dto
	 * @param userId
	 * @return ProductInformationDto
	 */
	String addToCart(CartProductDto dto, int userId,int productId);

	/**
	 * This method is implemented by its implementation class to delete Cart product
	 * 
	 * @param cartProductId
	 * @param userId
	 * @return boolean
	 */
	boolean deleteCartProuduct(int userId, int cartProductId);

}
