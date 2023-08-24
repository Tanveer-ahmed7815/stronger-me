package com.tyss.strongameapp.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.constants.UserConstants;
import com.tyss.strongameapp.dto.RecentAddressDto;
import com.tyss.strongameapp.entity.RecentSearchedAddress;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.UserNotFoundException;
import com.tyss.strongameapp.repository.RecentAddressRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecentAddressServiceImpl implements RecentAddressService {

	@Autowired
	private RecentAddressRepository recentAddressRepository;

	@Autowired
	private UserInformationRepository userInformationRepository;

	@Override
	public RecentAddressDto addRecentAddress(RecentAddressDto recentAddressDto, int userId) {

		Optional<UserInformation> userInformation = userInformationRepository.findById(userId);

		if (!userInformation.isPresent()) {

			throw new UserNotFoundException(UserConstants.USER_NOT_FOUND);
		} else {
			UserInformation userInfo = userInformation.get();

			log.info("Size " + userInfo.getSearchedAddress().size());

			if (userInfo.getSearchedAddress().size() >= 2) {

				recentAddressRepository.deleteFirstRecord(userId);
			}

			return setRecentAddressObject(recentAddressDto, userInfo);

		}

	}

	private RecentAddressDto setRecentAddressObject(RecentAddressDto recentAddressDto,
			UserInformation userInformation) {

		RecentSearchedAddress recentSearchAddress = new RecentSearchedAddress();
		BeanUtils.copyProperties(recentAddressDto, recentSearchAddress);
		recentSearchAddress.setSearchedAddressUser(userInformation);
		Date date = Calendar.getInstance().getTime();
		recentSearchAddress.setCreatedDate(date);
		recentAddressRepository.save(recentSearchAddress);
		BeanUtils.copyProperties(recentSearchAddress, recentAddressDto);
		return recentAddressDto;

	}

	@Override
	public List<RecentSearchedAddress> getRecentAddress(int userId) {

		Optional<UserInformation> userInformation = userInformationRepository.findById(userId);

		if (userInformation.isPresent()) {
			UserInformation userInfo = userInformation.get();

			return userInfo.getSearchedAddress();
		}

		else {
			throw new UserNotFoundException(UserConstants.USER_NOT_FOUND);
		}
	}

	@Override
	public Boolean deleteRecentAddress(int userId, int recentAddressId) {

		Optional<UserInformation> userInfo = userInformationRepository.findById(userId);
		if (!userInfo.isPresent()) {

			throw new UserNotFoundException(UserConstants.USER_NOT_FOUND);
		} else {

			Optional<RecentSearchedAddress> recentAddress = recentAddressRepository.findById(recentAddressId);
			if (recentAddress.isPresent()) {
				List<RecentSearchedAddress> searchedAddressList = userInfo.get().getSearchedAddress();

				for (RecentSearchedAddress recentSearchedAddress : searchedAddressList) {
					if (recentSearchedAddress.getSearchedId() == recentAddressId) {
						recentAddressRepository.deleteById(recentAddressId);
						return true;
					}
				}
			}

			return false;

		}
	}
}
