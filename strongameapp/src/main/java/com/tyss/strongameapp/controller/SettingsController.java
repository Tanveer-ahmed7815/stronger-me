package com.tyss.strongameapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.tyss.strongameapp.constants.UserConstants.*;
import com.tyss.strongameapp.dto.LeaderBoardPositionsDto;
import com.tyss.strongameapp.dto.MyOrderDto;
import com.tyss.strongameapp.dto.NotificationInformationDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.service.SettingsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal Settings controller is used to display settings page.
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
@RequestMapping("/settings")
public class SettingsController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private SettingsService settingService;

	/**
	 * This method allows the users to edit their profile.
	 * 
	 * @param data
	 * @return ResponseEntity<ResponseDto>
	 * @throws JsonProcessingException
	 */
	@ApiOperation("This api allows user to edit their profile details.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Information Updated Successfully"),
			@ApiResponse(code = 404, message = USER_NOT_FOUND) })
	@PutMapping("/edit")
	public ResponseEntity<ResponseDto> editUser(@RequestBody UserInformationDto data) {
		UserInformationDto response = settingService.editUser(data);
		ResponseDto responseDTO = new ResponseDto();
		if (response == null) {
			log.error("User Information Does Not Exist");
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			if (response.getCases().equalsIgnoreCase("")) {
				log.debug("User Information Updated Successfully");
				responseDTO.setError(false);
				responseDTO.setData("User Information Updated Successfully");
				return new ResponseEntity<>(responseDTO, HttpStatus.OK);
			} else {
				responseDTO.setError(true);
				responseDTO.setData(response.getCases());
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);

			}

		}
	}// End of edit user method

	/**
	 * This method is used to display the leader board page
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to fetch all leader board users include current user's position")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Leaderboard is Fetched Successfully"),
			@ApiResponse(code = 404, message = USER_NOT_FOUND) })
	@GetMapping("leaderboard/{userId}")
	public ResponseEntity<ResponseDto> leadBoard(@PathVariable int userId) {
		LeaderBoardPositionsDto dto = settingService.leadBoard(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Leaderboard is Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of leaderboard method

	/**
	 * This method is used to display the my order page.
	 * 
	 * @param user
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to fetch all orders specific to a particular user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Order is fetched successfully"),
			@ApiResponse(code = 404, message = USER_NOT_FOUND + "/ Your Order List is Empty") })
	@GetMapping("myorder/{userId}")
	public ResponseEntity<ResponseDto> myOrder(@PathVariable Integer userId) {
		List<MyOrderDto> dto = settingService.myOrder(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}
		if (dto.isEmpty()) {
			log.error("Your order list is empty");
			responseDTO.setError(true);
			responseDTO.setData("Your Order List is Empty");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Order is Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of myorder method

	/**
	 * This method is used to fetch all the notifications
	 * 
	 * @param user
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to fetch all notification which are specific to a particular user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notifications are Fetched Successfully"),
			@ApiResponse(code = 404, message = "User Not Found / No notifications") })
	@GetMapping("notification/{userId}")
	public ResponseEntity<ResponseDto> notification(@PathVariable Integer userId) {
		List<NotificationInformationDto> dto = settingService.notification(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error(USER_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}
		if (dto.isEmpty()) {
			log.error("No Notifications");
			responseDTO.setError(true);
			responseDTO.setData("No Notifications");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Notifications are Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of notification method

	/**
	 * This method is used to update profile image
	 * 
	 * @param data
	 * @param image
	 * @return ResponseEntity<ResponseDto>
	 * @throws JsonProcessingException
	 */
	@ApiOperation("This api is to update profile image")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Profile Updated Successfully"),
			@ApiResponse(code = 404, message = USER_NOT_FOUND) })
	@PutMapping("/upload/profile")
	public ResponseEntity<ResponseDto> uploadProfile(@RequestParam String data, @RequestParam MultipartFile image)
			throws JsonProcessingException {
		UserInformationDto userInformationDto = new ObjectMapper().readValue(data, UserInformationDto.class);
		UserInformationDto response = settingService.uploadImage(userInformationDto, image);
		ResponseDto responseDTO = new ResponseDto();
		if (response == null) {
			log.error(USER_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("User Profile Updated Successfully");
			responseDTO.setError(false);
			responseDTO.setData(response);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is used to generate referral code
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to generate referral code")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Generated ReferenceCode"),
			@ApiResponse(code = 404, message = USER_NOT_FOUND) })
	@GetMapping(path = "/referencecode/{userId}")
	public @ResponseBody ResponseEntity<ResponseDto> referenceCode(@PathVariable Integer userId) {
		String referenceCode = settingService.referenceCode(userId);
		ResponseDto responseDTO = new ResponseDto();
		if (referenceCode == null) {
			log.error(USER_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Successfully Generated ReferenceCode");
			responseDTO.setError(false);
			responseDTO.setData(referenceCode);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}

	}

}// End of Settings Controller class
