CREATE TABLE `customer_debenture_trading_account` (
      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
      `cust_id` bigint DEFAULT NULL COMMENT '客户号（个人/授权人）',
      `account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '理财账号',
      `trade_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易账户',
      `account_status` tinyint(1) DEFAULT '0' COMMENT '债券账户状态：0-正常',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      `is_deleted` tinyint DEFAULT NULL,
      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='债券交易账户信息表';

CREATE TABLE `customer_debenture_capital_account` (
      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
      `trade_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易账户',
      `fund_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金账户',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `is_deleted` tinyint DEFAULT NULL,
      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='债券子账户信息表';
