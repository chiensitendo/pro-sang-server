package com.sang.prosangserver.mappers;

import org.mapstruct.Mapper;

import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountUserDetailMapper {

	UserDetailResponse accountToUserDetail(Account account);
	
}
