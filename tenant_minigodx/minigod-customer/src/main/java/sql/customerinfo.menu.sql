INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093268985905154', 1123598815738675201, 'customerInfo', '客户信息表', 'menu', '/customer/customerInfo', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093268985905155', '1778093268985905154', 'customerInfo_add', '新增', 'add', '/customer/customerInfo/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093268985905156', '1778093268985905154', 'customerInfo_edit', '修改', 'edit', '/customer/customerInfo/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093268985905157', '1778093268985905154', 'customerInfo_delete', '删除', 'delete', '/api/minigod-customer/customerInfo/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093268985905158', '1778093268985905154', 'customerInfo_view', '查看', 'view', '/customer/customerInfo/view', 'file-text', 4, 2, 2, 1, NULL, 0);
