package com.ameba.account.api.bean.converter;

import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.bean.res.AccountingRes;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-05-01T21:34:02+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class VoucherConverterImpl implements VoucherConverter {

    @Override
    public Voucher convertReq(AccountingReq request) {
        if ( request == null ) {
            return null;
        }

        Voucher voucher = new Voucher();

        if ( request.getAccountId() != null ) {
            voucher.setAccountId( request.getAccountId() );
        }
        if ( request.getPartnerId() != null ) {
            voucher.setPartnerId( request.getPartnerId() );
        }
        if ( request.getProductCode() != null ) {
            voucher.setProductCode( request.getProductCode() );
        }
        if ( request.getRequestNo() != null ) {
            voucher.setRequestNo( request.getRequestNo() );
        }
        if ( request.getAmount() != null ) {
            voucher.setAmount( request.getAmount() );
        }
        if ( request.getAmountType() != null ) {
            voucher.setAmountType( request.getAmountType() );
        }
        if ( request.getDescription() != null ) {
            voucher.setDescription( request.getDescription() );
        }

        return voucher;
    }

    @Override
    public AccountingRes convertRes(Voucher voucher) {
        if ( voucher == null ) {
            return null;
        }

        AccountingRes accountingRes = new AccountingRes();

        if ( voucher.getPartnerId() != null ) {
            accountingRes.setPartnerId( voucher.getPartnerId() );
        }
        if ( voucher.getRequestNo() != null ) {
            accountingRes.setRequestNo( voucher.getRequestNo() );
        }
        if ( voucher.getCode() != null ) {
            accountingRes.setCode( voucher.getCode() );
        }
        if ( voucher.getMessage() != null ) {
            accountingRes.setMessage( voucher.getMessage() );
        }
        if ( voucher.getStatus() != null ) {
            accountingRes.setStatus( voucher.getStatus() );
        }
        if ( voucher.getAccountingDate() != null ) {
            accountingRes.setAccountingDate( DateTimeFormatter.ISO_LOCAL_DATE.format( voucher.getAccountingDate() ) );
        }

        return accountingRes;
    }
}
