package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.NotificationInformation;

public interface NotificationInformationRepository extends JpaRepository<NotificationInformation, Integer> {

	@Query(value="SELECT n.notificaton_id, n.notification_image,n.notification_type, n.notification_description from notification_infomation n"
			+ "  join user_notification un on n.notificaton_id=un.notificaton_id and un.user_id=:userId"
			,nativeQuery = true)
	List<NotificationInformation> getAllNotifications(int userId);
	

	@Query(value="select * from notification_infomation where notification_type is null", nativeQuery = true)
	List<NotificationInformation> getGenericNotification();
}
