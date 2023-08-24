package com.tyss.strongameapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "top_ten_trend")
public class TopTenTrend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trend_id")
	private int trendId;

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "content_id")
	private ModuleContent toptrendContent;

}
