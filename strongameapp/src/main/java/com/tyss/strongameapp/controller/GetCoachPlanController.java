package com.tyss.strongameapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.strongameapp.dto.PlanInformationDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.service.GetCoachPlanService;
import com.tyss.strongameapp.util.S3UploadFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal Get Coach plan controller is used to get plan list of
 *         coach.
 *
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
public class GetCoachPlanController {

	/**
	 * This field is to invoke business methods.
	 */
	@Autowired
	private GetCoachPlanService coachPlanService;

	/**
	 * This method is to get plan list of coach.
	 * 
	 * @param coachId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("getplan/{coachId}")
	public ResponseEntity<ResponseDto> getCoachPlan(@PathVariable int coachId) {
		List<PlanInformationDto> planListdto = coachPlanService.getCoachPlan(coachId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (planListdto.isEmpty()) {
			log.error("No Plan Available for Particular Coach");
			responseDTO.setError(true);
			responseDTO.setData("No Plans Available for Particular Coach");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Plan are Fetched Successfully for Specified Coach");
			responseDTO.setError(false);
			responseDTO.setData(planListdto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of method get coach plan

	@Autowired(required = true)
	private S3UploadFile s3UploadFile;

	@PostMapping("/send")
	public String image(@RequestParam MultipartFile file) {
		s3UploadFile.uploadFile(file);
		return file.getContentType();
	}

}// End of class Get coach plan controller class
