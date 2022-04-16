package com.sang.prosangserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sang.prosangserver.dto.request.CreateAccountRequest;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.enums.Roles;

@Mapper(componentModel = "spring")
public abstract class CreateAccountRequestToAccountMapper {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Mappings({
		@Mapping(target = "detail.lastName", source = "request.name")
	})
	public abstract Account createAccountRequestToAccount(CreateAccountRequest request);
	
	Roles map(int value) {
        return Roles.getRole(value);
    }
	
}
