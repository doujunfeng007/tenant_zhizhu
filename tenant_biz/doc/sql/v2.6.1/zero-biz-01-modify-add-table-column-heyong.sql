-- 协议文章表 增加字段
ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `identification` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文章标识' AFTER `key_id`;

ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `key_word_hant` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '繁体关键词' AFTER key_word;
ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `key_word_en` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '英文关键词' AFTER key_word_hant;


ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `title_hant` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '繁体标题' AFTER title;
ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `title_en` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '英文标题' AFTER title_hant;

ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `summary_hant` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '繁体摘要' AFTER summary;
ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `summary_en` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '英文摘要' AFTER summary_hant;

ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `content_hant` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '繁体内容' AFTER content;
ALTER TABLE zero_biz.`oms_article_library` ADD COLUMN `content_en` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文内容' AFTER content_hant;



-- 帮助中心内容发布信息 增加字段
ALTER TABLE zero_biz.oms_publish_content ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER clicks;

-- 帮助中心目录配置表 增加字段
ALTER TABLE zero_biz.oms_publish_item ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER sort_id;
