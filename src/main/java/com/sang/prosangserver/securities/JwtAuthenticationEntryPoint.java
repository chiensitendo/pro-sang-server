package com.sang.prosangserver.securities;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.services.MessageService;
import com.sang.prosangserver.utils.ResponseUtils;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4809638948112062218L;
	
	private final MessageService messageService;
	
	public JwtAuthenticationEntryPoint(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ResponseUtils.createUnauthorizedResponse(response, messageService.getMessage(ErrorMessages.UNAUTHORIZED));
	}
}
