INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093233745362945', 1123598815738675201, 'financingAccount', '客户理财账户表', 'menu', '/customer/financingAccount', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093233745362946', '1778093233745362945', 'financingAccount_add', '新增', 'add', '/customer/financingAccount/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093233745362947', '1778093233745362945', 'financingAccount_edit', '修改', 'edit', '/customer/financingAccount/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093233745362948', '1778093233745362945', 'financingAccount_delete', '删除', 'delete', '/api/minigod-customer/financingAccount/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093233745362949', '1778093233745362945', 'financingAccount_view', '查看', 'view', '/customer/financingAccount/view', 'file-text', 4, 2, 2, 1, NULL, 0);
