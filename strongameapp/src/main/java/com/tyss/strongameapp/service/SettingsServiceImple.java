package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.strongameapp.dto.LeaderBoardPositionsDto;
import com.tyss.strongameapp.dto.LeaderBoardUsersDto;
import com.tyss.strongameapp.dto.MyOrderDto;
import com.tyss.strongameapp.dto.NotificationInformationDto;
import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.entity.MyOrderDetails;
import com.tyss.strongameapp.entity.NotificationInformation;
import com.tyss.strongameapp.entity.OrderInformation;
import com.tyss.strongameapp.entity.SavedAddress;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.UserNotExistException;
import com.tyss.strongameapp.repository.NotificationInformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.util.S3UploadFile;

/**
 * SettingsServiceImple is implemented to display the content of setting page.
 * 
 * @author Sushma Guttal
 *
 */
@Service
public class SettingsServiceImple implements SettingsService {

	/**
	 * This field is used to invoke persistence layer method.
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is used to invoke persistence layer method.
	 */
	@Autowired
	private NotificationInformationRepository notificationRepo;

	/**
	 * This field is used to invoke business layer method.
	 */
	@Autowired
	private MyShopService myShopService;

	@Autowired(required = true)
	private S3UploadFile s3UploadFile;

	private static final Random RANDOM = new Random();

	/**
	 * This method is implemented to edit user profile.
	 * 
	 * @param data
	 * @return UserInformationDto
	 */
	@Override
	public UserInformationDto editUser(UserInformationDto data) {
		boolean nameFlag = CustomUserDetailService.isValidName(data.getName());
		data.setCases("");
		if (!nameFlag) {
			data.setCases(data.getCases() + " Enter Valid Name.");
		}
		if (data.getHeight() <= 0) {
			data.setCases(data.getCases() + " Height Cannot be Zero or Negative.");
		}
		if (data.getWeight() <= 0) {
			data.setCases(data.getCases() + " Weight Cannot be Zero or Negative.");
		}
		if (data.getGender() != null && !data.getGender().equalsIgnoreCase("Female")
				&& !data.getGender().equalsIgnoreCase("Male")) {
			data.setCases(data.getCases() + " Enter Valid Gender.");
		}
		if (!data.getCases().equalsIgnoreCase("")) {
			return data;
		}
		UserInformation user = userRepo.findUserById(data.getUserId());
		if (user != null) {
			Date dateOfBirth = data.getDateOFBirth();
			user.setDateOFBirth(dateOfBirth);
			String gender = data.getGender();
			user.setGender(gender);
			double height = data.getHeight();
			user.setHeight(height);
			String name = data.getName();
			user.setName(name);
			double weight = data.getWeight();
			user.setWeight(weight);
			UserInformation updatedUser = userRepo.save(user);
			BeanUtils.copyProperties(updatedUser, data);
			return data;
		} else
			return null;
	}
//End of editUser method.

	/**
	 * This method is implemented to display the leaderboard.
	 * 
	 * @param userId
	 * @return LeaderBoardDto
	 */
	@Override
	public LeaderBoardPositionsDto leadBoard(int userId) {
		UserInformation userEntity = userRepo.findUserById(userId);
		if (userEntity != null) {
			List<UserInformation> userList = userRepo.findAll();
			LeaderBoardPositionsDto boardPositionsDto = new LeaderBoardPositionsDto();
			List<LeaderBoardUsersDto> leaderBoardUsersDtoList = new ArrayList<>();
			for (UserInformation userInformation : userList) {
				LeaderBoardUsersDto leaderBoardUsersDto = new LeaderBoardUsersDto();
				leaderBoardUsersDto.setCoins(myShopService.getCoin(userInformation));
				BeanUtils.copyProperties(userInformation, leaderBoardUsersDto);
				leaderBoardUsersDtoList.add(leaderBoardUsersDto);
			}
			List<LeaderBoardUsersDto> collect = leaderBoardUsersDtoList.stream()
					.sorted((o1, o2) -> Double.compare(o2.getCoins(), o1.getCoins())).limit(50)
					.collect(Collectors.toList());
			int position = 0;
			for (LeaderBoardUsersDto boardUsersDto : collect) {
				boardUsersDto.setPosition(++position);
				if (boardUsersDto.getUserId() == userId)
					boardPositionsDto.setCurrentUserPosition(position);
			}
			boardPositionsDto.setBoardUsers(collect);
			return boardPositionsDto;
		} else
			return null;
	}// End of leadBoard method.

	/**
	 * This method is implemented to display the content of my order page
	 * 
	 * @param userId
	 * @return MyOrderDto
	 */
	@Override
	public List<MyOrderDto> myOrder(Integer userId) {
		if (userId != null) {
			UserInformation userEntity = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
			List<MyOrderDto> dtos = new ArrayList<>();
			List<MyOrderDetails> myOrderDetails = userEntity.getMyorder();
			if (!myOrderDetails.isEmpty()) {
				for (MyOrderDetails myOrder : myOrderDetails) {
					MyOrderDto dto = new MyOrderDto();
					OrderInformation information = myOrder.getOrderInformation();
					if (information != null) {
						dto.setOrderDate(information.getOrderDate());
						SavedAddress savedAddress = information.getSavedAddress();
						dto.setPhoneNumber(savedAddress.getPhoneNumber());
						dto.setUserName(savedAddress.getUserName());
						dto.setAddress(savedAddress.getAddressLine1() + " " + savedAddress.getAddressLine2() + " "
								+ savedAddress.getCity() + " " + savedAddress.getState() + " "
								+ savedAddress.getCountry() + " - " + savedAddress.getPincode());

					}
					BeanUtils.copyProperties(myOrder, dto);
					dtos.add(dto);
				}
			}
			Collections.reverse(dtos);
			return dtos;
		} else
			throw new com.tyss.strongameapp.exception.UserException("User Data Cannot be Null");

	}// End of myOrder method.

	/**
	 * This method is implemented to fetch list of notifications.
	 * 
	 * @param userId
	 * @return List<NotificationInformationDto>
	 */
	@Override
	public List<NotificationInformationDto> notification(Integer userId) {
		UserInformation userEntity = userRepo.findUserById(userId);
		if (userEntity != null) {
			List<NotificationInformation> notificationList = userEntity.getNotificaton();
			List<NotificationInformation> genericNotification = notificationRepo.getGenericNotification();
			notificationList.addAll(genericNotification);
			List<NotificationInformationDto> notificationDtoList = new ArrayList<>();
			for (NotificationInformation notificationInformation : notificationList) {
				NotificationInformationDto notificationDto = new NotificationInformationDto();
				BeanUtils.copyProperties(notificationInformation, notificationDto);
				notificationDtoList.add(notificationDto);
			}
			Collections.reverse(notificationDtoList);
			return notificationDtoList;
		} else
			return Collections.emptyList();
	}// End of notification method

	/**
	 * This method is used to update profile image
	 * 
	 * @param data
	 * @param image
	 * @return UserInformationDto
	 */
	@Override
	public UserInformationDto uploadImage(UserInformationDto data, MultipartFile image) {
		UserInformation userEntity = userRepo.findUserById(data.getUserId());
		if (userEntity != null) {
			if (userEntity.getPhoto() != null) {
				s3UploadFile.deleteS3Folder(userEntity.getPhoto());
			}
			userEntity.setPhoto(s3UploadFile.uploadFile(image));
			UserInformation updatedUser = userRepo.save(userEntity);
			BeanUtils.copyProperties(updatedUser, data);
			return data;
		} else
			return null;
	}

	/**
	 * This method is used to generate referral code
	 * 
	 * @param userId
	 * @return String
	 */
	@Override
	public String referenceCode(Integer userId) {
		UserInformation userEntity = userRepo.findUserById(userId);
		if (userEntity != null) {
			String generateCode = userEntity.getReferalCode();
			if (generateCode == null || generateCode.isEmpty()) {
				generateCode = RANDOM.ints(48, 122 + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
						.limit(8).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						.toString();
				userEntity.setReferalCode(generateCode);
				userRepo.save(userEntity);
			}
			return generateCode;
		} else
			return null;
	}

}// End of SettingsServiceImple class.
