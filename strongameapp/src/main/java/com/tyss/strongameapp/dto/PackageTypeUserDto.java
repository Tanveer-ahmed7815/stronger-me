package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class PackageTypeUserDto {

	private List<PopularRegularPackageDto> regularPopularPackages;

	private List<RecentUser> packageUsers;

	private long count;
	
	private List<String> subscribedUserImages;
}
