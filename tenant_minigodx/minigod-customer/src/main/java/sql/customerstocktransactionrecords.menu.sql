INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093309456744450', 1123598815738675201, 'stockTransactionRecords', '客户交易流水汇总表', 'menu', '/customer/stockTransactionRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093309456744451', '1778093309456744450', 'stockTransactionRecords_add', '新增', 'add', '/customer/stockTransactionRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093309456744452', '1778093309456744450', 'stockTransactionRecords_edit', '修改', 'edit', '/customer/stockTransactionRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093309456744453', '1778093309456744450', 'stockTransactionRecords_delete', '删除', 'delete', '/api/minigod-customer/stockTransactionRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093309456744454', '1778093309456744450', 'stockTransactionRecords_view', '查看', 'view', '/customer/stockTransactionRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
