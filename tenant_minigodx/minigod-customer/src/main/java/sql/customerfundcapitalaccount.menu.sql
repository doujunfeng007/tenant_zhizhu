INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093275826814977', 1123598815738675201, 'fundCapitalAccount', '基金账户信息表', 'menu', '/customer/fundCapitalAccount', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093275826814978', '1778093275826814977', 'fundCapitalAccount_add', '新增', 'add', '/customer/fundCapitalAccount/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093275826814979', '1778093275826814977', 'fundCapitalAccount_edit', '修改', 'edit', '/customer/fundCapitalAccount/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093275826814980', '1778093275826814977', 'fundCapitalAccount_delete', '删除', 'delete', '/api/minigod-customer/fundCapitalAccount/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `minigod_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1778093275826814981', '1778093275826814977', 'fundCapitalAccount_view', '查看', 'view', '/customer/fundCapitalAccount/view', 'file-text', 4, 2, 2, 1, NULL, 0);
