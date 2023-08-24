package com.tyss.strongameapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.SingleCoachDetailsDto;
import com.tyss.strongameapp.dto.SingleTransformationDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.TransformationDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.TransformationException;
import com.tyss.strongameapp.repository.TransformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

/**
 * This is the implementation class to fetch single transformation details.
 * 
 * @author HP
 *
 */
@Service
public class GetSingleTransformationServiceImple implements GetSingleTransformationService {

	/**
	 * This field is used to invoke persistence layer.
	 */
	@Autowired
	private TransformationRepository transformationRepo;

	/**
	 * This field is used to invoke business layer.
	 */
	@Autowired
	private HomePageService homePageService;

	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This method is used to fetch single transformation details.
	 * 
	 * @param transformationId
	 * @param userId
	 * @return SingleTransformationDto
	 */
	@Override
	public SingleTransformationDto getTransformationById(int transformationId, int userId) {
		SingleTransformationDto transformationDto = new SingleTransformationDto();
		transformationDto.setCases("");
		Optional<TransformationDetails> transformation = transformationRepo.findById(transformationId);
		UserInformation user = userRepo.findUserById(userId);

		if (user == null) {
			transformationDto.setCases(transformationDto.getCases() + "User Not Found.");
		}

		if (!transformation.isPresent()) {
			transformationDto.setCases(transformationDto.getCases() + " Transformation Not Found.");
		}
		if (transformationDto.getCases().length() == 0) {
			TransformationDetails transformationEntity = transformation
					.orElseThrow(() -> new TransformationException("Transformation Not Found"));
			BeanUtils.copyProperties(transformationEntity, transformationDto);
			CoachDetails coach = transformationEntity.getCoach();
			List<TransformationDetails> transformationList = null;
			SingleCoachDetailsDto singleCoach = new SingleCoachDetailsDto();
			if (coach != null) {
				transformationList = coach.getTransformations();
				BeanUtils.copyProperties(coach, singleCoach);
			}
			if (transformationList != null) {
				singleCoach.setNoOfUserTrained(transformationList.size());
			}
			Boolean flag = homePageService.getTransformationflag(transformationId, userId);
			if (flag == null) {
				transformationDto.setFlag(false);
			} else
				transformationDto.setFlag(flag);
			transformationDto.setTotalLikes(homePageService.getTransformationLikeCount(transformationDto));
			transformationDto.setSingleCoach(singleCoach);
		}
		return transformationDto;
	}// End of getTransformationById method.

}// ENd of GetSingleTransformationServiceImple class.
