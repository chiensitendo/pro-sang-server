package com.sang.prosangserver.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccountDetailRequest {
	
	@Size(max=100, message = "{validation.max.100}")
	private String firstName;
	
	@Size(max=100, message = "{validation.max.100}")
	private String middleName;
	
	@Size(max=100, message = "{validation.max.100}")
	private String lastName;
	
	@Size(max=100, message = "{validation.max.200}")
	private String address;
	
	private Integer country;
	
	private Integer stateOrProvince;
	
	@Size(max=100, message = "{validation.max.200}")
	private String website;
	
	@Size(max=100, message = "{validation.max.50}")
	private String phone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	
	private String description;
}
