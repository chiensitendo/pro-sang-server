package com.sang.prosangserver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountResponse {
	private String username;
	private String email;
	private Long id;
	@JsonProperty("created_date")
	private LocalDateTime createdDate;
	@JsonProperty("updated_date")
	private LocalDateTime updatedDate;
}
