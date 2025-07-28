INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093271443767297', 1123598815738675201, 'realnameVerify', '客户实名认证表', 'menu', '/customer/realnameVerify', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093271443767298', '1778093271443767297', 'realnameVerify_add', '新增', 'add', '/customer/realnameVerify/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093271443767299', '1778093271443767297', 'realnameVerify_edit', '修改', 'edit', '/customer/realnameVerify/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093271443767300', '1778093271443767297', 'realnameVerify_delete', '删除', 'delete', '/api/minigod-customer/realnameVerify/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093271443767301', '1778093271443767297', 'realnameVerify_view', '查看', 'view', '/customer/realnameVerify/view', 'file-text', 4, 2, 2, 1, NULL, 0);
