package com.tyss.strongameapp.service;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.AddressException;

import com.tyss.strongameapp.dto.UserInformationDto;

/**
 * PasswordService is implemented by PasswordServiceImple class, used for
 * password management.
 * 
 * @author Sushma Guttal
 *
 */
public interface PasswordService {

	/**
	 * This method is implemented by its implementation class to send otp to
	 * specified number.
	 * 
	 * @param phoneNumber
	 * @return String
	 */
	String sendOtp(Long phoneNumber);

	/**
	 * This method is implemented by its implementation class to provide forgot
	 * password functionality to user.
	 * 
	 * @param email
	 * @return String
	 * @throws MessagingException
	 * @throws NoSuchProviderException
	 * @throws AddressException
	 */
	String forgotPassword(String email) throws MessagingException;

	/**
	 * This method is implemented by its implementation class to provide change
	 * password functionality to user.
	 * 
	 * @param data
	 * @return UserInformationDto
	 */
	UserInformationDto changePassword(UserInformationDto data);

}// End of PasswordService interface.
