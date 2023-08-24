package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.constants.UserReviewConstants;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.UserCoachReviewDto;
import com.tyss.strongameapp.service.UserReviewService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@Slf4j
@RestController
@RequestMapping("/user-review")
public class UserReviewController {

	@Autowired
	private UserReviewService userReviewService;

	/**
	 * 
	 * @param userCoachReviewDto
	 * @param userId
	 * @param coachId
	 * @return
	 */

	@ApiOperation(value = "postReview", notes = "This API is to add/save the Coach-Review by User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Review Sent Successfully, Wait for Admin's Approval"),
			@ApiResponse(code = 400, message = "Review Not Submitted") })
	@PostMapping("/{userId}/{coachId}")
	public ResponseEntity<ResponseDto> postReview(@RequestBody UserCoachReviewDto userCoachReviewDto,
			@PathVariable int userId, @PathVariable int coachId) {

		UserCoachReviewDto postCoachReview = userReviewService.postCoachReview(userCoachReviewDto, userId, coachId);
		ResponseDto resp = new ResponseDto();
		if (postCoachReview != null) {
			resp.setError(false);
			resp.setData(postCoachReview);
			resp.setMessage("Review Sent Successfully, Wait for Admin's Approval ");
			return new ResponseEntity<>(resp, HttpStatus.CREATED);
		} else {
			resp.setError(true);
			resp.setMessage("Review Not Submitted");
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * 
	 * @param userCoachReviewDto
	 * @param reviewId
	 * @param userId
	 * @return
	 */
	@ApiOperation("This API is used to update Coach-Review")
	@PutMapping("/{userId}/{reviewId}")
	public ResponseEntity<ResponseDto> updateReview(@RequestBody UserCoachReviewDto userCoachReviewDto,
			@PathVariable int reviewId, @PathVariable int userId) {
		String updateReview = userReviewService.updateReview(userCoachReviewDto, reviewId, userId);
		ResponseDto responseDto = new ResponseDto();
		if (!updateReview.equalsIgnoreCase("Review Not Updated")) {
			responseDto.setError(false);
			responseDto.setMessage(updateReview);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		} else {
			responseDto.setError(true);
			responseDto.setMessage(updateReview);
			return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * This method is to get plan list of coach.
	 * 
	 * @param coachId
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(UserReviewConstants.DELETE_REVIEW_API)
	@ApiResponses(value = { @ApiResponse(code = 200, message = UserReviewConstants.DELETE_REVIEW_SUCCESS),
			@ApiResponse(code = 404, message = UserReviewConstants.REVIEW_NOT_FOUND) })
	@DeleteMapping("delete/{reviewId}/{userId}")
	public ResponseEntity<ResponseDto> getCoachPlan(@PathVariable int reviewId, @PathVariable int userId) {
		boolean flag = userReviewService.deleteCoachReview(reviewId, userId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (!flag) {
			log.error(UserReviewConstants.REVIEW_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(UserReviewConstants.REVIEW_NOT_FOUND);
			responseDTO.setMessage(UserReviewConstants.REVIEW_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.error(UserReviewConstants.DELETE_REVIEW_SUCCESS);
			responseDTO.setError(false);
			responseDTO.setData(UserReviewConstants.DELETE_REVIEW_SUCCESS);
			responseDTO.setMessage(UserReviewConstants.DELETE_REVIEW_SUCCESS);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}

}
