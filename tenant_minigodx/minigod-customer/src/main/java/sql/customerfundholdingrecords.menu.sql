INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093240103927810', 1123598815738675201, 'fundHoldingRecords', '客户基金持仓表', 'menu', '/customer/fundHoldingRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093240103927811', '1778093240103927810', 'fundHoldingRecords_add', '新增', 'add', '/customer/fundHoldingRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093240103927812', '1778093240103927810', 'fundHoldingRecords_edit', '修改', 'edit', '/customer/fundHoldingRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093240103927813', '1778093240103927810', 'fundHoldingRecords_delete', '删除', 'delete', '/api/minigod-customer/fundHoldingRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093240103927814', '1778093240103927810', 'fundHoldingRecords_view', '查看', 'view', '/customer/fundHoldingRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
