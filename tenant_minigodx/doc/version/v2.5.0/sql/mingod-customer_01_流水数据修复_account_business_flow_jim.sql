-- 执行地址:	203.86.122.229
-- 执行库：	ifund_account
-- 执行时间：	2.5 上线前
-- *********************************************************【ifund_account 库】************************************************************
alter table account_business_flow  add status int null comment '0.预付款，1.付款成功，2.取消付款，3.部分付款';

alter table account_business_flow  add pay_amount decimal(11, 4) null comment '付款金额';

alter table account_business_flow  add refunding_amount decimal(11, 4) null comment '退还金额';

alter table account_business_flow  modify amount decimal(16, 4) default 0 null comment '业务金额';

alter table account_business_flow  alter column pay_amount set default 0;

alter table account_business_flow  alter column refunding_amount set default 0;

alter table financing_account_amount_flows modify business_type int null comment '参考枚举ThawingType';

alter table financing_account_amount_flows add operation_type int null comment '1.可用存入2.可用扣除3.可用转冻结4.冻结扣除5.冻结转可用';

alter table financing_account_amount_flows modify type int null comment '账户类型1现金可用账户，2现金冻结账户，3现金在途账户';

alter table account_business_flow  add source varchar(32) null comment '来源';

alter table account_business_flow add exchange_rate varchar(64) comment '跨币种交易时的计算汇率';


alter table customer_cash_assets_history add hkd_available decimal(16, 4) default 0 comment '港币可用金额';
alter table customer_cash_assets_history add hkd_freeze decimal(16, 4) default 0  comment '港币冻结';
alter table customer_cash_assets_history add hkd_intransit decimal(16, 4) default 0  comment '港币在途';
alter table customer_cash_assets_history add usd_available decimal(16, 4) default 0 comment '美元可用金额';
alter table customer_cash_assets_history add usd_freeze decimal(16, 4) default 0  comment '美元冻结';
alter table customer_cash_assets_history add usd_intransit decimal(16, 4) default 0  comment '美元在途';
alter table customer_cash_assets_history add cny_available decimal(16, 4) default 0 comment '人民币可用金额';
alter table customer_cash_assets_history add cny_freeze decimal(16, 4) default 0  comment '人民币冻结';
alter table customer_cash_assets_history add cny_intransit decimal(16, 4) default 0  comment '人民币在途';




-- 【执行到这里】先备份表account_business_flow 和 financing_account_amount_flows
-- 【下面四条sql要按顺序执行】
update financing_account_amount_flows as f set f.operation_type = f.business_type where f.id > 0

update financing_account_amount_flows as f inner join account_business_flow as a on a.id = f.account_business_flow_id set f.business_type = a.business_type where f.id > 0

alter table account_business_flow drop column business_type;

alter table account_business_flow add business_type int(4) null comment '參考枚举BusinessType';

alter table account_business_flow drop column remark;
