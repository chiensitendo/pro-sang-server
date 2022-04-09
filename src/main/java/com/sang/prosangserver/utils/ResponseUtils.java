package com.sang.prosangserver.utils;

import org.springframework.http.HttpStatus;

import com.sang.prosangserver.dto.response.GenericResponse;

public class ResponseUtils {

	public static <T> GenericResponse buildOkResponse(T data) {
		return GenericResponse
				.builder()
				.status(HttpStatus.OK.value())
				.body(data)
				.build();
	}
}
