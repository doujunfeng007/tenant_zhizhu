
CREATE TABLE `customer_fund_trading_transaction` (
                                                     `id` int NOT NULL AUTO_INCREMENT COMMENT '标识',
                                                     `nomineeTransId` int DEFAULT NULL COMMENT '归集标识',
                                                     `orderId` int NOT NULL COMMENT '订单标识',
                                                     `subAccountId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子账号',
                                                     `type` int NOT NULL COMMENT '类型',
                                                     `mode` int DEFAULT NULL COMMENT '方式,1:金额;2:数量;3:比例;',
                                                     `fundCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '基金代码',
                                                     `currency` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'USD' COMMENT '币种',
                                                     `amount` decimal(23,8) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=2749 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='基金交易流水历史表';


CREATE TABLE `customer_fund_holding_history` (
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
) ENGINE=InnoDB AUTO_INCREMENT=12001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='基金持仓流水历史表';



-- auto-generated definition
create table customer_fund_trading_records
(
    id                     int auto_increment comment '自增ID'
        primary key,
    sub_account_id         varchar(32)                      null comment '客户ID',
    submit_date            datetime                         null comment '交易日期(提交日期)',
    settled_date           datetime                         null comment '清算日期',
    confirmed_date         datetime                         null comment '确认日期',
    rejected_date          datetime                         null comment '拒绝日期',
    canceled_date          datetime                         null comment '取消日期',
    fund_code              varchar(20)                      null comment '基金代码',
    fund_name              varchar(256)                     null comment '基金名称',
    type                   int                              null comment '类型,1:买;2:卖;3:交换买;4:交换卖;',
    mode                   int                              null comment '方式,1:金额;2:数量;3:权重比例;',
    currency               varchar(8)                       null comment '币种',
    amount                 decimal(23, 8)                   null comment '申请金额',
    business_amount        decimal(19, 8)                   null comment '成交数量',
    business_balance       decimal(19, 8)                   null comment '成交金额',
    business_price         decimal(19, 8)                   null comment '成交价格',
    fee_count              decimal(19, 8) unsigned zerofill null comment '佣金',
    sequence_no            int                              null comment '交易流水号',
    status                 int                              null comment '状态 ,100:PENDING;200:SUBMITTED;211:AUTHORIZED;221:POOLED;230:PLACED;270:CONFIRMED;300:SETTLED;400:REJECTED;500:CANCELED;600:FAILED;700:SUCCESS;',
    status_desc            varchar(256)                     null comment '状态描述',
    create_time            datetime                         null comment '创建时间',
    update_time            datetime                         null comment '修改时间',
    is_deleted             int(8) unsigned zerofill         null,
    transaction_gain_loss  decimal(19, 8)                   null comment '活利得、债券易卖出应收利息，债券易买入应付利息',
    transaction_net_amount decimal(19, 8)                   null comment '净成交额'
)
    comment '客户基金交易流水汇总表' row_format = DYNAMIC;





CREATE TABLE `customer_fund_holding_records` (
                                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                 `holding_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '持仓标识',
                                                 `sub_account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资金账号',
                                                 `fund_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金代码',
                                                 `fund_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金名称',
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
                                                 `total_gain_loss`    decimal(19, 8) null comment '累计总收益',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户基金持仓表';


CREATE TABLE `customer_fund_capital_account` (
                                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                 `trade_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易账户',
                                                 `fund_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '基金账户',
                                                 `is_master` tinyint(1) DEFAULT NULL COMMENT '是否是主账号：0-不是 1-是',
                                                 `account_status` tinyint(1) DEFAULT '0' COMMENT '基金账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]',
                                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                 `available_amount` decimal(16,2) DEFAULT NULL COMMENT '可取金额',
                                                 `freeze_amount` decimal(16,2) DEFAULT NULL COMMENT '冻结金额',
                                                 `transited_amount` decimal(16,2) DEFAULT NULL COMMENT '在途金额',
                                                 `account_type` int DEFAULT NULL COMMENT '账户类型[0=现金账户 M=保证金账户]',
                                                 `is_deleted` tinyint DEFAULT NULL,
                                                 PRIMARY KEY (`id`) USING BTREE,
                                                 UNIQUE KEY `uq_fund_account` (`fund_account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1859501847964860418 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='基金账户信息表';



CREATE TABLE `customer_fund_trading_account` (
                                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                 `cust_id` bigint DEFAULT NULL COMMENT '客户号（个人/授权人）',
                                                 `account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '理财账号',
                                                 `trade_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易账户',
                                                 `fund_account_type` tinyint(1) DEFAULT NULL COMMENT '基金账户类型：基金账户类型：1-个人 2-联名账户 3-机构账户',
                                                 `fund_oper_type` tinyint(1) DEFAULT NULL COMMENT '基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回',
                                                 `available_amount` decimal(16,2) DEFAULT NULL COMMENT '可用金额',
                                                 `freeze_amount` decimal(16,2) DEFAULT NULL COMMENT '冻结金额',
                                                 `transited_amount` decimal(16,2) DEFAULT NULL COMMENT '在途金额',
                                                 `currency` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '币种',
                                                 `account_status` tinyint(1) DEFAULT '0' COMMENT '基金账户状态：0-正常',
                                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                 `is_deleted` tinyint DEFAULT NULL,
                                                 `is_current` int DEFAULT '0' COMMENT '是否当前选中1是，0不是',
                                                 `risk_level` int DEFAULT NULL COMMENT '风险评级 1.保守型，2.稳健型，3.平衡型，4.增长型，5.进取型',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1859501847960666114 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='基金交易账户信息表';



CREATE TABLE `t_fund` (
                          `productId` bigint NOT NULL AUTO_INCREMENT,
                          `afbCode` longtext COLLATE utf8mb4_general_ci,
                          `productCode` longtext COLLATE utf8mb4_general_ci,
                          `isin` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金ISIN',
                          `mstarId` varchar(12) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'MStar ID',
                          `performanceId` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'MStar Performance ID',
                          `mstarFundId` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '晨星的fund_id',
                          `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金名称',
                          `legalName` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金法定名称',
                          `currency` varchar(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '货币单位',
                          `custodianCurrency` varchar(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对接上手使用的币种',
                          `sedol` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Sedol Code',
                          `citiCode` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `type` tinyint DEFAULT NULL COMMENT '基金类型, 1-Traditional, 2-Unit Trust, 3-Hedge Fund 4-Domestic Fund',
                          `mstarCategoryId` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '晨星分类ID',
                          `riskLevel` tinyint DEFAULT NULL COMMENT '基金风险等级',
                          `domicile` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金归属地',
                          `launchDate` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金成立时间',
                          `fundSize` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金规模',
                          `fundSizeCurrency` varchar(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金规模的货币单位',
                          `fundSizeEndDate` date DEFAULT NULL COMMENT '基金规模更新时间',
                          `incomeType` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收入类型',
                          `status` tinyint DEFAULT '0' COMMENT '0-不可交易, 1-可进行买卖交易, 2-仅支持申购, 3-仅支持赎回',
                          `branding` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '基金品牌名称',
                          `brandingId` int DEFAULT NULL COMMENT '基金品牌ID',
                          `companyId` int DEFAULT NULL COMMENT '基金公司ID',
                          `investmentStrategyEn` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '投资策略(英文)',
                          `investmentStrategyCn` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '投资策略(中文简体)',
                          `investmentStrategyTw` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '投资策略(中文繁体)',
                          `recommendEn` text COLLATE utf8mb4_general_ci COMMENT '基金点评(英文)',
                          `recommendCn` text COLLATE utf8mb4_general_ci COMMENT '基金点评(中文简体)',
                          `recommendTw` text COLLATE utf8mb4_general_ci COMMENT '基金点评(中文繁体)',
                          `primaryBenchmarkId` varchar(12) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主要基准ID',
                          `primaryBenchmarkName` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主要基准名称',
                          `primaryBenchmarkWeight` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主要基准权重',
                          `purchaseSettlementDays` int DEFAULT NULL COMMENT '申购到结算时间',
                          `rePurchaseSettlementDays` int DEFAULT NULL COMMENT '再次申购到结算时间',
                          `daysToSettlementType` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申购到结算的时间类型',
                          `dividendType` tinyint DEFAULT NULL COMMENT '分红类型, 0-None, 1-Accumulation, 2-Income',
                          `dividendFrequency` tinyint DEFAULT NULL COMMENT '分红频率, 0-None, 1-Daily, 2-Weekly, 3-Fortnightly, 4-Monthly, 5-Bimonthly, 6-Quarterly, 7-Semi-Annually, 8-Annually',
                          `dividendYield` decimal(19,8) DEFAULT NULL COMMENT '股息生息率',
                          `dividendYieldEndDate` date DEFAULT NULL COMMENT '股息生息率最后时间',
                          `annualManagementFee` decimal(19,5) DEFAULT NULL COMMENT '年度管理费率(%) == 实际管理费率(%)',
                          `actualManagementFee` decimal(19,5) DEFAULT NULL COMMENT '实际管理费率(%)',
                          `maxManagementFee` decimal(19,5) DEFAULT NULL COMMENT '最高管理费率(%)',
                          `subscriptionFee` decimal(19,5) DEFAULT NULL COMMENT '申购费率(%)',
                          `redemptionFee` decimal(19,5) DEFAULT NULL COMMENT '赎回费率(%)',
                          `performanceFee` decimal(19,5) DEFAULT NULL COMMENT '(%)',
                          `gainsFee` decimal(19,5) DEFAULT NULL COMMENT '盈利费率(%)',
                          `distributionFee` decimal(19,5) DEFAULT NULL COMMENT '分销商费率(%)',
                          `depositFee` decimal(19,5) DEFAULT NULL COMMENT '存款费率(%)',
                          `administrationFee` decimal(19,5) DEFAULT NULL COMMENT '管理员费率(%)',
                          `totalFee` decimal(19,5) DEFAULT NULL COMMENT '总费率(%)',
                          `hasCorporateAction` tinyint DEFAULT NULL COMMENT '是否有公司行动, 包括分红?',
                          `hasDividend` tinyint DEFAULT NULL COMMENT '是否有分红',
                          `hasBreakdown` tinyint DEFAULT NULL,
                          `hasDocument` tinyint DEFAULT NULL COMMENT '是否有文档',
                          `piFlag` tinyint DEFAULT NULL COMMENT '是否认可投资基金',
                          `trackingError12Months` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `distributionRate` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分配率',
                          `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `lastUpdatedBy` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `privateDataMarket` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '私有数据的市场分布',
                          `daStatus` tinyint NOT NULL DEFAULT '1' COMMENT '0 没有签署 da 协议，1 签署了 da 协议，2 没有签署 subda 协议',
                          PRIMARY KEY (`productId`),
                          UNIQUE KEY `idx_isin_currency` (`isin`,`currency`),
                          KEY `idx_mstar_fund_id` (`mstarFundId`),
                          KEY `idx_mstarid` (`mstarId`),
                          KEY `idx_performanceid` (`performanceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='交易系统同步-基金商品表';



CREATE TABLE `mstar_fund_newest_price` (
                                           `performance_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
                                           `ccy` varchar(3) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
                                           `price_date` date NOT NULL DEFAULT '1970-01-01',
                                           `price` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
                                           `adjusted_price_date` date NOT NULL DEFAULT '1970-01-01',
                                           `adjusted_price` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
                                           `remark` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
                                           `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                           `last_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                           PRIMARY KEY (`performance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='交易系统同步-基金价格表';
