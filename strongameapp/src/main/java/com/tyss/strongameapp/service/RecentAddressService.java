package com.tyss.strongameapp.service;

import java.util.List;

import com.tyss.strongameapp.dto.RecentAddressDto;
import com.tyss.strongameapp.entity.RecentSearchedAddress;

public interface RecentAddressService {
	
	RecentAddressDto addRecentAddress(RecentAddressDto recentAddressDto, int userId);

	List<RecentSearchedAddress> getRecentAddress(int userId);
	
	Boolean deleteRecentAddress(int userId, int recentAddressId);
}
