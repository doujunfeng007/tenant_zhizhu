alter table zero_biz.platform_email_content
    modify send_status int default 0 null comment '推送状态(0-未发送，1-已发送 2-发送失败 3-发送中)';


alter table ifund_account.customer_file
    add error_msg varchar(100) null comment '错误原因' after status;

alter table ifund_account.customer_file
    modify status int default 0 null comment '状态 0未发送 1已发送 2发送失败 3发送中';


alter table zero_biz.platform_mobile_content
    add sms_id varchar(100) null comment '唯一id_服务商消息id' after send_type;
