INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093229379092481', 1123598815738675201, 'customerbasicInfo', '客户基础资料信息表', 'menu', '/customer/customerbasicInfo', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093229379092482', '1778093229379092481', 'customerbasicInfo_add', '新增', 'add', '/customer/customerbasicInfo/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093229379092483', '1778093229379092481', 'customerbasicInfo_edit', '修改', 'edit', '/customer/customerbasicInfo/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093229379092484', '1778093229379092481', 'customerbasicInfo_delete', '删除', 'delete', '/api/minigod-customer/customerbasicInfo/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093229379092485', '1778093229379092481', 'customerbasicInfo_view', '查看', 'view', '/customer/customerbasicInfo/view', 'file-text', 4, 2, 2, 1, NULL, 0);
