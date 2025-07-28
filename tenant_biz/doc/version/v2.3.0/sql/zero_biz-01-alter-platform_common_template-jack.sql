
#添加开户通知模板code
INSERT INTO zero_biz.platform_common_template (id, bus_id, temp_code, ali_temp_code_hans, ali_temp_code_hant, ali_temp_code_en, ali_temp_param, title, title_hant, title_en, content, content_hant, content_en, apply_explain, msg_type, url, create_user, create_dept, create_time, update_user, update_time, status, is_deleted) VALUES (1816030891116123394, null, 13005, null, null, null, null, '开户成功', '開戶成功', 'Account opening success', '恭喜您！您的交易账户已成功开户。现在，您可以开始在我们的平台上进行各种精彩的交易活动啦！您可以自由探索市场，把握投资机遇。我们将始终为您提供优质的服务和支持，祝您交易顺利、收获满满！', null, null, '', 0, null, 1798979554172383233, null, '2024-07-24 16:41:19', 1798979554172383233, '2024-07-31 17:32:38', 1, 0);

#修改模板内容
#密码重置
UPDATE zero_biz.platform_common_template t SET t.content = '尊敬的客户，您已成功重置您的登录密码。' WHERE t.temp_code = 3062;
UPDATE zero_biz.platform_common_template t SET t.content = '尊敬的客户，您已成功重置交易密码。' WHERE t.temp_code = 3045;
