package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.tyss.strongameapp.dto.PaymentRequestDto;
import com.tyss.strongameapp.dto.RazorpayResponse;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal Payment controller class is to control payment
 *         activities.
 *
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
public class PaymentController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private PaymentService paymentService;

	/**
	 * This method is used to generate order id.
	 * 
	 * @param paymentDto
	 * @return ResponseEntity<ResponseDto>
	 * @throws RazorpayException 
	 */
	@PostMapping("/getorderid")
	public ResponseEntity<ResponseDto> getOrderId(@RequestBody PaymentRequestDto paymentDto) throws RazorpayException {

		String id = paymentService.getOrderId(paymentDto.getAmount(), paymentDto.getCurrency());
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (id == null) {
			log.error("Failed to Generate Order Id");
			responseDTO.setError(true);
			responseDTO.setData("Failed to Generate Order Id");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Order Id Generated Successfully");
			responseDTO.setError(false);
			responseDTO.setData(id);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get order id method

	/**
	 * This method is used to verify the signature.
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto>
	 */
	@PostMapping("/verifysignature")
	public ResponseEntity<ResponseDto> verifySignature(@RequestBody RazorpayResponse data) {

		boolean flag = paymentService.verifySignature(data);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.debug("Payment Unsuccessfull");
			responseDTO.setError(true);
			responseDTO.setData("Payment Unsuccessfull");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.error("Payment Successfull");
			responseDTO.setError(false);
			responseDTO.setData("Payment Successfull");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}// End of verify signature method

}// End of Payment Controller class.
