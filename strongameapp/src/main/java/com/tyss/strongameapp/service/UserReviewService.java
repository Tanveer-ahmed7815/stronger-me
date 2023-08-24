package com.tyss.strongameapp.service;
	
import com.tyss.strongameapp.dto.UserCoachReviewDto;

public interface UserReviewService {
	
	/**
	 * 
	 * This method is implemented by its implementation class to delete the Coach's Review
	 * 
	 * @param reviewId
	 * @param userId
	 * @return boolean
	 */
	boolean deleteCoachReview(int reviewId, int userId);


	UserCoachReviewDto postCoachReview(UserCoachReviewDto userCoachReviewDto, int userId, int coachId);

	String updateReview(UserCoachReviewDto userCoachReviewDto, int reviewId, int userId);

}
