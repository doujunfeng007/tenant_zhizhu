CREATE TABLE `ta_t_daily_interest` (
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
) ENGINE=InnoDB AUTO_INCREMENT=2883 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
