package com.ameba.account.repository;

import com.ameba.account.api.bean.domain.Account;
import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.type.AccountScope;
import com.ameba.account.api.type.AccountType;
import com.ameba.account.repository.mapper.AccountSqlMapper;
import com.ameba.core.exception.ApiDuplicateReqException;
import com.ameba.core.utils.IdWorker;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Setter
public class AccountRepository {
  @Autowired
  private IdWorker idWorker;
  @Autowired
  private AccountSqlMapper accountMapper;

  public int create(Account account) {
    try {
      account.buildId(idWorker.nextIdString());
      return accountMapper.insert(account);
    } catch (DuplicateKeyException ex) {
      throw new ApiDuplicateReqException(ex).enhance(account.getRequestNo() + "重复的请求");
    }
  }

  public boolean plusAmount(Voucher voucher) {
    return accountMapper.plusAmount(voucher.getAccountId(), voucher.getAmount(),
        LocalDateTime.now()) == 1;
  }

  public boolean minusAmount(Voucher voucher) {
    return accountMapper.minusAmount(voucher.getAccountId(),
        voucher.getAmount(), LocalDateTime.now()) == 1;
  }

  public Optional<Account> get(String accountId) {
    return Optional.ofNullable(accountMapper.get(accountId));
  }

  public Optional<Account> get(String partnerId, String accountId) {
    return Optional.ofNullable(accountMapper.getById(partnerId, accountId));
  }

  public Optional<Account> select(String partnerId, String owner, AccountScope scope, AccountType type) {
    return Optional.ofNullable(accountMapper.selectOne(partnerId, owner, scope, type));
  }
}
