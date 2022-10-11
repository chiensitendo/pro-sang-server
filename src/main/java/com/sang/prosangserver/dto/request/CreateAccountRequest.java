package com.sang.prosangserver.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sang.prosangserver.enums.Roles;
import com.sang.prosangserver.interfaces.EnumValidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.username.not_blank}")
	private String username;

	@Email
	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.email.not_blank}")
	private String email;

	@EnumValidation(enumClass = Roles.class, message = "{validation.role.not_exists}")
	@NotNull(message = "{account.role.not_null}")
	private Integer role;

	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.password.not_blank}")
	private String password;

	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.firstName.not_blank}")
	@JsonProperty("first_name")
	private String firstName;

	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.lastName.not_blank}")
	@JsonProperty("last_name")
	private String lastName;

	private String photoUrl;
}
