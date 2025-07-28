alter table sec_deposit_funds
    modify bank_code varchar(50) null comment '银行代码';

alter table sec_deposit_funds
    modify receiving_bank_code varchar(50) null comment '收款银行代码';

alter table sec_deposit_funds
    modify swift_code varchar(50) null comment 'SWIFT代码';

alter table sec_deposit_funds
    modify remittance_bank_id varchar(50) null comment '汇款银行id(edda入金需要：汇款银行bankId)';

ALTER TABLE client_edda_info_application
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_edda_fund_application
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_edda_info_image
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_fund_deposit_application
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_fund_deposit_image
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_fund_deposit_info
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_fund_refund_application
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_fund_withdraw_application
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE client_fund_withdraw_info
    MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE sec_deposit_funds
    MODIFY COLUMN modify_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
