package com.sang.prosangserver.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.services.MessageService;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	private final MessageService messageService;
	
	public ResponseExceptionHandler(MessageService messageService) {
		this.messageService = messageService;
	}

	@ExceptionHandler(value = {UserNotFoundException.class})
	protected ResponseEntity<GenericResponse> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(GenericResponse.builder()
						.status(HttpStatus.NOT_FOUND.value())
						.error(error)
						.build());
	}
	
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<GenericResponse> defaultErrorHandler(Exception ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", messageService.getMessage(ErrorMessages.INTERNAL_SERVER_ERROR));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(GenericResponse.builder()
						.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.error(error)
						.build());
	}
}
