ALTER TABLE `zero_biz`.`oms_article_library` MODIFY COLUMN `id` bigint NOT NULL FIRST;

ALTER TABLE `zero_biz`.`oms_article_library` ADD COLUMN `key_id` bigint NOT NULL COMMENT '主键' AFTER `id`;

ALTER TABLE `zero_biz`.`oms_article_library` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `key_id`;
