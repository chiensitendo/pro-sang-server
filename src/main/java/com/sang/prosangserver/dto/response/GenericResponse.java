package com.sang.prosangserver.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2401477446641292869L;
	int status;
	Object body;
	@Singular
	List<Map<String, ?>> errors;

}
