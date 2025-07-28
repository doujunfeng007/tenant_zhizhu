-- auto-generated definition
-- ifund_account
create table t_non_trading_day_config
(
    id             int unsigned auto_increment
        primary key,
    nonTradingDay  date                                     not null comment '非交易日',
    createTime     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    updateTime     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '修改时间',
    time_period    tinyint     default 0                    null comment '时间段 0 全天 1 上午 2 下午',
    reason_closure varchar(100)                             null comment '休市原因',
    uin            varchar(100)                             null comment '最后更新人'
)
    collate = utf8mb4_general_ci
    row_format = COMPACT;


#需要在dataxweb 里面添加该同步该表
