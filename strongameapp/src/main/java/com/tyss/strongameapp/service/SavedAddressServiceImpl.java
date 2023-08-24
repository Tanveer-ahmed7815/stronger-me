package com.tyss.strongameapp.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.constants.AddressConstants;
import com.tyss.strongameapp.constants.UserConstants;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.SavedAddressDTO;
import com.tyss.strongameapp.entity.SavedAddress;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.AddressLabelTypeException;
import com.tyss.strongameapp.exception.AddressTypeException;
import com.tyss.strongameapp.exception.UserNotExistException;
import com.tyss.strongameapp.exception.UserNotFoundException;
import com.tyss.strongameapp.repository.SavedAddressRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SavedAddressServiceImpl implements SavedAddressService {

	@Autowired
	private SavedAddressRepository savedAddressRepository;

	@Autowired
	private UserInformationRepository userInformationRepository;

	@Override
	public SavedAddressDTO addSaveAddress(SavedAddressDTO savedAddressDTO, int userId) {

		log.info("Request " + savedAddressDTO + " " + userId);
		SavedAddressDTO savedAddressResponseDTO = null;
		Optional<UserInformation> userInfo = userInformationRepository.findById(userId);
		if (userInfo.isPresent()) {

			UserInformation userInformation = userInfo.get();
			List<SavedAddress> savedAddressList = userInformation.getSavedAddress();

			boolean isDeleted = savedAddressList.stream().allMatch(SavedAddress::isDeleted);

			if (savedAddressList.isEmpty() || isDeleted) {

				return addFirstAddress(savedAddressDTO, userInformation);

			} else {
				checkLabel(savedAddressList, savedAddressDTO);
				if (savedAddressDTO.getAddressType() != null) {
					setDefaultType(savedAddressList);
				}

				SavedAddress savedAddress = new SavedAddress();
				BeanUtils.copyProperties(savedAddressDTO, savedAddress);
				savedAddress.setSavedAddressUser(userInformation);
				savedAddress.setCreatedDate(Calendar.getInstance().getTime());
				savedAddressRepository.save(savedAddress);
				log.info(savedAddress.getSavedAddressId() + "Saved-Address");
				savedAddressResponseDTO = new SavedAddressDTO();
				BeanUtils.copyProperties(savedAddress, savedAddressResponseDTO);
				log.info("Response " + savedAddressResponseDTO);
				return savedAddressResponseDTO;
			}

		} else {
			throw new UserNotFoundException(UserConstants.USER_NOT_FOUND);
		}

	}

	private SavedAddressDTO addFirstAddress(SavedAddressDTO savedAddressDTO, UserInformation userInformation) {

		SavedAddressDTO savedAddressResponseDTO = null;
		SavedAddress savedAddress = new SavedAddress();
		if (savedAddressDTO.getAddressType() == null) {
			savedAddressDTO.setAddressType("default");
		}
		BeanUtils.copyProperties(savedAddressDTO, savedAddress);

		savedAddress.setSavedAddressUser(userInformation);
		savedAddress.setCreatedDate(Calendar.getInstance().getTime());
		savedAddressRepository.save(savedAddress);
		log.info(savedAddress.getSavedAddressId() + "Saved-Address");
		savedAddressResponseDTO = new SavedAddressDTO();
		BeanUtils.copyProperties(savedAddress, savedAddressResponseDTO);
		log.info("Response " + savedAddressResponseDTO);
		return savedAddressResponseDTO;

	}

	private void setDefaultType(List<SavedAddress> savedAddressList) {

		savedAddressList.stream().forEach(s -> {
			if (s.getAddressType() != null) {
				s.setAddressType(null);
				savedAddressRepository.save(s);

			}
		});

	}

	private void checkLabel(List<SavedAddress> savedAddressList, SavedAddressDTO savedAddressDTO) {

		String labelDto = savedAddressDTO.getLabel().replaceAll("\\s+", "");
		for (SavedAddress address : savedAddressList) {
			String labelDb = address.getLabel().replaceAll("\\s+", "");
			if (labelDb.equalsIgnoreCase(labelDto) && (Boolean.FALSE.equals(address.isDeleted()))) {
				throw new AddressLabelTypeException(
						"Address Label Type " + savedAddressDTO.getLabel() + " is repeated");
			}
		}

	}

	@Override
	public List<SavedAddress> getSavedAddress(int userId) {

		UserInformation userInfo = userInformationRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));

		List<SavedAddress> savedAddress = userInfo.getSavedAddress();

		List<SavedAddress> updatedAddress = savedAddress.stream().filter(s -> Boolean.FALSE.equals(s.isDeleted())).collect(Collectors.toList());

	    Collections.reverse(updatedAddress);
	    
	    return updatedAddress;
	}

	@Override
	public String updateSavedAddress(int userId, int savedAddressId, SavedAddressDTO savedAddressDTO) {

		UserInformation userInfo = userInformationRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));

		SavedAddress saveAddress = savedAddressRepository.findById(savedAddressId)
				.orElseThrow(() -> new AddressTypeException(AddressConstants.NO_DATA));

		if (Boolean.TRUE.equals(saveAddress.isDeleted())) {

			throw new AddressTypeException(AddressConstants.NO_DATA);

		} else {

			if (saveAddress.getLabel().equalsIgnoreCase(savedAddressDTO.getLabel())) {

				return checkBeforeUpdate1(saveAddress, savedAddressDTO, userInfo, savedAddressId);

			} else {

				return checkBeforeUpdate2(savedAddressDTO, saveAddress, userInfo, savedAddressId);

			}

		}

	}

	private String checkBeforeUpdate1(SavedAddress saveAddress, SavedAddressDTO savedAddressDTO,
			UserInformation userInfo, int savedAddressId) {
		List<SavedAddress> saveAddressList = userInfo.getSavedAddress();

		if (savedAddressDTO.getAddressType() != null) {
			setDefaultType(saveAddressList);
		}

		for (SavedAddress address : saveAddressList) {

			if (address.getSavedAddressId() == savedAddressId) {

				return saveUpdatedAddress(savedAddressDTO, saveAddress, userInfo);

			}
		}
		return "User Address details does not exist";
	}

	private String checkBeforeUpdate2(SavedAddressDTO savedAddressDTO, SavedAddress saveAddress,
			UserInformation userInfo, int savedAddressId) {
		List<SavedAddress> saveAddressList = userInfo.getSavedAddress();
		for (SavedAddress address : saveAddressList) {

			if (address.getSavedAddressId() == savedAddressId) {
				checkLabel(saveAddressList, savedAddressDTO);
				if (savedAddressDTO.getAddressType() != null) {
					setDefaultType(saveAddressList);
				}

				return saveUpdatedAddress(savedAddressDTO, saveAddress, userInfo);

			}
		}
		return "User Address details does not exist";
	}

	private String saveUpdatedAddress(SavedAddressDTO savedAddressDTO, SavedAddress saveAddress,
			UserInformation userInfo) {

		BeanUtils.copyProperties(savedAddressDTO, saveAddress);
		saveAddress.setSavedAddressUser(userInfo);
		savedAddressRepository.save(saveAddress);
		return "Address Updated Successfully";

	}

	@Transactional
	@Override
	public Boolean deleteSavedAddress(int userId, int savedAddressId) {
		UserInformation userInfo = userInformationRepository.findById(userId).orElseThrow(UserNotExistException::new);
		
		Optional<SavedAddress> savedAddress = savedAddressRepository.findById(savedAddressId);
		if (savedAddress.isPresent()) {

			List<SavedAddress> addressList = userInfo.getSavedAddress();

			List<SavedAddress> savedAddressList = addressList.stream().filter(s -> (!s.isDeleted()))
					.collect(Collectors.toList());

			SavedAddress deleteAddress = savedAddressList.stream()
					.filter(s -> (s.getSavedAddressId() == savedAddressId)).findAny()
					.orElseThrow(() -> new AddressTypeException(AddressConstants.NO_DATA));

			if (deleteAddress.getAddressType() != null) {
				SavedAddress setDafaultAddress = savedAddressList.stream()
						.min(Comparator.comparingInt(SavedAddress::getSavedAddressId)).orElse(null);
				if (setDafaultAddress != null) {

					setDafaultAddress.setAddressType("default");

				}

			}

			deleteAddress.setAddressType(null);
			deleteAddress.setDeleted(true);

			return true;

		} else {
			return false;
		}

	}

	@Override
	public ResponseDto getDefaultAddress(int userId) {

		UserInformation userInfo = userInformationRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));
		List<SavedAddress> savedAddress = userInfo.getSavedAddress();

		SavedAddress saveAddress = savedAddress.stream()
				.filter(s -> (s.getAddressType() != null) && (Boolean.FALSE.equals(s.isDeleted()))).findFirst()
				.orElse(null);
		ResponseDto resp = new ResponseDto();
		if (saveAddress != null) {
			resp.setData(saveAddress);
			resp.setError(false);

		} else {

			resp.setMessage("No Default address found");

		}
		return resp;

	}

}
