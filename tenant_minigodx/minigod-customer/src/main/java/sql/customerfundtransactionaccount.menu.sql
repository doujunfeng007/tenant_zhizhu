INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093241731317762', 1123598815738675201, 'fundTransactionAccount', '基金交易账户信息表', 'menu', '/customer/fundTransactionAccount', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093241731317763', '1778093241731317762', 'fundTransactionAccount_add', '新增', 'add', '/customer/fundTransactionAccount/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093241731317764', '1778093241731317762', 'fundTransactionAccount_edit', '修改', 'edit', '/customer/fundTransactionAccount/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093241731317765', '1778093241731317762', 'fundTransactionAccount_delete', '删除', 'delete', '/api/minigod-customer/fundTransactionAccount/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093241731317766', '1778093241731317762', 'fundTransactionAccount_view', '查看', 'view', '/customer/fundTransactionAccount/view', 'file-text', 4, 2, 2, 1, NULL, 0);
