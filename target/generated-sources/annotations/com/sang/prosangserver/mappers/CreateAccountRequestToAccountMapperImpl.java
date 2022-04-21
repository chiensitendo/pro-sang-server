package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.request.CreateAccountRequest;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.entities.account.AccountDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-20T10:48:19+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9.1 (Amazon.com Inc.)"
)
@Component
public class CreateAccountRequestToAccountMapperImpl extends CreateAccountRequestToAccountMapper {

    @Override
    public Account createAccountRequestToAccount(CreateAccountRequest request) {
        if ( request == null ) {
            return null;
        }

        Account account = new Account();

        account.setDetail( createAccountRequestToAccountDetail( request ) );
        account.setEmail( request.getEmail() );
        account.setUsername( request.getUsername() );
        if ( request.getRole() != null ) {
            account.setRole( map( request.getRole().intValue() ) );
        }

        return account;
    }

    protected AccountDetail createAccountRequestToAccountDetail(CreateAccountRequest createAccountRequest) {
        if ( createAccountRequest == null ) {
            return null;
        }

        AccountDetail accountDetail = new AccountDetail();

        accountDetail.setLastName( createAccountRequest.getName() );

        return accountDetail;
    }
}
