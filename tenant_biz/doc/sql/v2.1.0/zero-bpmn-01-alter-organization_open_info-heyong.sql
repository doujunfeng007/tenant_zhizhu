
ALTER TABLE zero_bpmn.organization_open_info MODIFY COLUMN `cust_id` BIGINT NULL COMMENT '用户ID';
ALTER TABLE zero_bpmn.organization_open_info ADD COLUMN `open_account_access_way`	INT NOT NULL DEFAULT 1 COMMENT'开户接入方式: 1:H5开户 2:APP开户 3:线下开户' AFTER `id`;
ALTER TABLE zero_bpmn.organization_open_info ADD COLUMN `contact_phone_area` VARCHAR(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系人电话区号' AFTER `contact`;
ALTER TABLE zero_bpmn.open_account_bank_verity_info ADD COLUMN `phone_area` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '手机号码区号' AFTER `client_name`;
ALTER TABLE zero_bpmn.open_account_bank_verity_info MODIFY COLUMN `verify_count` int NULL DEFAULT 0 COMMENT '验证次数';

ALTER TABLE zero_bpmn.organization_open_info MODIFY COLUMN `approve_status` int NULL DEFAULT 0 COMMENT '审核状态:0.待审核 1.审核通过 2.审核不通过';
ALTER TABLE zero_bpmn.organization_open_info ADD COLUMN`open_status` int NULL DEFAULT 0 COMMENT '开户状态:0.待开户 1.开户成功 2.开户失败' AFTER `open_date`;
ALTER TABLE zero_bpmn.organization_open_info ADD COLUMN`open_result` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '开户结果' AFTER `open_status`;

ALTER TABLE zero_bpmn.open_account_bank_verity_info ADD COLUMN `verify_reason` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '验证结果(成功或失败原因)' AFTER `verify_time`;
ALTER TABLE zero_bpmn.professional_investor_pi_info ADD COLUMN `securities_phone_area` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '证券手机号码区号' AFTER `client_eng_name`;


-- 机构开户股东信息
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for organization_open_shareholder_info
-- ----------------------------
DROP TABLE IF EXISTS `organization_open_shareholder_info`;
CREATE TABLE `organization_open_shareholder_info`  (
                                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                       `open_info_id` bigint NOT NULL COMMENT '机构开户记录',
                                                       `cust_id` bigint NOT NULL COMMENT '用户号',
                                                       `tenant_id` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '租户 ID',
                                                       `title` int NULL COMMENT '职位:1.董事 2.股东 3.授权签署',
                                                       `type` int NULL COMMENT '类型:1.先生 2.小姐 3.博士 4.公司',
                                                       `first_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓氏',
                                                       `last_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
                                                       `company_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '公司名称',
                                                       `id_number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '身份证号码',
                                                       `is_pep` tinyint(1) NULL DEFAULT 0 COMMENT '是否PEP: 0.否 1.是',
                                                       `risk` int NULL DEFAULT 0 COMMENT '风险等级: 1.低风险 2.中风险 3.高风险',
                                                       `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                                       `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                       `is_deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
                                                       `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
                                                       `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
                                                       PRIMARY KEY (`id`) USING BTREE,
                                                       INDEX `idx_open_info_id`(`open_info_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1812084556840112131 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '机构开户董事、股东、授权签署' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
-- 添加风险承受程度
ALTER TABLE `zero_bpmn`.`organization_open_info` ADD COLUMN `accept_risk` int NULL DEFAULT NULL COMMENT '风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]' AFTER `swift_code`;
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1813444238549495809, '000000', 0, 'risk_type', '-1', '风险等级', 1, '用于个人和机构开户的风险等级', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1813444606100549633, '000000', 1813444238549495809, 'risk_type', '6', '非常进取型', 1, '', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1813444703966244866, '000000', 1813444238549495809, 'risk_type', '5', '进取型', 2, '', 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1813444828302192641, '000000', 1813444238549495809, 'risk_type', '4', '中度进取型', 3, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1813444996229541890, '000000', 1813444238549495809, 'risk_type', '3', '平稳型', 4, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1813445077729062914, '000000', 1813444238549495809, 'risk_type', '2', '中度保守型', 5, NULL, 0, 0);
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`) VALUES (1813445197702934529, '000000', 1813444238549495809, 'risk_type', '1', '保守型', 6, NULL, 0, 0);
