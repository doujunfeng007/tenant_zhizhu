INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093266238636033', 1123598815738675201, 'fundWithdrawRecords', '客户出金申请信息表', 'menu', '/customer/fundWithdrawRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093266238636034', '1778093266238636033', 'fundWithdrawRecords_add', '新增', 'add', '/customer/fundWithdrawRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093266238636035', '1778093266238636033', 'fundWithdrawRecords_edit', '修改', 'edit', '/customer/fundWithdrawRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093266238636036', '1778093266238636033', 'fundWithdrawRecords_delete', '删除', 'delete', '/api/minigod-customer/fundWithdrawRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093266238636037', '1778093266238636033', 'fundWithdrawRecords_view', '查看', 'view', '/customer/fundWithdrawRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
