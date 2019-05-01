package com.ameba.account.api.bean.converter;

import com.ameba.account.api.bean.domain.Account;
import com.ameba.account.api.bean.req.AccountReq;
import com.ameba.account.api.bean.res.AccountRes;
import com.ameba.account.api.type.AccountScope;
import com.ameba.account.api.type.AccountStatus;
import com.ameba.account.api.type.AccountType;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-05-01T21:34:02+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class AccountConverterImpl implements AccountConverter {

    @Override
    public Account convert(AccountReq request) {
        if ( request == null ) {
            return null;
        }

        Account account = new Account();

        if ( request.getPartnerId() != null ) {
            account.setPartnerId( request.getPartnerId() );
        }
        if ( request.getOwner() != null ) {
            account.setOwner( request.getOwner() );
        }
        if ( request.getAccountScope() != null ) {
            account.setAccountScope( Enum.valueOf( AccountScope.class, request.getAccountScope() ) );
        }
        if ( request.getAccountType() != null ) {
            account.setAccountType( Enum.valueOf( AccountType.class, request.getAccountType() ) );
        }
        if ( request.getRequestNo() != null ) {
            account.setRequestNo( request.getRequestNo() );
        }
        if ( request.getName() != null ) {
            account.setName( request.getName() );
        }
        if ( request.getAccountStatus() != null ) {
            account.setAccountStatus( Enum.valueOf( AccountStatus.class, request.getAccountStatus() ) );
        }

        return account;
    }

    @Override
    public AccountRes convert(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountRes accountRes = new AccountRes();

        if ( account.getPartnerId() != null ) {
            accountRes.setPartnerId( account.getPartnerId() );
        }
        if ( account.getRequestNo() != null ) {
            accountRes.setRequestNo( account.getRequestNo() );
        }
        if ( account.getId() != null ) {
            accountRes.setId( account.getId() );
        }
        if ( account.getName() != null ) {
            accountRes.setName( account.getName() );
        }
        if ( account.getOwner() != null ) {
            accountRes.setOwner( account.getOwner() );
        }
        if ( account.getAccountScope() != null ) {
            accountRes.setAccountScope( account.getAccountScope().name() );
        }
        if ( account.getAccountType() != null ) {
            accountRes.setAccountType( account.getAccountType().name() );
        }
        if ( account.getAccountStatus() != null ) {
            accountRes.setAccountStatus( account.getAccountStatus().name() );
        }
        if ( account.getAmount() != null ) {
            accountRes.setAmount( account.getAmount() );
        }

        return accountRes;
    }
}
