package com.ameba.account.repository;

import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.repository.mapper.AccountDetailSqlMapper;
import com.ameba.core.utils.IdWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class AccountDetailRepository {
  @Autowired
  private AccountDetailSqlMapper detailMapper;
  @Autowired
  private IdWorker idWorker;

  public int recordDetail(Voucher voucher) {
    return detailMapper.recordDetail(voucher, idWorker.nextId(), LocalDateTime.now());
  }
}
