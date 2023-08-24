package com.tyss.strongameapp.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.service.CustomUserDetailService;
import com.tyss.strongameapp.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailService userDetailService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserInformationRepository userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		final String deviceId = request.getHeader("deviceId");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		UserDetails userDetails = null;

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			userDetails = this.userDetailService.loadUserByUsername(username);
			if (Boolean.TRUE.equals(jwtUtil.validateToken(jwtToken, userDetails))) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		checkUserAccess(response, userDetails, jwtToken, deviceId);
		chain.doFilter(request, response);
	}

	public void checkUserAccess(HttpServletResponse response, UserDetails userDetails, String jwtToken,
			String deviceId) {
		if (userDetails != null) {
			UserInformation userInformation = userRepo.findByUsername(userDetails.getUsername());
			if (userInformation.getJwtToken() == null && userInformation.getDeviceId() == null) {
				getErrorReponse("You have Already Logged Out", response, 401);
			} else if (!(userInformation.getJwtToken().equalsIgnoreCase(jwtToken)
					&& userInformation.getDeviceId().equalsIgnoreCase(deviceId))) {
				getErrorReponse("Multiple Login Detected!!!", response, 403);
			}
		}
	}

	public void getErrorReponse(String message, HttpServletResponse response, int code) {
		response.setHeader("error", message);
		HashMap<String, Object> error = new HashMap<>();
		if (code == 401) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			error.put("statusCode", HttpStatus.UNAUTHORIZED.toString());
		} else {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			error.put("statusCode", HttpStatus.FORBIDDEN.toString());
		}
		error.put("timestamp", LocalDateTime.now().toString());
		error.put("error", true);
		error.put("message", message);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try {
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}