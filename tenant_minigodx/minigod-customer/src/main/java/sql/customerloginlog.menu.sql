INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093270076424194', 1123598815738675201, 'loginLog', '登陆日志表', 'menu', '/customer/loginLog', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093270076424195', '1778093270076424194', 'loginLog_add', '新增', 'add', '/customer/loginLog/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093270076424196', '1778093270076424194', 'loginLog_edit', '修改', 'edit', '/customer/loginLog/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093270076424197', '1778093270076424194', 'loginLog_delete', '删除', 'delete', '/api/minigod-customer/loginLog/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093270076424198', '1778093270076424194', 'loginLog_view', '查看', 'view', '/customer/loginLog/view', 'file-text', 4, 2, 2, 1, NULL, 0);
