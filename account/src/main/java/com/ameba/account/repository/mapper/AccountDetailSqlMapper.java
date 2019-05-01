package com.ameba.account.repository.mapper;

import com.ameba.account.api.bean.domain.Voucher;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface AccountDetailSqlMapper {

  @Insert("insert into account_detail (id,account_id,voucher_id,amount,amount_type,"
      + "accounting_amount,created_time) select #{id},#{v.accountId},#{v.id},#{v.amount},"
      + "#{v.amountType},amount,#{now} from account where id=#{v.accountId}")
  int recordDetail(@Param("v") Voucher voucher,
                   @Param("id") Long id,
                   @Param("now") LocalDateTime now);
}
