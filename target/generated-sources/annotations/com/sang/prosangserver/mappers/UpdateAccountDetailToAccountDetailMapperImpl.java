package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.request.UpdateAccountDetailRequest;
import com.sang.prosangserver.entities.account.AccountDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-20T10:48:19+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9.1 (Amazon.com Inc.)"
)
@Component
public class UpdateAccountDetailToAccountDetailMapperImpl implements UpdateAccountDetailToAccountDetailMapper {

    @Override
    public AccountDetail requestToAccountDetail(UpdateAccountDetailRequest request) {
        if ( request == null ) {
            return null;
        }

        AccountDetail accountDetail = new AccountDetail();

        if ( request.getFirstName() != null ) {
            accountDetail.setFirstName( request.getFirstName() );
        }
        if ( request.getMiddleName() != null ) {
            accountDetail.setMiddleName( request.getMiddleName() );
        }
        if ( request.getLastName() != null ) {
            accountDetail.setLastName( request.getLastName() );
        }
        if ( request.getAddress() != null ) {
            accountDetail.setAddress( request.getAddress() );
        }
        if ( request.getCountry() != null ) {
            accountDetail.setCountry( request.getCountry() );
        }
        if ( request.getStateOrProvince() != null ) {
            accountDetail.setStateOrProvince( request.getStateOrProvince() );
        }
        if ( request.getWebsite() != null ) {
            accountDetail.setWebsite( request.getWebsite() );
        }
        if ( request.getPhone() != null ) {
            accountDetail.setPhone( request.getPhone() );
        }
        if ( request.getDescription() != null ) {
            accountDetail.setDescription( request.getDescription() );
        }
        if ( request.getBirthday() != null ) {
            accountDetail.setBirthday( request.getBirthday() );
        }

        return accountDetail;
    }
}
