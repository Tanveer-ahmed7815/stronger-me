package com.tyss.strongameapp.service;

import org.springframework.web.multipart.MultipartFile;

import com.tyss.strongameapp.entity.PrivacyPolicyFiles;
import com.tyss.strongameapp.exception.FileStorageException;
import com.tyss.strongameapp.exception.MyFileNotFoundException;
/**
 * This class is used to manage the privacy policy files.
 * @author Sushma Guttal
 *
 */
public interface PrivacyPolicyService {

	/**
	 * This method is implemented to save the files to DB.
	 * @param filePojo
	 * @return PrivacyPolicyFiles
	 * @throws FileStorageException
	 */
	PrivacyPolicyFiles storeFile(MultipartFile filePojo) throws FileStorageException;

	
	/**
	 * This method is implemented to fetch the file.
	 * @param fileId
	 * @return PrivacyPolicyFiles
	 * @throws MyFileNotFoundException
	 */
	PrivacyPolicyFiles getFile(String fileId) throws MyFileNotFoundException;

	
	/**
	 * This method is implemented to delete the files. 
	 * @param fileId
	 * @return String
	 */
	String deleteFile(String fileId);

	
}
