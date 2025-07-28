INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093243161575425', 1123598815738675201, 'fundTransactionRecords', '客户交易流水汇总表', 'menu', '/customer/fundTransactionRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093243161575426', '1778093243161575425', 'fundTransactionRecords_add', '新增', 'add', '/customer/fundTransactionRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093243161575427', '1778093243161575425', 'fundTransactionRecords_edit', '修改', 'edit', '/customer/fundTransactionRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093243161575428', '1778093243161575425', 'fundTransactionRecords_delete', '删除', 'delete', '/api/minigod-customer/fundTransactionRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093243161575429', '1778093243161575425', 'fundTransactionRecords_view', '查看', 'view', '/customer/fundTransactionRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
