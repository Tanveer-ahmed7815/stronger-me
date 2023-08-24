package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.dto.PlaceOrderDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.service.BuyCartProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Buy Cart Product Controller is for placing the order.
 * 
 * @author Afridi
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
public class BuyCartProductController {

	/**
	 * This field is to invoke business layer methods
	 */
	@Autowired
	private BuyCartProductService buyCartProductService;

	/**
	 * 
	 * @param orderDto
	 * @return ResponseEntity
	 * @throws FirebaseMessagingException
	 */
	@ApiOperation("This api is to place cart orders")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Order Placed Succussfull") })
	@PutMapping("/placeanorder")
	public ResponseEntity<ResponseDto> placeCartOrderTwo(@RequestBody PlaceOrderDto placeOrderDto)
			throws FirebaseMessagingException {
		String response = buyCartProductService.placeAnOrder(placeOrderDto);
		ResponseDto responseDTO = new ResponseDto();
		log.debug(response);
		responseDTO.setError(false);
		responseDTO.setData(response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}// End of method place order
}
