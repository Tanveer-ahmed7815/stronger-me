package com.tyss.strongameapp.service;

import java.sql.Date;
import java.util.List;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.dto.BookNowDto;
import com.tyss.strongameapp.dto.CoachForSessionDetailsDto;
import com.tyss.strongameapp.dto.LiveModuleContentDto;
import com.tyss.strongameapp.dto.PackageDetailsDto;
import com.tyss.strongameapp.dto.PackageSubscribeDto;
import com.tyss.strongameapp.dto.PackageTypeUserDto;
import com.tyss.strongameapp.dto.SessionDetailsDto;
import com.tyss.strongameapp.dto.SpecializationModuleContentDto;
import com.tyss.strongameapp.dto.StudioBannerInformationDto;
import com.tyss.strongameapp.dto.StudioModuleDto;
import com.tyss.strongameapp.dto.TaglineDetailsDto;
import com.tyss.strongameapp.dto.TodaysLiveSessionDto;
import com.tyss.strongameapp.dto.WeeksLiveSessionsDTO;
import com.tyss.strongameapp.entity.RemaindSession;

/**
 * StudioService is implemented by StudioServiceImple class, which is used to
 * fetch contents of live session page.
 * 
 * @author Sushma Guttal
 *
 */
public interface StudioService {

	/**
	 * This method is implemented by its implementation class which is used to fetch
	 * all the coaches of live sessions.
	 * 
	 * @return List<CoachForSessionDetailsDto>
	 */
	List<CoachForSessionDetailsDto> getAllCoaches();

	/**
	 * This method is implemented by its implementation class which is used to fetch
	 * all todays live sessions.
	 * 
	 * @param userId
	 * @return List<SessionDetailsDto>
	 */
	TodaysLiveSessionDto getTodaySessions(int userId);

	/**
	 * This method is implemented by its implementation class which is used to get
	 * list of packages for live sessions.
	 * 
	 * @return List<PackageDetailsDto>
	 */
	PackageTypeUserDto getPackageList();

	/**
	 * This method is implemented by its implementation class which is used to join
	 * the session.
	 * 
	 * @param userId
	 * @param sessionId
	 * @return SessionDetailsDto
	 * @throws FirebaseMessagingException
	 */
	SessionDetailsDto joinSession(int userId, int sessionId) throws FirebaseMessagingException;

	/**
	 * This method is implemented by its implementation class to book the package.
	 * 
	 * @param userId
	 * @param packageId
	 * @param addOnId
	 * @return PackageDetailsDto
	 * @throws FirebaseMessagingException
	 */
	PackageDetailsDto bookPackage(BookNowDto bookNowDto) throws FirebaseMessagingException;

	/**
	 * This method is implemented by its implementation class to fetch package by
	 * its id.
	 * 
	 * @param packageId
	 * @return List<PackageDetailsDto>
	 */
	List<PackageDetailsDto> getPackageById(int packageId);

	/**
	 * This method is implemented by its implementation class to fetch tagline
	 * details
	 * 
	 * @return TaglineDetailsDto
	 */
	TaglineDetailsDto getTagLineDetails();

	/**
	 * This method is implemented by its implementation class to fetch studio
	 * banners details
	 * 
	 * @return List<StudioBannerInformationDto>
	 */
	List<StudioBannerInformationDto> getStudioBanner();

	/**
	 * This method is implemented by its implementation class to check user have
	 * subscribed to packages or not
	 * 
	 * @param userId
	 * @return PackageSubscribeDto
	 */
	PackageSubscribeDto isSubscribe(int userId);

	TodaysLiveSessionDto getSessionsDetails(int userId, Date sessiondate);

	/**
	 * This method is implemented by its implementation class to fetch all Live
	 * Modules
	 * 
	 * @param userId
	 * @return
	 */
	StudioModuleDto getallformates(int userId);

	/**
	 * This method is implemented by its implementation class to redirect to live
	 * content by studio banner id
	 * 
	 * @return LiveModuleContentDto
	 */
	LiveModuleContentDto redirectContent(int studioId);

	/**
	 * This method is implemented by its implementation class to turn on remain me
	 * 
	 * @return RemaindSession
	 */
	RemaindSession remaindMe(int userId, int sessionId) throws FirebaseMessagingException;

	/**
	 * This method is implemented by its implementation class to get content by
	 * specialization
	 * 
	 * @return List<LiveModuleContentDto>
	 */
	List<LiveModuleContentDto> getcontentByspecialization(int specializationId);

	/**
	 * This method is implemented by its implementation class to get live content by
	 * Id
	 * @param userId 
	 * 
	 * @return LiveModuleContentDto
	 */
	LiveModuleContentDto getliveContentById(int liveContentdId, int userId);

	/**
	 * This method is implemented by its implementation class to get Specialization
	 * by Id
	 * 
	 * @return SpecializationModuleContentDto
	 */
	SpecializationModuleContentDto getSpecializationById(int specializationId);

	Date getCurrentDate();

	public WeeksLiveSessionsDTO getWeekSessions(List<Date> dates, int userId);

	/**
	 * This method is implemented by its implementation class to store studio
	 * content stream by users
	 * 
	 * @param userId
	 * @param liveContentId
	 */
	void saveStudioStreamtime(int userId, int liveContentId);

}// End of LiveSessionService interface
