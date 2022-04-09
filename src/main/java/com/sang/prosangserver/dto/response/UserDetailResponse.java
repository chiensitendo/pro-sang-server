package com.sang.prosangserver.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailResponse {

	private Long id;
	
	private String username;
	
	private String email;
	
}
