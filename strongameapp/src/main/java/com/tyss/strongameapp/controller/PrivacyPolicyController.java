package com.tyss.strongameapp.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tyss.strongameapp.dto.FileResponseDTO;
import com.tyss.strongameapp.entity.PrivacyPolicyFiles;
import com.tyss.strongameapp.exception.FileStorageException;
import com.tyss.strongameapp.exception.MyFileNotFoundException;
import com.tyss.strongameapp.service.PrivacyPolicyService;
/**
 * PrivacyPolicyController is for managing the privacy policy files.
 * @author Sushma Guttal
 *
 */

@RestController
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app","https://strongermeuser.herokuapp.com"})
public class PrivacyPolicyController {
		
		private static final Logger logger = LoggerFactory.getLogger(PrivacyPolicyController.class);

		/**
		 * This field is used to invoke business layer methods.
		 */
	    @Autowired
	    private PrivacyPolicyService privacyPolicyService;
	   
	    /**
	     * This method is used to upload the files.
	     * @param filePojo
	     * @return ResponseEntity<PrivacyPolicyFiles>
	     */
	    @PostMapping("/upload/file")
	    public ResponseEntity<PrivacyPolicyFiles> uploadFile(@RequestParam("file") MultipartFile filePojo) {
	    	
	    	logger.info("File is uploaded");
	    	
	    	PrivacyPolicyFiles file = new PrivacyPolicyFiles();
			try {
				file = privacyPolicyService.storeFile(filePojo);
			} catch (FileStorageException e) {
				e.printStackTrace();
			}

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(file.getId())
	                .toUriString();

			/* FileResponseDTO fileResponse = */ new FileResponseDTO(file.getFileName(), fileDownloadUri,
	        		filePojo.getContentType(), filePojo.getSize());
	        
	        return new ResponseEntity<PrivacyPolicyFiles>(file, HttpStatus.OK);
	    }

	    /**
	     * This method is used to store multiple files.
	     * @param files
	     * @return ResponseEntity<MultipartFile[]>
	     */
	    @PostMapping("/upload/multiple/files")
	    public ResponseEntity<MultipartFile[]> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	    	        Arrays.asList(files)
	                .stream()
	                .map(file -> uploadFile(file))
	                .collect(Collectors.toList());
	    	        
	    	        return new ResponseEntity<MultipartFile[]>(files, HttpStatus.OK);
	    }

	    /**
	     * This method is used to fetch the file.
	     * @param fileId
	     * @return ResponseEntity<Resource>
	     * @throws MyFileNotFoundException
	     */
	    @GetMapping("/download/file/{fileId}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws MyFileNotFoundException {
	        // Load file from database
	    	PrivacyPolicyFiles file = privacyPolicyService.getFile(fileId);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(file.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
	                .body(new ByteArrayResource(file.getData()));
	    }

	    /**
	     * This method is used to delete the file.
	     * @param fileId
	     * @return String
	     */
	    @DeleteMapping("/delete/file/{fileId}")
	    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
	        // Load file from database
	    	String result = privacyPolicyService.deleteFile(fileId);
	    	 return new ResponseEntity<String>(result, HttpStatus.OK);
	    }
	

}
