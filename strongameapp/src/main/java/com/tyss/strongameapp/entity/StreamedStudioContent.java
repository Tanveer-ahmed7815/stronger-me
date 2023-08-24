package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "streamed_studio_content")
public class StreamedStudioContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5021746094836471115L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "streamed_studio_id")
	private int streamedStudioId;

	@Column(name = "streamed_time")
	private Date streamedTime;

	@Column(name = "view_count")
	private int viewCount;

	@Exclude
	@JsonBackReference(value = "user-streamedstudio")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation streamedStudioContentUser;

	@Exclude
	@JsonBackReference(value = "studiocontent-streamed")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "live_content_id")
	private LiveModuleContent streamedStudioContent;

}
