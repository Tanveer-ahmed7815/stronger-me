package com.tyss.strongameapp.controller;

import java.util.List;

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

import static com.tyss.strongameapp.constants.AddressConstants.*;

import com.tyss.strongameapp.constants.UserConstants;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.SavedAddressDTO;
import com.tyss.strongameapp.entity.SavedAddress;
import com.tyss.strongameapp.service.SavedAddressService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for SavedAddress Page . Here you find POST,GET,PUT
 * and DELETE APIs' for the SavedAddress Page. Here you can find the URL path
 * for all the SavedAddress Page services.
 * 
 * @author Tanveer
 * 
 */

@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@Slf4j
@RestController
@RequestMapping("/savedAddress")
public class SavedAddressController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private SavedAddressService savedAddressService;

	/**
	 * This method is to add/save the User saved addresses
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */

	@ApiOperation(value = "addsavedaddresses", notes = "This API is to add/save the User's saved addresses")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Address Saved Successfully "),
			@ApiResponse(code = 404, message = UserConstants.USER_NOT_FOUND),
			@ApiResponse(code = 400, message = "Address Label Type is Repeated") })
	@PostMapping("/{userId}")
	public ResponseEntity<ResponseDto> addSaveAddress(@RequestBody SavedAddressDTO savedAddressDTO,
			@PathVariable int userId) {
		log.info("Request " + savedAddressDTO + userId);
		SavedAddressDTO addressDTO = savedAddressService.addSaveAddress(savedAddressDTO, userId);
		ResponseDto responseDto = new ResponseDto();
		if (addressDTO != null) {
			responseDto.setError(false);
			responseDto.setData(addressDTO);
			responseDto.setMessage("Address Saved Successfully ");
			log.info(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
		} else {
			responseDto.setError(true);
			responseDto.setMessage("No Data Found");
			log.error(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This method is to get all User's saved addresses
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */

	@ApiOperation(value = "getSavedAddress", notes = " This API is to get User's all saved addresses")
	@ApiResponses(value = { @ApiResponse(code = 404, message = UserConstants.USER_NOT_FOUND),
			@ApiResponse(code = 400, message = "No Data Found"),
			@ApiResponse(code = 200, message = "Address Fetched Successfully") })
	@GetMapping("/{userId}")
	public ResponseEntity<ResponseDto> getSavedAddress(@PathVariable int userId) {
		List<SavedAddress> savedAddress = savedAddressService.getSavedAddress(userId);
		ResponseDto responseDto = new ResponseDto();
		if (!savedAddress.isEmpty()) {
			responseDto.setError(false);
			responseDto.setData(savedAddress);
			responseDto.setMessage("Address Fetched Successfully");
			log.info(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		} else {
			responseDto.setError(true);
			responseDto.setMessage(NO_DATA);
			log.error(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This method is to get Default address of the user. This API response is set
	 * to 200 status for both the scenarios whether the Default address is found or
	 * not
	 * 
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */

	@ApiResponses(value = { @ApiResponse(code = 200, message = "No Default Address Found/Object") })
	@GetMapping("/default-address/{userId}")
	public ResponseEntity<ResponseDto> getDefaultAddress(@PathVariable int userId) {
		ResponseDto resp = savedAddressService.getDefaultAddress(userId);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/**
	 * This method is to update User's saved addresses
	 * 
	 * @param userId
	 * @param savedAddressId
	 * @return ResponseEntity<ResponseDto> object
	 */

	@ApiOperation(value = "updateSavedAddress", notes = "This API is to update User's saved addresses")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Address Updated Successfully"),
			@ApiResponse(code = 400, message = "User Address Details Does Not Exist"),
			@ApiResponse(code = 404, message = UserConstants.USER_NOT_FOUND + " /Address Label Type is Repeated") })
	@PutMapping("/{userId}/{savedAddressId}")
	public ResponseEntity<ResponseDto> updateSavedAddress(@PathVariable int userId, @PathVariable int savedAddressId,
			@RequestBody SavedAddressDTO savedAddressDTO) {

		String resp = savedAddressService.updateSavedAddress(userId, savedAddressId, savedAddressDTO);
		ResponseDto responseDto = new ResponseDto();
		if (resp.equalsIgnoreCase("Address Updated Successfully")) {
			responseDto.setError(false);
			responseDto.setData(resp);
			log.info(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		} else {
			responseDto.setError(true);
			responseDto.setMessage(NO_DATA);
			log.error(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * This method is to delete User's saved addresses
	 * 
	 * @param userId
	 * @param savedAddressId
	 * @return ResponseEntity<ResponseDto> object
	 */

	@ApiOperation(value = "deleteSavedAddress", notes = "This API is to delete User's saved addresses")
	@ApiResponses(value = { @ApiResponse(code = 404, message = UserConstants.USER_NOT_FOUND),
			@ApiResponse(code = 400, message = "User Address Details Does Not Exist"),
			@ApiResponse(code = 200, message = "Address Deleted Successfully") })
	@DeleteMapping("/{userId}/{savedAddressId}")
	public ResponseEntity<ResponseDto> deleteSavedAddress(@PathVariable int userId, @PathVariable int savedAddressId) {

		Boolean resp = savedAddressService.deleteSavedAddress(userId, savedAddressId);
		ResponseDto responseDto = new ResponseDto();
		if (Boolean.FALSE.equals(resp)) {
			responseDto.setError(true);
			responseDto.setData(resp);
			responseDto.setMessage("User Address Details Does Not Exist");
			log.error(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		} else {
			responseDto.setError(false);
			responseDto.setData(resp);
			responseDto.setMessage("Address Deleted Successfully");
			log.info(RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}
	}

}
