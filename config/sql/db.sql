CREATE DATABASE IF NOT EXISTS akka_account DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

create user akka_account@'%' identified by 'akka_account';
SET PASSWORD FOR 'akka_account'@'localhost' = PASSWORD('akka_account');

grant all privileges on akka_account.* to akka_account@"%" ;

grant all privileges on akka_account.* to akka_account@"localhost" ;
