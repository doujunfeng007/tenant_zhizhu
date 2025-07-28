CREATE TABLE `ta_t_da_client` (
                                  `subAccountId` varchar(50) NOT NULL COMMENT 'daAccountId+daClientId',
                                  `daAccountId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT 'da 主账号 id',
                                  `daClientId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT 'da 下的客户 id',
                                  `custId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '客户ID',
                                  `extAccountId` varchar(32) DEFAULT NULL COMMENT '理财账号',
                                  `chineseName` varchar(100) DEFAULT NULL COMMENT '中文姓名',
                                  `firstName` varchar(50) DEFAULT NULL COMMENT '英文名',
                                  `lastName` varchar(50) DEFAULT NULL COMMENT '英文姓',
                                  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
                                  PRIMARY KEY (`subAccountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
