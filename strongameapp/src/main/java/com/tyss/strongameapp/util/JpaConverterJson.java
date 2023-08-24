package com.tyss.strongameapp.util;

import java.io.IOException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JpaConverterJson implements AttributeConverter<Object, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Object meta) {

		try {
			if (meta != null) {
				return objectMapper.writeValueAsString(meta);
			} else {
				return null;
			}

		} catch (JsonProcessingException ex) {
			return null;
			// or throw an error
		}
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		try {

			if (dbData != null) {
				return objectMapper.readValue(dbData, Object.class);
			} else {
				return null;
			}

		} catch (IOException ex) {
			// logger.error("Unexpected IOEx decoding json from database: " + dbData);
			return null;
		}
	}

}
