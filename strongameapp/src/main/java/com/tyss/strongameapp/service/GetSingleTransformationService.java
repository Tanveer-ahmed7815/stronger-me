package com.tyss.strongameapp.service;

import com.tyss.strongameapp.dto.SingleTransformationDto;
/**
 * GetSingleTransformationService is implemented by GetSingleTransformationServiceImple class to fetch transformation details by id.
 * @author HP
 *
 */
public interface GetSingleTransformationService {

	/**
	 * This method is implemented by its implementation class to fetch transformation details by id.
	 * @param transformationId
	 * @param userId
	 * @return SingleTransformationDto
	 */
	SingleTransformationDto getTransformationById(int transformationId, int userId);

}//End of GetSingleTransformationService interface.
