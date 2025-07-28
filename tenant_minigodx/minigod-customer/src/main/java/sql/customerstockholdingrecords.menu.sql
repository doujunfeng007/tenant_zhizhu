INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093278674747394', 1123598815738675201, 'stockHoldingRecords', '客户股票持仓表', 'menu', '/customer/stockHoldingRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093278674747395', '1778093278674747394', 'stockHoldingRecords_add', '新增', 'add', '/customer/stockHoldingRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093278674747396', '1778093278674747394', 'stockHoldingRecords_edit', '修改', 'edit', '/customer/stockHoldingRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093278674747397', '1778093278674747394', 'stockHoldingRecords_delete', '删除', 'delete', '/api/minigod-customer/stockHoldingRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093278674747398', '1778093278674747394', 'stockHoldingRecords_view', '查看', 'view', '/customer/stockHoldingRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
