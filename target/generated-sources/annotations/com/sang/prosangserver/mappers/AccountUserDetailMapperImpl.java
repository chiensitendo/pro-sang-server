package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.Account;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-09T15:06:46+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9.1 (Amazon.com Inc.)"
)
@Component
public class AccountUserDetailMapperImpl implements AccountUserDetailMapper {

    @Override
    public UserDetailResponse accountToUserDetail(Account account) {
        if ( account == null ) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();

        userDetailResponse.setId( account.getId() );
        userDetailResponse.setUsername( account.getUsername() );
        userDetailResponse.setEmail( account.getEmail() );

        return userDetailResponse;
    }
}
