package com.tyss.strongameapp.dto;

import java.util.List;
import lombok.Data;

@Data
public class PackageSubscribeDto {

	private List<PackageDetailsDto> packageDetails;

	private boolean isSubcribe;

	public PackageSubscribeDto() {
		super();
	}

}
