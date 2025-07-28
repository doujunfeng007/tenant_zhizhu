INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093230964539394', 1123598815738675201, 'customercardInfo', '客户银行卡记录', 'menu', '/customer/customercardInfo', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093230964539395', '1778093230964539394', 'customercardInfo_add', '新增', 'add', '/customer/customercardInfo/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093230964539396', '1778093230964539394', 'customercardInfo_edit', '修改', 'edit', '/customer/customercardInfo/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093230964539397', '1778093230964539394', 'customercardInfo_delete', '删除', 'delete', '/api/minigod-customer/customercardInfo/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093230964539398', '1778093230964539394', 'customercardInfo_view', '查看', 'view', '/customer/customercardInfo/view', 'file-text', 4, 2, 2, 1, NULL, 0);
