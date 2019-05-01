package com.ameba.account.service;

import com.ameba.account.api.AccountApi;
import com.ameba.account.api.bean.converter.AccountConverter;
import com.ameba.account.api.bean.domain.Account;
import com.ameba.account.api.bean.req.AccountReq;
import com.ameba.account.api.bean.req.GetAccountReq;
import com.ameba.account.api.bean.res.AccountRes;
import com.ameba.account.api.type.AccountScope;
import com.ameba.account.api.type.AccountStatus;
import com.ameba.account.api.type.AccountType;
import com.ameba.account.repository.AccountRepository;
import com.ameba.core.api.ApiTemplate;
import com.ameba.core.exception.ApiParamException;
import com.ameba.core.exception.Checker;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Setter
@Slf4j
public class AccountApiImpl implements AccountApi {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private AccountConverter converter;

  @Override
  public AccountRes create(AccountReq accountRequest) {
    return new ApiTemplate<AccountReq, AccountRes>(new AccountRes()) {
      @Override
      protected void validate(AccountReq request) {
        AccountScope.instance(request.getAccountScope())
            .orElseThrow(() -> new ApiParamException().enhance("accountScope不正确"));
        AccountType.instance(request.getAccountType())
            .orElseThrow(() -> new ApiParamException().enhance("accountType不正确"));
        AccountStatus.instance(request.getAccountStatus())
            .orElseThrow(() -> new ApiParamException().enhance("accountStatus不正确"));
      }

      @Override
      protected AccountRes process(AccountReq request) {
        Account account = converter.convert(request);
        int rows = accountRepository.create(account);
        Checker.checkDbResult(rows == 1, "创建账户失败");
        return converter.convert(account);
      }
    }.execute(accountRequest, "create-account", log);
  }

  @Override
  public AccountRes get(GetAccountReq accountGetRequest) {
    return new ApiTemplate<GetAccountReq, AccountRes>(new AccountRes()) {
      @Override
      protected AccountRes process(GetAccountReq request) {
        Optional<Account> account = accountRepository.get(request.getPartnerId(),
            request.getAccountId());
        AccountRes res = converter.convert(account
            .orElseThrow(() -> new ApiParamException().enhance(request.getAccountId() + "账户不存在")));
        res.setRequestNo(request.getRequestNo());
        return res;
      }
    }.execute(accountGetRequest, "get-account", log);
  }
}
