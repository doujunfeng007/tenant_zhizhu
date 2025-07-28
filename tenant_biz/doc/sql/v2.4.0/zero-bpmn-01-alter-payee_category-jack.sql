alter table payee_category
    add benefit_no varchar(50) not null comment '收款账号';

alter table payee_category
    add benefit_bank_name varchar(50) not null comment '收款银行名称';

