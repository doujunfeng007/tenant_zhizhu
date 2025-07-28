INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093232457711617', 1123598815738675201, 'customerdeviceInfo', '客户设备信息', 'menu', '/customer/customerdeviceInfo', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093232457711618', '1778093232457711617', 'customerdeviceInfo_add', '新增', 'add', '/customer/customerdeviceInfo/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093232457711619', '1778093232457711617', 'customerdeviceInfo_edit', '修改', 'edit', '/customer/customerdeviceInfo/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093232457711620', '1778093232457711617', 'customerdeviceInfo_delete', '删除', 'delete', '/api/minigod-customer/customerdeviceInfo/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093232457711621', '1778093232457711617', 'customerdeviceInfo_view', '查看', 'view', '/customer/customerdeviceInfo/view', 'file-text', 4, 2, 2, 1, NULL, 0);
