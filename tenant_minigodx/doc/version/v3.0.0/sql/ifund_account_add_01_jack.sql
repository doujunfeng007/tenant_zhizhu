alter table `ifund_account`.customer_statement_file_history
    add send_email_id varchar(50) null comment '邮件工具_发送id ' after send_num;
