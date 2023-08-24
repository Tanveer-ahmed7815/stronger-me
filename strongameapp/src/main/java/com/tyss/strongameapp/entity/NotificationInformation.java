package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "notification_infomation")
public class NotificationInformation  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5307089427279767224L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notificaton_id")
	private int notificationId;

	@Column(name = "notification_type")
	private String notificationType;

	@Column(name = "notification_description", length = 999)
	private String notificationDetails;

	@Column(name = "notification_image")
	private String notificationImage;

	@Column(name = "notification_clear")
	private boolean notificationClear;

	@Exclude
	@JsonBackReference(value = "user-notification")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "notificaton")
	private List<UserInformation> notificationUsers;

	public NotificationInformation() {
	}

	public NotificationInformation(int notificationId, String notificationType, String notificationDetails,
			String notificationImage, List<UserInformation> notificationUsers) {
		super();
		this.notificationId = notificationId;
		this.notificationType = notificationType;
		this.notificationDetails = notificationDetails;
		this.notificationImage = notificationImage;
		this.notificationUsers = notificationUsers;
	}

}
