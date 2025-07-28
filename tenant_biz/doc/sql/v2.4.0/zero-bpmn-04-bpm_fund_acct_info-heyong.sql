/*
 Navicat Premium Data Transfer

 Source Server         : SIT-ENV-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : 203.86.123.168:43436
 Source Schema         : zero_cust

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 21/08/2024 15:08:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bpm_fund_acct_info
-- ----------------------------
DROP TABLE IF EXISTS `bpm_fund_acct_info`;
CREATE TABLE `bpm_fund_acct_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trade_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易账户',
  `fund_account_main` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金主账户',
  `fund_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金子账户',
  `fund_account_type` tinyint(1) NULL DEFAULT NULL COMMENT '基金账户类型：0-个人户 1-机构户',
  `fund_oper_type` tinyint(1) NULL DEFAULT NULL COMMENT '基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回',
  `account_status` tinyint(1) NULL DEFAULT 0 COMMENT '基金账户状态：0-正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
  `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_fund_account`(`fund_account` ASC) USING BTREE,
  UNIQUE INDEX `idx_trade_account`(`trade_account` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1826125753038008323 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '基金账户信息表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
