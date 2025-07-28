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

 Date: 21/08/2024 15:05:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acct_risk_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `acct_risk_evaluation`;
CREATE TABLE `acct_risk_evaluation`  (
  `id` bigint NOT NULL COMMENT '主键',
  `cust_id` bigint NOT NULL COMMENT '用户号',
  `subjective_score` int NULL DEFAULT 0 COMMENT '主观题总分',
  `risk_score` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '风险评分',
  `objective_score` int NULL DEFAULT 0 COMMENT '客观题总分',
  `option_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '选项内容',
  `risk_type` int NULL DEFAULT NULL COMMENT '风险评级',
  `expiry_date` date NULL DEFAULT NULL COMMENT '失效日期',
  `is_push` int NULL DEFAULT NULL COMMENT '是否发送过期通知',
  `retest_sts` int NULL DEFAULT 0 COMMENT '0.未重测,1.已重测',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
  `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '风险评测记录表' ROW_FORMAT = DYNAMIC;


SET FOREIGN_KEY_CHECKS = 1;
