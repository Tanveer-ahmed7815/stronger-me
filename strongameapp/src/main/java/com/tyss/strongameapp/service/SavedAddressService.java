package com.tyss.strongameapp.service;

import java.util.List;

import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.SavedAddressDTO;
import com.tyss.strongameapp.entity.SavedAddress;

public interface SavedAddressService {

	SavedAddressDTO addSaveAddress(SavedAddressDTO savedAddressDTO, int userId);

	List<SavedAddress> getSavedAddress(int userId);

	String updateSavedAddress(int userId, int savedAddressId, SavedAddressDTO savedAddressDTO);

	Boolean deleteSavedAddress(int userId, int savedAddressId);

	ResponseDto getDefaultAddress(int userId);

}
