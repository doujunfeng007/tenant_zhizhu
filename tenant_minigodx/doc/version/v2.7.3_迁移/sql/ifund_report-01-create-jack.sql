CREATE TABLE `customer_bond_holding_records` (
                                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                 `holding_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '持仓标识',
                                                 `sub_account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资金账号',
                                                 `bond_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金代码',
                                                 `bond_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金名称',
                                                 `total_quantity` decimal(16,4) DEFAULT NULL COMMENT '总份额',
                                                 `available_quantity` decimal(16,4) DEFAULT NULL COMMENT '可用份额',
                                                 `frozen_quantity` decimal(16,4) DEFAULT NULL COMMENT '冻结份额',
                                                 `transited_quantity` decimal(16,4) DEFAULT NULL COMMENT '在途份额',
                                                 `withdraw_quantity` decimal(20,5) DEFAULT NULL COMMENT '可取份额',
                                                 `average_cost` decimal(16,4) DEFAULT NULL COMMENT '平均成本',
                                                 `diluted_cost` decimal(16,4) DEFAULT NULL COMMENT '摊薄平均成本，包含赎回',
                                                 `currency` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '货币类型',
                                                 `total_fee` decimal(16,4) DEFAULT NULL COMMENT '总费用',
                                                 `realized_gain_loss` decimal(16,4) DEFAULT NULL COMMENT '以实现盈亏，即卖出获利',
                                                 `create_time` date DEFAULT NULL COMMENT '创建时间',
                                                 `update_time` date DEFAULT NULL COMMENT '修改时间',
                                                 `is_deleted` tinyint DEFAULT NULL,
                                                 `total_gain_loss` decimal(19,8) DEFAULT NULL COMMENT '活利得累计总收益',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=444 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户债卷持仓表';




CREATE TABLE `customer_bond_holding_history` (
                                                 `id` int NOT NULL AUTO_INCREMENT COMMENT '记录标志',
                                                 `subAccountId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金账号',
                                                 `fundCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金/现金id',
                                                 `holdingType` int unsigned NOT NULL DEFAULT '1' COMMENT '持仓类型，1：fund；2：cash；3：icp',
                                                 `totalQuantity` decimal(19,8) DEFAULT NULL,
                                                 `availableQuantity` decimal(19,8) DEFAULT NULL,
                                                 `frozenQuantity` decimal(19,8) DEFAULT NULL,
                                                 `transitedQuantity` decimal(19,8) DEFAULT NULL,
                                                 `withdrawQuantity` decimal(19,8) DEFAULT NULL,
                                                 `averageCost` decimal(27,16) DEFAULT NULL,
                                                 `dilutedCost` decimal(27,16) DEFAULT NULL,
                                                 `currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '货币类型',
                                                 `accumulatedBuyAmount` decimal(19,8) DEFAULT NULL,
                                                 `accumulatedCashDividends` decimal(19,8) DEFAULT NULL,
                                                 `realizedGainLoss` decimal(19,8) DEFAULT NULL,
                                                 `totalGainLoss` decimal(19,8) DEFAULT NULL COMMENT '活利得累计总收益',
                                                 `totalFee` decimal(19,8) DEFAULT NULL,
                                                 `flowId` int NOT NULL DEFAULT '0' COMMENT '对应flow记录的主键，如果主键有多个字段，需要扩充',
                                                 `transactionTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                 `createTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                                 `updateTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                                                 PRIMARY KEY (`id`) USING BTREE,
                                                 UNIQUE KEY `idx_subaccountid_fundcode_flowid` (`fundCode`,`subAccountId`,`flowId`) USING BTREE,
                                                 KEY `idx_subaccount_datetime` (`subAccountId`,`transactionTime`,`fundCode`) USING BTREE,
                                                 KEY `idx_transactionTime` (`transactionTime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='债券持仓流水历史表';


CREATE TABLE `customer_bond_trading_transaction` (
                                                     `id` int NOT NULL AUTO_INCREMENT COMMENT '标识',
                                                     `nomineeTransId` int DEFAULT NULL COMMENT '归集标识',
                                                     `orderId` int NOT NULL COMMENT '订单标识',
                                                     `subAccountId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子账号',
                                                     `type` int NOT NULL COMMENT '类型',
                                                     `mode` int DEFAULT NULL COMMENT '方式,1:金额;2:数量;3:比例;',
                                                     `fundCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金代码',
                                                     `currency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'USD' COMMENT '币种',
                                                     `amount` decimal(19,8) DEFAULT NULL,
                                                     `quantity` decimal(19,8) DEFAULT NULL,
                                                     `price` decimal(19,8) DEFAULT NULL,
                                                     `transactionDate` datetime(3) NOT NULL COMMENT '交易日期',
                                                     `confirmedDate` datetime(3) DEFAULT NULL COMMENT '确认日期',
                                                     `expectedSettledDate` datetime(3) DEFAULT NULL COMMENT '预计清算日期',
                                                     `settledDate` datetime(3) DEFAULT NULL COMMENT '清算日期',
                                                     `effectiveSettledDate` datetime(3) DEFAULT NULL COMMENT '有效清算日期',
                                                     `settledAmount` decimal(19,8) DEFAULT NULL,
                                                     `fee` decimal(19,8) DEFAULT NULL,
                                                     `source` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易来源：Client;CorporateAction;ManualAdjustment;Admin',
                                                     `location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Custody;yfund',
                                                     `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                                                     `status` int unsigned NOT NULL COMMENT '状态代码,700:CONFIRMED;900:SETTLED',
                                                     `createTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                                     `updateTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                     PRIMARY KEY (`id`) USING BTREE,
                                                     KEY `nomineeTransId` (`nomineeTransId`) USING BTREE,
                                                     KEY `confirmedDate` (`confirmedDate`) USING BTREE,
                                                     KEY `idx_orderId_type` (`orderId`,`type`) USING BTREE,
                                                     KEY `idx_subAccountId_fundCode` (`subAccountId`,`fundCode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2739 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='债券交易流水历史表';



CREATE TABLE `customer_bond_trading_records` (
                                                 `id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                 `sub_account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '子账户id',
                                                 `submit_date` datetime DEFAULT NULL COMMENT '交易日期(提交日期)',
                                                 `settled_date` datetime DEFAULT NULL COMMENT '清算日期',
                                                 `confirmed_date` datetime DEFAULT NULL COMMENT '确认日期',
                                                 `rejected_date` datetime DEFAULT NULL COMMENT '拒绝日期',
                                                 `canceled_date` datetime DEFAULT NULL COMMENT '取消日期',
                                                 `bond_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金代码',
                                                 `bond_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金名称',
                                                 `type` int DEFAULT NULL COMMENT '类型,1:买;2:卖;3:交换买;4:交换卖;',
                                                 `mode` int DEFAULT NULL COMMENT '方式,1:金额;2:数量;3:权重比例;',
                                                 `currency` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '币种',
                                                 `amount` decimal(19,8) DEFAULT NULL COMMENT '申请金额',
                                                 `business_amount` decimal(19,8) DEFAULT NULL COMMENT '成交数量',
                                                 `business_balance` decimal(19,8) DEFAULT NULL COMMENT '成交金额',
                                                 `business_price` decimal(19,8) DEFAULT NULL COMMENT '成交价格',
                                                 `fee_count` decimal(19,8) unsigned zerofill DEFAULT NULL COMMENT '佣金',
                                                 `sequence_no` int DEFAULT NULL COMMENT '交易流水号',
                                                 `status` int DEFAULT NULL COMMENT '状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;',
                                                 `status_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态描述',
                                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                 `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                 `is_deleted` int(8) unsigned zerofill DEFAULT NULL,
                                                 `transaction_gain_loss` decimal(19,8) DEFAULT NULL COMMENT '活利得、债券易卖出应收利息，债券易买入应付利息',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1458 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户债券交易流水汇总表';


CREATE TABLE `customer_hld_holding_history` (
                                                `id` int NOT NULL AUTO_INCREMENT COMMENT '记录标志',
                                                `subAccountId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金账号',
                                                `fundCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金/现金id',
                                                `holdingType` int unsigned NOT NULL DEFAULT '1' COMMENT '持仓类型，1：fund；2：cash；3：icp',
                                                `totalQuantity` decimal(19,8) DEFAULT NULL,
                                                `availableQuantity` decimal(19,8) DEFAULT NULL,
                                                `frozenQuantity` decimal(19,8) DEFAULT NULL,
                                                `transitedQuantity` decimal(19,8) DEFAULT NULL,
                                                `withdrawQuantity` decimal(19,8) DEFAULT NULL,
                                                `averageCost` decimal(27,16) DEFAULT NULL,
                                                `dilutedCost` decimal(27,16) DEFAULT NULL,
                                                `currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '货币类型',
                                                `accumulatedBuyAmount` decimal(19,8) DEFAULT NULL,
                                                `accumulatedCashDividends` decimal(19,8) DEFAULT NULL,
                                                `realizedGainLoss` decimal(19,8) DEFAULT NULL,
                                                `totalGainLoss` decimal(19,8) DEFAULT NULL COMMENT '活利得累计总收益',
                                                `totalFee` decimal(19,8) DEFAULT NULL,
                                                `flowId` int NOT NULL DEFAULT '0' COMMENT '对应flow记录的主键，如果主键有多个字段，需要扩充',
                                                `transactionTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                `createTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                                `updateTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                                                PRIMARY KEY (`id`) USING BTREE,
                                                UNIQUE KEY `idx_subaccountid_fundcode_flowid` (`fundCode`,`subAccountId`,`flowId`) USING BTREE,
                                                KEY `idx_subaccount_datetime` (`subAccountId`,`transactionTime`,`fundCode`) USING BTREE,
                                                KEY `idx_transactionTime` (`transactionTime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='活利得持仓流水历史表';



CREATE TABLE `customer_hld_trading_transaction` (
                                                    `id` int NOT NULL AUTO_INCREMENT COMMENT '标识',
                                                    `nomineeTransId` int DEFAULT NULL COMMENT '归集标识',
                                                    `orderId` int NOT NULL COMMENT '订单标识',
                                                    `subAccountId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子账号',
                                                    `type` int NOT NULL COMMENT '类型',
                                                    `mode` int DEFAULT NULL COMMENT '方式,1:金额;2:数量;3:比例;',
                                                    `fundCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金代码',
                                                    `currency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'USD' COMMENT '币种',
                                                    `amount` decimal(19,8) DEFAULT NULL,
                                                    `quantity` decimal(19,8) DEFAULT NULL,
                                                    `price` decimal(19,8) DEFAULT NULL,
                                                    `transactionDate` datetime(3) NOT NULL COMMENT '交易日期',
                                                    `confirmedDate` datetime(3) DEFAULT NULL COMMENT '确认日期',
                                                    `expectedSettledDate` datetime(3) DEFAULT NULL COMMENT '预计清算日期',
                                                    `settledDate` datetime(3) DEFAULT NULL COMMENT '清算日期',
                                                    `effectiveSettledDate` datetime(3) DEFAULT NULL COMMENT '有效清算日期',
                                                    `settledAmount` decimal(19,8) DEFAULT NULL,
                                                    `fee` decimal(19,8) DEFAULT NULL,
                                                    `source` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易来源：Client;CorporateAction;ManualAdjustment;Admin',
                                                    `location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Custody;yfund',
                                                    `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                                                    `status` int unsigned NOT NULL COMMENT '状态代码,700:CONFIRMED;900:SETTLED',
                                                    `createTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                                    `updateTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                    PRIMARY KEY (`id`) USING BTREE,
                                                    KEY `nomineeTransId` (`nomineeTransId`) USING BTREE,
                                                    KEY `confirmedDate` (`confirmedDate`) USING BTREE,
                                                    KEY `idx_orderId_type` (`orderId`,`type`) USING BTREE,
                                                    KEY `idx_subAccountId_fundCode` (`subAccountId`,`fundCode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2706 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='活利得交易流水历史表';


CREATE TABLE `customer_hld_holding_records` (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                `holding_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '持仓标识',
                                                `sub_account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资金账号',
                                                `hld_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '活利得代码',
                                                `hld_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '活利得名称',
                                                `total_quantity` decimal(16,4) DEFAULT NULL COMMENT '总份额',
                                                `available_quantity` decimal(16,4) DEFAULT NULL COMMENT '可用份额',
                                                `frozen_quantity` decimal(16,4) DEFAULT NULL COMMENT '冻结份额',
                                                `transited_quantity` decimal(16,4) DEFAULT NULL COMMENT '在途份额',
                                                `withdraw_quantity` decimal(20,5) DEFAULT NULL COMMENT '可取份额',
                                                `average_cost` decimal(16,4) DEFAULT NULL COMMENT '平均成本',
                                                `diluted_cost` decimal(16,4) DEFAULT NULL COMMENT '摊薄平均成本，包含赎回',
                                                `currency` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '货币类型',
                                                `total_fee` decimal(16,4) DEFAULT NULL COMMENT '总费用',
                                                `realized_gain_loss` decimal(16,4) DEFAULT NULL COMMENT '以实现盈亏，即卖出获利',
                                                `create_time` date DEFAULT NULL COMMENT '创建时间',
                                                `update_time` date DEFAULT NULL COMMENT '修改时间',
                                                `is_deleted` tinyint DEFAULT NULL,
                                                `total_gain_loss` decimal(19,8) DEFAULT NULL COMMENT '活利得累计总收益',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=455 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户活利得持仓表';


CREATE TABLE `customer_hld_trading_records` (
                                                `id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                `sub_account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '子账户id',
                                                `submit_date` datetime DEFAULT NULL COMMENT '交易日期(提交日期)',
                                                `settled_date` datetime DEFAULT NULL COMMENT '清算日期',
                                                `confirmed_date` datetime DEFAULT NULL COMMENT '确认日期',
                                                `rejected_date` datetime DEFAULT NULL COMMENT '拒绝日期',
                                                `canceled_date` datetime DEFAULT NULL COMMENT '取消日期',
                                                `hld_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资金代码',
                                                `hld_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资金名称',
                                                `type` int DEFAULT NULL COMMENT '类型,1:买;2:卖;3:交换买;4:交换卖;',
                                                `mode` int DEFAULT NULL COMMENT '方式,1:金额;2:数量;3:权重比例;',
                                                `currency` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '币种',
                                                `amount` decimal(23,8) DEFAULT NULL COMMENT '申请金额',
                                                `business_amount` decimal(19,8) DEFAULT NULL COMMENT '成交数量',
                                                `business_balance` decimal(19,8) DEFAULT NULL COMMENT '成交金额',
                                                `business_price` decimal(19,8) DEFAULT NULL COMMENT '成交价格',
                                                `fee_count` decimal(19,8) unsigned zerofill DEFAULT NULL COMMENT '佣金',
                                                `sequence_no` int DEFAULT NULL COMMENT '交易流水号',
                                                `status` int DEFAULT NULL COMMENT '状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;',
                                                `status_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态描述',
                                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                `is_deleted` int(8) unsigned zerofill DEFAULT NULL,
                                                `transaction_gain_loss` decimal(19,8) DEFAULT NULL COMMENT '活利得、债券易卖出应收利息，债券易买入应付利息',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1462 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户活利德交易流水汇总表';


CREATE TABLE `t_non_trading_day_config` (
                                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                                            `nonTradingDay` date NOT NULL COMMENT '非交易日',
                                            `createTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                            `updateTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                                            `time_period` tinyint DEFAULT '0' COMMENT '时间段 0 全天 1 上午 2 下午',
                                            `reason_closure` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '休市原因',
                                            `uin` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;


CREATE TABLE `t_market_reference_price` (
                                            `productId` int unsigned NOT NULL COMMENT '产品 id',
                                            `price` decimal(21,8) DEFAULT '100.00000000' COMMENT '市场参考价格默认100.00',
                                            `lastUpdatedBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最后更新人',
                                            `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            PRIMARY KEY (`productId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='市场参考价格表';

CREATE TABLE `t_exch_rate_his` (
                                   `id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                   `orgId` int DEFAULT NULL COMMENT '原记录ID',
                                   `srcCurrency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '源币种',
                                   `dstCurrency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '目的币种',
                                   `rate` decimal(16,8) DEFAULT NULL COMMENT '汇率，即  目的币种=源币种X汇率',
                                   `dataUpdTime` datetime DEFAULT NULL COMMENT '数据更新时间',
                                   `recordUpdTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
                                   `createTime` datetime DEFAULT NULL COMMENT '记录创建时间',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=955 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;



CREATE TABLE `t_daily_interest` (
                                    `interestId` int NOT NULL AUTO_INCREMENT,
                                    `subAccountId` varchar(50) NOT NULL COMMENT '子账号 id',
                                    `productId` int NOT NULL COMMENT '产品 id',
                                    `interestDate` datetime NOT NULL COMMENT '计息日，及产生利息的日期',
                                    `currency` varchar(8) NOT NULL COMMENT '交易币种',
                                    `amount` decimal(21,8) NOT NULL COMMENT '计息金额',
                                    `faceValue` decimal(21,8) NOT NULL COMMENT '面值',
                                    `volume` int NOT NULL COMMENT '份额',
                                    `interest` decimal(21,8) NOT NULL COMMENT '当日利息',
                                    `interestRate` decimal(21,8) NOT NULL COMMENT '利率',
                                    `createTime` datetime NOT NULL COMMENT '记录创建时间',
                                    `updateTime` datetime NOT NULL COMMENT '记录更新时间',
                                    PRIMARY KEY (`interestId`),
                                    UNIQUE KEY `subacct_prd_date` (`subAccountId`,`productId`,`interestDate`)
) ENGINE=InnoDB AUTO_INCREMENT=8386 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


CREATE TABLE `t_da_client` (
                               `subAccountId` varchar(50) NOT NULL COMMENT 'daAccountId+daClientId',
                               `daAccountId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT 'da 主账号 id',
                               `daClientId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT 'da 下的客户 id',
                               `custId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '客户ID',
                               `extAccountId` varchar(32) DEFAULT NULL COMMENT '理财账号',
                               `chineseName` varchar(100) DEFAULT NULL COMMENT '中文姓名',
                               `firstName` varchar(150) DEFAULT NULL COMMENT '英文名',
                               `lastName` varchar(100) DEFAULT NULL COMMENT '英文姓',
                               `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                               `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
                               PRIMARY KEY (`subAccountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `customer_hld_capital_account` (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                `trade_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '交易账户',
                                                `sub_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子账户',
                                                `is_master` tinyint(1) DEFAULT NULL COMMENT '是否默认账号：0-不是 1-是',
                                                `account_status` tinyint(1) DEFAULT '0' COMMENT '账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]',
                                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                `account_type` int DEFAULT NULL COMMENT '账户类型[0=现金账户 M=保证金账户]',
                                                `is_deleted` tinyint DEFAULT NULL,
                                                `risk_level` int DEFAULT NULL COMMENT '客户风险评级 1.保守型，2.稳健型，3.平衡型，4.增长型，5.进取型',
                                                PRIMARY KEY (`id`) USING BTREE,
                                                UNIQUE KEY `uq_fund_account` (`sub_account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=332 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='活利得子账户信息表';

CREATE TABLE `customer_hld_trading_account` (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                `cust_id` bigint DEFAULT NULL COMMENT '客户号（个人/授权人）',
                                                `trade_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易账户',
                                                `account_type` tinyint(1) DEFAULT NULL COMMENT '账户类型：账户类型：1-个人 2-联名账户 3-机构账户',
                                                `oper_type` tinyint(1) DEFAULT NULL COMMENT '账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回',
                                                `account_status` tinyint(1) DEFAULT '0' COMMENT '账户状态：0-正常',
                                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                `is_deleted` tinyint DEFAULT NULL,
                                                `is_current` int DEFAULT '0' COMMENT '是否当前选中1是，0不是',
                                                `risk_level` int DEFAULT NULL,
                                                `account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '理财账号',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=332 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='活利得交易账户信息表';



-- auto-generated definition
create table customer_bond_trading_account
(
    id             bigint auto_increment comment '主键'
        primary key,
    cust_id        bigint               null comment '客户号（个人/授权人）',
    trade_account  varchar(20)          null comment '交易账户',
    account_type   tinyint(1)           null comment '账户类型：账户类型：1-个人 2-联名账户 3-机构账户',
    oper_type      tinyint(1)           null comment '账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回',
    account_status tinyint(1) default 0 null comment '账户状态：0-正常',
    create_time    datetime             null comment '创建时间',
    update_time    datetime             null comment '更新时间',
    is_deleted     tinyint              null,
    is_current     int        default 0 null comment '是否当前选中1是，0不是',
    risk_level     int                  null comment '客户风险等级 风险评级 1.保守型，2.稳健型，3.平衡型，4.增长型，5.进取型',
    account_id     varchar(32)          null comment '理财账号'
)
    comment '债券交易账户信息表' charset = utf8
                                 row_format = DYNAMIC;



-- auto-generated definition
create table customer_bond_capital_account
(
    id             bigint auto_increment comment '主键'
        primary key,
    trade_account  varchar(20)                            null comment '交易账户',
    sub_account    varchar(20) collate utf8mb4_general_ci null comment '子账户',
    is_master      tinyint(1)                             null comment '是否默认账号：0-不是 1-是',
    account_status tinyint(1) default 0                   null comment '账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]',
    create_time    datetime                               null comment '创建时间',
    update_time    datetime                               null comment '更新时间',
    account_type   int                                    null comment '账户类型[0=现金账户 M=保证金账户]',
    is_deleted     tinyint                                null,
    constraint uq_fund_account
        unique (sub_account)
)
    comment '债券易子账户信息表' charset = utf8mb4
                                 row_format = DYNAMIC;

CREATE TABLE `organization_basic_info` (
                                           `id` int NOT NULL AUTO_INCREMENT,
                                           `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                           `cust_id` bigint DEFAULT NULL COMMENT '用户id',
                                           `name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公司名称',
                                           `address` varchar(220) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公司地址',
                                           `registration_address` varchar(220) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '注册地址',
                                           `registration_certificate` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                           `business_registration_certificate` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商业登记证',
                                           `business_nature` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务性质',
                                           `funding_source` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '资金来源',
                                           `contacts` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系人',
                                           `contacts_mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系人电话',
                                           `contacts_email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系人邮箱',
                                           `target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '开户目的，0股票基金交易，1发行产品',
                                           `bank_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '银行名称',
                                           `swift_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '银行代码',
                                           `account_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '银行账户名',
                                           `bank_account_number` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '银行账户号码',
                                           `create_time` datetime DEFAULT NULL,
                                           `update_time` datetime DEFAULT NULL,
                                           `status` int DEFAULT NULL,
                                           `is_deleted` int DEFAULT '0',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
