UPDATE `zero_cloud`.`zero_i18n_config` SET content='Bank card number:%s has been used by someone else and cannot be used again. Please re-enter!'  WHERE `config_key`='open_account_data_bank_card_number_error_notice' AND id='2685';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='Customer number: [%s] Institution account opening application has not been approved yet. Please wait for the account opening application to be approved before conducting risk assessment!'  WHERE `config_key`='risk_organization_open_account_record_unfinished_notice' AND id='2625';


UPDATE `zero_cloud`.`zero_i18n_config` SET content='Email:%s has been used by someone else and cannot be used again. Please re-enter!'  WHERE `config_key`='open_account_data_email_error_notice' AND id='2679';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='Supplementary ID number:%s has been used by someone else and cannot be used again. Please re-enter!'  WHERE `config_key`='open_account_data_supplementary_id_error_notice' AND id='2676';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='ID number:%s has been used by someone else and cannot be used again. Please re-enter!'  WHERE `config_key`='open_account_data_id_error_notice' AND id='2682';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='ID number No.:%s has been used by others and cannot be used again. Please re-enter!'  WHERE `config_key`='open_account_data_id_card_number_error_notice' AND id='2688';



UPDATE `zero_cloud`.`zero_i18n_config` SET content='Mobile number:%s has been used by someone else and cannot be used again. Please re-enter!'  WHERE `config_key`='open_account_data_phone_number_error_notice' AND id='2691';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='Bank card number:%s has been used as a deposit bank card by someone else when opening an account, and cannot be used again. Please re-enter!'  WHERE `config_key`='open_account_data_bank_card_used_error_notice' AND id='2694';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='Upload file (%s) failed!'  WHERE `config_key`='organization_open_account_uploadfile_failed_notice' AND id='2808';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='The size of the uploaded image cannot exceed %s M and be less than %s K!'  WHERE `config_key`='organization_open_account_submit_image_error_notice' AND id='2799';
UPDATE `zero_cloud`.`zero_i18n_config` SET content='In the institutional account opening parameter object, the shareholder information of type %s does not exist!'  WHERE `config_key`='organization_open_account_type_not_exist_notice' AND id='2829';


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3043, 'zero-biz-bpmn', 'withdrawals_fund_submit_query_receive_account_error_notice', 'zh-CN', '提交出金申请,查询付款账户信息异常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3044, 'zero-biz-bpmn', 'withdrawals_fund_submit_query_receive_account_error_notice', 'zh-HK', '提交出金申請,查詢付款賬戶信息異常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3045, 'zero-biz-bpmn', 'withdrawals_fund_submit_query_receive_account_error_notice', 'en-US', 'Submitting a withdrawal request, checking payment account information is abnormal!', NULL, NULL, NULL, NULL, 0, 0);

INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3046, 'zero-biz-bpmn', 'withdrawals_fund_submit_receive_account_error_notice', 'zh-CN', '提交出金申请,付款账户信息异常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3047, 'zero-biz-bpmn', 'withdrawals_fund_submit_receive_account_error_notice', 'zh-HK', '提交出金申請,付款賬戶信息異常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3048, 'zero-biz-bpmn', 'withdrawals_fund_submit_receive_account_error_notice', 'en-US', 'Submitting withdrawal request, abnormal payment account information!', NULL, NULL, NULL, NULL, 0, 0);

-- 专业投资者认证提交选择项字典英文脚本
SELECT *FROM zero_dict_biz WHERE `code`='professional_investor_options' AND tenant_id='000000';
-- 先删除专业投资者认证提交选择项字典配置
DELETE FROM zero_dict_biz WHERE `code`='professional_investor_options' AND tenant_id='000000';
-- 再重新插入专业投资者认证提交选择项
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1787674070023221250, '000000', 0, 'professional_investor_options', '-1', '专业投资者投资选项', '-1', '專業投資者投資選項', '-1', 'Professional Investor Investment Options', 0, '「专业投资者」的待遇', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1787676044231782402, '000000', 1787674070023221250, 'professional_investor_options', 'opt-a', 'a.本人账户内的投资组合；', 'opt-a', 'a.本人賬戶內的投資組合；', 'opt-a', 'a. The investment portfolio in my account;', 1, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1787676285878218753, '000000', 1787674070023221250, 'professional_investor_options', 'opt-b', 'b.本人联同有联系者（配偶或子女）于某联权共有账户内的投资组合；', 'opt-b', 'b.本人聯同有聯系者（配偶或子女）於某聯權共有賬戶內的投資組合；', 'opt-b', 'b. I, along with my spouse or children, have an investment portfolio in a joint ownership account;', 2, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1787676716448690178, '000000', 1787674070023221250, 'professional_investor_options', 'opt-c', 'c.本人联同一名或多于一名有联系者以外的人士于某联权共有账户内的投资组合中自己所占部分；', 'opt-c', 'c.本人聯同一名或多於一名有聯系者以外的人士於某聯權共有賬戶內的投資組合中自己所占部分；', 'opt-c', 'c. I, along with one or more individuals other than my affiliated persons, hold my own portion of the investment portfolio in a joint ownership account;', 3, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1787676825202798593, '000000', 1787674070023221250, 'professional_investor_options', 'opt-d', 'd.在有关日期的主要业务是持有投资项目并在有关日期由本人全资拥有的法团的投资组合。', 'opt-d', 'd.在有關日期的主要業務是持有投資項目並在有關日期由本人全資擁有的法團的投資組合。', 'opt-d', 'd. At the relevant date, the main business was holding investment projects and the investment portfolio of the corporation wholly owned by me at the relevant date.', 4, NULL, 0, 0);



-- 先查看原来的字典是否有配多语言
SELECT *FROM zero_dict_biz WHERE tenant_id='000000' AND `code` ='client_card_bank_type';
-- 然后再删除原来的记录
DELETE FROM zero_dict_biz WHERE tenant_id='000000' AND `code` ='client_card_bank_type';

-- 插入银行卡类型
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1801161860375158785, '000000', 0, 'client_card_bank_type', '-1', '银行卡类型', '-1', '銀行卡類型', '-1', 'Bank card type', 1, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1801162037987155969, '000000', 1801161860375158785, 'client_card_bank_type', '1', '香港本地银行', '1', '香港本地銀行', '1', 'Local banks in Hong Kong', 1, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1801162075148689410, '000000', 1801161860375158785, 'client_card_bank_type', '2', '中国大陆银行', '2', '中國大陸銀行', '2', 'Bank of Chinese Mainland', 2, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1801162123404156929, '000000', 1801161860375158785, 'client_card_bank_type', '3', '海外银行', '3', '海外銀行', '3', 'Overseas banks', 3, NULL, 0, 0);



-- 净资产字典值，先查看原来的字典是否有配多语言
SELECT *FROM zero_dict_biz WHERE code='net_asset' AND tenant_id='000000';
-- 然后再删除原来的记录
DELETE FROM zero_dict_biz WHERE code='net_asset' AND tenant_id='000000';


INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323352011456514, '000000', 0, 'net_asset', '-1', '净资产', '-1', '凈資產', '-1', 'Net assets', 0, 'Net assets', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323429656412162, '000000', 2553323352011456514, 'net_asset', '1', '≤50 万', '1', '≤50 萬', '1', '≤ 500000', 1, '≤50 万', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323464917925890, '000000', 2553323352011456514, 'net_asset', '2', '50–100(含) 万', '2', '50–100(含) 萬', '2', '500000 to 1000000 (inclusive)', 2, '50–100(含) 万', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323524527374338, '000000', 2553323352011456514, 'net_asset', '3', '100-500(含) 万', '3', '100-500(含) 萬', '3', '1-5 million (inclusive)', 3, '100-500(含) 万', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323555028353026, '000000', 2553323352011456514, 'net_asset', '4', '500-1000(含) 万', '4', '500-1000(含) 萬', '4', '5-10 million (inclusive)', 4, '500-1000(含) 万', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323619880681474, '000000', 2553323352011456514, 'net_asset', '5', '1000-5000(含) 万', '5', '1000-5000(含) 萬', '5', '10-50 million (inclusive)', 5, '1000-5000(含) 万', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323668815626241, '000000', 2553323352011456514, 'net_asset', '6', '5000万-1亿(含)', '6', '5000萬-1億(含)', '6', '50-100 million (inclusive)', 6, '5000万-1亿(含)', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2553323724914442242, '000000', 2553323352011456514, 'net_asset', '7', '>1亿', '7', '>1億', '7', '>100 million', 7, '>1亿', 0, 0);



-- 国家区域排序，前端是按sort最大的排在最前面的
SELECT *FROM zero_dict_biz WHERE `code`='country_or_region' AND tenant_id='000000' ORDER BY sort DESC;

-- 删除原来旧的记录
DELETE FROM zero_dict_biz WHERE `code`='country_or_region' AND tenant_id='000000';

-- 插入新的排序
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2554349811380121601, '000000', 2554349303525404674, 'country_or_region', '1', '中国大陆', '1', '中國大陸', '1', 'Chinese mainland', 16, '中国大陆', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2554349887976501249, '000000', 2554349303525404674, 'country_or_region', '2', '港澳台地区', '2', '港澳臺地區', '2', 'Hong Kong, Macao and Taiwan regions', 15, '港澳台地区', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561965015071100929, '000000', 2554349303525404674, 'country_or_region', '17', '英国', '17', '英國', '17', 'Britain', 14, '英国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2554350032440913922, '000000', 2554349303525404674, 'country_or_region', '5', '美国', '5', '美國', '5', 'U.S.A', 13, '美国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964703514005505, '000000', 2554349303525404674, 'country_or_region', '10', '柬埔寨', '10', '柬埔寨', '10', 'Cambodia', 12, '柬埔寨', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964674816577537, '000000', 2554349303525404674, 'country_or_region', '9', '加拿大', '9', '加拿大', '9', 'Canada', 11, '加拿大', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964479072604162, '000000', 2554349303525404674, 'country_or_region', '6', '澳大利亚', '6', '澳大利亞', '6', 'Australia', 10, '澳大利亚', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964580692201473, '000000', 2554349303525404674, 'country_or_region', '7', '菲律宾', '7', '菲律賓', '7', 'Philippines', 9, '菲律宾', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964620559060994, '000000', 2554349303525404674, 'country_or_region', '8', '韩国', '8', '韓國', '8', 'Korea', 8, '韩国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964841963786242, '000000', 2554349303525404674, 'country_or_region', '13', '泰国', '13', '泰國', '13', 'Thailand', 7, '泰国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964863988076546, '000000', 2554349303525404674, 'country_or_region', '14', '新加坡', '14', '新加坡', '14', 'Singapore', 6, '新加坡', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964808686178306, '000000', 2554349303525404674, 'country_or_region', '12', '日本', '12', '日本', '12', 'Japan', 5, '日本', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964767456169986, '000000', 2554349303525404674, 'country_or_region', '11', '马来西亚', '11', '馬來西亞', '11', 'Malaysia', 4, '马来西亚', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964953620353026, '000000', 2554349303525404674, 'country_or_region', '15', '新西兰', '15', '新西蘭', '15', 'New Zealand', 3, '新西兰', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964995127185410, '000000', 2554349303525404674, 'country_or_region', '16', '越南', '16', '越南', '16', 'Vietnam', 2, '越南', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561965077696253954, '000000', 2554349303525404674, 'country_or_region', '18', '印度尼西亚', '18', '印度尼西亞', '18', 'Indonesia', 1, '印度尼西亚', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2554349303525404674, '000000', 0, 'country_or_region', '-1', '国家/地区', '-1', '國家/地區', NULL, NULL, NULL, '国家/地区列表', 0, 0);




-- 国家排序，前端是按sort最大的排在最前面的
SELECT *FROM zero_dict_biz WHERE `code`='nationality' AND tenant_id='000000' ORDER BY sort DESC;

-- 删除原来旧的排序记录
DELETE FROM zero_dict_biz WHERE `code`='nationality' AND tenant_id='000000';

-- 插入新的排序记录
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2554343921906089986, '000000', 2554343680897187842, 'nationality', '1', '中国', '1', '中國', '1', 'China', 18, '中国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2562693603030372353, '000000', 2554343680897187842, 'nationality', '2', '中国香港', '2', '中國香港', '2', 'Hong Kong, China', 17, '中国香港', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2562693692629094402, '000000', 2554343680897187842, 'nationality', '3', '中国澳门', '3', '中國澳門', '3', 'Macao China', 16, '中国澳门', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2562694105638014977, '000000', 2554343680897187842, 'nationality', '4', '中国台湾', '4', '中國臺灣', '4', 'Taiwan, China', 15, '中国台湾', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964161366659073, '000000', 2554343680897187842, 'nationality', '17', '英国', '17', '英國', '17', 'Britain', 14, '英国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963900560642050, '000000', 2554343680897187842, 'nationality', '12', '日本', '12', '日本', '12', 'Japan', 13, '日本', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963868193198082, '000000', 2554343680897187842, 'nationality', '11', '马来西亚', '11', '馬來西亞', '11', 'Malaysia', 12, '马来西亚', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963793610084353, '000000', 2554343680897187842, 'nationality', '10', '柬埔寨', '10', '柬埔寨', '10', 'Cambodia', 11, '柬埔寨', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963764795215874, '000000', 2554343680897187842, 'nationality', '9', '加拿大', '9', '加拿大', '9', 'Canada', 10, '加拿大', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963693399773186, '000000', 2554343680897187842, 'nationality', '8', '韩国', '8', '韓國', '8', 'Korea', 9, '韩国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963614773350402, '000000', 2554343680897187842, 'nationality', '7', '菲律宾', '7', '菲律賓', '7', 'Philippines', 8, '菲律宾', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963582162636802, '000000', 2554343680897187842, 'nationality', '6', '澳大利亚', '6', '澳大利亞', '6', 'Australia', 7, '澳大利亚', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2554348954156658689, '000000', 2554343680897187842, 'nationality', '5', '美国', '5', '美國', '5', 'U.S.A', 6, '美国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964080232042497, '000000', 2554343680897187842, 'nationality', '15', '新西兰', '15', '新西蘭', '15', 'New Zealand', 5, '新西兰', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964000741593090, '000000', 2554343680897187842, 'nationality', '14', '新加坡', '14', '新加坡', '14', 'Singapore', 4, '新加坡', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964118932885505, '000000', 2554343680897187842, 'nationality', '16', '越南', '16', '越南', '16', 'Vietnam', 3, '越南', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561963977366736897, '000000', 2554343680897187842, 'nationality', '13', '泰国', '13', '泰國', '13', 'Thailand', 2, '泰国', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2561964223069065217, '000000', 2554343680897187842, 'nationality', '18', '印度尼西亚', '18', '印度尼西亞', '18', 'Indonesia', 1, '印度尼西亚', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (2554343680897187842, '000000', 0, 'nationality', '-1', '国籍', '-1', '國籍', '-1', 'nationality', 0, '国籍列表', 0, 0);



-- 单独修改Minigod服务的配置(先删除config_key配重复的键)
DELETE FROM `zero_cloud`.`zero_i18n_config` WHERE id IN(3166, 3167, 3168);

-- 再重新插入删除的键
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3166, 'minigod-customer', 'minigod_cust_role_not_bound_change_role_notice', 'zh-CN', '设置失败，用户未绑定该角色!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3167, 'minigod-customer', 'minigod_cust_role_not_bound_change_role_notice', 'zh-HK', '設設置失敗，用戶未綁定该角色!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3168, 'minigod-customer', 'minigod_cust_role_not_bound_change_role_notice', 'en-US', 'Setting failed, user not bound to this role!', NULL, NULL, NULL, NULL, 0, 0);
-- 单独修改结束 --


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3169, 'zero-biz-bpmn', 'withdrawals_fund_submit_bank_code_id_empty_notice', 'zh-CN', '出金银行bankCode和bankId不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3170, 'zero-biz-bpmn', 'withdrawals_fund_submit_bank_code_id_empty_notice', 'zh-HK', '出金銀行bankCode和bankId不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3171, 'zero-biz-bpmn', 'withdrawals_fund_submit_bank_code_id_empty_notice', 'en-US', 'The bankCode and bankId of the withdrawal bank cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3172, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_failed_notice', 'zh-CN', '查询出金银行信息失败!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3173, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_failed_notice', 'zh-HK', '查詢出金銀行信息失敗!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3174, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_failed_notice', 'en-US', 'Failed to query the information of withdrawal bank!', NULL, NULL, NULL, NULL, 0, 0);

INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3175, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_config_failed_notice', 'zh-CN', '查询出金银行信息失败,请确认出金银行信息配置是否存在,bankCode:%s,bankId:%s!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3176, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_config_failed_notice', 'zh-HK', '查詢出金銀行信息失敗,請確認出金銀行信息配置是否存在,bankCode:%s,bankId:%s!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3177, 'zero-biz-bpmn', 'withdrawals_fund_query_Withdrawal_bank_config_failed_notice', 'en-US', 'Failed to retrieve the information of the withdrawal bank. Please confirm the configuration of withdrawal bank information exists, bankCode:%s, bankId:%s!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3178, 'zero-biz-bpmn', 'withdrawals_fund_submit_failed_notice', 'zh-CN', '提交出金申请失败！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3179, 'zero-biz-bpmn', 'withdrawals_fund_submit_failed_notice', 'zh-HK', '提交出金申請失敗！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3180, 'zero-biz-bpmn', 'withdrawals_fund_submit_failed_notice', 'en-US', 'Failed to submit withdrawal request!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3181, 'zero-biz-bpmn', 'withdrawals_fund_obtain_fund_account_failed_notice', 'zh-CN', '资金账号获取失败！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3182, 'zero-biz-bpmn', 'withdrawals_fund_obtain_fund_account_failed_notice', 'zh-HK', '資金賬號獲取失敗！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3183, 'zero-biz-bpmn', 'withdrawals_fund_obtain_fund_account_failed_notice', 'en-US', 'Failed to obtain fund account!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3184, 'zero-biz-bpmn', 'withdrawals_fund_submit_bank_bind_unbind_modify_failed_notice', 'zh-CN', '提交绑定/解绑/修改收款银行申请失败！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3185, 'zero-biz-bpmn', 'withdrawals_fund_submit_bank_bind_unbind_modify_failed_notice', 'zh-HK', '提交綁定/解綁/修改收款銀行申請失敗！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3186, 'zero-biz-bpmn', 'withdrawals_fund_submit_bank_bind_unbind_modify_failed_notice', 'en-US', 'Failed to submit the application for binding/unbinding/modifying the receiving bank!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3187, 'zero-biz-bpmn', 'withdrawals_fund_revocation_successful_notice', 'zh-CN', '撤销成功！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3188, 'zero-biz-bpmn', 'withdrawals_fund_revocation_successful_notice', 'zh-HK', '撤銷成功！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3189, 'zero-biz-bpmn', 'withdrawals_fund_revocation_successful_notice', 'en-US', 'Revocation successful!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3190, 'zero-biz-bpmn', 'withdrawals_fund_upload_image_type_error_notice', 'zh-CN', '非法图片类型,只支持jpg,jpeg,png,gif,heic格式!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3191, 'zero-biz-bpmn', 'withdrawals_fund_upload_image_type_error_notice', 'zh-HK', '非法圖片類型,只支持jpg,jpeg,png,gif,heic格式!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3192, 'zero-biz-bpmn', 'withdrawals_fund_upload_image_type_error_notice', 'en-US', 'Illegal image type, only supports JPG, JPEG, PNG, GIF, Heic formats!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3193, 'zero-biz-bpmn', 'withdrawals_fund_upload_image_size_error_notice', 'zh-CN', '上传图片大小不能超过%sM和小于%sK!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3194, 'zero-biz-bpmn', 'withdrawals_fund_upload_image_size_error_notice', 'zh-HK', '上傳圖片大小不能超過%sM和小于%sK!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3195, 'zero-biz-bpmn', 'withdrawals_fund_upload_image_size_error_notice', 'en-US', 'The size of the uploaded image cannot exceed %s M and be less than %s K!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3196, 'zero-biz-bpmn', 'withdrawals_fund_upload_voucher_failed_notice', 'zh-CN', '上传凭证失败!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3197, 'zero-biz-bpmn', 'withdrawals_fund_upload_voucher_failed_notice', 'zh-HK', '上傳憑證失敗!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3198, 'zero-biz-bpmn', 'withdrawals_fund_upload_voucher_failed_notice', 'en-US', 'Upload voucher failed!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3199, 'zero-biz-bpmn', 'withdrawals_fund_confirm_submit_account_notice', 'zh-CN', '请确认您提交的汇款账号是您本人的汇款账号！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3200, 'zero-biz-bpmn', 'withdrawals_fund_confirm_submit_account_notice', 'zh-HK', '請確認您提交的彙款賬號是您本人的彙款賬號！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3201, 'zero-biz-bpmn', 'withdrawals_fund_confirm_submit_account_notice', 'en-US', 'Please confirm that the remittance account you submitted is your own remittance account!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3202, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_not_exist_notice', 'zh-CN', '银行卡信息不存在！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3203, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_not_exist_notice', 'zh-HK', '銀行卡信息不存在！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3204, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_not_exist_notice', 'en-US', 'Bank card information does not exist!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3205, 'zero-biz-bpmn', 'withdrawals_fund_card_already_has_application_notice', 'zh-CN', '该银行卡已经存在审批流程！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3206, 'zero-biz-bpmn', 'withdrawals_fund_card_already_has_application_notice', 'zh-HK', '該銀行卡已經存在審批流程！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3207, 'zero-biz-bpmn', 'withdrawals_fund_card_already_has_application_notice', 'en-US', 'The bank card already has an approval process in place!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3208, 'zero-biz-bpmn', 'withdrawals_fund_card_already_has_unbinding_application_notice', 'zh-CN', '该银行卡存在解绑审批流程！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3209, 'zero-biz-bpmn', 'withdrawals_fund_card_already_has_unbinding_application_notice', 'zh-HK', '該銀行卡存在解綁審批流程！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3210, 'zero-biz-bpmn', 'withdrawals_fund_card_already_has_unbinding_application_notice', 'en-US', 'The bank card has a unbinding approval process!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3211, 'zero-biz-bpmn', 'withdrawals_fund_unbound_bank_card_not_exist_notice', 'zh-CN', '解绑的银行卡不存在！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3212, 'zero-biz-bpmn', 'withdrawals_fund_unbound_bank_card_not_exist_notice', 'zh-HK', '解綁的銀行卡不存在！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3213, 'zero-biz-bpmn', 'withdrawals_fund_unbound_bank_card_not_exist_notice', 'en-US', 'The unbound bank card does not exist!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3214, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_been_unbound_notice', 'zh-CN', '该银行卡已解绑！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3215, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_been_unbound_notice', 'zh-HK', '該銀行卡已解綁！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3216, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_been_unbound_notice', 'en-US', 'The bank card has been unbound!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3217, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_has_modification_process_notice', 'zh-CN', '该银行卡存在修改审批流程！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3218, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_has_modification_process_notice', 'zh-HK', '該銀行卡存在修改審批流程！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3219, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_has_modification_process_notice', 'en-US', 'The bank card has a modification approval process!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3220, 'zero-biz-bpmn', 'withdrawals_fund_modified_bank_card_not_exist_notice', 'zh-CN', '修改的银行卡不存在！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3221, 'zero-biz-bpmn', 'withdrawals_fund_modified_bank_card_not_exist_notice', 'zh-HK', '修改的銀行卡不存在！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3222, 'zero-biz-bpmn', 'withdrawals_fund_modified_bank_card_not_exist_notice', 'en-US', 'The modified bank card does not exist!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3223, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_been_bound_notice', 'zh-CN', '已绑定过该银行卡！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3224, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_been_bound_notice', 'zh-HK', '已綁定過該銀行卡！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3225, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_been_bound_notice', 'en-US', 'This bank card has already been bound!', NULL, NULL, NULL, NULL, 0, 0);

INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3226, 'zero-biz-bpmn', 'withdrawals_fund_submit_application_failed_notice', 'zh-CN', '提交银行卡审批申请失败！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3227, 'zero-biz-bpmn', 'withdrawals_fund_submit_application_failed_notice', 'zh-HK', '提交銀行卡審批申請失敗！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3228, 'zero-biz-bpmn', 'withdrawals_fund_submit_application_failed_notice', 'en-US', 'Failed to submit bank card approval application!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3229, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_id_empty_notice', 'zh-CN', '%s的银行卡ID不能为空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3230, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_id_empty_notice', 'zh-HK', '%s的銀行卡ID不能爲空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3231, 'zero-biz-bpmn', 'withdrawals_fund_bank_card_id_empty_notice', 'en-US', '%s the bank card ID of s cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3232, 'zero-biz-bpmn', 'withdrawals_fund_amount_error_notice', 'zh-CN', '取款金额必须大于手续费！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3233, 'zero-biz-bpmn', 'withdrawals_fund_amount_error_notice', 'zh-HK', '取款金額必須大于手續費！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3234, 'zero-biz-bpmn', 'withdrawals_fund_amount_error_notice', 'en-US', 'The withdrawal amount must be greater than the handling fee!', NULL, NULL, NULL, NULL, 0, 0);





INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3235, 'zero-biz-bpmn', 'withdrawals_fund_can_be_amount_error_notice', 'zh-CN', '查询账户可提金额出错！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3236, 'zero-biz-bpmn', 'withdrawals_fund_can_be_amount_error_notice', 'zh-HK', '查詢賬戶可提金額出錯！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3237, 'zero-biz-bpmn', 'withdrawals_fund_can_be_amount_error_notice', 'en-US', 'Error in checking the amount that can be withdrawn from the account!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3238, 'zero-biz-bpmn', 'withdrawals_fund_insufficient_amount_error_notice', 'zh-CN', '可提取余额不足！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3239, 'zero-biz-bpmn', 'withdrawals_fund_insufficient_amount_error_notice', 'zh-HK', '可提取余額不足！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3240, 'zero-biz-bpmn', 'withdrawals_fund_insufficient_amount_error_notice', 'en-US', 'Insufficient withdrawable balance!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3241, 'zero-biz-bpmn', 'withdrawals_fund_currency_type_error_notice', 'zh-CN', '取款币种: %s 不正确，不支持该币种!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3242, 'zero-biz-bpmn', 'withdrawals_fund_currency_type_error_notice', 'zh-HK', '取款幣種: %s 不正確，不支持該幣種!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3243, 'zero-biz-bpmn', 'withdrawals_fund_currency_type_error_notice', 'en-US', 'Withdrawal currency:%s is incorrect and not supported!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3244, 'zero-biz-bpmn', 'withdrawals_fund_application_processing_error_notice', 'zh-CN', '取款申请处理失败!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3245, 'zero-biz-bpmn', 'withdrawals_fund_application_processing_error_notice', 'zh-HK', '取款申請處理失敗!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3246, 'zero-biz-bpmn', 'withdrawals_fund_application_processing_error_notice', 'en-US', 'Withdrawal application processing failed!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3247, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_frozen_amount_failed_notice', 'zh-CN', '取款冻结金额失败!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3248, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_frozen_amount_failed_notice', 'zh-HK', '取款凍結金額失敗!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3249, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_frozen_amount_failed_notice', 'en-US', 'Withdrawal frozen amount failed!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3250, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_date_exception_notice', 'zh-CN', '出金申请信息提交失败，日期解析异常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3251, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_date_exception_notice', 'zh-HK', '出金申請信息提交失敗，日期解析異常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3252, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_date_exception_notice', 'en-US', 'Submission of withdrawal application information failed, date parsing exception!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3253, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_customer_id_error_notice', 'zh-CN', '客户ID不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3254, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_customer_id_error_notice', 'zh-HK', '客戶ID不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3255, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_customer_id_error_notice', 'en-US', 'Customer ID cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3256, 'zero-biz-bpmn', 'withdrawals_fund_manage_account_empty_notice', 'zh-CN', '理财账号不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3257, 'zero-biz-bpmn', 'withdrawals_fund_manage_account_empty_notice', 'zh-HK', '理財賬號不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3258, 'zero-biz-bpmn', 'withdrawals_fund_manage_account_empty_notice', 'en-US', 'The wealth management account cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);




INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3259, 'zero-biz-bpmn', 'withdrawals_fund_customer_name_empty_notice', 'zh-CN', '客户名称不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3260, 'zero-biz-bpmn', 'withdrawals_fund_customer_name_empty_notice', 'zh-HK', '客戶名稱不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3261, 'zero-biz-bpmn', 'withdrawals_fund_customer_name_empty_notice', 'en-US', 'Customer name cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3262, 'zero-biz-bpmn', 'withdrawals_fund_customer_en_name_empty_notice', 'zh-CN', '客户英文名不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3263, 'zero-biz-bpmn', 'withdrawals_fund_customer_en_name_empty_notice', 'zh-HK', '客戶英文名不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3264, 'zero-biz-bpmn', 'withdrawals_fund_customer_en_name_empty_notice', 'en-US', 'Customer English name cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3265, 'zero-biz-bpmn', 'withdrawals_fund_customer_mobile_empty_notice', 'zh-CN', '手机号不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3266, 'zero-biz-bpmn', 'withdrawals_fund_customer_mobile_empty_notice', 'zh-HK', '手機號不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3267, 'zero-biz-bpmn', 'withdrawals_fund_customer_mobile_empty_notice', 'en-US', 'Mobile number cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3268, 'zero-biz-bpmn', 'withdrawals_fund_currency_empty_notice', 'zh-CN', '请选择币种!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3269, 'zero-biz-bpmn', 'withdrawals_fund_currency_empty_notice', 'zh-HK', '請選擇幣種!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3270, 'zero-biz-bpmn', 'withdrawals_fund_currency_empty_notice', 'en-US', 'Please select a currency!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3271, 'zero-biz-bpmn', 'withdrawals_fund_commission_time_empty_notice', 'zh-CN', '请选择委托时间!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3272, 'zero-biz-bpmn', 'withdrawals_fund_commission_time_empty_notice', 'zh-HK', '請選擇委托時間!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3273, 'zero-biz-bpmn', 'withdrawals_fund_commission_time_empty_notice', 'en-US', 'Please select the commission time!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3274, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_method_empty_notice', 'zh-CN', '请选择取款方式!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3275, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_method_empty_notice', 'zh-HK', '請選擇取款方式!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3276, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_method_empty_notice', 'en-US', 'Please choose a withdrawal method!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3574, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_amount_empty_notice', 'zh-CN', '请输入取款金额!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3575, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_amount_empty_notice', 'zh-HK', '請輸入取款金額!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3576, 'zero-biz-bpmn', 'withdrawals_fund_withdrawal_amount_empty_notice', 'en-US', 'Please enter the withdrawal amount!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3277, 'zero-biz-bpmn', 'withdrawals_fund_receiving_bank_empty_notice', 'zh-CN', '请选择收款银行!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3278, 'zero-biz-bpmn', 'withdrawals_fund_receiving_bank_empty_notice', 'zh-HK', '請選擇收款銀行!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3279, 'zero-biz-bpmn', 'withdrawals_fund_receiving_bank_empty_notice', 'en-US', 'Please select the receiving bank!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3280, 'zero-biz-bpmn', 'withdrawals_fund_receiving_bank_account_empty_notice', 'zh-CN', '请选择收款银行账号!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3281, 'zero-biz-bpmn', 'withdrawals_fund_receiving_bank_account_empty_notice', 'zh-HK', '請選擇收款銀行賬號!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3282, 'zero-biz-bpmn', 'withdrawals_fund_receiving_bank_account_empty_notice', 'en-US', 'Please select the receiving bank account!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3283, 'zero-biz-bpmn', 'withdrawals_fund_bank_code_empty_notice', 'zh-CN', '银行代码不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3284, 'zero-biz-bpmn', 'withdrawals_fund_bank_code_empty_notice', 'zh-HK', '銀行代碼不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3285, 'zero-biz-bpmn', 'withdrawals_fund_bank_code_empty_notice', 'en-US', 'Bank code cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3286, 'zero-biz-bpmn', 'withdrawals_fund_bank_code_format_notice', 'zh-CN', '银行代码格式错误, 限数字!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3287, 'zero-biz-bpmn', 'withdrawals_fund_bank_code_format_notice', 'zh-HK', '銀行代碼格式錯誤, 限數字!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3288, 'zero-biz-bpmn', 'withdrawals_fund_bank_code_format_notice', 'en-US', 'Bank code format error, limited to digits!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3289, 'zero-biz-bpmn', 'withdrawals_fund_relationship_third_party_notice', 'zh-CN', '与第三方收款人关系不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3290, 'zero-biz-bpmn', 'withdrawals_fund_relationship_third_party_notice', 'zh-HK', '與第三方收款人關系不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3291, 'zero-biz-bpmn', 'withdrawals_fund_relationship_third_party_notice', 'en-US', 'The relationship with the third-party payee cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3292, 'zero-biz-bpmn', 'withdrawals_fund_reason_third_party_notice', 'zh-CN', '第三方收款原因不能为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3293, 'zero-biz-bpmn', 'withdrawals_fund_reason_third_party_notice', 'zh-HK', '第三方收款原因不能爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3294, 'zero-biz-bpmn', 'withdrawals_fund_reason_third_party_notice', 'en-US', 'The reason for third-party payment cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3295, 'zero-biz-bpmn', 'withdrawals_fund_dbs_bank_not_exist_notice', 'zh-CN', '查询DBS付款银行不存在!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3296, 'zero-biz-bpmn', 'withdrawals_fund_dbs_bank_not_exist_notice', 'zh-HK', '查詢DBS付款銀行不存在!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3297, 'zero-biz-bpmn', 'withdrawals_fund_dbs_bank_not_exist_notice', 'en-US', 'Querying DBS payment bank does not exist!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3298, 'zero-biz-bpmn', 'withdrawals_fund_unsupported_bank_account_type_notice', 'zh-CN', '不支持的银行账户类型, 资金帐号:%s 银行账号类型:%s!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3299, 'zero-biz-bpmn', 'withdrawals_fund_unsupported_bank_account_type_notice', 'zh-HK', '不支持的銀行賬戶類型, 資金帳號:%s 銀行賬號類型:%s!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3300, 'zero-biz-bpmn', 'withdrawals_fund_unsupported_bank_account_type_notice', 'en-US', 'Unsupported bank account type, fund account: %s bank account type: %s !', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3301, 'zero-biz-bpmn', 'withdrawals_fund_fps_not_support_usd_notice', 'zh-CN', 'FPS ID不支持美金!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3302, 'zero-biz-bpmn', 'withdrawals_fund_fps_not_support_usd_notice', 'zh-HK', 'FPS ID不支持美金!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3303, 'zero-biz-bpmn', 'withdrawals_fund_fps_not_support_usd_notice', 'en-US', 'FPS ID does not support USD!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3304, 'zero-biz-bpmn', 'withdrawals_fund_fps_id_invalid_notice', 'zh-CN', 'FPS Id 验证无效，请重新输入!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3305, 'zero-biz-bpmn', 'withdrawals_fund_fps_id_invalid_notice', 'zh-HK', 'FPS Id 驗證無效，請重新輸入!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3306, 'zero-biz-bpmn', 'withdrawals_fund_fps_id_invalid_notice', 'en-US', 'FPS Id verification is invalid, please re-enter!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3307, 'zero-biz-bpmn', 'withdrawals_fund_amount_limit_notice', 'zh-CN', '取款金额超限，必须小于 %s !', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3308, 'zero-biz-bpmn', 'withdrawals_fund_amount_limit_notice', 'zh-HK', '取款金額超限，必須小于 %s', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3309, 'zero-biz-bpmn', 'withdrawals_fund_amount_limit_notice', 'en-US', 'The withdrawal amount exceeds the limit and must be less than %s !', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3310, 'zero-biz-bpmn', 'edda_fund_application_detail_query_failed_notice', 'zh-CN', '入金申请记录详情信息查询失败,数据不存在!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3311, 'zero-biz-bpmn', 'edda_fund_application_detail_query_failed_notice', 'zh-HK', '入金申請記錄詳情信息查詢失敗,數據不存在!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3312, 'zero-biz-bpmn', 'edda_fund_application_detail_query_failed_notice', 'en-US', 'Failed to query the details of the deposit application record, the data does not exist!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3313, 'zero-biz-bpmn', 'edda_auth_info_not_exist_notice', 'zh-CN', 'EDDA 授权信息不存在!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3314, 'zero-biz-bpmn', 'edda_auth_info_not_exist_notice', 'zh-HK', 'EDDA 授權信息不存在!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3315, 'zero-biz-bpmn', 'edda_auth_info_not_exist_notice', 'en-US', 'Edda authorization information does not exist!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3316, 'zero-biz-bpmn', 'edda_account_data_exception_notice', 'zh-CN', '账户: %s 数据异常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3317, 'zero-biz-bpmn', 'edda_account_data_exception_notice', 'zh-HK', '賬戶: %s 數據異常!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3318, 'zero-biz-bpmn', 'edda_account_data_exception_notice', 'en-US', 'Account:%s data exception!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3319, 'zero-biz-bpmn', 'edda_account_not_auth_notice', 'zh-CN', '账户: %s 还未通过EDDA授权!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3320, 'zero-biz-bpmn', 'edda_account_not_auth_notice', 'zh-HK', '賬戶: %s 還未通過EDDA授權!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3321, 'zero-biz-bpmn', 'edda_account_not_auth_notice', 'en-US', 'Account:%s has not been authorized by EDDA yet!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3322, 'zero-biz-bpmn', 'edda_receiving_bank_query_failed_notice', 'zh-CN', '根据入金银行和汇款方式、币种查询收款银行信息失败!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3323, 'zero-biz-bpmn', 'edda_receiving_bank_query_failed_notice', 'zh-HK', '根據入金銀行和彙款方式、幣種查詢收款銀行信息失敗!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3324, 'zero-biz-bpmn', 'edda_receiving_bank_query_failed_notice', 'en-US', 'Failed to query the receiving bank information based on the deposit bank, remittance method, and currency!', NULL, NULL, NULL, NULL, 0, 0);


INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3325, 'zero-biz-bpmn', 'edda_receiving_bank_not_config_notice', 'zh-CN', '未配置该入金银行和汇款方式、币种的收款信息!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3326, 'zero-biz-bpmn', 'edda_receiving_bank_not_config_notice', 'zh-HK', '未配置該入金銀行和彙款方式、幣種的收款信息!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3327, 'zero-biz-bpmn', 'edda_receiving_bank_not_config_notice', 'en-US', 'The receiving information for the deposit bank, remittance method, and currency has not been configured!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3328, 'zero-biz-bpmn', 'edda_receiving_bank_list_query_failed_notice', 'zh-CN', '获取收款银行列表失败!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3330, 'zero-biz-bpmn', 'edda_receiving_bank_list_query_failed_notice', 'zh-HK', '獲取收款銀行列表失敗!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3331, 'zero-biz-bpmn', 'edda_receiving_bank_list_query_failed_notice', 'en-US', 'Failed to retrieve the list of receiving banks!', NULL, NULL, NULL, NULL, 0, 0);




INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3332, 'zero-biz-bpmn', 'edda_receiving_bank_list_empty_notice', 'zh-CN', '收款银行列表数据为空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3333, 'zero-biz-bpmn', 'edda_receiving_bank_list_empty_notice', 'zh-HK', '收款銀行列表數據爲空!', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3334, 'zero-biz-bpmn', 'edda_receiving_bank_list_empty_notice', 'en-US', 'The data in the receiving bank list is empty!', NULL, NULL, NULL, NULL, 0, 0);




INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3335, 'zero-biz-bpmn', 'deposit_fund_bank_id_no_not_null_notice', 'zh-CN', '银行开户证件号码不能为空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3336, 'zero-biz-bpmn', 'deposit_fund_bank_id_no_not_null_notice', 'zh-HK', '銀行開戶證件號碼不能為空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3337, 'zero-biz-bpmn', 'deposit_fund_bank_id_no_not_null_notice', 'en-US', 'The bank account opening certificate number cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3338, 'zero-biz-bpmn', 'deposit_fund_bank_id_kind_not_null_notice', 'zh-CN', '银行证件类型不能为空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3339, 'zero-biz-bpmn', 'deposit_fund_bank_id_kind_not_null_notice', 'zh-HK', '銀行證件類型不能為空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3340, 'zero-biz-bpmn', 'deposit_fund_bank_id_kind_not_null_notice', 'en-US', 'Bank ID type cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3341, 'zero-biz-bpmn', 'deposit_fund_deposit_account_type_not_null_notice', 'zh-CN', '账户类型不能为空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3342, 'zero-biz-bpmn', 'deposit_fund_deposit_account_type_not_null_notice', 'zh-HK', '賬戶類型不能為空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3343, 'zero-biz-bpmn', 'deposit_fund_deposit_account_type_not_null_notice', 'en-US', 'Account type cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);



INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3344, 'zero-biz-bpmn', 'deposit_fund_deposit_account_not_null_notice', 'zh-CN', '银行账户账号不能为空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3345, 'zero-biz-bpmn', 'deposit_fund_deposit_account_not_null_notice', 'zh-HK', '銀行賬戶賬號不能為空！', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_i18n_config` (`id`, `sys_model`, `config_key`, `language_type`, `content`, `create_user`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (3346, 'zero-biz-bpmn', 'deposit_fund_deposit_account_not_null_notice', 'en-US', 'Bank account number cannot be empty!', NULL, NULL, NULL, NULL, 0, 0);

