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

 Date: 21/08/2024 15:08:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acct_risk_question_option
-- ----------------------------
DROP TABLE IF EXISTS `acct_risk_question_option`;
CREATE TABLE `acct_risk_question_option`  (
  `id` bigint NOT NULL COMMENT '主键',
  `question_id` bigint NOT NULL COMMENT '问题ID',
  `sort` int NULL DEFAULT NULL COMMENT '问题顺序',
  `option_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项内容',
  `lang` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多语言标识',
  `option_score` int NULL DEFAULT NULL COMMENT '选项分值',
  `option_id` int NULL DEFAULT NULL COMMENT '选项ID',
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
  UNIQUE INDEX `idx_lang_option_question`(`lang` ASC, `option_id` ASC, `question_id` ASC) USING BTREE,
  INDEX `idx_question_id`(`question_id` ASC) USING BTREE,
  INDEX `idx_lang`(`lang` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '基金风险评测-题目选项表' ROW_FORMAT = DYNAMIC;


SET FOREIGN_KEY_CHECKS = 1;
