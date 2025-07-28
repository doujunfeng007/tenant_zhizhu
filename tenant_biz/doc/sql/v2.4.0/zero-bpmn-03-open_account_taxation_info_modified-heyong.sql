/*
 Navicat Premium Data Transfer

 Source Server         : PI-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : 203.86.123.168:43436
 Source Schema         : zero_bpmn

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 01/08/2024 16:56:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for open_account_taxation_info_modify
-- ----------------------------
DROP TABLE IF EXISTS `open_account_taxation_info_modify`;
CREATE TABLE `open_account_taxation_info_modify`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `apply_id` bigint NOT NULL COMMENT '修改申请记录ID',
  `application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '预约流水号',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户ID',
  `tax_country` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '司法管辖区',
  `tax_number` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '税务编号',
  `has_tax_number` int NULL DEFAULT NULL COMMENT '是否有税务编号[0=否 1=是]',
  `reason_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '理由类型',
  `reason_desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '理由描述',
  `additional_disclosures` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '额外披露',
   -- 系统字段 --
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
  `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '租户 ID',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_application_id`(`application_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1216710545533222000 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '税务信息表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
