/*
 Navicat Premium Data Transfer

 Source Server         : 生产环境数据库
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40-241)
 Source Host           : 10.10.1.20:13049
 Source Schema         : zero_bpmn

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40-241)
 File Encoding         : 65001

 Date: 28/11/2024 14:29:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer_invest_knowledge_questionnaire
-- ----------------------------
DROP TABLE IF EXISTS `customer_invest_knowledge_questionnaire`;
CREATE TABLE `customer_invest_knowledge_questionnaire`  (
                                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                                            `application_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约流水号',
                                                            `cust_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
                                                            `option_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '问卷选项内容',
                                                            `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                                            `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
                                                            `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                            `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
                                                            `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除',
                                                            `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
                                                            `status` int NULL DEFAULT NULL COMMENT '状态',
                                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1859949202122465282 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户开户投资知识问卷表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
