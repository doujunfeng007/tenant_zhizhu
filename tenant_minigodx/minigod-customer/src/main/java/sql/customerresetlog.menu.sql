INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093272878219266', 1123598815738675201, 'resetLog', '客户密码重置日志', 'menu', '/customer/resetLog', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093272878219267', '1778093272878219266', 'resetLog_add', '新增', 'add', '/customer/resetLog/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093272878219268', '1778093272878219266', 'resetLog_edit', '修改', 'edit', '/customer/resetLog/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093272878219269', '1778093272878219266', 'resetLog_delete', '删除', 'delete', '/api/minigod-customer/resetLog/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093272878219270', '1778093272878219266', 'resetLog_view', '查看', 'view', '/customer/resetLog/view', 'file-text', 4, 2, 2, 1, NULL, 0);
