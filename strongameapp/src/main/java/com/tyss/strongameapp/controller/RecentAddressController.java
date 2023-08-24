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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.constants.AddressConstants;
import com.tyss.strongameapp.constants.UserConstants;
import com.tyss.strongameapp.dto.RecentAddressDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.entity.RecentSearchedAddress;
import com.tyss.strongameapp.service.RecentAddressService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a controller class for RecentAddress Page . Here you find POST,GET,
 * DELETE APIs' for the RecentAddress Page. Here you can find the URL path for
 * all the RecentAddress Page services.
 * 
 * @author Tanveer
 * 
 */
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@Slf4j
@RestController
@RequestMapping("/recentAddress")
public class RecentAddressController {

	/**
	 * This field is used to invoke business layer methods
	 */
	@Autowired
	private RecentAddressService recentAddressService;

	/**
	 * This method is to add/save the User recent addresses
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@ApiOperation(value = "addRecentAddress", notes = "This API is to add/save the User's Recent addresses")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Recent Searched Address Saved Successfully "),
			@ApiResponse(code = 404, message = UserConstants.USER_NOT_FOUND),
			@ApiResponse(code = 400, message = "No Data Found")

	})
	@PostMapping("/{userId}")
	public ResponseEntity<ResponseDto> addRecentAddress(@RequestBody RecentAddressDto recentAddressDto,
			@PathVariable int userId) {
		log.info("Request " + recentAddressDto + userId);
		RecentAddressDto addRecentAddressDto = recentAddressService.addRecentAddress(recentAddressDto, userId);
		ResponseDto responseDto = new ResponseDto();
		if (addRecentAddressDto != null) {
			responseDto.setError(false);
			responseDto.setData(addRecentAddressDto);
			responseDto.setMessage("Recent Searched Address Saved Successfully ");
			log.info(AddressConstants.RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
		} else {
			responseDto.setError(true);
			responseDto.setMessage("No Data Found");
			log.error(AddressConstants.RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * This method is to get all User's recent addresses
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@ApiOperation(value = "getRecentAddress", notes = "This API is to fetch the User's Recent addresses")
	@ApiResponses(value = { @ApiResponse(code = 404, message = UserConstants.USER_NOT_FOUND),
			@ApiResponse(code = 400, message = "No Data Found"),
			@ApiResponse(code = 200, message = "Address Fetched Successfully") })
	@GetMapping("/{userId}")
	public ResponseEntity<ResponseDto> getRecentAddress(@PathVariable int userId) {

		List<RecentSearchedAddress> recentAddress = recentAddressService.getRecentAddress(userId);
		ResponseDto responseDto = new ResponseDto();
		if (recentAddress.isEmpty()) {
			responseDto.setError(true);
			responseDto.setMessage(AddressConstants.NO_DATA);
			log.error(AddressConstants.RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		} else {
			responseDto.setData(recentAddress);
			responseDto.setError(false);
			responseDto.setMessage("Address Fetched Successfully");
			log.info(AddressConstants.RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}

	}

	/**
	 * This method is to delete User Address
	 * 
	 * @param userId
	 * @param recentAddressId
	 * @return ResponseEntity<ResponseDto> object
	 */
	@ApiOperation(value = "getRecentAddress", notes = "This API is to delete the User's Recent addresses")
	@ApiResponses(value = { @ApiResponse(code = 404, message = UserConstants.USER_NOT_FOUND),
			@ApiResponse(code = 400, message = "User Address Details Does Not Exist"),
			@ApiResponse(code = 200, message = "Recent Address Deleted Successfully") })

	@DeleteMapping("/{userId}/{recentAddressId}")
	public ResponseEntity<ResponseDto> deleteRecentAddress(@PathVariable int userId,
			@PathVariable int recentAddressId) {
		Boolean flag = recentAddressService.deleteRecentAddress(userId, recentAddressId);
		ResponseDto responseDto = new ResponseDto();
		if (Boolean.FALSE.equals(flag)) {
			log.error("User Address Details Does Not Exist");
			responseDto.setError(true);
			responseDto.setMessage(AddressConstants.NO_DATA);
			log.info(AddressConstants.RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		} else {
			responseDto.setError(false);
			responseDto.setData("Recent Address Deleted Successfully");
			log.info(AddressConstants.RESPONSE_DTO + responseDto);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}

	}

}
