alter table ifund_account.t_product
    add faceValue decimal(21, 8) default 100.00000000  null comment '面值';
#需要在dataxweb 里面添加该同步字段
