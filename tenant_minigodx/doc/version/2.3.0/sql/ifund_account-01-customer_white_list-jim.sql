-- 建索引
create index index_statistical_time on customer_cash_assets_history (statistical_time);

--新增白名单表
create table customer_white_list
(
    id           int auto_increment
        primary key,
    tenant_id    varchar(32)   null comment '租户id',
    cust_id      bigint        null comment '客户id',
    account_id   int           null comment '理财账号',
    create_time  datetime      null,
    creator      bigint        null comment '创建人id',
    creator_name varchar(32)   null comment '创建人名称',
    status       int default 0 null comment '0有效，1无效',
    id_deleted   int default 0 null comment '0正常，1删除'
);

--添加字段
alter table customer_info
    add risk_expiry_date date null comment '风险测评过期时间';
