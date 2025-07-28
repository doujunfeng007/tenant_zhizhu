alter table ifund_account.customer_info
    add pwd_upd_time datetime null comment '密码修改时间';
alter table ifund_account.customer_info
    modify pwd_warn_status tinyint(1) default 0 not null comment '密码提醒状态';
