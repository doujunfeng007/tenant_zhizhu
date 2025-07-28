ALTER TABLE cash_withdrawals_bank ADD COLUMN  `receipt_bank_name_hant` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出金银行名称繁体' AFTER `receipt_bank_name`;
ALTER TABLE cash_withdrawals_bank ADD COLUMN  `receipt_bank_name_en` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出金银行名称英文' AFTER `receipt_bank_name_hant`;

/*增加字段后，生产的数据需要手动补全繁体字段和英文字段*/

