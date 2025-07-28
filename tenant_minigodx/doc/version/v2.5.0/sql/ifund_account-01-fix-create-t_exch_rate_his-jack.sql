CREATE TABLE `t_exch_rate_his` (
                                   `id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                   `orgId` int NOT NULL COMMENT '原记录ID',
                                   `srcCurrency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '源币种',
                                   `dstCurrency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目的币种',
                                   `rate` decimal(16,8) NOT NULL COMMENT '汇率，即  目的币种=源币种X汇率',
                                   `dataUpdTime` datetime NOT NULL COMMENT '数据更新时间',
                                   `recordUpdTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
                                   `createTime` datetime NOT NULL COMMENT '记录创建时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   KEY `idx_currency_date` (`srcCurrency`,`dstCurrency`,`dataUpdTime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=763 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
