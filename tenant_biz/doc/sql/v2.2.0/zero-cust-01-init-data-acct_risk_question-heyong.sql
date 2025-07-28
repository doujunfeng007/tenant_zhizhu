
ALTER TABLE acct_risk_question MODIFY COLUMN  `question_type` int NULL DEFAULT NULL COMMENT '1.个人户题库，2.公司户题库，3.PI个人认证户题库, 4.风险等级评估题库';


-- 第1题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024001, 1001, '【淨資產值】請問你的淨資產值(即是總資產扣除總負債)為多少(不包括自住房屋)?\n(答案應是投資概況第1條至第6條問題的答案總和減去私人貸款)', 1, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024001, 2001, '【净资产值】请问你的净资产值（即是总资产扣除总负债）为多少(不包括自住房屋)?\n(答案应是投资概况第1条至第6条问题的答案总和减去私人贷款)', 1, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024001, 3001, '【Household Net Worth-Approximately】 what is the net worth (total asset values minus total liabilities) of your family (excluding home)?\n(The answer should be sum of those to Questions 1-6 of Investment Profile less any personal loan) ', 1, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第2题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024002, 1002, '【可動用淨收入】請問你每月的可動用淨收入為多少? (答案應等同財務概況第6條問題的答案)', 2, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024002, 2002, '【可动用净收入】请问你每月的可动用净收入为多少？（答案应等同财务概况第6条问题的答案）', 2, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024002, 3002, '【Household Disposable Income】What is your monthly household disposable income? (The answer should be same as your answer to Question 6 of Financial Situations) ', 2, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第3题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024003, 1003, '【年齡】我的年齡是?', 3, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024003, 2003, '【年龄】我的年龄是?', 3, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024003, 3003, '【Age】My age is? ', 3, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第4题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024004, 1004, '【投資年期】我現在的投資，我打算在將來動用時間:', 4, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024004, 2004, '【投资年期】我现在的投资，我打算在将来动用时间：', 4, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024004, 3004, '【Investment Horizon】I plan to use the money I am investing: ', 4, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第5题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024005, 1005, '【現在的投資佔總可投資資產比例】我現在的投資，佔我可以投資的資產(不包括房屋)百份比是:', 5, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024005, 2005, '【现在的投资占总可投资资产比例】我现在的投资，占我可以投资的资产（不包括房屋）百份比是：', 5, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024005, 3005, '【Weighting Of This Portfolio in Total Investable Assets】What percentage of your investable assets (not including home) does this investment represent:', 5, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第6题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024006, 1006, '【未來入息增長-作為財富累積速度的指標】你預期你的個人收入在未來五年增幅會如何?', 6, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024006, 2006, '【未来入息增长-作为财富累积速度的指标】你预期你的个人收入在未来五年增幅会如何?', 6, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024006, 3006, '【Future Income Growth - An Indication on Speed of Wealth Accumulation】How do you expect your income will grow over the next 5 years?', 6, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第7题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024007, 1007, '【備用錢】閣下有應急備用錢嗎?', 7, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024007, 2007, '【备用钱】阁下有应急备用钱吗?', 7, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024007, 3007, '【Emergency Savings】Do you have emergency savings?', 7, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第8题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024008, 1008, '【風險態度的量度】對作出投資決定是否感到舒適?', 8, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024008, 2008, '【风险态度的量度】对作出投资决定是否感到舒适?', 8, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024008, 3008, '【Measuring Risk Attitude】Comfort On Making Investment Decisions?', 8, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第9题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024009, 1009, '【投資目標】你的投資目標是甚麼?', 9, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024009, 2009, '【投资目标】你的投资目标是甚么?', 9, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024009, 3009, '【Investment Objective】What is your investment objective?', 9, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第10题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024010, 1010, '【投資經驗】你曾經投資股票或股票基金嗎?', 10, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024010, 2010, '【投资经验】你曾经投资股票或股票基金吗?', 10, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024010, 3010, '【Investment Experience】Have you ever invested in individual stocks or stock mutual funds?', 10, 'en', 4, 2, 1, 0, NOW(), NOW());


-- 第11题（3种语言）
INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (12024011, 1011, '【對於投資虧損的反應】閣下可接受投資組合最多的虧損程度是多少?', 11, 'zh-han', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (22024011, 2011, '【对于投资亏损的反应】阁下可接受投资组合最多的亏损程度是多少?', 11, 'zh-hans', 4, 2, 1, 0, NOW(), NOW());

INSERT INTO `zero_cust`.`acct_risk_question` (`id`, `question_id`, `question`, `sort`, `lang`, `question_type`, `opt_type`, `check_type`, `flag`, `create_time`, `update_time`)
VALUES (32024011, 3011, '【Reaction To Investment Loss】What is the highest level of investment loss in your business’ portfolio that your business can tolerate?', 11, 'en', 4, 2, 1, 0, NOW(), NOW());
