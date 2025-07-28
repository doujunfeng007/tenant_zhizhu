INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093235435667457', 1123598815738675201, 'fundAssetRecords', '客户资产流水汇总表', 'menu', '/customer/fundAssetRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093235435667458', '1778093235435667457', 'fundAssetRecords_add', '新增', 'add', '/customer/fundAssetRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093235435667459', '1778093235435667457', 'fundAssetRecords_edit', '修改', 'edit', '/customer/fundAssetRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093235435667460', '1778093235435667457', 'fundAssetRecords_delete', '删除', 'delete', '/api/minigod-customer/fundAssetRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093235435667461', '1778093235435667457', 'fundAssetRecords_view', '查看', 'view', '/customer/fundAssetRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
