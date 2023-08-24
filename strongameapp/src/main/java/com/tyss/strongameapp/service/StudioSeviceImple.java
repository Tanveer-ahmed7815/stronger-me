package com.tyss.strongameapp.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.constants.LiveSessionConstants;
import com.tyss.strongameapp.dto.BookNowDto;
import com.tyss.strongameapp.dto.CoachForSessionDetailsDto;
import com.tyss.strongameapp.dto.LiveModuleContentDto;
import com.tyss.strongameapp.dto.LiveModuleDto;
import com.tyss.strongameapp.dto.PackageDetailsDto;
import com.tyss.strongameapp.dto.PackageSubscribeDto;
import com.tyss.strongameapp.dto.PackageTaglineDetailsDto;
import com.tyss.strongameapp.dto.PackageTypeUserDto;
import com.tyss.strongameapp.dto.PopularRegularPackageDto;
import com.tyss.strongameapp.dto.RecentUser;
import com.tyss.strongameapp.dto.RecentUserLayer;
import com.tyss.strongameapp.dto.SessionDetailsDto;
import com.tyss.strongameapp.dto.SpecializationContentDto;
import com.tyss.strongameapp.dto.SpecializationModuleContentDto;
import com.tyss.strongameapp.dto.StudioBannerInformationDto;
import com.tyss.strongameapp.dto.StudioModuleDto;
import com.tyss.strongameapp.dto.TaglineDetailsDto;
import com.tyss.strongameapp.dto.TodaysLiveSessionDto;
import com.tyss.strongameapp.dto.WeeksLiveSessionsDTO;
import com.tyss.strongameapp.entity.CoachDetails;
import com.tyss.strongameapp.entity.LiveModule;
import com.tyss.strongameapp.entity.LiveModuleContent;
import com.tyss.strongameapp.entity.MyOrderDetails;
import com.tyss.strongameapp.entity.NotificationInformation;
import com.tyss.strongameapp.entity.PackageDetails;
import com.tyss.strongameapp.entity.RemaindSession;
import com.tyss.strongameapp.entity.SessionDetails;
import com.tyss.strongameapp.entity.SessionNotificationDetails;
import com.tyss.strongameapp.entity.SpecializationContent;
import com.tyss.strongameapp.entity.StreamedStudioContent;
import com.tyss.strongameapp.entity.StudioBannerInformation;
import com.tyss.strongameapp.entity.TaglineDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.ModuleContentException;
import com.tyss.strongameapp.exception.PackageException;
import com.tyss.strongameapp.exception.SessionException;
import com.tyss.strongameapp.exception.UserNotExistException;
import com.tyss.strongameapp.repository.CoachDetailsRepo;
import com.tyss.strongameapp.repository.LiveContentRepository;
import com.tyss.strongameapp.repository.LiveModuleRepository;
import com.tyss.strongameapp.repository.MyOrderDetailsRepository;
import com.tyss.strongameapp.repository.NotificationInformationRepository;
import com.tyss.strongameapp.repository.PackageDetailsRepository;
import com.tyss.strongameapp.repository.RemaindSessionRepo;
import com.tyss.strongameapp.repository.SessionDetailsRepository;
import com.tyss.strongameapp.repository.SessionNotificationRepository;
import com.tyss.strongameapp.repository.SpecializationContentRepository;
import com.tyss.strongameapp.repository.StudioBannerInformationRepository;
import com.tyss.strongameapp.repository.TaglineDetailsRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.util.MyFireBaseUtility;

/**
 * This is the implementation class to fetch contents of studio page.
 * 
 * @author SushmaGuttal
 *
 */
@Service
public class StudioSeviceImple implements StudioService {

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private CoachDetailsRepo coachRepo;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private SessionDetailsRepository sessionRepo;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private PackageDetailsRepository packageRepo;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private SessionNotificationRepository sessionNotificationRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private MyOrderDetailsRepository myOrderRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private TaglineDetailsRepository taglineRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private NotificationInformationRepository notificationRepository;

	/**
	 * This field is used to fetch fire base layer methods.
	 */
	@Autowired
	private MyFireBaseUtility firebaseService;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private StudioBannerInformationRepository studioBannerRepo;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private LiveModuleRepository liveModuleRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	private RemaindSessionRepo remaindSessionRepo;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	SpecializationContentRepository specializationRepository;

	/**
	 * This field is used to fetch persistence layer methods.
	 */
	@Autowired
	LiveContentRepository liveContentRepository;

	/**
	 * This method is implemented to fetch all coaches of live sessions.
	 * 
	 * @return List<CoachForSessionDetailsDto>
	 */
	@Override
	public List<CoachForSessionDetailsDto> getAllCoaches() {
		List<CoachDetails> coachList = coachRepo.findAll();
		if (coachList.isEmpty()) {
			return Collections.emptyList();
		} else {
			List<CoachForSessionDetailsDto> coachDtoList = new ArrayList<>();
			for (CoachDetails coachDetails : coachList) {
				CoachForSessionDetailsDto coachDto = new CoachForSessionDetailsDto();
				BeanUtils.copyProperties(coachDetails, coachDto);
			}
			return coachDtoList;
		}
	}// End of getAllCoaches method.

	/**
	 * This method is implemented to fetch todays all sessions.
	 * 
	 * @return List<SessionDetailsDto>
	 */
	@Override
	public TodaysLiveSessionDto getTodaySessions(int userId) {

		TodaysLiveSessionDto list = new TodaysLiveSessionDto();
		UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);

		Calendar currentCalenderDate = Calendar.getInstance();
		java.util.Date currentUtilDate = currentCalenderDate.getTime();

		List<SessionDetails> userSessions = userEntity.getUserSessions();

		if (userEntity.getPackageExpiryDate() == null) {
			list.setFlag(false);
		}
		list.setFlag(!currentUtilDate.after(userEntity.getPackageExpiryDate()));
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date date = new java.sql.Date(utilDate.getTime());
		List<SessionDetails> sessionList = sessionRepo.getTodaySession(date);
		if (sessionList.isEmpty()) {
			list.setSessionList(null);
		} else {
			List<SessionDetailsDto> sessionDtoList = new ArrayList<>();
			for (SessionDetails sessionDetails : sessionList) {
				SessionDetailsDto sessionDto = new SessionDetailsDto();
				BeanUtils.copyProperties(sessionDetails, sessionDto);
				Calendar sessionDate = Calendar.getInstance();
				sessionDate.setTime(sessionDto.getSessionTime());
				sessionDate.add(Calendar.MINUTE, -15);

				Calendar currentDate = Calendar.getInstance();
				currentDate.add(Calendar.MINUTE, 330);

				SimpleDateFormat sessionDateSDF = new SimpleDateFormat(LiveSessionConstants.SIMPLE_DATE_FORMAT);
				String sessionDateString = sessionDateSDF.format(sessionDate.getTime());

				SimpleDateFormat currentDateSDF = new SimpleDateFormat(LiveSessionConstants.SIMPLE_DATE_FORMAT);
				String currentDateString = currentDateSDF.format(currentDate.getTime());

				if (userSessions.stream().anyMatch(s -> s.getSessionId() == sessionDetails.getSessionId())) {
					sessionDto.setUserSessionMapped(true);
				}

				if (currentDateString.compareToIgnoreCase(sessionDateString) > 0) {
					sessionDto.setSessionFlag(true);
				}
				sessionDto.setPhoto(sessionDetails.getCoachForSession().getPhoto());
				sessionDto.setSessionCoachName(sessionDetails.getCoachForSession().getCoachName());
				sessionDtoList.add(sessionDto);

				Comparator<SessionDetailsDto> c = (p, o) -> p.getSessionDate().compareTo(o.getSessionDate());
				c = c.thenComparing((p, o) -> p.getSessionTime().compareTo(o.getSessionTime()));
				sessionDtoList.sort(c);
			}

			list.setSessionList(sessionDtoList);
		}
		return list;
	}// End of getTodaySessions method.

	/**
	 * This method is implemented to fetch list of packages
	 * 
	 * @return List<PackageDetailsDto>
	 */
	@Override
	public PackageTypeUserDto getPackageList() {
		List<PackageDetails> findAll = packageRepo.findAll();
		List<PackageDetails> packageList = findAll.stream().filter(x -> x.getPackageType().equalsIgnoreCase("regular"))
				.collect(Collectors.toList());
		List<PackageDetails> polularPackageList = findAll.stream()
				.filter(x -> x.getPackageType().equalsIgnoreCase("popular")).collect(Collectors.toList());
		if (packageList.isEmpty() && polularPackageList.isEmpty())
			return null;
		else {
			List<RecentUserLayer> recentUserLayers = userRepo.getAllSubscribedUsers();
			List<String> subscribedUserImages = recentUserLayers.stream().map(RecentUserLayer::getPhoto)
					.collect(Collectors.toList());
			Collections.reverse(subscribedUserImages);
			subscribedUserImages = subscribedUserImages.stream().limit(3).collect(Collectors.toList());
			PackageTaglineDetailsDto regularTag = new PackageTaglineDetailsDto();
			BeanUtils.copyProperties(
					packageList.stream().filter(p -> p.getPackageTagline() != null)
							.map(PackageDetails::getPackageTagline).findFirst().orElse(new TaglineDetails()),
					regularTag);
			PackageTaglineDetailsDto popularTag = new PackageTaglineDetailsDto();
			BeanUtils.copyProperties(
					polularPackageList.stream().filter(p -> p.getPackageTagline() != null)
							.map(PackageDetails::getPackageTagline).findFirst().orElse(new TaglineDetails()),
					popularTag);
			List<PackageDetailsDto> packageDtoList = new ArrayList<>();
			for (PackageDetails packageDetails : packageList) {
				PackageDetailsDto packageDto = new PackageDetailsDto();
				BeanUtils.copyProperties(packageDetails, packageDto);
				packageDtoList.add(packageDto);
			}
			List<PackageDetailsDto> popularPackageDtoList = new ArrayList<>();
			for (PackageDetails packageDetails : polularPackageList) {
				PackageDetailsDto packageDto = new PackageDetailsDto();
				BeanUtils.copyProperties(packageDetails, packageDto);
				popularPackageDtoList.add(packageDto);
			}
			PackageTypeUserDto packageType = new PackageTypeUserDto();
			PopularRegularPackageDto popular = new PopularRegularPackageDto();
			popular.setPackageType("popular");
			popular.setPackages(popularPackageDtoList);
			popular.setTaglineDetails(popularTag);
			PopularRegularPackageDto regular = new PopularRegularPackageDto();
			regular.setPackageType("regular");
			regular.setPackages(packageDtoList);
			regular.setTaglineDetails(regularTag);
			List<PopularRegularPackageDto> dtos = new ArrayList<>();
			dtos.add(regular);
			dtos.add(popular);
			packageType.setRegularPopularPackages(dtos);
			packageType.setSubscribedUserImages(subscribedUserImages);
			packageType.setCount(recentUserLayers.stream().count());
			return packageType;
		}
	}// End of getPackageList method.

	/**
	 * This method is implemented to join specified session
	 * 
	 * @param userId
	 * @param sessionId
	 * @return SessionDetailsDto
	 * @throws FirebaseMessagingException
	 */
	@Override
	public SessionDetailsDto joinSession(int userId, int sessionId) throws FirebaseMessagingException {
		UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		SessionDetails sessionEntity = sessionRepo.findById(sessionId)
				.orElseThrow(() -> new SessionException("Session Not Found"));

		SessionDetailsDto sessionDto = new SessionDetailsDto();
		BeanUtils.copyProperties(sessionEntity, sessionDto);

		int slotsAvailable = sessionEntity.getSlotsAvailable();

		if (slotsAvailable == 0) {
			sessionDto.setCases(5);
			return sessionDto;
		}

		java.util.Date date = new java.util.Date();

		Calendar sessionDate = Calendar.getInstance();
		sessionDate.setTime(sessionDto.getSessionDate());

		Calendar sessionTime = Calendar.getInstance();
		sessionTime.setTime(sessionDto.getSessionTime());
		sessionTime.add(Calendar.MINUTE, -15);

		sessionDate.set(Calendar.HOUR_OF_DAY, sessionTime.get(Calendar.HOUR_OF_DAY));
		sessionDate.set(Calendar.MINUTE, sessionTime.get(Calendar.MINUTE));
		sessionDate.set(Calendar.SECOND, sessionTime.get(Calendar.MINUTE));
		sessionDate.set(Calendar.SECOND, sessionTime.get(Calendar.SECOND));

		Calendar currentCalenderDate = Calendar.getInstance();

		java.util.Date packageInvalidDate = userEntity.getPackageExpiryDate();

		if (packageInvalidDate == null) {
			sessionDto.setCases(1);
			return sessionDto;
		} else if (date.after(packageInvalidDate)) {
			sessionDto.setCases(2);
			return sessionDto;

		} else if (currentCalenderDate.after(sessionDate)) {
			sessionDto.setCases(6);
			return sessionDto;
		} else {
			SessionDetails session = sessionRepo.isUserSessionMapped(userId, sessionId);
			if (session == null) {

				SessionNotificationDetails sessionNotificationDetails = new SessionNotificationDetails();
				sessionNotificationDetails.setSessionNotificationDescription(userEntity.getName() + "("
						+ userEntity.getEmail() + ")" + " " + "Wants to Join the Live Session of" + " "
						+ sessionDto.getSessionName() + " " + "by" + " " + sessionDto.getSessionCoachName() + " " + "on"
						+ " " + sessionDto.getSessionDate() + " " + "at" + " " + sessionDto.getSessionTime());
				sessionNotificationDetails.setSessionNotificationType("session");

				sessionNotificationDetails.setSessionNotificationUser(userEntity);
				sessionNotificationRepository.save(sessionNotificationDetails);

				userEntity.getSessionNotifications().add(sessionNotificationDetails);
				userEntity.getUserSessions().add(sessionEntity);
				userEntity = userRepo.save(userEntity);
				sessionEntity.setSlotsAvailable(slotsAvailable - 1);
				SessionDetails updatedSession = sessionRepo.save(sessionEntity);
				return getLiveSessionsDTO(updatedSession, userEntity.getUserSessions(), currentCalenderDate);

			} else {
				sessionDto.setCases(3);
				return sessionDto;
			}
		}
	}// End of joinSession method.

	/**
	 * This method is implemented to book package.
	 * 
	 * @param userId
	 * @param packageId
	 * @return PackageDetailsDto
	 * @throws FirebaseMessagingException
	 */
	@Override
	public PackageDetailsDto bookPackage(BookNowDto bookNowDto) throws FirebaseMessagingException {
		UserInformation userEntity = userRepo.findById(bookNowDto.getUserId()).orElseThrow(UserNotExistException::new);
		PackageDetails packageEntity = packageRepo.findById(bookNowDto.getRegularPackageId())
				.orElseThrow(() -> new PackageException("Package Not Found"));
		PackageDetailsDto packageDto = new PackageDetailsDto();

		double packageDuration = packageEntity.getPackageDuration();
		int packageDurationInMins = (int) (packageDuration * 10080);

		java.util.Date date = new java.util.Date();

		Calendar nowTwo = Calendar.getInstance();
		nowTwo.setTime(date);
		nowTwo.add(Calendar.MINUTE, 330 + packageDurationInMins);
		java.util.Date dateTwo = nowTwo.getTime();

		userEntity.setPackageExpiryDate(dateTwo);

		userEntity.getUserPackages().add(packageEntity);

		NotificationInformation userNotification = new NotificationInformation();
		userNotification.setNotificationDetails("You Have Enrolled Online Session Package of Duration" + " "
				+ ((int) packageEntity.getPackageDuration()) + " " + "Months");
		userNotification.setNotificationType("specific");

		NotificationInformation notification = new NotificationInformation();
		notification
				.setNotificationDetails(userEntity.getName() + " " + "has Enrolled Online Session Package of Duration"
						+ " " + ((int) packageEntity.getPackageDuration()) + " " + "Months");
		notification.setNotificationType("package");

		MyOrderDetails myOrder = new MyOrderDetails();
		myOrder.setName(packageEntity.getPackageName());

		double discount = packageEntity.getOfferPercentage();
		double savings = (discount * packageEntity.getActualPrice()) / 100;
		double finalPrice = packageEntity.getActualPrice() - savings;
		myOrder.setPrice(finalPrice);

		myOrder.setType("package");
		myOrder.setImage(packageEntity.getPackageIcon());
		myOrder.setUserMyOrder(userEntity);
		myOrderRepository.save(myOrder);
		userEntity.getMyorder().add(myOrder);

		/**
		 * 
		 * if (!bookNowDto.getAddOnIds().isEmpty()) {
		 * notification.setNotificationDetails( userEntity.getName() + " " + "has
		 * enrolled online session package of duration" + " " + ((int)
		 * packageEntity.getPackageDuration()) + " " + "months along with");
		 * userNotification. setNotificationDetails("You have enrolled online session
		 * package of duration " + ((int) packageEntity.getPackageDuration()) + " " +
		 * "months along with"); List<Integer> addOnId = bookNowDto.getAddOnIds(); for
		 * (Integer integer : addOnId) { Optional<PackageDetails> addOnPackage =
		 * packageRepo.findById(integer); if (addOnPackage.isPresent()) { MyOrderDetails
		 * myOrderAddOn = new MyOrderDetails();
		 * myOrderAddOn.setName(addOnPackage.get().getPackageName());
		 * myOrderAddOn.setType("Add-on");
		 * myOrderAddOn.setImage(addOnPackage.get().getPackageIcon());
		 * myOrderAddOn.setPrice(addOnPackage.get().getActualPrice());
		 * 
		 * double offerPercentage = addOnPackage.get().getOfferPercentage(); double
		 * saving = (offerPercentage * addOnPackage.get().getActualPrice()) / 100;
		 * double finalAddOnPrice = addOnPackage.get().getActualPrice() - saving;
		 * myOrderAddOn.setPaidProductPrice(finalAddOnPrice);
		 * 
		 * myOrderAddOn.setUserMyOrder(userEntity);
		 * myOrderRepository.save(myOrderAddOn);
		 * 
		 * userNotification.setNotificationDetails(userNotification.
		 * getNotificationDetails() + " " + addOnPackage.get().getPackageName() + ", ");
		 * notification.setNotificationDetails( notification.getNotificationDetails() +
		 * " " + addOnPackage.get().getPackageName() + ", ");
		 * userEntity.getMyorder().add(myOrderAddOn);
		 * 
		 * userEntity.getUserPackages().add(addOnPackage.get()); } } }
		 */
		userEntity.getNotificaton().add(userNotification);
		notificationRepository.save(notification);
		userRepo.save(userEntity);

		firebaseService.sendTokenNotification(userEntity.getFirebaseToken(), userNotification.getNotificationDetails(),
				userNotification.getNotificationImage());

		BeanUtils.copyProperties(packageEntity, packageDto);

		return packageDto;
	}// End of bookPackage method.

	/**
	 * This method is used to fetch package details by package id.
	 * 
	 * @param packageId
	 * @return List<PackageDetailsDto>
	 */
	@Override
	public List<PackageDetailsDto> getPackageById(int packageId) {
		Optional<PackageDetails> optionalRegularPackage = packageRepo.findById(packageId);
		List<PackageDetailsDto> packageDtoList = new ArrayList<>();
		if (optionalRegularPackage.isPresent()) {
			PackageDetails regularPackage = optionalRegularPackage.get();
			List<PackageDetails> addOn = packageRepo.getAddOn();
			List<PackageDetails> packageList = new ArrayList<>();
			packageList.add(regularPackage);
			packageList.addAll(addOn);

			for (PackageDetails packageDetails : packageList) {
				PackageDetailsDto packageDto = new PackageDetailsDto();
				BeanUtils.copyProperties(packageDetails, packageDto);
				packageDtoList.add(packageDto);
			}

		}
		return packageDtoList;
	}// End of getPackageById method.

	/**
	 * This method is used to fetch tagline details
	 * 
	 * @return TaglineDetailsDto
	 */
	@Override
	public TaglineDetailsDto getTagLineDetails() {
		List<TaglineDetails> taglineDetailsList = taglineRepository.findAll();
		if (!taglineDetailsList.isEmpty()) {
			TaglineDetails taglineDetails = taglineDetailsList.get(taglineDetailsList.size() - 1);

			TaglineDetailsDto taglineDetailsDto = new TaglineDetailsDto();
			BeanUtils.copyProperties(taglineDetails, taglineDetailsDto);

			List<UserInformation> userList = userRepo.getUserDescend();

			List<String> images = new ArrayList<>();
			for (UserInformation userInformation : userList) {
				if (userInformation.getPhoto() != null) {
					images.add(userInformation.getPhoto());
				}
			}
			int userCount = userList.size();
			taglineDetailsDto.setUserCount(userCount);
			taglineDetailsDto.getUserImages().addAll(images);

			return taglineDetailsDto;

		} else
			return null;
	}// End of getTagLineDetails method

	@Override
	public List<StudioBannerInformationDto> getStudioBanner() {
		List<StudioBannerInformation> allStudioBanner = studioBannerRepo.findAll();
		List<StudioBannerInformationDto> dtos = new ArrayList<>();
		if (!allStudioBanner.isEmpty()) {
			for (StudioBannerInformation bannerInformation : allStudioBanner) {
				StudioBannerInformationDto dto = new StudioBannerInformationDto();
				BeanUtils.copyProperties(bannerInformation, dto);
				LiveModuleContent moduleContent = bannerInformation.getModuleContent();
				if (moduleContent != null) {
					dto.setId(moduleContent.getLiveContentId());
					dto.setName(moduleContent.getLiveContentName());
				}
				dtos.add(dto);
			}
			return dtos;
		} else
			return Collections.emptyList();
	}

	/**
	 * This method is to check validity and get user packages
	 * 
	 * @param userId
	 * @return PackageSubscribeDto
	 */
	@Override
	public PackageSubscribeDto isSubscribe(int userId) {
		UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		Calendar currentCalenderDate = Calendar.getInstance();
		java.util.Date currentUtilDate = currentCalenderDate.getTime();
		List<PackageDetails> userPackageDetails = userEntity.getUserPackages();
		PackageSubscribeDto dto = new PackageSubscribeDto();
		PackageDetailsDto packageDto = new PackageDetailsDto();
		List<PackageDetailsDto> dtos = new ArrayList<>();
		if (!userPackageDetails.isEmpty() && userEntity.getPackageExpiryDate() != null) {
			if (currentUtilDate.before(userEntity.getPackageExpiryDate())) {
				dto.setSubcribe(true);
			}
		} else {
			dto.setSubcribe(false);
		}
		for (PackageDetails information : userPackageDetails) {
			BeanUtils.copyProperties(information, packageDto);
			dtos.add(packageDto);
		}
		dto.setPackageDetails(dtos);
		return dto;
	}

	@Override
	public TodaysLiveSessionDto getSessionsDetails(int userId, Date sessiondate) {
		TodaysLiveSessionDto list = new TodaysLiveSessionDto();
		UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		Calendar currentCalenderDate = Calendar.getInstance();
		java.util.Date currentUtilDate = currentCalenderDate.getTime();

		List<SessionDetails> userSessions = userEntity.getUserSessions();

		if (userEntity.getPackageExpiryDate() == null) {
			list.setFlag(false);
		}
		list.setFlag(!currentUtilDate.after(userEntity.getPackageExpiryDate()));
		List<SessionDetails> findAll = sessionRepo.findAll();
		List<SessionDetails> sessionList = findAll.stream().filter(x -> x.getSessionDate().equals(sessiondate))
				.collect(Collectors.toList());

		if (sessionList.isEmpty()) {
			list.setSessionList(null);
		} else {
			List<SessionDetailsDto> sessionDtoList = new ArrayList<>();
			for (SessionDetails sessionDetails : sessionList) {
				SessionDetailsDto sessionDto = new SessionDetailsDto();
				BeanUtils.copyProperties(sessionDetails, sessionDto);
				Calendar sessionDate = Calendar.getInstance();
				sessionDate.setTime(sessionDto.getSessionTime());
				sessionDate.add(Calendar.MINUTE, -15);

				Calendar currentDate = Calendar.getInstance();
				currentDate.add(Calendar.MINUTE, 330);

				SimpleDateFormat sessionDateSDF = new SimpleDateFormat(LiveSessionConstants.SIMPLE_DATE_FORMAT);
				String sessionDateString = sessionDateSDF.format(sessionDate.getTime());

				SimpleDateFormat currentDateSDF = new SimpleDateFormat(LiveSessionConstants.SIMPLE_DATE_FORMAT);
				String currentDateString = currentDateSDF.format(currentDate.getTime());

				if (userSessions.stream().anyMatch(s -> s.getSessionId() == sessionDetails.getSessionId())) {
					sessionDto.setUserSessionMapped(true);
				}

				if (currentDateString.compareToIgnoreCase(sessionDateString) > 0) {
					sessionDto.setSessionFlag(true);
				}
				sessionDto.setPhoto(sessionDetails.getCoachForSession().getPhoto());
				sessionDto.setSessionCoachName(sessionDetails.getCoachForSession().getCoachName());
				sessionDtoList.add(sessionDto);

				Comparator<SessionDetailsDto> c = (p, o) -> p.getSessionDate().compareTo(o.getSessionDate());
				c = c.thenComparing((p, o) -> p.getSessionTime().compareTo(o.getSessionTime()));
				sessionDtoList.sort(c);
			}

			list.setSessionList(sessionDtoList);
		}
		return list;
	}// End of getSessionsDetails method.

	/**
	 * This method is to get all LiveModule
	 * 
	 * @return List<LiveModuleDto>
	 */
	@Override
	public StudioModuleDto getallformates(int userId) {
		List<LiveModule> findAll = liveModuleRepository.findAll();
		StudioModuleDto dto = new StudioModuleDto();
		List<LiveModuleDto> dtos = new ArrayList<>();
		for (LiveModule liveModule : findAll) {
			LiveModuleDto data = new LiveModuleDto();
			List<SpecializationContentDto> specializationContentDtos = new ArrayList<>();
			BeanUtils.copyProperties(liveModule, data);
			for (SpecializationContent specializationContent : liveModule.getSpecializationContents()) {
				SpecializationContentDto specializationContentDto = new SpecializationContentDto();
				BeanUtils.copyProperties(specializationContent, specializationContentDto);
				specializationContentDtos.add(specializationContentDto);
				data.setSpecializationContents(specializationContentDtos);
			}
			dtos.add(data);
		}
		dto.setLiveModules(dtos);
		dto.setSubcribed(checkPackageSubcription(userId));
		return dto;
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

	/**
	 * This method is to store user have set a remainder
	 * 
	 * @param userId
	 * @param sessionId
	 * @return RemaindSession
	 */
	@Override
	@Transactional
	public RemaindSession remaindMe(int userId, int sessionId) throws FirebaseMessagingException {
		UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		SessionDetails sessionDetails = sessionRepo.findById(sessionId).orElse(null);
		if (sessionDetails != null) {
			List<RemaindSession> remaindSessionList = userEntity.getRemaindSession();
			List<RemaindSession> collect = remaindSessionList.stream().filter(x -> x.getRemaindSessionId() == sessionId)
					.collect(Collectors.toList());
			if (remaindSessionList.isEmpty() || collect.isEmpty()) {
				RemaindSession remaindSessionNew = new RemaindSession();
				remaindSessionNew.setRemainderFlag(true);
				remaindSessionNew.setSessionId(sessionId);
				remaindSessionNew.setRemaindSessionUser(userEntity);
				remaindSessionList.add(remaindSessionNew);
				remaindSessionRepo.save(remaindSessionNew);
				userEntity.setRemaindSession(remaindSessionList);
				userRepo.save(userEntity);
				firebaseService.sendNotificationOnTime(userEntity.getFirebaseToken(), sessionDetails, "image url");
				return remaindSessionNew;
			} else {
				RemaindSession remaindSession = collect.get(0);
				remaindSession.setRemainderFlag(!Boolean.TRUE.equals(remaindSession.getRemainderFlag()));
				remaindSessionRepo.save(remaindSession);
				firebaseService.sendNotificationOnTime(userEntity.getFirebaseToken(), sessionDetails, "image url");
				return remaindSession;
			}
		}
		return null;
	}

	/**
	 * This method is used to get content by specializationId
	 * 
	 * @param specializationId
	 * @return List<LiveModuleContentDto>
	 */
	@Override
	public List<LiveModuleContentDto> getcontentByspecialization(int specializationId) {
		List<LiveModuleContentDto> liveModuleContentDtos = new ArrayList<>();
		SpecializationContent collect = specializationRepository.findById(specializationId).orElse(null);
		if (collect != null) {
			for (LiveModuleContent content : collect.getLiveModuleContents()) {
				LiveModuleContentDto contentDto = new LiveModuleContentDto();
				BeanUtils.copyProperties(content, contentDto);
				contentDto.setBoardUsers(getStreamUSers(content));
				liveModuleContentDtos.add(contentDto);
			}
			return liveModuleContentDtos;
		}

		return Collections.emptyList();
	}

	/**
	 * This method is used to redirect to content using studioId
	 * 
	 * @param studioId
	 * @return LiveModuleContentDto
	 */
	@Override
	public LiveModuleContentDto redirectContent(int studioId) {
		StudioBannerInformation bannerInformation = studioBannerRepo.findById(studioId).orElse(null);
		if (bannerInformation != null) {
			LiveModuleContentDto contentDto = new LiveModuleContentDto();
			LiveModuleContent liveModuleContent = bannerInformation.getModuleContent();
			BeanUtils.copyProperties(liveModuleContent, contentDto);
			contentDto.setBoardUsers(getStreamUSers(liveModuleContent));
			return contentDto;
		}
		return null;
	}

	/**
	 * This method is to fetch live content by id
	 * 
	 * @param liveContentdId
	 * @param userId
	 * @return LiveModuleContentDto
	 */
	@Override
	public LiveModuleContentDto getliveContentById(int liveContentdId, int userId) {
		LiveModuleContent liveModuleContent = liveContentRepository.findById(liveContentdId).orElse(null);
		if (liveModuleContent != null) {
			LiveModuleContentDto contentDto = new LiveModuleContentDto();
			BeanUtils.copyProperties(liveModuleContent, contentDto);
			contentDto.setBoardUsers(getStreamUSers(liveModuleContent));
			contentDto.setSubcribed(checkPackageSubcription(userId));
			return contentDto;
		}
		return null;
	}

	/**
	 * This method is to get specialization by id
	 * 
	 * @param specializationId
	 * @return SpecializationModuleContentDto
	 */
	@Override
	public SpecializationModuleContentDto getSpecializationById(int specializationId) {
		SpecializationContent orElse = specializationRepository.findById(specializationId).orElse(null);
		SpecializationModuleContentDto dto = new SpecializationModuleContentDto();
		if (orElse != null) {
			List<LiveModuleContentDto> liveModuleContentDtos = new ArrayList<>();
			BeanUtils.copyProperties(orElse, dto);
			for (LiveModuleContent liveModuleContent : orElse.getLiveModuleContents()) {
				LiveModuleContentDto contentDto = new LiveModuleContentDto();
				BeanUtils.copyProperties(liveModuleContent, contentDto);
				contentDto.setBoardUsers(getStreamUSers(liveModuleContent));
				liveModuleContentDtos.add(contentDto);
			}
			dto.setLiveModuleContents(liveModuleContentDtos);
			return dto;
		}
		return null;
	}

	@Override
	public Date getCurrentDate() {
		long millis = System.currentTimeMillis();
		return new java.sql.Date(millis);
	}

	/**
	 * This method is used to fetch week's session list
	 * 
	 * @param dates
	 * @param userId
	 * 
	 * @return WeeksLiveSessionsDTO
	 * 
	 */
	@Override
	public WeeksLiveSessionsDTO getWeekSessions(List<Date> dates, int userId) {
		UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		WeeksLiveSessionsDTO weeksLiveSessionsDTO = new WeeksLiveSessionsDTO();
		Calendar currentCalenderDate = Calendar.getInstance();
		java.util.Date currentUtilDate = currentCalenderDate.getTime();
		weeksLiveSessionsDTO.setPackageFlag(!(userEntity.getPackageExpiryDate() == null
				|| currentUtilDate.after(userEntity.getPackageExpiryDate())));
		List<SessionDetails> userSessions = userEntity.getUserSessions();
		List<TodaysLiveSessionDto> allDatesLiveSessions = new ArrayList<>();
		for (Date date : dates) {
			TodaysLiveSessionDto daysLiveSessions = new TodaysLiveSessionDto();
			List<SessionDetails> sessionList = sessionRepo.getTodaySession(date);
			List<SessionDetailsDto> sessionDtoList = new ArrayList<>();
			for (SessionDetails sessionDetails : sessionList) {
				sessionDtoList.add(getLiveSessionsDTO(sessionDetails, userSessions, currentCalenderDate));
			}
			Comparator<SessionDetailsDto> comparedSessions = (p, o) -> p.getSessionDate().compareTo(o.getSessionDate());
			comparedSessions = comparedSessions
					.thenComparing((p, o) -> p.getSessionTime().compareTo(o.getSessionTime()));
			sessionDtoList.sort(comparedSessions);
			daysLiveSessions.setSessionList(sessionDtoList);
			daysLiveSessions.setDate(date);
			allDatesLiveSessions.add(daysLiveSessions);

		}
		weeksLiveSessionsDTO.setDaySessionList(allDatesLiveSessions);
		return weeksLiveSessionsDTO;
	}// End of getTodaySessions method.

	/**
	 * This method is used to fetch LiveSessionDTO list
	 * 
	 * @param sessionDetails
	 * @param userSessions
	 * @param currentCalenderDate
	 * 
	 * @return SessionDetailsDto
	 * 
	 */
	public SessionDetailsDto getLiveSessionsDTO(SessionDetails sessionDetails, List<SessionDetails> userSessions,
			Calendar currentCalenderDate) {
		List<UserInformation> sessionUsers = sessionDetails.getSessionUsers();
		SessionDetailsDto sessionDto = new SessionDetailsDto();
		BeanUtils.copyProperties(sessionDetails, sessionDto);
		if (sessionUsers != null) {
			sessionDto.setImages(sessionUsers.stream().map(UserInformation::getPhoto).collect(Collectors.toList()));
			sessionDto.setUserCount(sessionUsers.size());
		}
		List<SessionDetails> userMappedSessions = userSessions.stream()
				.filter(s -> s.getSessionId() == sessionDetails.getSessionId()).collect(Collectors.toList());

		if (!userMappedSessions.isEmpty()) {
			sessionDto.setUserSessionMapped(true);
		}

		Calendar sessionDate = Calendar.getInstance();
		sessionDate.setTime(sessionDto.getSessionDate());

		Calendar sessionTime = Calendar.getInstance();
		sessionTime.setTime(sessionDto.getSessionTime());
		sessionTime.add(Calendar.MINUTE, -15);

		sessionDate.set(Calendar.HOUR_OF_DAY, sessionTime.get(Calendar.HOUR_OF_DAY));
		sessionDate.set(Calendar.MINUTE, sessionTime.get(Calendar.MINUTE));
		sessionDate.set(Calendar.SECOND, sessionTime.get(Calendar.MINUTE));
		sessionDate.set(Calendar.SECOND, sessionTime.get(Calendar.SECOND));
		if (currentCalenderDate.before(sessionDate)) {
			sessionDto.setSessionFlag(true);
		}
		sessionDto.setPhoto(sessionDetails.getCoachForSession().getPhoto());
		sessionDto.setSessionCoachName(sessionDetails.getCoachForSession().getCoachName());
		return sessionDto;

	}

	/**
	 * This method is to store stream studio content by users
	 * 
	 * @param userId
	 * @param liveContentId
	 */
	@Override
	@Transactional
	public void saveStudioStreamtime(int userId, int liveContentId) {
		UserInformation user = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		LiveModuleContent liveModuleContent = liveContentRepository.findById(liveContentId)
				.orElseThrow(ModuleContentException::new);
		java.util.Date date = new java.util.Date();
		List<StreamedStudioContent> streamedStudioContents = liveModuleContent.getStudioContentStreamed();
		StreamedStudioContent streamedStudioContent = streamedStudioContents.stream()
				.filter(x -> x.getStreamedStudioContentUser().getUserId() == userId).findFirst().orElse(null);
		if (streamedStudioContent != null) {
			streamedStudioContent.setStreamedTime(date);
			streamedStudioContent.setViewCount(streamedStudioContent.getViewCount() + 1);
		} else {
			streamedStudioContent = new StreamedStudioContent();
			streamedStudioContent.setStreamedTime(date);
			streamedStudioContent.setStreamedStudioContent(liveModuleContent);
			streamedStudioContent.setStreamedStudioContentUser(user);
			streamedStudioContent.setViewCount(1);
			streamedStudioContents.add(streamedStudioContent);
		}
	}

	/**
	 * This method is to get studio streamed content users
	 * 
	 * @param content
	 * @return List<RecentUser>
	 */
	private List<RecentUser> getStreamUSers(LiveModuleContent liveModuleContent) {
		List<UserInformation> users = liveModuleContent.getStudioContentStreamed().stream()
				.sorted((i, j) -> j.getStreamedTime().compareTo(i.getStreamedTime()))
				.map(StreamedStudioContent::getStreamedStudioContentUser).collect(Collectors.toList());

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

}
// End of LiveSessionSeviceImple class.
