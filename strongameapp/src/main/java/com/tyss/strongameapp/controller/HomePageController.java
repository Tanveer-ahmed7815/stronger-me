package com.tyss.strongameapp.controller;

import static com.tyss.strongameapp.constants.HomePageConstants.COACHES_FETCHED;
import static com.tyss.strongameapp.constants.HomePageConstants.COACH_NOT_FOUND;
import static com.tyss.strongameapp.constants.HomePageConstants.GET_COACH_FILTERS_API;
import static com.tyss.strongameapp.constants.HomePageConstants.GET_COACH_FILTERS_FAILURE;
import static com.tyss.strongameapp.constants.HomePageConstants.GET_COACH_FILTERS_SUCCESS;
import static com.tyss.strongameapp.constants.HomePageConstants.GET_FILTERED_COACH_API;
import static com.tyss.strongameapp.constants.HomePageConstants.SEARCH_COACH_BY_NAME_API;
import static com.tyss.strongameapp.constants.UserConstants.USER_NOT_FOUND;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tyss.strongameapp.dto.CoachDetailsDto;
import com.tyss.strongameapp.dto.CoachFilterDTO;
import com.tyss.strongameapp.dto.DietPlanDetailsDto;
import com.tyss.strongameapp.dto.DietRecipeLikeDto;
import com.tyss.strongameapp.dto.HomeDisplayDto;
import com.tyss.strongameapp.dto.ModuleContentDto;
import com.tyss.strongameapp.dto.ModuleDto;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.TransformationLikeDetailsDto;
import com.tyss.strongameapp.service.HomePageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal Home page controller is used to display the home page.
 *
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
@RequestMapping("/homepage")
public class HomePageController {

	/**
	 * This field is to invoke the business layer methods.
	 */
	@Autowired
	private HomePageService homePageService;

	/**
	 * This method is used for home page listing.
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/getall/{userId}")
	public ResponseEntity<ResponseDto> homePageDisplay(@PathVariable int userId) {
		HomeDisplayDto dto = homePageService.homePageDisplay(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (!dto.getCases().equalsIgnoreCase("")) {
			log.error(dto.getCases());
			responseDTO.setError(true);
			responseDTO.setData(dto.getCases());
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Home page is displaying");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of home page display method.

	/**
	 * This method allows the user to post like for diet recipe.
	 * 
	 * @param like
	 * @return ResponseEntity<ResponseDto>
	 */
	@PostMapping("/dietlike")
	public ResponseEntity<ResponseDto> addDietLike(@RequestBody DietRecipeLikeDto like) {
		DietRecipeLikeDto dietRecipeLike = homePageService.save(like);
		ResponseDto responseDTO = new ResponseDto();
		if (dietRecipeLike == null) {
			log.error("No Likes Available for Specified Diet Recipe");
			responseDTO.setError(true);
			responseDTO.setData("No Likes Available for Specified Diet Recipe");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("User have posted like to the diet recipe");
			responseDTO.setError(false);
			responseDTO.setData(dietRecipeLike);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of add diet like method

	/**
	 * This method allows the user to post like for transformation.
	 * 
	 * @param like
	 * @return ResponseEntity<ResponseDto>
	 */
	@PostMapping("/translike")
	public ResponseEntity<ResponseDto> addTransformationLike(@RequestBody TransformationLikeDetailsDto like) {
		TransformationLikeDetailsDto transformationLike = homePageService.save(like);
		ResponseDto responseDTO = new ResponseDto();
		if (transformationLike == null) {
			log.error("No Likes Available for Specified Transformation");
			responseDTO.setError(true);
			responseDTO.setData("No Likes Available for Specified Transformation");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("User have posted like to the transformation");
			responseDTO.setError(false);
			responseDTO.setData(transformationLike);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of add transformation like method

	/**
	 * This method is used to fetch all diet recipes in descending order by name.
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterdietbynamedesc/{userId}")
	public ResponseEntity<ResponseDto> filterDietByNameDesc(@PathVariable int userId) {
		List<DietPlanDetailsDto> dto = homePageService.filterDietByNameDesc(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(USER_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Diet Recipe is Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of filter diet by name desc method

	/**
	 * This method is used to fetch all diet recipes in ascending order by name.
	 * 
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@GetMapping("/filterdietbyname/{userId}")
	public ResponseEntity<ResponseDto> filterDietByName(@PathVariable int userId) {
		List<DietPlanDetailsDto> dto = homePageService.filterDietByName(userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(USER_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Diet Recipe is Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of filter diet by name method

	@GetMapping("/searchdiet/{userId}/{keyword}")
	public ResponseEntity<ResponseDto> searchDiet(@PathVariable int userId, @PathVariable String keyword) {
		List<DietPlanDetailsDto> dto = homePageService.searchDiet(userId, keyword);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			responseDTO.setError(true);
			responseDTO.setData("No Diet Recipe Found");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	@GetMapping("/getdietbyid/{userId}/{dietId}")
	public ResponseEntity<ResponseDto> getDietById(@PathVariable int userId, @PathVariable int dietId) {
		DietPlanDetailsDto dto = homePageService.getDietById(userId, dietId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			responseDTO.setError(true);
			responseDTO.setData("Diet Recipe Not Found");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is to get all modules including top ten trends content
	 * 
	 * @return ResponseEntity<ResponseDto>
	 * @throws JsonProcessingException
	 */
	@ApiOperation("This api fetch all modules including top ten trends content")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of Modules Fetched Successfully"),
			@ApiResponse(code = 404, message = "No Modules Exist") })
	@GetMapping("/getallmodules/{userId}")
	public ResponseEntity<ResponseDto> getAllModule(@PathVariable int userId) throws JsonProcessingException {
		ModuleDto dto = homePageService.getAllModule(userId);
		ResponseDto responseDTO = new ResponseDto();
		if (dto.getModules().isEmpty()) {
			log.error("No Modules Exist");
			responseDTO.setError(true);
			responseDTO.setData("No Modules Type Exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("List of Modules Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is to get content by moduleId
	 * 
	 * @param moduleId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api fetch all content by moduleId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of Content Fetched Successfully"),
			@ApiResponse(code = 404, message = "No Module Type Exist") })
	@GetMapping("/getcontentsbymoduleid/{moduleId}")
	public ResponseEntity<ResponseDto> getContentByModuleId(@PathVariable int moduleId) throws JsonProcessingException {
		List<ModuleContentDto> list = homePageService.getContentByModuleId(moduleId);
		ResponseDto responseDTO = new ResponseDto();
		if (list.isEmpty()) {
			log.error("No Module Available Exist");
			responseDTO.setError(true);
			responseDTO.setData("No Module Type Exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("List of Content Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(list);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is to get content details by contentId
	 * 
	 * @param contentId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api fetch content by contentId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of Content Fetched Successfully"),
			@ApiResponse(code = 404, message = "No Module Available Exist") })
	@GetMapping("/getcontentbyid/{contentId}/{userId}")
	public ResponseEntity<ResponseDto> getContentByContentId(@PathVariable int contentId, @PathVariable int userId) {
		ModuleContentDto moduleContentDto = homePageService.getContentByContentId(contentId, userId);
		ResponseDto responseDTO = new ResponseDto();
		if (moduleContentDto == null) {
			log.error("No Module Available Exist");
			responseDTO.setError(true);
			responseDTO.setData("No Module Type Exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("List of Content Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(moduleContentDto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is to redirect to content by homeBannerId
	 * 
	 * @param homebannerId
	 * @return ResponseEntity<ResponseDto>
	 * @throws JsonProcessingException
	 */
	@ApiOperation("This api is to redirect to content by homeBannerId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Content Fetched Successfully"),
			@ApiResponse(code = 404, message = "No Content Type Exist") })
	@GetMapping("/contentdetails/{homebannerId}")
	public ResponseEntity<ResponseDto> redirectContentByBannerId(@PathVariable int homebannerId)
			throws JsonProcessingException {
		ModuleContentDto dto = homePageService.redirectContentByBannerId(homebannerId);
		ResponseDto responseDTO = new ResponseDto();
		if (dto == null) {
			log.error("No Content Available Exist");
			responseDTO.setError(true);
			responseDTO.setData("No Content Type Exist");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Content Fetched Successfully");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

	/**
	 * This method is to store content stream by users
	 * 
	 * @param userId
	 * @param contentId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation("This api is to store content stream by users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Saved Stream Time"),
			@ApiResponse(code = 404, message = "No Module/Content/Specialization Found") })
	@PutMapping("/savestreamtime/{userId}/{contentId}")
	public ResponseEntity<ResponseDto> saveStreamtime(@PathVariable int userId, @PathVariable int contentId) {
		homePageService.saveStreamtime(userId, contentId);
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(false);
		responseDTO.setMessage("Saved Stream Time");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * This method is used to filter coaches by badge, specialization and language
	 * 
	 * @param coachFilterDTO
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = GET_FILTERED_COACH_API)
	@ApiResponses(value = { @ApiResponse(code = 404, message = COACH_NOT_FOUND),
			@ApiResponse(code = 200, message = COACHES_FETCHED) })
	@PostMapping("/filter/coach")
	public ResponseEntity<ResponseDto> filterCoaches(@RequestBody CoachFilterDTO coachFilterDTO) {
		List<CoachDetailsDto> dto = homePageService.getCoachList(coachFilterDTO);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(COACH_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(COACH_NOT_FOUND);
			responseDTO.setMessage(COACH_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(COACHES_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(COACHES_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of filter coaches by badge, specialization and language method

	/**
	 * This method is used to fetch badges, specializations and languages for
	 * filtering the coach
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = GET_COACH_FILTERS_API)
	@ApiResponses(value = { @ApiResponse(code = 404, message = GET_COACH_FILTERS_FAILURE),
			@ApiResponse(code = 200, message = GET_COACH_FILTERS_SUCCESS) })
	@GetMapping("/coach/filter/type")
	public ResponseEntity<ResponseDto> getCoachFilters() {
		CoachFilterDTO dto = homePageService.getCoachFilters();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error(GET_COACH_FILTERS_FAILURE);
			responseDTO.setError(true);
			responseDTO.setData(GET_COACH_FILTERS_FAILURE);
			responseDTO.setMessage(GET_COACH_FILTERS_FAILURE);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(GET_COACH_FILTERS_SUCCESS);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(GET_COACH_FILTERS_SUCCESS);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getCoachfilters method

	/**
	 * This method is used to search coaches by coach name
	 * 
	 * @param coachName
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = SEARCH_COACH_BY_NAME_API)
	@ApiResponses(value = { @ApiResponse(code = 404, message = COACH_NOT_FOUND),
			@ApiResponse(code = 200, message = COACHES_FETCHED) })
	@GetMapping("/search/coach/{coachName}")
	public ResponseEntity<ResponseDto> searchCoachByName(@PathVariable String coachName) {
		List<CoachDetailsDto> dto = homePageService.searchCoachByName(coachName);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(COACH_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(COACH_NOT_FOUND);
			responseDTO.setMessage(COACH_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(COACHES_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(COACHES_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of searchCoachByName method

}// End of Home page controller class
