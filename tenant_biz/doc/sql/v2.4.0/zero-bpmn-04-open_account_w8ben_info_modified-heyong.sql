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

 Date: 01/08/2024 16:22:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for open_account_w8ben_info_modify
-- ----------------------------
DROP TABLE IF EXISTS `open_account_w8ben_info_modify`;
CREATE TABLE `open_account_w8ben_info_modify`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `apply_id` bigint NOT NULL COMMENT '修改申请记录ID',
  `application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '预约流水号',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户ID',
  `w8_permanent_residence_address_country` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久居住地址国家',
  `w8_permanent_residence_province_city_county` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久居住地省市区',
  `w8_permanent_residence_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久居住地址',
  `w8_mailing_address_country` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮寄地址国家',
  `w8_mailing_address_province_city_county` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '省市区',
  `w8_mailing_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮寄详细地址',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1116123586716001110 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'w8信息表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
