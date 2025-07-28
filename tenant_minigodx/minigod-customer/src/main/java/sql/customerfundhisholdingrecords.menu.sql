INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093277479370754', 1123598815738675201, 'fundHisHoldingRecords', '客户基金历史持仓表', 'menu', '/customer/fundHisHoldingRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093277479370755', '1778093277479370754', 'fundHisHoldingRecords_add', '新增', 'add', '/customer/fundHisHoldingRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093277483565058', '1778093277479370754', 'fundHisHoldingRecords_edit', '修改', 'edit', '/customer/fundHisHoldingRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093277483565059', '1778093277479370754', 'fundHisHoldingRecords_delete', '删除', 'delete', '/api/minigod-customer/fundHisHoldingRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093277483565060', '1778093277479370754', 'fundHisHoldingRecords_view', '查看', 'view', '/customer/fundHisHoldingRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
