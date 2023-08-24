package com.tyss.strongameapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sushma Guttal Buy Product Controller is for placing the order.
 */

@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
public class BuyProductController {
	/**
	 * This field is to invoke business layer methods
	 */
	/*
	 * @Autowired private ProductInformationRepository productRepo;
	 * 
	 *//**
		 * This field is to invoke business layer methods
		 */
	/*
	 * @Autowired private UserInformationRepository userRepo;
	 * 
	 *//**
		 * This field is to invoke business layer methods
		 */
	/*
	 * @Autowired private BuyProductService buyProductService;
	 * 
	 *//**
		 * This method is to place the order.
		 * 
		 * @param orderDto
		 * @return ResponseEntity<ResponseDto> object
		 *//*
			 * @PutMapping("/placeorder") public ResponseEntity<ResponseDto>
			 * placeOrder(@RequestBody OrderPlaceDto orderDto) { int productId =
			 * orderDto.getProductId(); OrderInformationDto orderDto2 = orderDto.getOrder();
			 * Optional<ProductInformation> productEntity = productRepo.findById(productId);
			 * Optional<UserInformation> user = userRepo.findById(orderDto.getUserId());
			 * String response = buyProductService.placeOrder(orderDto2, productEntity,
			 * user); ResponseDto responseDTO = new ResponseDto(); if
			 * (response.startsWith("Order placed successfully")) { log.error(response);
			 * responseDTO.setError(false); responseDTO.setData(response); return new
			 * ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK); } else { // create
			 * and return ResponseEntity object log.debug(response);
			 * responseDTO.setError(true); responseDTO.setData(response); return new
			 * ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND); } }// End of
			 * method place order
			 * 
			 * @PutMapping("/placecartorder") public ResponseEntity<ResponseDto>
			 * placeCartOrder(@RequestBody OrderCartPlaceDto orderDto) throws
			 * FirebaseMessagingException { Optional<UserInformation> user =
			 * userRepo.findById(orderDto.getUserId()); String response =
			 * buyProductService.placeCartOrder(orderDto.getOrder(), user); ResponseDto
			 * responseDTO = new ResponseDto(); if
			 * (response.startsWith("Order placed successfully")) { log.debug(response);
			 * responseDTO.setError(false); responseDTO.setData(response); return new
			 * ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK); } else { // create
			 * and return ResponseEntity object log.debug(response);
			 * responseDTO.setError(true); responseDTO.setData(response); return new
			 * ResponseEntity<ResponseDto>(responseDTO, HttpStatus.NOT_FOUND); } }// End of
			 * method place order
			 */
}// End of the class Buy Product Controller class
