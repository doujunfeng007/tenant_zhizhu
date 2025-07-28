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

 Date: 21/08/2024 15:06:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acct_risk_question
-- ----------------------------
DROP TABLE IF EXISTS `acct_risk_question`;
CREATE TABLE `acct_risk_question`  (
  `id` bigint NOT NULL COMMENT '主键',
  `question_id` bigint NULL DEFAULT NULL COMMENT '问题ID',
  `question` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评测问题',
  `sort` int NULL DEFAULT NULL COMMENT '问题顺序',
  `lang` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '多语言标识',
  `question_type` int NULL DEFAULT NULL COMMENT '1.个人户题库，2.公司户题库，3.PI个人认证户题库, 4.风险等级评估题库',
  `opt_type` int NULL DEFAULT NULL COMMENT '1.主观题,2.客观题',
  `check_type` int NULL DEFAULT NULL COMMENT '1.单选题,2.复选题',
  `flag` int NULL DEFAULT 0 COMMENT '0.有效，1.失效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
  `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_question_lang`(`question_id` ASC, `lang` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '基金风险评测题库表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
