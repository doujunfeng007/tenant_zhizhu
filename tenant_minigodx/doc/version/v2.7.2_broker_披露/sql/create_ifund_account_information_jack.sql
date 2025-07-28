-- auto-generated definition
create table exchange_disclosure_announcement
(
    id             int auto_increment
        primary key,
    type           int           null comment '公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告',
    title          varchar(255)  null comment '标题',
    content        text          null comment '内容',
    time           datetime      null comment '发布时间',
    cust_id        varchar(255)  null comment '发布人id',
    cust_name      varchar(100)  null comment '发布人名字',
    status         int           null comment '状态;1.已保存  2待审核  3已驳回  4已审核  5已发布',
    application_id varchar(50)   null comment '预约号',
    rejected_cause varchar(256)  null comment '拒绝原因',
    is_deleted     int default 0 null comment '是否删除;是否删除  0未删除  1已删除',
    create_user    varchar(32)   null comment '创建人',
    create_time    datetime      null comment '创建时间',
    update_user    varchar(32)   null comment '更新人',
    update_time    datetime      null comment '更新时间'
)
    comment '披露公告表';

-- auto-generated definition
create table exchange_disclosure_announcement_file
(
    id              int auto_increment
        primary key,
    announcement_id int           null comment '公告id',
    file_url        varchar(256)  null comment '文件地址',
    is_deleted      int default 0 null comment '是否删除;是否删除  0未删除  1已删除',
    create_user     varchar(32)   null comment '创建人',
    create_time     datetime      null comment '创建时间',
    update_user     varchar(32)   null comment '更新人',
    update_time     datetime      null comment '更新时间'
)
    comment '披露公告附件表';

-- auto-generated definition
create table exchange_disclosure_announcement_product
(
    id              int auto_increment
        primary key,
    announcement_id int           null comment '公告id',
    product_id      varchar(255)  null comment '产品id',
    product_name    varchar(50)   null comment '产品名字',
    product_isin    varchar(100)  null comment '产品isin',
    is_deleted      int default 0 null comment '是否删除;是否删除  0未删除  1已删除',
    create_user     varchar(32)   null comment '创建人',
    create_time     datetime      null comment '创建时间',
    update_user     varchar(32)   null comment '更新人',
    update_time     datetime      null comment '更新时间'
)
    comment '披露产品公告表';




-- auto-generated definition
create table exchange_announcement
(
    id             int auto_increment
        primary key,
    type           int           null comment '公告类型;1.评级文件  2.业绩公告  3定期报告  4派息公告  5重大事项  6停牌公告',
    title          varchar(255)  null comment '标题',
    content        text          null comment '内容',
    time           datetime      null comment '发布时间',
    cust_id        varchar(255)  null comment '发布人id',
    cust_name      varchar(100)  null comment '发布人名字',
    status         int           null comment '状态;1.已保存  2待审核  3已驳回  4已审核  5已发布',
    application_id varchar(50)   null comment '预约号',
    rejected_cause varchar(256)  null comment '拒绝原因',
    is_deleted     int default 0 null comment '是否删除;是否删除  0未删除  1已删除',
    create_user    varchar(32)   null comment '创建人',
    create_time    datetime      null comment '创建时间',
    update_user    varchar(32)   null comment '更新人',
    update_time    datetime      null comment '更新时间'
)
    comment '交易所公告表';



-- auto-generated definition
create table exchange_announcement_file
(
    id              int auto_increment
        primary key,
    announcement_id int           null comment '公告id',
    file_url        varchar(256)  null comment '文件地址',
    is_deleted      int default 0 null comment '是否删除;是否删除  0未删除  1已删除',
    create_user     varchar(32)   null comment '创建人',
    create_time     datetime      null comment '创建时间',
    update_user     varchar(32)   null comment '更新人',
    update_time     datetime      null comment '更新时间'
)
    comment '交易所公告附件表';

