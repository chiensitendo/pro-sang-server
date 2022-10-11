package com.sang.prosangserver.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sang.prosangserver.exceptions.LyricCommentNotFoundException;
import com.sang.prosangserver.exceptions.LyricException;
import com.sang.prosangserver.exceptions.LyricNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UnauthorizedException;
import com.sang.prosangserver.exceptions.UserExistsException;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.services.MessageService;
import com.sang.prosangserver.utils.ResponseUtils;

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
				.body(ResponseUtils.buildErrorResponse(HttpStatus.NOT_FOUND, error));
	}

	@ExceptionHandler(value = {LyricNotFoundException.class})
	protected ResponseEntity<GenericResponse> handleLyricNotFoundException(LyricNotFoundException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ResponseUtils.buildErrorResponse(HttpStatus.NOT_FOUND, error));
	}

	@ExceptionHandler(value = {LyricException.class})
	protected ResponseEntity<GenericResponse> handleLyricException(LyricException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ResponseUtils.buildErrorResponse(HttpStatus.BAD_REQUEST, error));
	}

	@ExceptionHandler(value = {LyricCommentNotFoundException.class})
	protected ResponseEntity<GenericResponse> handleLyricCommentNotFoundException(LyricCommentNotFoundException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ResponseUtils.buildErrorResponse(HttpStatus.NOT_FOUND, error));
	}

	@ExceptionHandler(value = {UserExistsException.class})
	protected ResponseEntity<GenericResponse> handleUserExistsException(UserExistsException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ResponseUtils.buildErrorResponse(HttpStatus.FORBIDDEN, error));
	}

	@ExceptionHandler(value = InvalidFormatException.class)
	protected ResponseEntity<GenericResponse> handleDateTimeParseException(InvalidFormatException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ResponseUtils.buildErrorResponse(HttpStatus.BAD_REQUEST, error));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> error = new HashMap<String, String>();
		for(FieldError err: ex.getFieldErrors()) {
			error.put(err.getField(), err.getDefaultMessage());
		}

		return ResponseEntity.status(status)
				.body(ResponseUtils.buildValidationErrorResponse(status, error));
	}

	@ExceptionHandler(value = {DisabledException.class, BadCredentialsException.class, InternalAuthenticationServiceException.class, UnauthorizedException.class})
	public ResponseEntity<GenericResponse> handleAuthenticationManagerException(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ResponseUtils
						.buildErrorResponse(HttpStatus.UNAUTHORIZED,
								Collections.singletonMap("error", messageService.getMessage(ErrorMessages.UNAUTHORIZED))));
	}

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<GenericResponse> defaultErrorHandler(Exception ex) {
		ex.printStackTrace();
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", messageService.getMessage(ErrorMessages.INTERNAL_SERVER_ERROR));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error));
	}

}
