ALTER TABLE platform_push_msg_history MODIFY relation_id varchar(64) DEFAULT NULL COMMENT '第三方id';
ALTER TABLE platform_email_content ADD COLUMN channel int DEFAULT '0';
ALTER TABLE platform_push_msg_history ADD COLUMN channel int DEFAULT '0';
ALTER TABLE platform_push_msg_history ADD COLUMN device_id varchar(120) NULL COMMENT '推送设备信息';
ALTER TABLE platform_email_content ADD COLUMN msg_id varchar(120);
ALTER TABLE platform_mobile_content ADD COLUMN msg_id varchar(120);
ALTER TABLE platform_push_msg_history ADD COLUMN msg_id varchar(120);
