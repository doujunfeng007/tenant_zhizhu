ALTER TABLE zero_bpmn.organization_open_info ADD `approve_user_id` bigint NULL DEFAULT NULL COMMENT '审核人ID' AFTER  `approve_reason`;
ALTER TABLE zero_bpmn.organization_open_info ADD `approve_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人名称' AFTER `approve_user_id`;

ALTER TABLE zero_bpmn.organization_open_info ADD `expiry_date` date NULL DEFAULT NULL COMMENT '风险等级有效期' AFTER `accept_risk`;
ALTER TABLE zero_bpmn.customer_account_open_info ADD `expiry_date` date NULL DEFAULT NULL COMMENT '风险等级有效期' AFTER `accept_risk`;


ALTER TABLE `zero_biz`.`oms_board_info` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `pc_link`;
ALTER TABLE `zero_biz`.`oms_ad_info` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `is_show_in_activity`;
ALTER TABLE `zero_biz`.`oms_app_version` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `app_code`;


UPDATE zero_biz.oms_board_info SET tenant_id='000000';
UPDATE zero_biz.oms_ad_info SET tenant_id='000000';
UPDATE zero_biz.oms_app_version SET tenant_id='000000';
