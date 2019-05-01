package com.ameba.account.repository.mapper;


import com.ameba.account.api.bean.domain.Voucher;
import com.ameba.account.api.type.AmountType;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface VoucherSqlMapper {
  @Insert("insert into voucher ("
      + "id,partner_id,account_id,product_code,request_no,"
      + "amount,amount_type,accounting_date,status,"
      + "code,message,version,description,"
      + "created_time,updated_time)"
      + " values("
      + "#{id},#{partnerId},#{accountId},#{productCode},#{requestNo},"
      + "#{amount},#{amountType},#{accountingDate},#{status},"
      + "#{code},#{message},#{version},#{description},"
      + "#{createdTime},#{updatedTime})")
  int insert(Voucher voucher);

  @Select("SELECT * FROM voucher WHERE id = #{id}")
  @Results({
      @Result(property = "partnerId", column = "partner_id"),
      @Result(property = "accountId", column = "account_id"),
      @Result(property = "productCode", column = "product_code"),
      @Result(property = "requestNo", column = "request_no"),
      @Result(property = "amountType", column = "amount_type", javaType = AmountType.class),
      @Result(property = "accountingDate", column = "accounting_date"),
      @Result(property = "updatedTime", column = "updated_time"),
      @Result(property = "createdTime", column = "created_time")})
  Voucher get(Long id);

  @Select("SELECT * FROM voucher WHERE request_no = #{requestNo}")
  @ResultMap("get-Long")
  Voucher getByRequestNo(String requestNo);

  @Update("update voucher set status=#{v.status},code=#{v.code},"
      + "message=#{v.message},updated_time=#{now} where id=#{v.id}")
  int updateResult(@Param("v") Voucher voucher, @Param("now") LocalDateTime now);

}
