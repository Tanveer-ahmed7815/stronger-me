package com.tyss.strongameapp.controller;

import static com.tyss.strongameapp.constants.CartConstants.EMPTY_CART;
import static com.tyss.strongameapp.constants.CartConstants.INVALID_INPUT;
import static com.tyss.strongameapp.constants.CartConstants.SUCCESSFULLY_ADD_TO_CART;
import static com.tyss.strongameapp.constants.CartConstants.SUCCESSFULLY_REMOVED_FROM_CART;
import static com.tyss.strongameapp.constants.CartConstants.SUCCESSFULLY_UPDATED_CART;
import static com.tyss.strongameapp.constants.CartConstants.UNABLE_TO_REMOVED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.CartDto;
import com.tyss.strongameapp.dto.CartProductDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.UpdateCartProductDto;
import com.tyss.strongameapp.service.CartService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for Cart . Here you find GET, POST, PUT, DELETE
 * controllers for the CartProduct. Here you can find the URL path for all the
 * Cart services.
 * 
 * @author Afridi
 * 
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
@RequestMapping("/mycart")
public class CartController {

	/**
	 * This field is to invoke business layer methods
	 */
	@Autowired
	private CartService cartService;

	/**
	 * This method is to save CartProduct
	 * 
	 * @param quantityDto
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@ApiOperation("This api is to save cartproduct details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESSFULLY_ADD_TO_CART),
			@ApiResponse(code = 400, message = "Enter valid size / Insufficient Coins!") })
	@PostMapping("addtocart/{userId}/{productId}")
	public ResponseEntity<ResponseDto> addToCart(@RequestBody CartProductDto quantityDto, @PathVariable int userId,
			@PathVariable int productId) {
		String message = cartService.addToCart(quantityDto, userId, productId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (message.equalsIgnoreCase("Successfully Added to Cart")
				|| message.equalsIgnoreCase("Product Variant Already Added, Used Coins Updated")) {
			log.error(message);
			responseDTO.setError(false);
			responseDTO.setData(message);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			log.debug(message);
			responseDTO.setError(true);
			responseDTO.setMessage(message);
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
	}// End of add product by id method

	/**
	 * This method is to update CartProduct
	 * 
	 * @param updateCartProductDto
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@ApiOperation("This api is to update cartproduct details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESSFULLY_UPDATED_CART),
			@ApiResponse(code = 400, message = INVALID_INPUT) })
	@PutMapping("updatecart/{userId}")
	public ResponseEntity<ResponseDto> updateCart(@RequestBody UpdateCartProductDto updateCartProductDto,
			@PathVariable int userId) {
		UpdateCartProductDto dto = cartService.updateCart(updateCartProductDto, userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData(INVALID_INPUT);
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug(SUCCESSFULLY_UPDATED_CART);
			responseDTO.setError(false);
			responseDTO.setData(SUCCESSFULLY_UPDATED_CART);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End of update cart by List of product method

	/**
	 * This method is to delete CartProduct
	 * 
	 * @param userId
	 * @param cartProductId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@ApiOperation("This api is to delete cartproduct details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESSFULLY_REMOVED_FROM_CART),
			@ApiResponse(code = 404, message = UNABLE_TO_REMOVED) })
	@DeleteMapping("removeproduct/{userId}/{cartProductId}")
	public ResponseEntity<ResponseDto> deleteCartProuduct(@PathVariable int userId, @PathVariable int cartProductId) {
		boolean flag = cartService.deleteCartProuduct(userId, cartProductId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (flag) {
			responseDTO.setError(true);
			responseDTO.setData(UNABLE_TO_REMOVED);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(SUCCESSFULLY_REMOVED_FROM_CART);
			responseDTO.setError(false);
			responseDTO.setData(SUCCESSFULLY_REMOVED_FROM_CART);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End of update cart method

	/**
	 * This method is to get CartProducts
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@ApiOperation("This api is to fetch all cartproducts details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = EMPTY_CART),
			@ApiResponse(code = 200, message = "successfully fetched Cart Details") })
	@GetMapping("viewcart/{userId}")
	public ResponseEntity<ResponseDto> viewCart(@PathVariable int userId) {
		CartDto cartdto = cartService.viewCart(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (cartdto == null) {
			log.debug(EMPTY_CART);
			responseDTO.setError(false);
			responseDTO.setData(EMPTY_CART);
		} else {
			log.debug("successfully fetched Cart Details");
			responseDTO.setError(false);
			responseDTO.setData(cartdto);
		}
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}// End of view cart products method

}
