package com.ameba.endpoint;


import com.ameba.account.api.AccountApi;
import com.ameba.account.api.bean.req.AccountReq;
import com.ameba.account.api.bean.req.GetAccountReq;
import com.ameba.account.api.bean.res.AccountRes;

import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountEndpoint {

  @Autowired
  private AccountApi accountApi;

  @POST
  public AccountRes create(AccountReq req) {
    return accountApi.create(req);
  }

  @GET
  @Path("{partnerId}/{accountId}")
  public AccountRes get(
      @PathParam("partnerId") String partnerId,
      @PathParam("accountId") String accountId) {
    GetAccountReq req = new GetAccountReq();
    req.setPartnerId(partnerId);
    req.setRequestNo(accountId);
    req.setAccountId(accountId);
    return accountApi.get(req);
  }

}
