package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.UserStepsStatsDto;
import com.tyss.strongameapp.service.StepsDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal
 * Steps data controller is used to manage the steps data page.
 *
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app","https://strongermeuser.herokuapp.com"})
@RestController
public class StepsDataController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private StepsDataService stepsService;


	/**
	 * This method is used to save the steps data.
	 * @param data
	 * @return ResponseEntity<ResponseDto>
	 */
	@PutMapping("/updatesteps")
	public ResponseEntity<ResponseDto> saveSteps(@RequestBody UserStepsStatsDto data){
		UserStepsStatsDto dto = new UserStepsStatsDto();
		if(data.getCurrentSteps()!=0) {
			dto=stepsService.saveSteps(data);
		}
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("Steps information should not be null");
			responseDTO.setError(true);
			responseDTO.setData("Steps information should not be null");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Steps are updated successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

		}
	}//End of save steps method

}//End of Steps Data Controller class.
