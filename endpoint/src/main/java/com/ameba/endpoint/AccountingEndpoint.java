package com.ameba.endpoint;

import com.ameba.account.api.AccountingApi;
import com.ameba.account.api.bean.req.AccountingReq;
import com.ameba.account.api.bean.req.GetAccountingReq;
import com.ameba.account.api.bean.res.AccountingRes;

import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("accounting")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountingEndpoint {

  @Autowired
  private AccountingApi accountingApi;

  @POST
  public AccountingRes create(AccountingReq req) {
    return accountingApi.execute(req);
  }

  @GET
  @Path("{partnerId}/{requestNo}")
  public AccountingRes get(
      @PathParam("partnerId") String partnerId,
      @PathParam("requestNo") String requestNo) {
    GetAccountingReq req = new GetAccountingReq();
    req.setRequestNo(requestNo);
    req.setPartnerId(partnerId);
    return accountingApi.get(req);
  }
}
