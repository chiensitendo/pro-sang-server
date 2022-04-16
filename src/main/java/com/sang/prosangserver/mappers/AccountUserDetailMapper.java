package com.sang.prosangserver.mappers;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.account.Account;

@Mapper(componentModel = "spring")
public interface AccountUserDetailMapper {

	@Mappings({
		@Mapping(target = "firstName", source = "detail.firstName"),
		@Mapping(target = "middleName", source = "detail.middleName"),
		@Mapping(target = "lastName", source = "detail.lastName"),
		@Mapping(target = "address", source = "detail.address"),
		@Mapping(target = "country", source = "detail.country"),
		@Mapping(target = "stateOrProvince", source = "detail.stateOrProvince"),
		@Mapping(target = "website", source = "detail.website"),
		@Mapping(target = "phone", source = "detail.phone"),
		@Mapping(target = "description", source = "detail.description"),
		@Mapping(target = "birthday", source = "detail.birthday", qualifiedByName = "toBirthdate")
	})
	UserDetailResponse accountToUserDetail(Account account);
	
	@Named("toBirthdate")
	default LocalDate toBirthdate(LocalDate date) {
		return date;
	}
	
}
