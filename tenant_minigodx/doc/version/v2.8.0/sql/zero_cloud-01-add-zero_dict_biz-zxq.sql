-- 新增活利得利率变更提醒时间间隔字典
INSERT INTO `zero_cloud`.`zero_dict_biz`(`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1854091241310306306, '000000', 0, 'hld_rate_change_warn', '-1', '活利得利率变更提醒', NULL, NULL, NULL, NULL, 1, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz`(`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1854092032624476162, '000000', 1854091241310306306, 'hld_rate_change_warn', '活利得利率变更提醒日期间隔（月）', '2', '', '', '', '', 1, '', 0, 0);

-- 新增产品到期提醒时间间隔字典
INSERT INTO `zero_cloud`.`zero_dict_biz`(`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1854092468454604802, '000000', 0, 'product_expired_warn', '-1', '产品到期提醒', NULL, NULL, NULL, NULL, 1, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz`(`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1854092600029921281, '000000', 1854092468454604802, 'product_expired_warn', '产品到期提醒时间间隔（月）', '1', '', '', '', '', 1, '', 0, 0);

-- 新增债券易派息提醒时间间隔字典
INSERT INTO `zero_cloud`.`zero_dict_biz`(`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1854092909028491265, '000000', 0, 'bond_dividend_warn', '-1', '债券易派息提醒', NULL, NULL, NULL, NULL, 1, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz`(`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1854093038275969026, '000000', 1854092909028491265, 'bond_dividend_warn', '债券易派息提醒时间间隔', '1', '', '', '', '', 1, '', 0, 0);


