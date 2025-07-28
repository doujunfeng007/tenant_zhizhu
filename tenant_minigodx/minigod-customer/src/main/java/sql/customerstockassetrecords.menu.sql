INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093274333642753', 1123598815738675201, 'stockAssetRecords', '客户资产流水汇总表', 'menu', '/customer/stockAssetRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093274333642754', '1778093274333642753', 'stockAssetRecords_add', '新增', 'add', '/customer/stockAssetRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093274333642755', '1778093274333642753', 'stockAssetRecords_edit', '修改', 'edit', '/customer/stockAssetRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093274333642756', '1778093274333642753', 'stockAssetRecords_delete', '删除', 'delete', '/api/minigod-customer/stockAssetRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093274333642757', '1778093274333642753', 'stockAssetRecords_view', '查看', 'view', '/customer/stockAssetRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
