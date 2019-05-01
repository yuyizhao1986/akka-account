package com.ameba.account.api.bean.converter;

import com.ameba.account.api.bean.domain.Account;
import com.ameba.account.api.bean.req.AccountReq;
import com.ameba.account.api.bean.res.AccountRes;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AccountConverter {

  Account convert(AccountReq request);

  AccountRes convert(Account account);

}
