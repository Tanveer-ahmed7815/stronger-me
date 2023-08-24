package com.tyss.strongameapp.config;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		try {
			response.setHeader("error", "You Dont Have Access to Use the Application");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			HashMap<String, Object> error = new HashMap<>();
			error.put("statusCode", HttpStatus.UNAUTHORIZED.toString());
			error.put("timestamp", LocalDateTime.now().toString());
			error.put("error", true);
			error.put("message", "You Dont Have Access to Use the Application");
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		} catch (JsonGenerationException exception) {
			exception.printStackTrace();
		}
	}
}