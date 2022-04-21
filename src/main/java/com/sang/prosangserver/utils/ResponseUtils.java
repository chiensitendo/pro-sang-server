package com.sang.prosangserver.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sang.prosangserver.constants.GeneralConstants;
import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.dto.response.GenericResponse.GenericResponseBuilder;

public class ResponseUtils {

	public static <T> GenericResponse buildOkResponse(T data) {
		return GenericResponse
				.builder()
				.status(HttpStatus.OK.value())
				.body(data)
				.build();
	}
	
	@SafeVarargs
	public static GenericResponse buildErrorResponse(HttpStatus status, Map<String, String> ... errors) {
		GenericResponseBuilder responseBuilder = GenericResponse
		.builder()
		.status(status.value());
		for (Map<String, String> error: errors) {
			responseBuilder = responseBuilder.error(error);
		}
		return responseBuilder.build();
	}
	
	@SafeVarargs
	public static GenericResponse buildValidationErrorResponse(HttpStatus status, Map<String, String> ... errors) {
		GenericResponseBuilder responseBuilder = GenericResponse
		.builder()
		.status(status.value());
		for (Map<String, String> error: errors) {
			responseBuilder = responseBuilder.validation(error);
		}
		return responseBuilder.build();
	}
	
	@SafeVarargs
	public static String buildErrorResponseJSONString(HttpStatus status, Map<String, String> ... errors) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(buildErrorResponse(status, errors));
	}
	
	public static void createUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
		response.setContentType(GeneralConstants.CONTENT_TYPE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter()
			.print(ResponseUtils.buildErrorResponseJSONString(HttpStatus.UNAUTHORIZED, 
					Collections.singletonMap("error", message)));
	}
	
	
}
