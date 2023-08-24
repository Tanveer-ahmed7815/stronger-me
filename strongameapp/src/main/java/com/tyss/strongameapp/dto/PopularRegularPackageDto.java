package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class PopularRegularPackageDto {

	private String packageType;

	List<PackageDetailsDto> Packages;

	PackageTaglineDetailsDto taglineDetails;

}
