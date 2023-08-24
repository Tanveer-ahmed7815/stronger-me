package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.dto.PlanInformationDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.SingleCoachDetailsDto;
import com.tyss.strongameapp.service.GetSingleCoachService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal Get Single coach controller is used to fetch the
 *         details of a coach.
 *
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
@RequestMapping("/singlecoach")
public class GetSingleCoachController {

	/**
	 * This field is to invoke the business layer methods
	 */
	@Autowired
	private GetSingleCoachService getSingleCoachService;

	/**
	 * This method is used to fetch the details of single coach.
	 * 
	 * @param coachId
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getcoach/{coachId}/{userId}")
	public ResponseEntity<ResponseDto> getCoachById(@PathVariable int coachId, @PathVariable int userId) {
		SingleCoachDetailsDto dto = getSingleCoachService.getCoachById(coachId, userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object	
			log.debug("" + responseDTO);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}// End of get coach by id method

	/**
	 * This method allows the user to enroll plan.
	 * 
	 * @param userId
	 * @param plan
	 * @return ResponseEntity<ResponseDto>
	 * @throws FirebaseMessagingException
	 */
	@PutMapping(value = "enrollplan/{userId}/{coachId}")
	public ResponseEntity<ResponseDto> enrollPlan(@PathVariable int userId, @PathVariable int coachId,
			@RequestBody PlanInformationDto plan) throws FirebaseMessagingException {
		String response = getSingleCoachService.enrollPlan(userId, coachId, plan);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (response.equalsIgnoreCase(" Plan Enrolled Successfully")) {
			log.debug(response);
			responseDTO.setError(false);
			responseDTO.setData(response);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			log.error(response);
			responseDTO.setError(true);
			responseDTO.setData(response);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}
	}// End of enroll plan method

	/**
	 * This method is used to get plan details by plan id.
	 * 
	 * @param planId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping(value = "getplanbyid/{planId}")
	public ResponseEntity<ResponseDto> getPlanById(@PathVariable int planId) {
		PlanInformationDto planDto = getSingleCoachService.getPlanById(planId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (planDto == null) {
			log.error("Plan Not Found.");
			responseDTO.setError(true);
			responseDTO.setData("Plan Not Found.");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);

		} else {
			log.debug("Plan is fetched");
			responseDTO.setError(false);
			responseDTO.setData(planDto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

}// End of Get single coach controller class
