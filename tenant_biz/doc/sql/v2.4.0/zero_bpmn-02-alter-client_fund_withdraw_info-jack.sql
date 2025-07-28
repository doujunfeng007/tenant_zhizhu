## zero_bpmn添加字段 recv_bank_type 银行卡类型
alter table zero_bpmn.client_fund_withdraw_info
    add recv_bank_type int null comment '银行卡类型 1香港  2大陆 3海外' after recv_bank_acct;

## 设置默认值  需要确认线上数据是不是都是香港银行卡
UPDATE zero_bpmn.client_fund_withdraw_info
SET recv_bank_type = 1;
