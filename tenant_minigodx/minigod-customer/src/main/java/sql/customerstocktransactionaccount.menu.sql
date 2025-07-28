INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093307783217154', 1123598815738675201, 'stockTransactionAccount', '交易账户信息表', 'menu', '/customer/stockTransactionAccount', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093307783217155', '1778093307783217154', 'stockTransactionAccount_add', '新增', 'add', '/customer/stockTransactionAccount/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093307783217156', '1778093307783217154', 'stockTransactionAccount_edit', '修改', 'edit', '/customer/stockTransactionAccount/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093307783217157', '1778093307783217154', 'stockTransactionAccount_delete', '删除', 'delete', '/api/minigod-customer/stockTransactionAccount/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093307783217158', '1778093307783217154', 'stockTransactionAccount_view', '查看', 'view', '/customer/stockTransactionAccount/view', 'file-text', 4, 2, 2, 1, NULL, 0);
