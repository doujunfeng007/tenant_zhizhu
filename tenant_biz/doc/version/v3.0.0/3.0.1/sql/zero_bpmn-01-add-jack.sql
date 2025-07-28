alter table zero_bpmn.customer_account_open_info
    add w8_agreement_time datetime(2) null comment 'w8协议_签署时间';


-- 投资调研问卷
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (6090, 'zero-biz-bpmn', 'w8_protocol_expired_or_not_signed', 'zh-CN', 'W-8BEN信息认证已到期或未签约，请重新确认。', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (6091, 'zero-biz-bpmn', 'w8_protocol_expired_or_not_signed', 'zh-HK', 'W-8BEN信息認證已到期或未簽約，請重新確認!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (6092, 'zero-biz-bpmn', 'w8_protocol_expired_or_not_signed', 'en-US', 'W-8BEN information certification has expired or not signed, please re-confirm', NULL, NULL, NULL, NULL, 0, 0);
