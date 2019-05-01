package com.ameba.account.api.bean.converter;


import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.bean.res.AccountingRes;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface VoucherConverter {

  Voucher convertReq(AccountingReq request);

  AccountingRes convertRes(Voucher voucher);
}
