package com.ameba.account.repository.mapper;

import com.ameba.account.api.bean.domain.Account;
import com.ameba.account.api.type.AccountScope;
import com.ameba.account.api.type.AccountStatus;
import com.ameba.account.api.type.AccountType;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AccountSqlMapper {

  @Insert("insert into account ("
      + "id,partner_id,owner,account_scope,account_type,"
      + "request_no,name,account_status,amount,"
      + "version,created_time,updated_time)"
      + " values("
      + "#{id},#{partnerId},#{owner},#{accountScope},#{accountType},"
      + "#{requestNo},#{name},#{accountStatus},#{amount},"
      + "#{version},#{createdTime},#{updatedTime})")
  int insert(Account account);

  @Select("SELECT * FROM account WHERE id = #{id}")
  @Results({
      @Result(property = "partnerId", column = "partner_id"),
      @Result(property = "accountScope", column = "account_scope", javaType = AccountScope.class),
      @Result(property = "accountType", column = "account_type", javaType = AccountType.class),
      @Result(property = "requestNo", column = "request_no"),
      @Result(property = "accountStatus", column = "account_status", javaType = AccountStatus.class),
      @Result(property = "createdTime", column = "created_time"),
      @Result(property = "updatedTime", column = "updated_time")})
  Account get(String id);

  @Select("SELECT * FROM account where id = #{id} and partner_id=#{partnerId}")
  @ResultMap("get-String")
  Account getById(@Param("partnerId") String partnerId,
                  @Param("id") String id);

  @Select("SELECT * FROM account where partner_id=#{partnerId} and owner=#{owner} "
      + "and account_scope=#{scope} and account_type=#{type}")
  @ResultMap("get-String")
  Account selectOne(@Param("partnerId") String partnerId,
                    @Param("owner") String owner,
                    @Param("scope") AccountScope scope,
                    @Param("type") AccountType type);

  @Update("update account set amount=amount+#{amount},"
      + "updated_time=#{now} where id=#{id}")
  int plusAmount(@Param("id") String id,
                 @Param("amount") BigDecimal amount,
                 @Param("now") LocalDateTime now);

  @Update("update account set amount=amount-#{amount},"
      + "updated_time=#{now} where id=#{id} and amount>=#{amount}")
  int minusAmount(@Param("id") String id,
                  @Param("amount") BigDecimal amount,
                  @Param("now") LocalDateTime now);
}
