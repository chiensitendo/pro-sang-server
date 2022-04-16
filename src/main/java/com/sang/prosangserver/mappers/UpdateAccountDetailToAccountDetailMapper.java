package com.sang.prosangserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import com.sang.prosangserver.dto.request.UpdateAccountDetailRequest;
import com.sang.prosangserver.entities.account.AccountDetail;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface UpdateAccountDetailToAccountDetailMapper {
	
	public AccountDetail requestToAccountDetail(UpdateAccountDetailRequest request);	
}
