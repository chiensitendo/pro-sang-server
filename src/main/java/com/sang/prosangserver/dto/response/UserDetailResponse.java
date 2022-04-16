package com.sang.prosangserver.dto.response;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailResponse {

	private Long id;
	
	private String username;
	
	private String email;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String address;
	
	private Integer country;
	
	private Integer stateOrProvince;
	
	private String website;
	
	private String phone;
	
	private String description;
	
	private LocalDate birthday;
	
}
