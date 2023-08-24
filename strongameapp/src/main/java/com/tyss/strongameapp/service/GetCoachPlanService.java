package com.tyss.strongameapp.service;

import java.util.List;

import com.tyss.strongameapp.dto.PlanInformationDto;
/**
 * GetCoachPlanService is implemented by GetCoachPlanServiceImple to fetch plan list of specified coach.
 * @author Sushma Guttal
 *
 */
public interface GetCoachPlanService {

	/**
	 * This method is implemented by its implementation class to fetch plan list of specified coach.
	 * @param coachId
	 * @return List<PlanInformationDto>
	 */
	List<PlanInformationDto> getCoachPlan(int coachId);

}//End of GetCoachPlanService interface.
