package com.tyss.strongameapp.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BookNowDto {

	private int userId ;
	
	private int regularPackageId;
	
	private List<Integer> addOnIds = new ArrayList<>();

	public BookNowDto() {
		super();
	}

	public BookNowDto(int userId, int regularPackageId, List<Integer> addOnIds) {
		super();
		this.userId = userId;
		this.regularPackageId = regularPackageId;
		this.addOnIds = addOnIds;
	}
	
}
