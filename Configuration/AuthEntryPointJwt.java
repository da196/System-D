package com.Hrms.Configuration;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		logger.error("Unauthorized error: {}", authException.getMessage());

		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error: Forbidden");
	}

	public Set<String> attributes(HttpServletRequest request) {
		Set<String> attrList = new HashSet<>();
		try {
			Enumeration<String> attributes = (Enumeration<String>) request.getAttributeNames();
			while (attributes.hasMoreElements()) {

				attrList.add(attributes.nextElement());

			}
		} catch (Exception e) {

		}

		return attrList;
	}

}
