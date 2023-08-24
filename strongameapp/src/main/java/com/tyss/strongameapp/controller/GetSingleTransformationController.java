package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.SingleTransformationDto;
import com.tyss.strongameapp.service.GetSingleTransformationService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal Get single transformation controller is to fetch the
 *         details of a transformation.
 *
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
public class GetSingleTransformationController {
	/**
	 * This field is to invoke the business layer methods
	 */
	@Autowired
	private GetSingleTransformationService transformationService;

	/**
	 * This method is used to fetch the details of single transformation by its id.
	 * 
	 * @param transformationId
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("gettransformation/{transformationId}/{userId}")
	public ResponseEntity<ResponseDto> getTransformationById(@PathVariable int transformationId,
			@PathVariable int userId) {
		SingleTransformationDto transformationDto = transformationService.getTransformationById(transformationId,
				userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (!transformationDto.getCases().equalsIgnoreCase("")) {
			responseDTO.setError(true);
			log.error(transformationDto.getCases());
			responseDTO.setData(transformationDto.getCases());
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.error("transformation is fetched");
			responseDTO.setError(false);
			responseDTO.setData(transformationDto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get transformation by id method.

}// End of Get Single transformation class
