package com.tyss.strongameapp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.strongameapp.dto.LeaderBoardPositionsDto;
import com.tyss.strongameapp.dto.MyOrderDto;
import com.tyss.strongameapp.dto.NotificationInformationDto;
import com.tyss.strongameapp.dto.UserInformationDto;

/**
 * SettingsService is implemented by SettingsServiceImple class to display the
 * content of setting page.
 * 
 * @author Sushma Guttal
 *
 */
public interface SettingsService {

	/**
	 * This method is implemented by its implementation class, used to edit the user
	 * profile.
	 * 
	 * @param data
	 * @param image
	 * @return UserInformationDto
	 */
	UserInformationDto editUser(UserInformationDto data);

	/**
	 * This method is implemented by its implementation class, used to display the
	 * leader board.
	 * 
	 * @param userId
	 * @return LeaderBoardDto
	 */
	LeaderBoardPositionsDto leadBoard(int userId);

	/**
	 * This method is implemented by its implementation class, to display the
	 * contend of my order page.
	 * 
	 * @param userId
	 * @return MyOrderDto
	 */
	List<MyOrderDto> myOrder(Integer userId);

	/**
	 * This method is implemented by its implementation class, to fetch all the
	 * notifications.
	 * 
	 * @param userId
	 * @return List<NotificationInformationDto>
	 */
	List<NotificationInformationDto> notification(Integer userId);

	/**
	 * This method is implemented by its implementation class, to update profile
	 * image
	 * 
	 * @param data
	 * @param image
	 * @return UserInformationDto
	 */
	UserInformationDto uploadImage(UserInformationDto data, MultipartFile image);

	/**
	 * This method is implemented by its implementation class, to generate referral
	 * code
	 * 
	 * @param userId
	 * @return String
	 */
	String referenceCode(Integer userId);

}// End of SettingsService interface.
