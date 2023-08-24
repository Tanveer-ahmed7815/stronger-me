package com.tyss.strongameapp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.AddressException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.UserInformationDto;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.UserInformationRepository;

/**
 * PasswordServiceImple is implementation class, used for password management.
 * 
 * @author Sushma Guttal
 *
 */
@Service
public class PasswordServiceImple implements PasswordService {

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private MyShopService myShopService;

	/**
	 * This field is used to fetch variable of application.properties.
	 */
	@Value("${apikey}")
	private String apikey;

	/**
	 * This field is used to fetch variable of application.properties.
	 */
	@Value("${senderid}")
	private String senderid;

	/**
	 * This field is used to fetch variable of application.properties.
	 */
	@Value("${route}")
	private String routeId;

	private static final Random RANDOM = new Random();

	/**
	 * This method is implemented to send otp to the specified phone number.
	 * 
	 * @param phoneNumber
	 * @return String
	 */
	@Override
	public String sendOtp(Long phoneNumber) {
		String apiKey = apikey;
		String message = otpGenerate();
		try {
			message = URLEncoder.encode(message, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder response = send(message, phoneNumber, apiKey);
		if (response == null) {
			return null;
		}
		return message;
	}// End of sendOtp method.

	/**
	 * This method is implemented to generate otp.
	 * 
	 * @return String
	 */
	private String otpGenerate() {
		int otp1 = 1000 + RANDOM.nextInt(9000);
		return "" + otp1;

	}// End of otpGenerate method.

	/**
	 * This method consists of logic for sending otp.
	 * 
	 * @param message
	 * @param l
	 * @param apikey
	 * @return StringBuffer
	 */
	public StringBuilder send(String message, Long l, String apikey) {
		StringBuilder response = new StringBuilder();
		String senderId = senderid;
		String route = routeId;
		String url = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apikey + "&sender_id=" + senderId
				+ "&message=" + 132400 + "&variables_values=" + message + "|12345" + "&route=" + route + "&numbers="
				+ l;
		boolean flag = false;
		try {
			URL myurl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("cache-control", "no-cache");
			connection.getResponseCode();
			connection.getResponseMessage();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				response.append(line);
			}
			String responseString = response.toString();
			if (responseString.contains("false")) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag) {
			return null;
		} else
			return response;

	}// End of send method.

	/**
	 * This method is implemented to provide forgot password functionality
	 * 
	 * @param email
	 * @return String
	 * @throws MessagingException
	 * @throws NoSuchProviderException
	 * @throws AddressException
	 */
	@Override
	public String forgotPassword(String email) throws MessagingException {
		boolean flag = CustomUserDetailService.isValidEmail(email);
		if (!flag) {
			return "Enter Valid Email";
		}
		UserInformation user = userRepo.getuserId(email);
		if (user != null) {
			String name = user.getName();
			myShopService.sendMail(email, name, "forgotpassword");
			return "Mail Sent Successfully";
		} else
			return null;
	}// End of forgotPassword method.

	/**
	 * This method is implemented to provide change password functionality
	 * 
	 * @param data
	 * @return UserInformationDto
	 */
	@Override
	public UserInformationDto changePassword(UserInformationDto data) {
		boolean emailFlag = CustomUserDetailService.isValidEmail(data.getEmail());
		String dtoPassword = data.getPassword();
		String dtoConfirmPassword = data.getConfirmPassword();
		data.setCases("");
		if (!emailFlag) {
			data.setCases(data.getCases() + " Enter valid email.");
		}
		if (dtoPassword != null && (dtoPassword.length() < 8 || dtoPassword.length() > 15)) {
			data.setCases(data.getCases() + " Password length should between 8 to 15.");
		}
		if (dtoConfirmPassword != null && (dtoConfirmPassword.length() < 8 || dtoConfirmPassword.length() > 15)) {
			data.setCases(data.getCases() + " Confirm password length should between 8 to 15.");
		}

		if (!data.getCases().equalsIgnoreCase("")) {
			return data;
		}

		UserInformation user = userRepo.getuserId(data.getEmail());
		if (user != null) {
			String password = bcryptEncoder.encode(data.getPassword());
			user.setPassword(password);
			String confirmPassword = bcryptEncoder.encode(data.getConfirmPassword());
			user.setConfirmPassword(confirmPassword);
			userRepo.save(user);
			BeanUtils.copyProperties(user, data);
			return data;
		} else
			return null;
	}// End of changePassword method.

}// End of PasswordServiceImple class.
