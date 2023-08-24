package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.tyss.strongameapp.dto.LogoutDTO;
import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.entity.AdminRewardDetails;
import com.tyss.strongameapp.entity.RewardDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.entity.UserStepsStats;
import com.tyss.strongameapp.exception.FireBaseDeviceNotRegister;
import com.tyss.strongameapp.repository.AdminRewardInformationRepository;
import com.tyss.strongameapp.repository.RewardDetailsRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.repository.UserStepsRepository;

/**
 * CustomUserDetailService is used to save and log out the user.
 * 
 * @author Sushma Guttal
 *
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private RewardDetailsRepository rewardRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	UserInformationRepository userRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private UserInformationRepository dao;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private UserStepsRepository stepsRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private AdminRewardInformationRepository adminRewardRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private MyShopService myShopService;

	@Autowired
	private FirebaseMessaging firebaseMessaging;

	/**
	 * This method is used to load the user by name.
	 * 
	 * @param email
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserInformation userInfo = dao.findByUsername(email);
		if (userInfo == null) {
			throw new UsernameNotFoundException("Email does not exist");
		}
		if (userInfo.getPassword() != null)
			return new org.springframework.security.core.userdetails.User(userInfo.getEmail(), userInfo.getPassword(),
					new ArrayList<>());
		else
			return new org.springframework.security.core.userdetails.User(userInfo.getEmail(), userInfo.getEmail(),
					new ArrayList<>());

	}// End of load user by name method

	/**
	 * This method is used to save user details after successful registration.
	 * 
	 * @param user
	 * @param token
	 * @return UserInformationDto
	 * @throws FirebaseMessagingException
	 * @throws MessagingException
	 */
	public UserInformationDto save(UserInformationDto user, String token)
			throws FirebaseMessagingException, MessagingException {
		user.setCases(validateUser(user));

		RewardDetails reward = new RewardDetails();
		reward.setNoOfSteps(0);

//		ReferenceCode Check
		String result = rewardOnReference(user.getFriendRefereneCode());
		try {
			reward.setRewardCoins(Double.parseDouble(result));
		} catch (NumberFormatException e) {
			reward.setRewardCoins(0);
			user.setCases(user.getCases() + result);
		}

		if (!user.getCases().equalsIgnoreCase("")) {
			return user;
		}

		UserInformation userEntity = userRepo.getuserId(user.getEmail());
		UserInformation userEntityTwo = userRepo.getUserByPhone(user.getMobileNo());
		if (userEntity == null && userEntityTwo == null) {
			UserInformation newUser = new UserInformation();
			newUser.setName(user.getName());
			newUser.setEmail(user.getEmail());
			if (user.getPassword() != null) {
				newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			}
			if (user.getConfirmPassword() != null) {
				newUser.setConfirmPassword(bcryptEncoder.encode(user.getConfirmPassword()));
			}
			newUser.setPhoto(user.getPhoto());
			newUser.setDateOFBirth(user.getDateOFBirth());
			newUser.setGender(user.getGender());
			newUser.setHeight(user.getHeight());
			newUser.setWeight(user.getWeight());
			newUser.setMobileNo(user.getMobileNo());
			newUser.setReferenceCount(true);
			myShopService.sendMail(user.getEmail(), user.getName(), "register");
			newUser.setReward(reward);
			AdminRewardDetails adminReward = new AdminRewardDetails();
			adminReward.setAdminRewardCoins(0);
			newUser.setAdminReward(adminReward);
			UserStepsStats userStepsEntity = new UserStepsStats();
			userStepsEntity.setCoinsEarned(0);
			userStepsEntity.setCurrentSteps(0);
			userStepsEntity.setDay(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			newUser.setSteps(userStepsEntity);
//			FireBase Subscribe for everyUser
			newUser.setFirebaseToken(user.getFirebaseToken());
			newUser.setDeviceId(user.getDeviceId());
			newUser.setJwtToken(token);
			TopicManagementResponse response = firebaseMessaging
					.subscribeToTopic(Arrays.asList(user.getFirebaseToken()), "StrongerMe");
			if (response.getSuccessCount() == 0) {
				throw new FireBaseDeviceNotRegister(" Device is Not Registered with Firebase ");
			}
			dao.save(newUser);
			rewardRepo.save(reward);
			adminRewardRepo.save(adminReward);
			stepsRepo.save(userStepsEntity);
			user.setToken(token);
			BeanUtils.copyProperties(newUser, user);
			return user;
		} else
			return null;
	}// End of save method.

	private String validateUser(UserInformationDto user) {
		user.setCases("");
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		if (!isValidName(user.getName())) {
			user.setCases(user.getCases() + " Enter Valid Name.");
		}
		if (!isValidEmail(user.getEmail())) {
			user.setCases(user.getCases() + " Enter Valid Email Id.");
		}
		if (!isValidNumber(user.getMobileNo())) {
			user.setCases(user.getCases() + " Enter Valid Phone Number.");
		}
		if (user.getHeight() <= 0) {
			user.setCases(user.getCases() + " Height Cannot be Zero or Negative.");
		}
		if (user.getWeight() <= 0) {
			user.setCases(user.getCases() + " Weight Cannot be Zero or Negative.");
		}
		if (user.getGender() != null && !user.getGender().equalsIgnoreCase("Female")
				&& !user.getGender().equalsIgnoreCase("Male")) {
			user.setCases(user.getCases() + " Enter Valid Gender.");
		}
		if (password != null && (password.length() < 8 || password.length() > 15)) {
			user.setCases(user.getCases() + " Password Length Should be Between 8 to 15.");
		}
		if (confirmPassword != null && (confirmPassword.length() < 8 || confirmPassword.length() > 15)) {
			user.setCases(user.getCases() + " Confirm Password Length Should be Between 8 to 15.");
		}
		return user.getCases();
	}

	/**
	 * This method is implemented to validate name.
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		String regex = "[a-zA-Z\\s]*$";
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(name);
		return m.matches();
	}

	/**
	 * This method is implemented to validate the phone number.
	 * 
	 * @param number
	 * @return boolean
	 */
	public static boolean isValidNumber(long number) {
		if (number == 0) {
			return false;
		}
		String numberString = number + "";
		return !(numberString.length() < 10 || numberString.length() > 10);
	}

	/**
	 * This method is used to save notification count after user logs out.
	 * 
	 * @param notificationCount
	 * @param userId
	 * @return int
	 */
	public int logOut(int notificationCount, int userId) {
		UserInformation userEntity = userRepo.findUserById(userId);
		if (userEntity != null) {
			userEntity.setNotificationCount(notificationCount);
			userRepo.save(userEntity);
			return notificationCount;
		} else
			throw new com.tyss.strongameapp.exception.UserException("User Information Cannot be Null");

	}// End of log out method.

	/**
	 * This method is used to logout from the application
	 * 
	 * @param logoutDTO
	 * 
	 * @return boolean
	 */
	public boolean userLogout(LogoutDTO logoutDTO) {
		UserInformation userEntity = userRepo.findUserById(logoutDTO.getUserId());
		if (userEntity != null) {
			if (userEntity.getJwtToken() != null && userEntity.getDeviceId() != null) {
				if (userEntity.getJwtToken().equalsIgnoreCase(logoutDTO.getJwtToken())
						&& userEntity.getDeviceId().equalsIgnoreCase(logoutDTO.getDeviceId())) {
					userEntity.setJwtToken(null);
					userEntity.setDeviceId(null);
					userRepo.save(userEntity);
				}
				return true;
			} else {
				return false;
			}
		} else
			throw new UsernameNotFoundException("User Not Found");

	}// End of log out method.

	/**
	 * This method is implemented to validate email.
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean isValidEmail(String email) {

		if (email == null) {
			return false;
		}

		String regex = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+(com)+$";
		Pattern p = Pattern.compile(regex);

		String regexTwo = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+(in)+$";
		Pattern pTwo = Pattern.compile(regexTwo);

		Matcher m = p.matcher(email);
		Matcher mTwo = pTwo.matcher(email);

		return m.matches() || mTwo.matches();
	}

	private String rewardOnReference(String friendReference) {
		if (friendReference != null && !"".equals(friendReference)) {
			UserInformation frndUser = userRepo.findByReferalCode(friendReference);
			if (frndUser != null) {
				if (userRepo.countByReferenceCount(true) < 50) {
					RewardDetails frndReward = frndUser.getReward();
					frndReward.setRewardCoins(frndReward.getRewardCoins() + 5);
					frndUser.setReward(frndReward);
					return "5";
				} else
					return "50 - Reference Code are Expired ";
			} else
				return "Invalid Reference Code";
		}
		return "0";
	}
}// End of CustomUserDetailService class.