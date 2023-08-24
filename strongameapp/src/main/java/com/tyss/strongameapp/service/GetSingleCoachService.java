package com.tyss.strongameapp.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.dto.PlanInformationDto;
import com.tyss.strongameapp.dto.SingleCoachDetailsDto;

/**
 * GetSingleCoachService is implemented by GetSingleCoachServiceImple to fetch single coach details and to enroll plan and coach.
 * @author Sushma Guttal
 *
 */
public interface GetSingleCoachService {

	/**
	 * This method is implemented by its implementation class to fetch single coach details.
	 * @param coachId
	 * @param userId
	 * @return SingleCoachDetailsDto
	 */
	SingleCoachDetailsDto getCoachById(int coachId, int userId);

	/**
	 * This method is implemented by its implementation class to enroll plan and coach.
	 * @param userId
	 * @param coachId
	 * @param plan
	 * @return
	 * @throws FirebaseMessagingException 
	 */
	String enrollPlan(int userId, int coachId, PlanInformationDto plan) throws FirebaseMessagingException;

	
	/**
	 * This method is implemented by its implementation class to fetch plan details.
	 * @param planId
	 * @return PlanInformationDto
	 */
	PlanInformationDto getPlanById(int planId);
}//End of GetSingleCoachService interface
