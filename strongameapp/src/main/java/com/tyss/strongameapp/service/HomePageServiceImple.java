package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.CoachDetailsDto;
import com.tyss.strongameapp.dto.CoachFilterDTO;
import com.tyss.strongameapp.dto.CoachReviewDto;
import com.tyss.strongameapp.dto.DietPlanDetailsDto;
import com.tyss.strongameapp.dto.DietRecipeLikeDto;
import com.tyss.strongameapp.dto.HomeBannerInformationDto;
import com.tyss.strongameapp.dto.HomeDisplayDto;
import com.tyss.strongameapp.dto.ModuleContentDto;
import com.tyss.strongameapp.dto.ModuleDto;
import com.tyss.strongameapp.dto.ModuleHomePageDto;
import com.tyss.strongameapp.dto.RecentUser;
import com.tyss.strongameapp.dto.SingleTransformationDto;
import com.tyss.strongameapp.dto.StrongermeModuleDto;
import com.tyss.strongameapp.dto.TransformationDetailsDto;
import com.tyss.strongameapp.dto.TransformationLikeDetailsDto;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.CoachReview;
import com.tyss.strongameapp.entity.DietRecipeDetails;
import com.tyss.strongameapp.entity.DietRecipeLike;
import com.tyss.strongameapp.entity.HomeBannerInformation;
import com.tyss.strongameapp.entity.ModuleContent;
import com.tyss.strongameapp.entity.PackageDetails;
import com.tyss.strongameapp.entity.PlanMaster;
import com.tyss.strongameapp.entity.SpecializationMaster;
import com.tyss.strongameapp.entity.StreamedContent;
import com.tyss.strongameapp.entity.StrongermeModule;
import com.tyss.strongameapp.entity.TopTenTrend;
import com.tyss.strongameapp.entity.TransformationDetails;
import com.tyss.strongameapp.entity.TransformationLikeDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.DietRecipeException;
import com.tyss.strongameapp.exception.ModuleContentException;
import com.tyss.strongameapp.exception.TransformationException;
import com.tyss.strongameapp.exception.UserNotExistException;
import com.tyss.strongameapp.repository.AdvertisementInformationRepository;
import com.tyss.strongameapp.repository.CoachDetailsRepo;
import com.tyss.strongameapp.repository.DietLikeRepository;
import com.tyss.strongameapp.repository.DietPlanDetailsRepo;
import com.tyss.strongameapp.repository.HomeBannerInformationRepository;
import com.tyss.strongameapp.repository.ModuleContentRepository;
import com.tyss.strongameapp.repository.ModuleRepository;
import com.tyss.strongameapp.repository.PlanMasterRepository;
import com.tyss.strongameapp.repository.SpecializationMasterRepository;
import com.tyss.strongameapp.repository.StreamedContentRepo;
import com.tyss.strongameapp.repository.TopTenTrendRepo;
import com.tyss.strongameapp.repository.TransformationLikeRepository;
import com.tyss.strongameapp.repository.TransformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

/**
 * This is the implementation class to fetch home page content.
 * 
 * @author Sushma Guttal
 *
 */
@Service
public class HomePageServiceImple implements HomePageService {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private HomePageService homePageService;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private CoachDetailsRepo coachRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private HomeBannerInformationRepository homeBannerRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private DietLikeRepository dietLikeRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private DietPlanDetailsRepo dietRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private TransformationRepository transformationRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private TransformationLikeRepository transformationLikeRepo;

	/**
	 * This field is used to invoke persistence layer methods.'
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private MyShopService myShopService;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private AdvertisementInformationRepository advertisementRepo;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	ModuleContentRepository contentRepository;

	@Autowired
	TopTenTrendRepo trendsRepo;

	@Autowired
	StreamedContentRepo streamRepo;

	@Autowired
	private PlanMasterRepository planMasterRepo;

	@Autowired
	private SpecializationMasterRepository specializationMasterRepo;

	@Value("${languages}")
	private String languages;

	/**
	 * This method is implemented to display home page content.
	 * 
	 * @param userId
	 * @return HomeDisplayDto
	 */
	@Override
	public HomeDisplayDto homePageDisplay(int userId) {
		HomeDisplayDto home = new HomeDisplayDto();
		home.setCases("");
		UserInformation userEntity = userRepo.findUserById(userId);
		if (userEntity == null) {
			home.setCases(home.getCases() + "User Not Found.");
		}
		if (home.getCases().equalsIgnoreCase("")) {
			home.setDiet(homePageService.getDietList(userId));
			home.setTransformation(homePageService.getTransformationList(userId));
			home.setCoach(homePageService.getCoachList());
			List<HomeBannerInformation> homeBannerList = homeBannerRepo.findAll();
			List<HomeBannerInformationDto> homeBannerDtoList = new ArrayList<>();
			for (HomeBannerInformation homeBannerInformation : homeBannerList) {
				HomeBannerInformationDto homeBannerDto = new HomeBannerInformationDto();
				BeanUtils.copyProperties(homeBannerInformation, homeBannerDto);
				if (homeBannerInformation.getHomeBannerDiet() != null) {
					int id = homeBannerInformation.getHomeBannerDiet().getDietId();
					homeBannerDto.setId(id);
				} else if (homeBannerInformation.getHomeBannerModule() != null) {
					int id = homeBannerInformation.getHomeBannerModule().getContentId();
					homeBannerDto.setId(id);
				} else if (homeBannerInformation.getHomeBannerCoach() != null) {
					int id = homeBannerInformation.getHomeBannerCoach().getCoachId();
					homeBannerDto.setId(id);
				} else if (homeBannerInformation.getHomeBannerTransformation() != null) {
					int id = homeBannerInformation.getHomeBannerTransformation().getTransformationId();
					homeBannerDto.setId(id);
				}
				homeBannerDtoList.add(homeBannerDto);
			}
			home.setAdvertisements(advertisementRepo.findAll());
			home.setHomeBanner(homeBannerDtoList);
			home.setCoins(myShopService.getCoin(userRepo.findUserById(userId)));
		}
		return home;
	}// End of homePageDisplay method.

	/**
	 * This method is used to to check whether user has liked the specified diet
	 * recipe or not.
	 * 
	 * @param dietId
	 * @param userId
	 * @return Boolean
	 */
	public Boolean getflag(int dietId, Integer userId) {
		return dietLikeRepo.getFlag(dietId, userId);
	}// End of getflag method.

	/**
	 * This method is implemented to get list of diet recipes.
	 * 
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> getDietList(Integer userId) {
		List<DietRecipeDetails> dietList = dietRepo.findAll();
		if (dietList.isEmpty())
			return Collections.emptyList();
		else {
			List<DietPlanDetailsDto> dietDtoList = new ArrayList<>();
			for (DietRecipeDetails dietPlanDetails : dietList) {
				DietPlanDetailsDto dietDto = new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietPlanDetails, dietDto);
				dietDto.setTotalLikes(homePageService.getDietLikeCount(dietDto));
				Boolean flag = getflag(dietDto.getDietId(), userId);
				if (flag == null)
					dietDto.setFlag(false);
				else
					dietDto.setFlag(flag);
				dietDtoList.add(dietDto);

			}
			return dietDtoList;
		}
	}// End of getDietList method.

	/**
	 * This method is implemented to list of transformation.
	 * 
	 * @param userId
	 * @return List<TransformationDetailsDto>
	 */
	@Override
	public List<TransformationDetailsDto> getTransformationList(Integer userId) {

		List<TransformationDetails> transformationList = transformationRepo.findAll();
		if (transformationList.isEmpty())
			return Collections.emptyList();
		else {
			List<TransformationDetailsDto> transformationDtoList = new ArrayList<>();
			for (TransformationDetails transformationDetails : transformationList) {
				TransformationDetailsDto transformationDto = new TransformationDetailsDto();
				BeanUtils.copyProperties(transformationDetails, transformationDto);
				CoachDetails coach = transformationDetails.getCoach();
				if (coach != null) {
					transformationDto.setCoachImage(coach.getPhoto());
				}
				transformationDto.setTotalLikes(homePageService.getTransformationLikeCount(transformationDto));
				Boolean flag = homePageService.getTransformationflag(transformationDto.getTransformationId(), userId);
				if (flag == null)
					transformationDto.setFlag(false);
				else
					transformationDto.setFlag(flag);
				transformationDtoList.add(transformationDto);

			}
			return transformationDtoList;
		}
	}// End of getTransformationList method.

	/**
	 * This method is implemented to fetch total like count for specified diet
	 * 
	 * @param dietDto
	 * @return Integer
	 */
	@Override
	public Integer getDietLikeCount(DietPlanDetailsDto dietDto) {
		if (dietDto != null) {
			Optional<DietRecipeDetails> dietEntity = dietRepo.findById(dietDto.getDietId());
			if (dietEntity.isPresent()) {
				DietRecipeDetails dietEntity2 = dietEntity.get();
				return dietLikeRepo.getDietLikeCount(dietEntity2.getDietId());
			} else
				return null;
		} else
			throw new com.tyss.strongameapp.exception.DietRecipeException("Diet Recipe Details Cannot be Empty");

	}// End of getDietLikeCount method.

	/**
	 * This method is implemented to fetch total count for specified transformation.
	 * 
	 * @param transformationDto
	 * @return Integer
	 */
	@Override
	public Integer getTransformationLikeCount(TransformationDetailsDto transformationDto) {
		if (transformationDto != null) {
			Optional<TransformationDetails> transformationEntity = transformationRepo
					.findById(transformationDto.getTransformationId());
			if (transformationEntity.isPresent()) {
				TransformationDetails transformationEntity2 = transformationEntity.get();
				return transformationLikeRepo.getTransformationLikeCount(transformationEntity2.getTransformationId());
			} else
				return null;
		} else
			throw new com.tyss.strongameapp.exception.TransformationLikeException("Transformation Like Cannot be Null");
	}// End of getTransformationLikeCount method.

	/**
	 * This method is implemented to check whether user has liked specified
	 * transformation or not.
	 * 
	 * @param userId
	 * @param transformationId
	 * @return Boolean
	 */
	@Override
	public Boolean getTransformationflag(int transformationId, Integer userId) {
		return transformationLikeRepo.getFlag(transformationId, userId);
	}// End of getTransformationflag method.

	/**
	 * This method is used to save like posted by user for specified diet recipe.
	 * 
	 * @param like DietRecipeLikeDto
	 * @return
	 */
	@Transactional
	@Override
	public DietRecipeLikeDto save(DietRecipeLikeDto like) {
		if (like != null) {
			int userId = like.getDietLikeUser().getUserId();
			int dietRecipeId = like.getDietRecipe().getDietId();
			boolean flag = like.isUserLike();
			DietRecipeLike dietLike = dietLikeRepo.findDietLike(userId, dietRecipeId);
			if (dietLike != null) {
				dietLikeRepo.updateDietLike(userId, dietRecipeId, flag);
				BeanUtils.copyProperties(dietLike, like);
				int dietId = dietLike.getDietRecipe().getDietId();
				DietRecipeDetails dietEntity = dietRepo.findById(dietId).orElseThrow(DietRecipeException::new);
				DietPlanDetailsDto dietDto = new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietEntity, dietDto);
				int likeCount = getDietLikeCount(dietDto);
				like.setTotalLikes(likeCount);
				like.setDietLikeUser(null);
				like.setDietRecipe(null);
				like.setUserLike(flag);
			} else {
				DietRecipeLike dietLike2 = new DietRecipeLike();
				dietLike2.setDietLikeUser(
						userRepo.findById(like.getDietLikeUser().getUserId()).orElseThrow(UserNotExistException::new));
				dietLike2.setDietRecipe(
						dietRepo.findById(like.getDietRecipe().getDietId()).orElseThrow(DietRecipeException::new));
				dietLike2.setUserLike(like.isUserLike());
				dietLike2 = dietLikeRepo.save(dietLike2);
				BeanUtils.copyProperties(dietLike2, like);
				int dietId = dietLike2.getDietRecipe().getDietId();
				DietRecipeDetails dietEntity = dietRepo.findById(dietId).orElseThrow(DietRecipeException::new);
				DietPlanDetailsDto dietDto = new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietEntity, dietDto);
				int likeCount = getDietLikeCount(dietDto);
				like.setTotalLikes(likeCount);
				like.setDietLikeUser(null);
				like.setDietRecipe(null);
			}
			return like;
		} else {
			return null;
		}
	}// End of save method.

	/**
	 * This method is used to save like posted by user for specified transformation.
	 * 
	 * @param like
	 * @return TransformationLikeDetailsDto
	 */
	@Transactional
	@Override
	public TransformationLikeDetailsDto save(TransformationLikeDetailsDto like) {
		if (like != null) {
			int userId = like.getTransformationLikeUser().getUserId();
			int transformId = like.getTransformationLike().getTransformationId();
			boolean flag = like.isLike();
			TransformationLikeDetails likeEntity = transformationLikeRepo.findTransLikeId(userId, transformId);
			if (likeEntity != null) {
				transformationLikeRepo.update(userId, transformId, flag);
				BeanUtils.copyProperties(likeEntity, like);
				int transformationId = likeEntity.getTransformationLike().getTransformationId();
				TransformationDetails transformationEntity = transformationRepo.findById(transformationId)
						.orElseThrow(() -> new TransformationException("Transformation not Found"));
				TransformationDetailsDto transformationDto = new TransformationDetailsDto();
				BeanUtils.copyProperties(transformationEntity, transformationDto);
				int likeCount = getTransformationLikeCount(transformationDto);
				like.setTotalLikes(likeCount);
				like.setTransformationLikeUser(null);
				like.setTransformationLike(null);
				like.setLike(flag);
			} else {
				TransformationLikeDetails likeEntity2 = new TransformationLikeDetails();
				likeEntity2.setTransformationLike(
						transformationRepo.findById(like.getTransformationLike().getTransformationId())
								.orElseThrow(() -> new TransformationException("Transformation Not Found")));
				likeEntity2.setTransformationLikeUser(userRepo.findById(like.getTransformationLikeUser().getUserId())
						.orElseThrow(UserNotExistException::new));
				likeEntity2.setLike(like.isLike());
				likeEntity2 = transformationLikeRepo.save(likeEntity2);
				BeanUtils.copyProperties(likeEntity2, like);
				int transformationId = likeEntity2.getTransformationLike().getTransformationId();
				TransformationDetails transformationEntity = transformationRepo.findById(transformationId)
						.orElseThrow(() -> new TransformationException("Transformation Not Found"));
				TransformationDetailsDto transformationDto = new TransformationDetailsDto();
				BeanUtils.copyProperties(transformationEntity, transformationDto);
				int likeCount = getTransformationLikeCount(transformationDto);
				like.setTotalLikes(likeCount);
				like.setTransformationLikeUser(null);
				like.setTransformationLike(null);
			}
			return like;
		} else {
			return null;
		}
	}// End of save method.

	/**
	 * This method is implemented to fetch list of coaches.
	 * 
	 * @return List<CoachDetailsDto>
	 */
	@Override
	public List<CoachDetailsDto> getCoachList() {
		List<CoachDetails> coachList = coachRepo.findAll();
		List<CoachDetailsDto> coachDtoList = new ArrayList<>();
		for (CoachDetails coachDetails : coachList) {
			CoachDetailsDto coachDto = new CoachDetailsDto();
			BeanUtils.copyProperties(coachDetails, coachDto);
			coachDto.setCoachReview(addOnlyApprovedReviews(coachDetails.getCoachReview(), coachDetails.getCoachId()));
			coachDtoList.add(coachDto);
		}
		List<CoachDetailsDto> topList = coachDtoList.stream().filter(CoachDetailsDto::isTopList)
				.collect(Collectors.toList());
		coachDtoList = coachDtoList.stream().filter(x -> !x.isTopList()).collect(Collectors.toList());
		topList.addAll(coachDtoList);
		return topList;
	}// End of getCoachList method.

	/**
	 * This method is implemented to get total count of likes for specified
	 * transformation
	 * 
	 * @param transformationDto
	 * @return Integer
	 */
	@Override
	public Integer getTransformationLikeCount(SingleTransformationDto transformationDto) {
		if (transformationDto != null) {
			Optional<TransformationDetails> transformationEntity = transformationRepo
					.findById(transformationDto.getTransformationId());
			if (transformationEntity.isPresent()) {
				TransformationDetails transformationEntity2 = transformationEntity.get();
				return transformationLikeRepo.getTransformationLikeCount(transformationEntity2.getTransformationId());
			} else
				return null;
		} else
			throw new com.tyss.strongameapp.exception.TransformationLikeException("Transformation Like Cannot be Null");
	}// End of getTransformationLikeCount method.

	/**
	 * This method is implemented to filter diet recipes by name in descending
	 * order.
	 * 
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> filterDietByNameDesc(int userId) {
		List<DietPlanDetailsDto> dietDtoList = new ArrayList<>();
		UserInformation user = userRepo.findUserById(userId);
		if (user == null) {
			return Collections.emptyList();
		} else {
			List<DietRecipeDetails> dietList = dietRepo.filterDietByNameDesc();
			if (dietList != null) {
				for (DietRecipeDetails dietInformation : dietList) {
					DietPlanDetailsDto dietDto = new DietPlanDetailsDto();
					BeanUtils.copyProperties(dietInformation, dietDto);
					Boolean flag = getflag(dietInformation.getDietId(), userId);
					if (flag == null)
						dietDto.setFlag(false);
					else
						dietDto.setFlag(flag);
					dietDto.setTotalLikes(getDietLikeCount(dietDto));
					dietDtoList.add(dietDto);
				}
			}
			return dietDtoList;
		}
	}// End of filterDietByNameDesc method.

	/**
	 * This method is implemented to filer the diet recipes in ascending order by
	 * name.
	 * 
	 * @param userId
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> filterDietByName(int userId) {
		List<DietPlanDetailsDto> dietDtoList = new ArrayList<>();
		UserInformation user = userRepo.findUserById(userId);
		if (user == null) {
			return Collections.emptyList();
		} else {
			List<DietRecipeDetails> dietList = dietRepo.filterDietByName();
			if (!dietList.isEmpty()) {
				for (DietRecipeDetails dietInformation : dietList) {
					DietPlanDetailsDto dietDto = new DietPlanDetailsDto();
					BeanUtils.copyProperties(dietInformation, dietDto);
					Boolean flag = getflag(dietDto.getDietId(), userId);
					if (flag == null)
						dietDto.setFlag(false);
					else
						dietDto.setFlag(flag);
					dietDto.setTotalLikes(getDietLikeCount(dietDto));
					dietDtoList.add(dietDto);
				}
			}
			return dietDtoList;
		}
	}// End of filterDietByName method.

	/**
	 * This method is implemented to search diet recipes.
	 * 
	 * @param userId
	 * @param keyword
	 * @return List<DietPlanDetailsDto>
	 */
	@Override
	public List<DietPlanDetailsDto> searchDiet(int userId, String keyword) {
		List<DietRecipeDetails> dietList = dietRepo.serach(keyword);
		if (dietList != null) {
			List<DietPlanDetailsDto> dietDtoList = new ArrayList<>();
			for (DietRecipeDetails dietPlanDetails : dietList) {
				DietPlanDetailsDto dietDto = new DietPlanDetailsDto();
				BeanUtils.copyProperties(dietPlanDetails, dietDto);
				Boolean flag = getflag(dietDto.getDietId(), userId);
				if (flag == null)
					dietDto.setFlag(false);
				else
					dietDto.setFlag(flag);
				dietDto.setTotalLikes(getDietLikeCount(dietDto));
				dietDtoList.add(dietDto);
			}
			return dietDtoList;
		} else
			return Collections.emptyList();
	}// End of searchDiet method.

	@Override
	public DietPlanDetailsDto getDietById(int userId, int dietId) {
		Optional<DietRecipeDetails> dietRecipe = dietRepo.findById(dietId);
		if (dietRecipe.isPresent()) {
			DietRecipeDetails dietRecipeEntity = dietRecipe.get();
			DietPlanDetailsDto dietDto = new DietPlanDetailsDto();
			BeanUtils.copyProperties(dietRecipeEntity, dietDto);
			dietDto.setTotalLikes(homePageService.getDietLikeCount(dietDto));
			Boolean flag = getflag(dietDto.getDietId(), userId);
			if (flag == null)
				dietDto.setFlag(false);
			else
				dietDto.setFlag(flag);
			return dietDto;
		}
		return null;
	}

	/**
	 * This method is to get all modules including top ten trends content
	 * 
	 * @return List<StrongermeModuleDto>
	 */
	@Override
	public ModuleDto getAllModule(int userId) {
		ModuleDto moduleDto = new ModuleDto();
		List<StrongermeModule> findAll = new ArrayList<>();
		StrongermeModule topModule = new StrongermeModule();
		topModule.setModuleName("Top 10 Trending");
		List<Integer> contentIdList = trendsRepo.findAllContentId();
		List<ModuleContent> findAllById = contentRepository.findAllById(contentIdList);
		topModule.setModuleContent(findAllById);
		findAll.add(topModule);
		findAll.addAll(moduleRepository.findAll());
		List<StrongermeModuleDto> dtos = new ArrayList<>();
		for (StrongermeModule strongermeModule : findAll) {
			StrongermeModuleDto dto = new StrongermeModuleDto();
			BeanUtils.copyProperties(strongermeModule, dto);
			List<ModuleHomePageDto> homePageDtos = new ArrayList<>();
			for (ModuleContent content : strongermeModule.getModuleContent()) {
				ModuleHomePageDto moduleHomePageDto = new ModuleHomePageDto();
				moduleHomePageDto.setBoardUsers(getStreamUSers(content));
				BeanUtils.copyProperties(content, moduleHomePageDto);
				homePageDtos.add(moduleHomePageDto);
				dto.setContents(homePageDtos);
			}
			dtos.add(dto);
		}
		moduleDto.setModules(dtos);
		moduleDto.setSubcribed(checkPackageSubcription(userId));
		return moduleDto;
	}

	/**
	 * This method is to get streamed content users
	 * 
	 * @param content
	 * @return List<RecentUser>
	 */
	private List<RecentUser> getStreamUSers(ModuleContent liveModuleContent) {
		List<UserInformation> users = liveModuleContent.getContentStreamed().stream()
				.sorted((i, j) -> j.getStreamedTime().compareTo(i.getStreamedTime()))
				.map(StreamedContent::getStreamedUser).collect(Collectors.toList());
		
		List<RecentUser> recentUsers = new ArrayList<>();
		for (UserInformation information : users) {
			if (information != null) {
				RecentUser recentUser = new RecentUser();
				BeanUtils.copyProperties(information, recentUser);
				recentUsers.add(recentUser);
			}
		}
		return recentUsers;
	}

	/**
	 * This method is to store content stream by users
	 * 
	 * @param userId
	 * @param contentId
	 */
	@Override
	@Transactional
	public void saveStreamtime(int userId, int contentId) {
		UserInformation user = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		ModuleContent moduleContent = contentRepository.findById(contentId).orElseThrow(ModuleContentException::new);
		Date date = new Date();
		List<StreamedContent> streamedContents = moduleContent.getContentStreamed();
		StreamedContent streamedContent = streamedContents.stream()
				.filter(x -> x.getStreamedUser().getUserId() == userId).findFirst().orElse(null);
		if (streamedContent != null) {
			streamedContent.setStreamedTime(date);
			streamedContent.setViewCount(streamedContent.getViewCount() + 1);
		} else {
			streamedContent = new StreamedContent();
			streamedContent.setStreamedTime(date);
			streamedContent.setStreamedContent(moduleContent);
			streamedContent.setStreamedUser(user);
			streamedContent.setViewCount(1);
			streamedContents.add(streamedContent);
		}
	}

	/**
	 * This method is to get content by moduleId
	 * 
	 * @param moduleId
	 * @return List<ModuleContentDto>
	 */
	@Override
	public List<ModuleContentDto> getContentByModuleId(int moduleId) {
		StrongermeModule module = new StrongermeModule();
		if (moduleId == 0) {
			module.setModuleId(0);
			module.setModuleContent(
					trendsRepo.findAll().stream().map(TopTenTrend::getToptrendContent).collect(Collectors.toList()));
		} else {
			module = moduleRepository.findById(moduleId)
					.orElseThrow(() -> new ModuleContentException("Module Doesn't Exist"));
		}
		List<ModuleContent> moduleContents = module.getModuleContent();
		ArrayList<ModuleContentDto> list = new ArrayList<>();
		for (ModuleContent moduleContent : moduleContents) {
			ModuleContentDto moduleContentDto = new ModuleContentDto();
			BeanUtils.copyProperties(moduleContent, moduleContentDto);
			moduleContentDto.setModuleId(moduleId);
			moduleContentDto.setBoardUsers(getStreamUSers(moduleContent));
			list.add(moduleContentDto);
		}
		return list;
	}

	/**
	 * This method is to redirect to content by homeBannerId
	 * 
	 * @param homebannerId
	 * @return ModuleContentDto
	 */
	@Override
	public ModuleContentDto redirectContentByBannerId(int homebannerId) {
		HomeBannerInformation bannerInformation = homeBannerRepo.findById(homebannerId).orElse(null);
		if (bannerInformation != null) {
			ModuleContent homeBannerModule = bannerInformation.getHomeBannerModule();
			if (homeBannerModule != null) {
				ModuleContentDto moduleContentDto = new ModuleContentDto();
				BeanUtils.copyProperties(homeBannerModule, moduleContentDto);
				moduleContentDto.setModuleId(homeBannerModule.getStrongermeModule().getModuleId());
				moduleContentDto.setBoardUsers(getStreamUSers(homeBannerModule));
				return moduleContentDto;
			}
		}
		return null;
	}

	/**
	 * This method is to get content details by contentId
	 * 
	 * @param contentId
	 * @param userId
	 * @return ResponseEntity<ResponseDto>
	 */
	@Override
	public ModuleContentDto getContentByContentId(int contentId, int userId) {
		ModuleContent moduleContent = contentRepository.findById(contentId).orElse(null);
		if (moduleContent != null) {
			ModuleContentDto moduleContentDto = new ModuleContentDto();
			BeanUtils.copyProperties(moduleContent, moduleContentDto);
			moduleContentDto.setModuleId(moduleContent.getStrongermeModule().getModuleId());
			moduleContentDto.setBoardUsers(getStreamUSers(moduleContent));
			moduleContentDto.setSubcribed(checkPackageSubcription(userId));
			return moduleContentDto;
		}
		return null;
	}

	/**
	 * This method is implemented to get coachFilter page data
	 * 
	 * 
	 * @return CoachFilterDTO
	 */
	@Override
	public CoachFilterDTO getCoachFilters() {
		List<String> languageList = new ArrayList<>();
		if (languages != null) {
			String[] data = languages.split(",");
			languageList = Arrays.asList(data);
		}
		languageList = languageList.stream().map(String::toUpperCase).collect(Collectors.toList());
		List<PlanMaster> planMasters = planMasterRepo.findAll();
		List<SpecializationMaster> specializationMasters = specializationMasterRepo.findAll();

		List<String> badges = planMasters.stream().map(PlanMaster::getPlanName).collect(Collectors.toList());
		List<String> specializations = specializationMasters.stream().map(SpecializationMaster::getSpecializationName)
				.collect(Collectors.toList());

		if (badges.isEmpty() && specializations.isEmpty() && languageList.isEmpty()) {
			return null;
		} else {
			return new CoachFilterDTO(badges, specializations, languageList);
		}

	}

	/**
	 * This method is implemented to filter coaches by badge, specialization and
	 * language
	 * 
	 * @param coachFilterDTO
	 * @return List<CoachDetailsDto>
	 */
	@Override
	public List<CoachDetailsDto> getCoachList(CoachFilterDTO coachFilterDTO) {
		List<CoachDetails> coachList = coachRepo.findAll();
		List<CoachDetailsDto> coachDtoList = new ArrayList<>();
		for (CoachDetails coachDetails : coachList) {
			CoachDetailsDto coachDto = new CoachDetailsDto();
			BeanUtils.copyProperties(coachDetails, coachDto);
			coachDto.setCoachReview(addOnlyApprovedReviews(coachDetails.getCoachReview(), coachDetails.getCoachId()));
			coachDtoList.add(coachDto);
		}
		if (coachFilterDTO.getBadge() != null && !(coachFilterDTO.getBadge().isEmpty())) {
			coachDtoList = coachDtoList.stream().filter(c -> c.getBadge().equalsIgnoreCase(coachFilterDTO.getBadge()))
					.collect(Collectors.toList());
		}
		if (coachFilterDTO.getSpecialization() != null && !(coachFilterDTO.getSpecialization().isEmpty())) {
			coachDtoList = coachDtoList
					.stream().filter(c -> c.getSpecializations().stream().map(String::toUpperCase)
							.collect(Collectors.toList()).contains(coachFilterDTO.getSpecialization().toUpperCase()))
					.collect(Collectors.toList());
		}
		if (coachFilterDTO.getLanguage() != null && !(coachFilterDTO.getLanguage().isEmpty())) {
			coachDtoList = coachDtoList
					.stream().filter(c -> c.getLanguages().stream().map(String::toUpperCase)
							.collect(Collectors.toList()).contains(coachFilterDTO.getLanguage().toUpperCase()))
					.collect(Collectors.toList());
		}
		List<CoachDetailsDto> topList = coachDtoList.stream().filter(CoachDetailsDto::isTopList)
				.collect(Collectors.toList());
		coachDtoList = coachDtoList.stream().filter(c -> !c.isTopList()).collect(Collectors.toList());
		topList.addAll(coachDtoList);
		return topList;
	}

	/**
	 * This method is implemented to search coaches by coach name
	 * 
	 * @param keyword
	 * @return List<CoachDetailsDto>
	 */
	@Override
	public List<CoachDetailsDto> searchCoachByName(String keyword) {
		List<CoachDetails> coachEntities = coachRepo.searchByName(keyword);
		List<CoachDetailsDto> coachDetailsDtos = new ArrayList<>();
		for (CoachDetails coachDetails : coachEntities) {
			CoachDetailsDto coachDetailsDto = new CoachDetailsDto();
			BeanUtils.copyProperties(coachDetails, coachDetailsDto);
			coachDetailsDto
					.setCoachReview(addOnlyApprovedReviews(coachDetails.getCoachReview(), coachDetails.getCoachId()));
			coachDetailsDtos.add(coachDetailsDto);
		}
		return coachDetailsDtos;
	}

	/**
	 * This method is to send CoachReviewDto List which will be filtered WRT
	 * approved status
	 * 
	 * @param coachReview
	 * @return
	 */
	private List<CoachReviewDto> addOnlyApprovedReviews(List<CoachReview> coachReviewList, int coachId) {
		List<CoachReviewDto> coachReviewDtos = new ArrayList<>();
		for (CoachReview review : coachReviewList) {
			if (review.getStatus().equalsIgnoreCase("APPROVED")) {
				CoachReviewDto reviewDto = new CoachReviewDto();
				BeanUtils.copyProperties(review, reviewDto);
				reviewDto.setCoachId(coachId);
				if (review.getReviewUser() != null) {
					reviewDto.setUserId(review.getReviewUser().getUserId());
					reviewDto.setName(review.getReviewUser().getName());
					reviewDto.setImageUrl(review.getReviewUser().getPhoto());
				}
				coachReviewDtos.add(reviewDto);
			}
		}
		return coachReviewDtos;
	}

	/**
	 * This method is to check user have subscribed or not
	 * 
	 * @param userId
	 * @return boolean
	 */
	private boolean checkPackageSubcription(int userId) {
		UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		Calendar currentCalenderDate = Calendar.getInstance();
		java.util.Date currentUtilDate = currentCalenderDate.getTime();
		List<PackageDetails> userPackageDetails = userEntity.getUserPackages();
		return (!userPackageDetails.isEmpty() && userEntity.getPackageExpiryDate() != null
				&& currentUtilDate.before(userEntity.getPackageExpiryDate()));

	}

}// End of HomePageServiceImple class.
