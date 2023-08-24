package com.tyss.strongameapp.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.constants.AddressConstants;
import com.tyss.strongameapp.constants.UserConstants;
import com.tyss.strongameapp.constants.UserReviewConstants;
import com.tyss.strongameapp.dto.UserCoachReviewDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.CoachReview;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.CoachReviewException;
import com.tyss.strongameapp.exception.InvalidRatingException;
import com.tyss.strongameapp.exception.OperationAccessDeniedException;
import com.tyss.strongameapp.exception.UserNotFoundException;
import com.tyss.strongameapp.exception.UserPlanException;
import com.tyss.strongameapp.repository.CoachDetailsRepo;
import com.tyss.strongameapp.repository.CoachReviewRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

@Service
public class UserReviewServiceImpl implements UserReviewService {

	@Autowired
	private UserInformationRepository userInformationRepository;

	@Autowired
	private CoachDetailsRepo coachDetailsRepo;

	@Autowired
	private CoachReviewRepository coachReviewRepository;

	@Override
	public UserCoachReviewDto postCoachReview(UserCoachReviewDto userCoachReviewDto, int userId, int coachId) {

		UserInformation userInfo = userInformationRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));

		CoachDetails coachDetails = coachDetailsRepo.findById(coachId)
				.orElseThrow(() -> new UserNotFoundException(AddressConstants.COACH_NOT_FOUND));

		if (!userInfo.getUserCoach().contains(coachDetails)) {
			throw new UserPlanException("Cannot Add Review Without Subscribing for Coach!!");
		}

		List<CoachReview> coachReview = userInfo.getCoachReview();

		List<CoachReview> userReviews = coachReview.stream().filter(c -> (c.getReviewCoach().getCoachId() == coachId))
				.collect(Collectors.toList());

		if (userReviews.stream().filter(r -> (r.getStatus().equalsIgnoreCase("REJECTED"))).count() > 1) {
			throw new CoachReviewException("Review Cannot be Submitted");
		}

		else if (userReviews.stream()
				.anyMatch(r -> (r.getStatus().equalsIgnoreCase(UserReviewConstants.STATUS_PENDING)))) {
			throw new CoachReviewException("Previous Review Status is PENDING!!");
		} else if (userReviews.stream().anyMatch(r -> (r.getStatus().equalsIgnoreCase("APPROVED")))) {
			throw new CoachReviewException("Review Already Submitted");
		} else {
			CoachReview userCoachReview = new CoachReview();
			BeanUtils.copyProperties(userCoachReviewDto, userCoachReview);
			validateRating(userCoachReviewDto.getRating());
			userCoachReview.setReviewCoach(coachDetails);
			userCoachReview.setReviewUser(userInfo);
			userCoachReview.setStatus(UserReviewConstants.STATUS_PENDING);
			coachReviewRepository.save(userCoachReview);
			BeanUtils.copyProperties(userCoachReview, userCoachReviewDto);
			return userCoachReviewDto;
		}

	}

	private void validateRating(double rating) {

		Pattern numpat = Pattern.compile("\\d\\.((?<!0\\.)0|5)");
		Matcher m = numpat.matcher("" + rating);
		if (rating > 5 || !m.matches()) {
			throw new InvalidRatingException("Invalid Ratings");
		}

	}

	/**
	 * 
	 * This method is to delete the coach's review
	 * 
	 * @param reviewId
	 * @param userId
	 * @return boolean
	 * 
	 */
	@Override
	public boolean deleteCoachReview(int reviewId, int userId) {
		UserInformation user = userInformationRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new UserNotFoundException("User Not Found");
		} else {
			CoachReview coachReview = coachReviewRepository.findById(reviewId).orElse(null);
			if (coachReview == null) {
				return false;
			} else {
				UserInformation reviewUser = coachReview.getReviewUser();
				if (reviewUser != null && userId == reviewUser.getUserId()) {
					coachReviewRepository.delete(coachReview);
					return true;
				} else {
					throw new OperationAccessDeniedException("User Not Authorized to Delete the Coach's Review");
				}
			}
		}
	}

	@Override
	public String updateReview(UserCoachReviewDto userCoachReviewDto, int reviewId, int userId) {

		UserInformation userInfo = userInformationRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));
		List<CoachReview> coachReview = userInfo.getCoachReview();

		CoachReview reviewCoach = coachReview.stream().filter(c -> (c.getReviewId() == reviewId)).findFirst()
				.orElseThrow(() -> new CoachReviewException("Review Not Found"));
		if (reviewCoach.getStatus().equalsIgnoreCase("APPROVED")) {

			BeanUtils.copyProperties(userCoachReviewDto, reviewCoach);
			validateRating(userCoachReviewDto.getRating());
			reviewCoach.setStatus(UserReviewConstants.STATUS_PENDING);
			coachReviewRepository.save(reviewCoach);
			return "Review Updated successfully,Please Wait Untill Admin Approves!!";
		} else {
			return "Review Cannot be Updated";
		}

	}

}
