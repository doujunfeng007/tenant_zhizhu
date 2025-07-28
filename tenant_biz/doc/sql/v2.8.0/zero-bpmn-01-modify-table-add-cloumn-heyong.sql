
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `id` bigint NOT NULL PRIMARY KEY COMMENT '主键ID';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `bank_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款银行代码';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款银行名称';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `bank_ename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款银行英文名称';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `bank_acct_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款银行账户名称';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `bank_acct` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款银行账户';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `ccy` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '币种代码[CNY-人民币 USD-美元 HKD-港币]';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `bank_acct_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账户类型';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `service_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务类别[SEC-证券 FET-期货]';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `gl_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `gt_in_bank_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜台退款公司银行代码';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `gt_in_bank_acct` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜台退款付款银行账户';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `gt_fee_in_bank_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜台退款手续费公司银行代码';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `gt_fee_in_bank_acct` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜台退款手续费付款银行账户';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `gt_out_bank_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜台取款公司银行代码';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `gt_out_bank_acct` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜台取款付款银行账户';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `active` smallint NULL DEFAULT NULL COMMENT '是否可用[0-否 1-是]';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '更新人';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建部门';
ALTER TABLE `zero_bpmn`.`bank_paying` MODIFY COLUMN `status` int NOT NULL DEFAULT 0  COMMENT '状态';


ALTER TABLE `zero_biz`.`cash_deposit_bank` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人' AFTER `create_time`;
ALTER TABLE `zero_biz`.`cash_deposit_bank` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人' AFTER `update_time`;
ALTER TABLE `zero_biz`.`cash_deposit_bank` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_biz`.`cash_deposit_bank` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_biz`.`cash_deposit_bank` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_biz`.`cash_deposit_bank` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';


UPDATE `zero_biz`.`cash_deposit_bank` SET `tenant_id` = '000000', `status` = 1;


ALTER TABLE `zero_biz`.`cash_withdrawals_bank` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人' AFTER `create_time`;
ALTER TABLE `zero_biz`.`cash_withdrawals_bank` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人' AFTER `update_time`;
ALTER TABLE `zero_biz`.`cash_withdrawals_bank` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_biz`.`cash_withdrawals_bank` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_biz`.`cash_withdrawals_bank` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_biz`.`cash_withdrawals_bank` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

UPDATE `zero_biz`.`cash_withdrawals_bank` SET `tenant_id` = '000000', `status` = 1;


ALTER TABLE `zero_biz`.`cash_payee_bank` ADD COLUMN `create_user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人名称' AFTER `create_user`;
ALTER TABLE `zero_biz`.`cash_payee_bank` ADD COLUMN `update_user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改人名称' AFTER `update_user`;
ALTER TABLE `zero_biz`.`cash_payee_bank` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_biz`.`cash_payee_bank` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_biz`.`cash_payee_bank` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_biz`.`cash_payee_bank` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

UPDATE `zero_biz`.`cash_payee_bank` SET `tenant_id` = '000000', `status` = 1;

ALTER TABLE `zero_biz`.`cash_payee_bank_detail` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_biz`.`cash_payee_bank_detail` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_biz`.`cash_payee_bank_detail` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_biz`.`cash_payee_bank_detail` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

UPDATE `zero_biz`.`cash_payee_bank_detail` SET `tenant_id` = '000000', `status` = 1;


ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人';
ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间';
ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人';
ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_biz`.`cash_support_currency_bank` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

UPDATE `zero_biz`.`cash_support_currency_bank` SET `tenant_id` = '000000', `status` = 1;



ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人';
ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间';
ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人';
ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_biz`.`cash_withdrawals_support_currency_bank` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

UPDATE `zero_biz`.`cash_withdrawals_support_currency_bank` SET `tenant_id` = '000000', `status` = 1;


/*
 Navicat Premium Data Transfer

 Source Server         : SIT-ENV-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : 203.86.123.168:43436
 Source Schema         : zero_biz

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 18/10/2024 14:19:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cash_withdrawals_support_currency_bank
-- ----------------------------
DROP TABLE IF EXISTS `cash_withdrawals_support_currency_bank`;
CREATE TABLE `cash_withdrawals_support_currency_bank`  (
                                                           `id` bigint NOT NULL COMMENT '主键',
                                                           `withdrawals_id` bigint NULL DEFAULT NULL COMMENT '出金银行ID【cash_withdrawals_bank】表主键ID',
                                                           `account_type` tinyint(1) NULL DEFAULT 1 COMMENT '账户类型 1-大账户 2-子账户',
                                                           `currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '币种 HKD USD CNY',
                                                           `payment_bank_id` bigint NOT NULL COMMENT '付款银行ID 【cash_payee_bank】表主键ID',
                                                           `payment_bank_detail_id` bigint NULL DEFAULT NULL COMMENT '账户详情ID【cash_payee_bank_detail】表主键ID',
                                                           `support_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '付款方式 1-香港银行普通转账 2-香港银行本地转账 3-电汇 4-FPS ID',
                                                           `sort_order` int NOT NULL DEFAULT 0 COMMENT '降序排序',
                                                           `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认',
                                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '出金银行 付款方式 币种 付款银行关联表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;





-- 出金银行列表增加转账方式字段
ALTER TABLE cash_withdrawals_bank ADD COLUMN `support_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接受转账方式1-香港银行普通转账 2-香港银行本地转账 3-电汇 4-FPS ID' AFTER swift_code;

-- 银行配置列表增加银行类型字段：区分收款银行和付款银行
ALTER TABLE cash_payee_bank ADD COLUMN `bank_type` INT(1) NOT NULL DEFAULT 1 COMMENT '银行卡类型(1.收款银行 2.付款银行)' AFTER bank_id;
