ALTER TABLE professional_investor_pi_info ADD COLUMN `declaration` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '被视为专业投资者身份的确认书声明(勾选项)' AFTER treatment;
