package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.entities.account.AccountDetail;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-20T10:48:19+0700",
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

        userDetailResponse.setFirstName( accountDetailFirstName( account ) );
        userDetailResponse.setMiddleName( accountDetailMiddleName( account ) );
        userDetailResponse.setLastName( accountDetailLastName( account ) );
        userDetailResponse.setAddress( accountDetailAddress( account ) );
        userDetailResponse.setCountry( accountDetailCountry( account ) );
        userDetailResponse.setStateOrProvince( accountDetailStateOrProvince( account ) );
        userDetailResponse.setWebsite( accountDetailWebsite( account ) );
        userDetailResponse.setPhone( accountDetailPhone( account ) );
        userDetailResponse.setDescription( accountDetailDescription( account ) );
        userDetailResponse.setBirthday( toBirthdate( accountDetailBirthday( account ) ) );
        userDetailResponse.setId( account.getId() );
        userDetailResponse.setUsername( account.getUsername() );
        userDetailResponse.setEmail( account.getEmail() );

        return userDetailResponse;
    }

    private String accountDetailFirstName(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        String firstName = detail.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String accountDetailMiddleName(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        String middleName = detail.getMiddleName();
        if ( middleName == null ) {
            return null;
        }
        return middleName;
    }

    private String accountDetailLastName(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        String lastName = detail.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String accountDetailAddress(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        String address = detail.getAddress();
        if ( address == null ) {
            return null;
        }
        return address;
    }

    private Integer accountDetailCountry(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        Integer country = detail.getCountry();
        if ( country == null ) {
            return null;
        }
        return country;
    }

    private Integer accountDetailStateOrProvince(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        Integer stateOrProvince = detail.getStateOrProvince();
        if ( stateOrProvince == null ) {
            return null;
        }
        return stateOrProvince;
    }

    private String accountDetailWebsite(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        String website = detail.getWebsite();
        if ( website == null ) {
            return null;
        }
        return website;
    }

    private String accountDetailPhone(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        String phone = detail.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }

    private String accountDetailDescription(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        String description = detail.getDescription();
        if ( description == null ) {
            return null;
        }
        return description;
    }

    private LocalDate accountDetailBirthday(Account account) {
        if ( account == null ) {
            return null;
        }
        AccountDetail detail = account.getDetail();
        if ( detail == null ) {
            return null;
        }
        LocalDate birthday = detail.getBirthday();
        if ( birthday == null ) {
            return null;
        }
        return birthday;
    }
}
