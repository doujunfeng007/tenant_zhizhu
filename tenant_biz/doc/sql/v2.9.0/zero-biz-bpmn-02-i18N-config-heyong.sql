

SELECT * FROM `zero_cloud`.`zero_i18n_config` WHERE config_key IN('withdrawals_fund_submit_query_receive_account_error_notice','withdrawals_fund_submit_receive_account_error_notice','withdrawals_fund_query_Withdrawal_bank_failed_notice');

DELETE FROM `zero_cloud`.`zero_i18n_config` WHERE config_key IN('withdrawals_fund_submit_query_receive_account_error_notice','withdrawals_fund_submit_receive_account_error_notice','withdrawals_fund_query_Withdrawal_bank_failed_notice');


-- 出金申请提交
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3043, 'zero-biz-bpmn', 'withdrawals_fund_submit_query_receive_account_error_notice', 'zh-CN', '提交出金申请失败,根据币种:%s和转账方式:%s,查询付款账户信息异常,不支持该转账方式或币种!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3044, 'zero-biz-bpmn', 'withdrawals_fund_submit_query_receive_account_error_notice', 'zh-HK', '提交出金申請失敗,根據幣種:%s和轉賬方式:%s,查詢付款賬戶信息異常,不支持該轉賬方式或幣種!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3045, 'zero-biz-bpmn', 'withdrawals_fund_submit_query_receive_account_error_notice', 'en-US', 'Failed to submit withdrawal request. According to currency:%s and transfer method:%s, there is an abnormal query of payment account information. This transfer method or currency is not supported!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3046, 'zero-biz-bpmn', 'withdrawals_fund_submit_receive_account_error_notice', 'zh-CN', '提交出金申请失败,根据币种:%s和转账方式:%s,未查询到该转账方式和币种的付款账户信息配置!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3047, 'zero-biz-bpmn', 'withdrawals_fund_submit_receive_account_error_notice', 'zh-HK', '提交出金申請失敗,根據幣種:%s和轉賬方式:%s,未查詢到該轉賬方式和幣種的付款賬戶信息配置!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3048, 'zero-biz-bpmn', 'withdrawals_fund_submit_receive_account_error_notice', 'en-US', 'Failed to submit withdrawal request. According to the currency:%s and transfer method:%s, the payment account information configuration for this transfer method and currency could not be found!', NULL, NULL, NULL, NULL, 0, 0);




INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3172, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_failed_notice', 'zh-CN', '查询出金银行信息失败,绑定的银行卡失效,请重新绑定!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3173, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_failed_notice', 'zh-HK', '查詢出金銀行信息失敗,绑定的银行卡失效,请重新绑定!!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3174, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_failed_notice', 'en-US', 'Failed to query the information of withdrawal bank, the bound bank card is invalid, please rebind it!', NULL, NULL, NULL, NULL, 0, 0);



-- 投资调研问卷
SELECT *FROM `zero_cloud`.`zero_i18n_config`  WHERE id IN(3701,3702,3703);
DELETE FROM `zero_cloud`.`zero_i18n_config`  WHERE id IN(3701,3702,3703);

INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3701, 'zero-biz-bpmn', 'open_account_submit_data_invest_questionnaires_null_notice', 'zh-CN', '投资调研问卷不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3702, 'zero-biz-bpmn', 'open_account_submit_data_invest_questionnaires_null_notice', 'zh-HK', '投資調研問卷不能為空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3703, 'zero-biz-bpmn', 'open_account_submit_data_invest_questionnaires_null_notice', 'en-US', 'The investment research questionnaire cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);


-- 先查询，如果有了执行DELETE
SELECT *FROM `zero_cloud`.`zero_i18n_config`  WHERE id IN(6078,6079,6080);
DELETE FROM `zero_cloud`.`zero_i18n_config`  WHERE id IN(6078,6079,6080);

INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (6078, 'zero-biz-bpmn', 'customer_question_answer_is_empty', 'zh-CN', '投资调研问卷,问题或者答案不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (6079, 'zero-biz-bpmn', 'customer_question_answer_is_empty', 'zh-HK', '投資調研問卷,問題或者答案不能為空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (6080, 'zero-biz-bpmn', 'customer_question_answer_is_empty', 'en-US', 'Investment research questionnaire, questions or answers cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);
