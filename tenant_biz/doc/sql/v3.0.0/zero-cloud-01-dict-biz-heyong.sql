SELECT *FROM zero_dict_biz WHERE `code`='id_kind' and tenant_id='000000' order by sort ASC;

-- 插入完成后，需要查询确认一下parent_id是不是跟1~6的是一样的。
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `dict_key_hk`, `dict_value_hk`, `dict_key_en`, `dict_value_en`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1863851232322777089, '000000', 2551527472653258753, 'id_kind', '7', '香港非永居身份证', '7', '香港非永居身份證', '7', 'Hong Kong Non Permanent Identity Card', 7, NULL, 0, 0);
