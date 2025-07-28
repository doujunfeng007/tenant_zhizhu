INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093306625589250', 1123598815738675201, 'stockOpenAcocuntInfo', '客户股票开户资料', 'menu', '/customer/stockOpenAcocuntInfo', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093306625589251', '1778093306625589250', 'stockOpenAcocuntInfo_add', '新增', 'add', '/customer/stockOpenAcocuntInfo/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093306625589252', '1778093306625589250', 'stockOpenAcocuntInfo_edit', '修改', 'edit', '/customer/stockOpenAcocuntInfo/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093306625589253', '1778093306625589250', 'stockOpenAcocuntInfo_delete', '删除', 'delete', '/api/minigod-customer/stockOpenAcocuntInfo/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093306625589254', '1778093306625589250', 'stockOpenAcocuntInfo_view', '查看', 'view', '/customer/stockOpenAcocuntInfo/view', 'file-text', 4, 2, 2, 1, NULL, 0);
