-- 执行地址:	203.86.122.229
-- 执行库：	zero_cloud
-- 执行时间：	2.5 上线前
-- *********************************************************【zero_cloud 库】************************************************************
alter table zero_dict_biz add dict_key_hk varchar(255) null comment '繁体key';

alter table zero_dict_biz add dict_value_hk varchar(255) null comment '繁体值';

alter table zero_dict_biz add dict_key_en varchar(255) null comment '英文key';

alter table zero_dict_biz add dict_value_en varchar(255) null comment '英文值';
