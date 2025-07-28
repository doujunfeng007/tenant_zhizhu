/*
 Navicat Premium Data Transfer

 Source Server         : 开户-sit
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 203.86.123.168:43436
 Source Schema         : zero_bpmn

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 13/08/2024 13:33:40
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer_account_open_image_modify
-- ----------------------------
DROP TABLE IF EXISTS `customer_account_open_image_modify`;
CREATE TABLE `customer_account_open_image_modify`
(
    `id`                  bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `apply_id`            bigint(0) NOT NULL COMMENT '修改申请记录ID',
    `application_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT '预约流水号',
    `file_name`           varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件名',
    `file_storage_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '存储文件名',
    `ext_name`            varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT '文件拓展名',
    `image_location`      int(0) NOT NULL DEFAULT 0 COMMENT '图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标 9=活体]',
    `image_location_type` int(0) NOT NULL DEFAULT 0 COMMENT '图片位置类型[0=未知 1=身份证正面照 2=身份证反面照片 3=左手竖起1个指头 4=右手竖起1个指头 5=左手竖起2个指头 6=右手竖起2个指头 7=左手竖起3个指头 8=右手竖起3个指头 9=正面照 10=电子签名照]',
    `storage_path`        varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '指定存储点路径',
    `create_user`         bigint(0) NULL DEFAULT NULL COMMENT '创建人',
    `update_user`         bigint(0) NULL DEFAULT NULL COMMENT '更新人',
    `create_time`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`              varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
    `tenant_id`           varchar(30) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT '租户 ID',
    `create_dept`         bigint(0) NULL DEFAULT NULL COMMENT '创建科室',
    `status`              int(0) NULL DEFAULT NULL COMMENT '状态',
    `is_deleted`          int(0) NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                 `idx_application_id`(`application_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1821784708265746434 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户开户申请图片信息表' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
