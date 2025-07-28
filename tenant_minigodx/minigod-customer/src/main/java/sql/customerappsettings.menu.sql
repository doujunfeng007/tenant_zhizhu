INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093310694064129', 1123598815738675201, 'appSettings', '客户app设置信息表', 'menu', '/customer/appSettings', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093310694064130', '1778093310694064129', 'appSettings_add', '新增', 'add', '/customer/appSettings/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093310694064131', '1778093310694064129', 'appSettings_edit', '修改', 'edit', '/customer/appSettings/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093310694064132', '1778093310694064129', 'appSettings_delete', '删除', 'delete', '/api/minigod-customer/appSettings/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093310694064133', '1778093310694064129', 'appSettings_view', '查看', 'view', '/customer/appSettings/view', 'file-text', 4, 2, 2, 1, NULL, 0);
