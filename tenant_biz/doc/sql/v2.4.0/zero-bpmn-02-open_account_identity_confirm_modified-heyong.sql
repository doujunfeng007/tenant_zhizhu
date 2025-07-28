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

 Date: 01/08/2024 17:06:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for open_account_identity_confirm_modify
-- ----------------------------
DROP TABLE IF EXISTS `open_account_identity_confirm_modify`;
CREATE TABLE `open_account_identity_confirm_modify`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apply_id` bigint NOT NULL COMMENT '修改申请记录ID',
  `application_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  
  `is_register_employee` tinyint(1) NULL DEFAULT NULL COMMENT '是否证监会持牌或注册人士的雇员',
  `center_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '中央编号',
  `organization_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '注册机构',
  
  `is_employed` tinyint(1) NULL DEFAULT NULL COMMENT '您是否是iFund公司雇员',
  `employed_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '亲属姓名',
  `employed_relation` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '亲属关系',
  
  
  `is_executive` tinyint(1) NULL DEFAULT NULL COMMENT '是否公司的高级管理人员或董事',
  `executive_company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司名称',
  `executive_company_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '股票代码',
  
  `is_illegal` tinyint(1) NULL DEFAULT NULL COMMENT '是否违法或违反监管',
  `illegal_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '违法或违反监管具体情况',
  
  `is_have_lineal_relatives` tinyint(1) NULL DEFAULT NULL COMMENT '是否政治人物或高级政府官员是直系亲属',
  `lineal_relatives_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '政治人物名称',
  `lineal_relatives_job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '政治人物所担任的公职',
  `lineal_relatives_job_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '政治人物所担任公职期限',
  `customer_relations` int NULL DEFAULT NULL COMMENT '与客户之关系',
  
  `is_mate_have_account` tinyint(1) NULL DEFAULT NULL COMMENT '配偶是否在公司开有账户',
  `mate_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配偶账户',
 
  `is_stock_right_more35` tinyint(1) NULL DEFAULT NULL COMMENT '是否个人或配偶共同控制iFund公司客户35%以上的股权',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '35%以上账户',
  
  -- 系统字段 --
  `create_user` bigint NULL DEFAULT NULL,
  `update_user` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户 ID',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1316710112288125200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '身份确认信息表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
