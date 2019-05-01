package com.ameba.account.repository;

import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.repository.mapper.VoucherSqlMapper;
import com.ameba.core.exception.ApiDuplicateReqException;
import com.ameba.core.utils.IdWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class VoucherRepository {
  @Autowired
  private VoucherSqlMapper voucherMapper;
  @Autowired
  private IdWorker idWorker;

  public int create(Voucher voucher) {
    try {
      voucher.setId(idWorker.nextId());
      return voucherMapper.insert(voucher);
    } catch (DuplicateKeyException ex) {
      throw new ApiDuplicateReqException(ex).enhance(
          voucher.getRequestNo() + "重复的请求");
    }
  }

  public Optional<Voucher> get(Long voucherId) {
    return Optional.ofNullable(voucherMapper.get(voucherId));
  }

  public Optional<Voucher> getByRequestNo(String requestNo) {
    return Optional.ofNullable(voucherMapper.getByRequestNo(requestNo));
  }

  public int updateResult(Voucher voucher) {
    return voucherMapper.updateResult(voucher, LocalDateTime.now());
  }
}
