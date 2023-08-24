package com.tyss.strongameapp.service;

import com.tyss.strongameapp.dto.UserStepsStatsDto;

/**
 * StepsDataService is implemented by StepsDataServiceImple class, which is used to save and update user steps.
 * @author Sushma Guttal
 *
 */
public interface StepsDataService {

	/**
	 * This method is implemented by its implementation class to update and save user steps
	 * @param data
	 * @return UserStepsStatsDto
	 */
	UserStepsStatsDto saveSteps(UserStepsStatsDto data);

}//End of StepsDataService interface
