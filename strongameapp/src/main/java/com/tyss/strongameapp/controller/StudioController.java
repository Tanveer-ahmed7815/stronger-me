package com.tyss.strongameapp.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.constants.LiveSessionConstants;
import com.tyss.strongameapp.dto.BookNowDto;
import com.tyss.strongameapp.dto.CoachForSessionDetailsDto;
import com.tyss.strongameapp.dto.LiveModuleContentDto;
import com.tyss.strongameapp.dto.PackageDetailsDto;
import com.tyss.strongameapp.dto.PackageSubscribeDto;
import com.tyss.strongameapp.dto.PackageTypeUserDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.SessionDetailsDto;
import com.tyss.strongameapp.dto.SpecializationModuleContentDto;
import com.tyss.strongameapp.dto.StudioBannerInformationDto;
import com.tyss.strongameapp.dto.StudioModuleDto;
import com.tyss.strongameapp.dto.TaglineDetailsDto;
import com.tyss.strongameapp.dto.TodaysLiveSessionDto;
import com.tyss.strongameapp.dto.WeeksLiveSessionsDTO;
import com.tyss.strongameapp.entity.RemaindSession;
import com.tyss.strongameapp.service.StudioService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * StudioController is used to display the content of live session page.
 * 
 * @author Sushma Guttal
 *
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
public class StudioController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private StudioService studioService;

	/**
	 * This method is used to fetch all the coaches.
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getallcoaches")
	public ResponseEntity<ResponseDto> getAllCoaches() {
		List<CoachForSessionDetailsDto> dto = studioService.getAllCoaches();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Coach Found");
			responseDTO.setError(true);
			responseDTO.setData("No Coach Found");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Coach is Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get all coaches method

	/**
	 * This method is used to fetch todays session list
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/gettodaysessions/{userId}")
	public ResponseEntity<ResponseDto> getTodaySessions(@PathVariable int userId) {
		TodaysLiveSessionDto dto = studioService.getTodaySessions(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.getSessionList() == null) {
			log.error("No Session is Found");
			responseDTO.setError(true);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else if (dto.getCases().equalsIgnoreCase("")) {
			log.debug("Sessions are Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setMessage(dto.getCases());
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}
	}// End of get all todays session method

	@GetMapping("/getsessionsbydates/{userId}/{sessiondate}")
	public ResponseEntity<ResponseDto> getSessionsDetails(@PathVariable int userId, @PathVariable Date sessiondate) {
		TodaysLiveSessionDto dto = studioService.getSessionsDetails(userId, sessiondate);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.getSessionList() == null) {
			log.error("No Session is Found");
			responseDTO.setError(true);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setMessage(dto.getCases());
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}
	}// End of get all todays session method

	/**
	 * This method is used to fetch list of packages.
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getpackagelist")
	public ResponseEntity<ResponseDto> getPackageList() {
		PackageTypeUserDto dto = studioService.getPackageList();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Package is Found");
			responseDTO.setError(true);
			responseDTO.setData("No Package is Found");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Package is Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get all todays session method

	/**
	 * This method is used to fetch package by id.
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getpackagebyid/{packageId}")
	public ResponseEntity<ResponseDto> getPackageById(@PathVariable int packageId) {
		List<PackageDetailsDto> dto = studioService.getPackageById(packageId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error("Package is Not Found");
			responseDTO.setError(true);
			responseDTO.setData("Package is Not Found");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Package is Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get all todays session method

	/**
	 * This method is used to enroll the package.
	 * 
	 * @param userId
	 * @param packageId
	 * @return ResponseEntity<ResponseDto>
	 * @throws FirebaseMessagingException
	 */
	@PutMapping("/booknow")
	public ResponseEntity<ResponseDto> bookPackage(@RequestBody BookNowDto bookNowDto)
			throws FirebaseMessagingException {
		PackageDetailsDto dto = studioService.bookPackage(bookNowDto);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (!dto.getCases().equalsIgnoreCase("")) {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setMessage(dto.getCases());
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Package is Booked");
			responseDTO.setError(false);
			responseDTO.setData("You Have Successfully Booked The Package");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of bookPackage class

	/**
	 * This method is used to perform join now operation
	 * 
	 * @param userId
	 * @param sessionId
	 * @return ResponseEntity<ResponseDto>
	 * @throws FirebaseMessagingException
	 */
	@PutMapping("/joinnow/{userId}/{sessionId}")
	public ResponseEntity<ResponseDto> joinSession(@PathVariable int userId, @PathVariable int sessionId)
			throws FirebaseMessagingException {
		SessionDetailsDto dto = studioService.joinSession(userId, sessionId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.getCases() == 1) {
			log.error("You Have Not Enrolled Any Package");
			responseDTO.setError(true);
			responseDTO.setData("You Have Not Enrolled Any Package");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else if (dto.getCases() == 2) {
			log.error("Your Package is Expired");
			responseDTO.setError(true);
			responseDTO.setData("Your Package is Expired");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else if (dto.getCases() == 3) {
			log.debug("You Have Already Booked the Session");
			responseDTO.setError(true);
			responseDTO.setData("You Have Already Booked the Session");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else if (dto.getCases() == 5) {
			log.error("No SLots Available");
			responseDTO.setError(true);
			responseDTO.setData("No Slots Available");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else if (dto.getCases() == 6) {
			log.error("Session Expired");
			responseDTO.setError(true);
			responseDTO.setData("Session Has Been Expired");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("You have Successfully Booked the Session");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage("You have Successfully Booked the Session");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		}

	}// End of get all todays session method

	/**
	 * This method is used to fetch tag line details.
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/tagline")
	public ResponseEntity<ResponseDto> getTaglineDetails() {
		TaglineDetailsDto dto = studioService.getTagLineDetails();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Tagline is Not Found");
			responseDTO.setError(true);
			responseDTO.setData("Tagline is Not Found");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Tagline is Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get all todays session method

	/**
	 * This method is to get studio banners
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to get all studio banners")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Advertisements and banners are fetched"),
			@ApiResponse(code = 404, message = "No advertisements and Studio banners available for you") })
	@GetMapping("/getstudiobanner")
	public ResponseEntity<ResponseDto> getStudioBanner() {
		List<StudioBannerInformationDto> dto = studioService.getStudioBanner();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Advertisements and Studio Banners Available for You");
			responseDTO.setError(true);
			responseDTO.setData("No Advertisements and Studio Banners Available for You");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Advertisements and Banners are Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getStudioBanners method

	/**
	 * This method is to get all LiveModule (name,format and id)
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to get live modules")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Live Module details Fetched successfully"),
			@ApiResponse(code = 404, message = "No Live Modules Available for You") })
	@GetMapping("/getallformates/{userId}")
	public ResponseEntity<ResponseDto> getallformates(@PathVariable int userId) {
		StudioModuleDto dto = studioService.getallformates(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.getLiveModules().isEmpty()) {
			responseDTO.setError(true);
			responseDTO.setData("No Live Modules Available for You");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getAllFormates method

	/**
	 * This method is used to get content by specializationId
	 * 
	 * @param specializationId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to get live content by specializationId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Content details Fetched successfully"),
			@ApiResponse(code = 404, message = "SpecializationContent Details Does Not Exist") })
	@GetMapping("/getcontentbyspecialization/{specializationId}")
	public ResponseEntity<ResponseDto> getcontentByspecialization(
			@PathVariable("specializationId") int specializationId) {
		List<LiveModuleContentDto> dto = studioService.getcontentByspecialization(specializationId);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("SpecializationContent Details Does Not Exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("SpecializationContent Details Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is to get specialization by id
	 * 
	 * @param specializationId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to get specialization by Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched Specialization"),
			@ApiResponse(code = 404, message = "No Specialization Available for You") })
	@GetMapping("/getspecializationbyid/{specializationId}")
	public ResponseEntity<ResponseDto> getSpecializationById(@PathVariable int specializationId) {
		SpecializationModuleContentDto dto = studioService.getSpecializationById(specializationId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Specialization Available for You");
			responseDTO.setError(true);
			responseDTO.setData("No Specialization Available for You");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Fetched Specialization");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is to fetch live content by id
	 * 
	 * @param liveContentdId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to get live content by Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Content are Fetched"),
			@ApiResponse(code = 404, message = "No Contain Available for You") })
	@GetMapping("/getlivecontentbyid/{liveContentdId}/{userId}")
	public ResponseEntity<ResponseDto> getliveContentById(@PathVariable int liveContentdId, @PathVariable int userId) {
		LiveModuleContentDto dto = studioService.getliveContentById(liveContentdId, userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Contain Available for You");
			responseDTO.setError(true);
			responseDTO.setData("No Content Available for You");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Content are Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is used to redirect to content using studioId
	 * 
	 * @param studioId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to redirect to content using studioId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Content Formate are Fetched"),
			@ApiResponse(code = 404, message = "No Content Formate Available for You") })
	@GetMapping("/redirecttocontent/{studioId}")
	public ResponseEntity<ResponseDto> redirectContent(@PathVariable int studioId) {
		LiveModuleContentDto dto = studioService.redirectContent(studioId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Content Formate Available for You");
			responseDTO.setError(true);
			responseDTO.setData("No Content Formate Available for You");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Content Formate are Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getAllFormates method

	/**
	 * This method is to check and get user's subscribe packages
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to check and get user's subscribe packages")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Subcribe Plan Successfully Fetched"),
			@ApiResponse(code = 404, message = "No Packages Subscribe") })
	@GetMapping("/subscribe/{userId}")
	public ResponseEntity<ResponseDto> isSubscribe(@PathVariable int userId) {
		PackageSubscribeDto dto = studioService.isSubscribe(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Packages Subscribe");
			responseDTO.setError(true);
			responseDTO.setData("No Packages Subscribe");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Subcribe Plan Successfully Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getStudioBanners method

	/**
	 * This method is to store user's remainder flag for session
	 * 
	 * @param userId
	 * @param sessionId
	 * @return ResponseEntity<ResponseDto>
	 * @throws FirebaseMessagingException
	 */
	@ApiOperation("This api is to store user's remainder flag for session")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Remaindme is On or Remaindme is Off"),
			@ApiResponse(code = 404, message = "Invalid - No Session availabe") })
	@GetMapping("/remaindme/{userId}/{sessionId}")
	public ResponseEntity<ResponseDto> remaindMe(@PathVariable int userId, @PathVariable int sessionId)
			throws FirebaseMessagingException {
		RemaindSession dto = studioService.remaindMe(userId, sessionId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("Invalid - No Session Availabe");
			responseDTO.setError(true);
			responseDTO.setMessage("Invalid - No Session Availabe");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else if (Boolean.TRUE.equals(dto.getRemainderFlag())) {
			log.debug("Remaindme is On");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage("Remaindme is On");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			log.debug("Remaindme is Off");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage("Remaindme is off");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getStudioBanners method

	/**
	 * This method is used to fetch Live Sessions for specified dates
	 * 
	 * @param dates
	 * @param userId
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	/**
	 * This method is used to fetch Live Sessions for specified dates
	 * 
	 * @param dates
	 * @param userId
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = LiveSessionConstants.GET_SESSIONS_API)
	@ApiResponses(value = { @ApiResponse(code = 200, message = LiveSessionConstants.NOT_SUBSCRIBED),
			@ApiResponse(code = 200, message = LiveSessionConstants.GET_SESSIONS_SUCCESS),
			@ApiResponse(code = 404, message = LiveSessionConstants.USER_NOT_FOUND) })
	@PostMapping("/get/week/sessions/{userId}")
	public ResponseEntity<ResponseDto> getWeekSessions(@RequestBody List<Date> dates, @PathVariable int userId) {
		WeeksLiveSessionsDTO dto = studioService.getWeekSessions(dates, userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		responseDTO.setError(false);
		responseDTO.setData(dto);
		if (!dto.isPackageFlag()) {
			log.error(LiveSessionConstants.NOT_SUBSCRIBED);
			responseDTO.setMessage(LiveSessionConstants.NOT_SUBSCRIBED);
		} else {
			log.debug(LiveSessionConstants.GET_SESSIONS_SUCCESS);
			responseDTO.setMessage(LiveSessionConstants.GET_SESSIONS_SUCCESS);
		}
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}// End of get all todays session method

	/**
	 * This method is to get Current Date
	 * 
	 * @return ResponseEntity<ResponseDto> object
	 */
	@GetMapping("/currenDate")
	public ResponseEntity<ResponseDto> getCurrentDate() {

		Date currentDate = studioService.getCurrentDate();
		log.info("Date: " + currentDate);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setError(false);
		responseDto.setData(currentDate);

		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	/**
	 * This method is to store stream studio content by users
	 * 
	 * @param userId
	 * @param liveContentId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to store stream studio content by users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Stream Time Saved"),
			@ApiResponse(code = 404, message = "No Module/Content/Specialization Found") })
	@PutMapping("/studio/contentstreamtime/{userId}/{liveContentId}")
	public ResponseEntity<ResponseDto> saveStreamtime(@PathVariable int userId, @PathVariable int liveContentId) {
		studioService.saveStudioStreamtime(userId, liveContentId);
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(false);
		responseDTO.setMessage("Saved Stream Time");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

}// End of LiveSessionController
