package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class FileResponseDTO {

private String fileName;
	
	private String fileDownloadUri;
	
	private String contentType;
	
	private long size;

	public FileResponseDTO(String fileName, String fileDownloadUri, String contentType, long size) {
		super();
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.contentType = contentType;
		this.size = size;
	}

	public FileResponseDTO() {
		super();
	}
	
}
