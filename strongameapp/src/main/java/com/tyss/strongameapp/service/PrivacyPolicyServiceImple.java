package com.tyss.strongameapp.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tyss.strongameapp.entity.PrivacyPolicyFiles;
import com.tyss.strongameapp.exception.FileStorageException;
import com.tyss.strongameapp.exception.MyFileNotFoundException;
import com.tyss.strongameapp.repository.PrivacyPolicyRepository;

/**
 * This class is an implementation class for privacy file management.
 * 
 * @author Sushma Guttal
 *
 */
@Service
public class PrivacyPolicyServiceImple implements PrivacyPolicyService {

	/**
	 * This filed is to invoke persistence layer methods.
	 */
	@Autowired
	private PrivacyPolicyRepository privacyPolicyRepository;

	/**
	 * This method is implemented to save the files to DB.
	 * 
	 * @param MultipartFile
	 * @throws FileStorageException
	 * @return PrivacyPolicyFiles
	 */
	@Override
	public PrivacyPolicyFiles storeFile(MultipartFile file) throws FileStorageException {
		String fileName = file.getOriginalFilename();
		if (fileName != null) {
			fileName = StringUtils.cleanPath(fileName);
		} else {
			throw new FileStorageException("Sorry! Invalid Filename");
		}
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			PrivacyPolicyFiles dbFile = new PrivacyPolicyFiles(fileName, file.getContentType(), file.getBytes());
			return privacyPolicyRepository.save(dbFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * This method is implemented to fetch the file.
	 * 
	 * @param String
	 * @throws MyFileNotFoundException
	 * @return PrivacyPolicyFiles
	 */
	@Override
	public PrivacyPolicyFiles getFile(String fileId) throws MyFileNotFoundException {
		Optional<PrivacyPolicyFiles> file = privacyPolicyRepository.findById(fileId);
		if (!file.isPresent())
			throw new MyFileNotFoundException("File Not Found");
		else
			return file.get();
	}

	/**
	 * This method is implemented to delete the files.
	 * 
	 * @param String
	 * @return String
	 */
	@Override
	public String deleteFile(String fileId) {
		privacyPolicyRepository.deleteById(fileId);
		return "File is deleted Successfully";
	}

}
