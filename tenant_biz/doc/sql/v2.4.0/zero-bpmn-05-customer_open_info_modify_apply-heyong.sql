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
-- Table structure for customer_open_info_modify_apply
-- ----------------------------
DROP TABLE IF EXISTS `customer_open_info_modify_apply`;
CREATE TABLE `customer_open_info_modify_apply`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '预约流水号',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名称',
  `apply_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '申请记录标题',
  `data_section` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改数据标识',
  `approve_status` int NULL DEFAULT NULL COMMENT '审核状态[0-待审核 1-审核通过 2-审核不通过]',
  `approve_user_id` bigint NULL DEFAULT NULL COMMENT '审核人ID',
  `approve_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人名称',
  `approve_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核意见',
  `update_status` int NULL DEFAULT NULL COMMENT '更新状态[0-待更新 1-更新成功 2-更新失败]',
  `update_result` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新操作结果',
  `sync_status` int NULL DEFAULT NULL COMMENT '同步状态[0-待同步 1-同步成功 2-同步失败]',
  `sync_result` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '同步操作结果',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1516123586716220000 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户资料修改申请表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
