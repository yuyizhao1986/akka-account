use akka_account;

CREATE TABLE account (
  id varchar(64)  NOT NULL,
  partner_id varchar(32)  NOT NULL,
  owner varchar(64)  NOT NULL,
  account_scope varchar(32)  NOT NULL,
  account_type varchar(32)  NOT NULL,
  request_no varchar(64)  NOT NULL,
  name varchar(32)  NOT NULL,
  account_status varchar(32)  NOT NULL,
  amount decimal(19,4) NOT NULL,
  version bigint(20) NOT NULL,
  created_time datetime NOT NULL,
  updated_time datetime NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UIDX_OWNER_SCOPE_TYPE (partner_id,owner,account_scope,account_type),
  UNIQUE KEY UIDX_REQUEST_NO (request_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE account_detail (
  id bigint(20) NOT NULL,
  account_id varchar(36)  NOT NULL,
  amount decimal(19,4) NOT NULL,
  amount_type varchar(32)  NOT NULL,
  accounting_amount decimal(19,4) NOT NULL,
  voucher_id bigint(20) NOT NULL,
  created_time datetime NOT NULL,
  PRIMARY KEY (id),
  KEY IDX_VOUCHER_ID (voucher_id),
  KEY IDX_ACCOUNT_ID_TIME (account_id,created_time)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE voucher (
  id bigint(20) NOT NULL,
  partner_id varchar(32)  NOT NULL,
  account_id varchar(64)  NOT NULL,
  product_code varchar(16)  NOT NULL,
  request_no varchar(64)  NOT NULL,
  amount decimal(19,4) NOT NULL,
  amount_type varchar(32)  NOT NULL,
  accounting_date date NOT NULL,
  status varchar(32)  NOT NULL,
  code varchar(32)  NOT NULL,
  message varchar(256)  NOT NULL,
  version bigint(20) NOT NULL,
  description varchar(256)  NOT NULL,
  created_time datetime NOT NULL,
  updated_time datetime NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UIDX_REQUEST_NO (request_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

