CREATE TABLE `payee_category` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `sequence_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易流水号',
                                  `cust_id` int NOT NULL COMMENT '用户id',
                                  `account_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '理财账号',
                                  `pay_way` int NOT NULL COMMENT '支付方式  1 现金  2活利得',
                                  `payee_category` int NOT NULL COMMENT '收款人类别 1本人  2非本人',
                                  `currency` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '代码',
                                  `cash` decimal(30,8) DEFAULT NULL COMMENT '现金',
                                  `product_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名称 活利得名称',
                                  `product_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品代码',
                                  `selling_amount` decimal(30,8) DEFAULT NULL COMMENT '卖出金额',
                                  `payee_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '收款人名字',
                                  `receivable_amount` decimal(30,8) DEFAULT NULL COMMENT '收款金额',
                                  `pay_time` varchar(30) DEFAULT NULL COMMENT '支付时间',
                                  `order_remark` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订单备注',
                                  `create_time` datetime DEFAULT NULL,
                                  `update_time` datetime DEFAULT NULL,
                                  `order_status` int DEFAULT NULL COMMENT '订单状态 1未提交  2已提交',
                                  `pay_status` int DEFAULT NULL COMMENT '支付状态 1未支付 2已支付',
                                  `order_sequence_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '柜台交易订单流水号',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收款人类别管理表/线上支付记录表';
CREATE TABLE `payee_category_img` (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `img_path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '存入的路径',
                                      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                      `cust_id` bigint DEFAULT NULL COMMENT '用户 ID',
                                      `account_id` varchar(20) DEFAULT NULL COMMENT '理财账号',
                                      `transact_id` bigint DEFAULT NULL COMMENT '图片对应交易流水号',
                                      `tenant_id` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '租户 ID',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1813446711139598340 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='收款记录类别图片表';
