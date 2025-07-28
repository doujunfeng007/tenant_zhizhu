INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093267576619009', 1123598815738675201, 'goldDepositRecords', '客户入金申请信息表', 'menu', '/customer/goldDepositRecords', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093267576619010', '1778093267576619009', 'goldDepositRecords_add', '新增', 'add', '/customer/goldDepositRecords/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093267576619011', '1778093267576619009', 'goldDepositRecords_edit', '修改', 'edit', '/customer/goldDepositRecords/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093267576619012', '1778093267576619009', 'goldDepositRecords_delete', '删除', 'delete', '/api/minigod-customer/goldDepositRecords/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093267576619013', '1778093267576619009', 'goldDepositRecords_view', '查看', 'view', '/customer/goldDepositRecords/view', 'file-text', 4, 2, 2, 1, NULL, 0);
