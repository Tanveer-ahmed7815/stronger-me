package com.tyss.strongameapp.util;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.tyss.strongameapp.entity.SessionDetails;

/**
 * Here you find methods for sending notification using topic or token (token is
 * for particular user and topic is like channel who are all subscribed to that
 * topic will get notification)
 * 
 * @author Afridi
 * 
 */
@Service
public class MyFireBaseUtility {

	/**
	 * This field is create a firebaseMessaging bean to send notification
	 */
	@Autowired
	private FirebaseMessaging firebaseMessaging;

	/**
	 * This field is TITILE for notification
	 */
	private static final String TITLE = "Stronger Me";

	/**
	 * /** This method is to send notification to token
	 * 
	 * @param firebaseToken
	 * @param msg
	 * @param url
	 * @return String
	 * @throws FirebaseMessagingException
	 */
	public String sendTokenNotification(String firebaseToken, String msg, String url)
			throws FirebaseMessagingException {
		Notification notification = Notification.builder().setTitle(TITLE).setBody(msg).setImage(url).build();
		Message message = Message.builder().setToken(firebaseToken).setNotification(notification).build();
		return firebaseMessaging.send(message);
	}

	public void sendNotificationOnTime(String firebaseToken, SessionDetails sessionDetails, String notificationImage) {
		// String time = sessionDetails.getSessionDate()+"
		// "+sessionDetails.getSessionTime();
		Notification notification = Notification.builder().setTitle(TITLE)
				.setBody(sessionDetails.getSessionName() + " Session will start at " + sessionDetails.getSessionTime())
				.setImage(notificationImage).build();
		Message message = Message.builder().setToken(firebaseToken).setNotification(notification).build();
		try {
			firebaseMessaging.sendAsync(message).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	// .setWebpushConfig(WebpushConfig.builder().setFcmOptions(WebpushFcmOptions.withLink("....")).build())
}
