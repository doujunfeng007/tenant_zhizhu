/*
 Navicat Premium Data Transfer

 Source Server         : SIT-ENV-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : 203.86.123.168:43436
 Source Schema         : zero_bpmn

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 14/09/2024 09:12:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for client_bank_card_image
-- ----------------------------
DROP TABLE IF EXISTS `client_bank_card_image`;
CREATE TABLE `client_bank_card_image`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                           `application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '预约流水号',
                                           `image_type` int NOT NULL DEFAULT 0 COMMENT '凭证类型[0-银行卡凭证 1-银行凭证]',
                                           `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件名',
                                           `file_storage_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '存储文件名',
                                           `ext_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件拓展名',
                                           `storage_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '指定存储点路径',
                                           `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                           `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                           `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
                                           `create_time` datetime NOT NULL COMMENT '创建时间',
                                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                           `remark` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
                                           `tenant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '租户ID',
                                           `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
                                           `status` int NULL DEFAULT NULL COMMENT '状态',
                                           PRIMARY KEY (`id`) USING BTREE,
                                           INDEX `idx_application_id`(`application_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 263 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '银行卡凭证表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
